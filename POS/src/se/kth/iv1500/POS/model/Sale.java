package se.kth.iv1500.POS.model;

import se.kth.iv1500.POS.DTOs.ItemDTO;
import se.kth.iv1500.POS.DTOs.SaleDTO;
import se.kth.iv1500.POS.dbHandler.CustomerRegistry;
import se.kth.iv1500.POS.dbHandler.DiscountRules;
import se.kth.iv1500.POS.dbHandler.Printer;
import se.kth.iv1500.POS.model.exceptions.ItemAlreadyAddedException;

import java.util.ArrayList;
import java.util.List;

public class Sale {
    private Amount runningTotal = new Amount(0, "kr");
    private List<ItemDTO> itemsInCurrentSale = new ArrayList<>();
    private Amount change;
    private Amount totalPriceAfterDiscount = new Amount(0, "kr");
    private SaleDTO saleInfo;

    private List<RevenueObserver> observers = new ArrayList<>();


    /**
     * Creates an instance of sale
     */
    public Sale() {

    }

    /**
     * adds an new item to the current sale, updates the running total including the VAT
     *
     * @param itemInfo an instance of <code>ItemDTO</code> that contains information about an item
     * @return an instance of <code>SaleDTO</code> that contains information about current sale
     * @throws ItemAlreadyAddedException when the item is adready added to the basket
     */
    public SaleDTO addItem(ItemDTO itemInfo) throws ItemAlreadyAddedException {

        if (isItemUnique(itemInfo)) {
            this.itemsInCurrentSale.add(itemInfo);
            int quantity = itemInfo.getItemQuantity();
            updateRunningTotal(itemInfo, quantity);
            saleInfo = new SaleDTO.Builder().setRunningTotal(this.runningTotal).setItemInfo(this.itemsInCurrentSale).setChange(this.change).setTotalPriceAfterDiscount(this.totalPriceAfterDiscount).createSaleDTO();
            return saleInfo;
        } else {
            throw new ItemAlreadyAddedException("Item " +
                    itemInfo.getItemIdentifier() + " already added.");
        }


    }

    private boolean isItemUnique(ItemDTO itemInfo) {
        for (ItemDTO itemDTO : itemsInCurrentSale) {
            if (itemDTO.getItemIdentifier().equals(itemInfo.getItemIdentifier())) {
                return false;
            }
        }
        return true;
    }

    private void updateRunningTotal(ItemDTO itemInfo, int quantity) {
        Amount priceAfterVat = this.countItemPriceIncludinVAT(itemInfo);
        int amountOfPriceAfterVat = priceAfterVat.getAmount();
        int amountToUpdateRunningTotal = quantity * amountOfPriceAfterVat;
        this.runningTotal.addAmount(amountToUpdateRunningTotal);
    }

    private Amount countItemPriceIncludinVAT(ItemDTO itemInfo) {
        Amount priceOfItem = itemInfo.getPrice();
        int amountOfPrice = priceOfItem.getAmount();
        double vatRate = itemInfo.getVateRate();
        double priceIncludingVAT = amountOfPrice + (amountOfPrice * vatRate);
        int roundedPriceAfterVat = (int) Math.round(priceIncludingVAT);
        return new Amount(roundedPriceAfterVat, "kr");
    }

    /**
     * Checks if the customer is a member or not and subtracts the amount discount from the total price
     *
     * @param customerID       A CustomerID as String that represent the customer identification
     * @param customerRegistry Is a database where Customers are saved
     * @return Amount Total price
     */
    public Amount countDiscount(String customerID, CustomerRegistry customerRegistry) {
        DiscountRules discountRules = new DiscountRules();
        Amount totalAmount = this.runningTotal;
        double countedDiscount = totalAmount.getAmount();
        if (customerRegistry.isEligible(customerID)) {
            countedDiscount = countedDiscount * (1 - discountRules.discountRateMember(this.saleInfo));
        } else {
            countedDiscount = countedDiscount * (1 - discountRules.discountRateNotMember(this.saleInfo));
        }
        int roundedPriceAfterDiscount = (int) Math.round(countedDiscount);
        this.totalPriceAfterDiscount.addAmount(roundedPriceAfterDiscount);
        return this.totalPriceAfterDiscount;
    }

    /**
     * calculates the change amount to return to a customer.
     * And creates an instance of <code>SaleDTO</code> that contains information about current sale,
     * including the change amount
     *
     * @param amountPaid by the customer
     * @return returns the change amount
     */
    public Amount countPayment(Amount amountPaid) {
        int amountInChange = amountPaid.amountSubtraction(this.totalPriceAfterDiscount);
        change = new Amount(amountInChange, "kr");
        saleInfo = new SaleDTO.Builder().setRunningTotal(this.runningTotal).setItemInfo(this.itemsInCurrentSale).setChange(this.change).setTotalPriceAfterDiscount(this.totalPriceAfterDiscount).createSaleDTO();
        return change;
    }

    public SaleDTO getSaleDTO() {
        return this.saleInfo;
    }

    /**
     * Prints a receipt for the current sale on the
     * specified printer.
     */
    public void printReceipt(Printer printer) {
        Receipt receipt = new Receipt(saleInfo);
        printer.printReceipt(receipt);
        notifyObservers();
    }

    private void notifyObservers() {
        for (RevenueObserver obs : observers) {
            obs.addAdditionalAmountPaid(totalPriceAfterDiscount);
        }
    }

    public void addObservers(List<RevenueObserver> revenueDisplays) {
        observers.addAll(revenueDisplays);
    }
}
	
