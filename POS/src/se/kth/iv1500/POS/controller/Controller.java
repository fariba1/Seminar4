/**
 *
 */
package se.kth.iv1500.POS.controller;

import se.kth.iv1500.POS.DTOs.ItemDTO;
import se.kth.iv1500.POS.DTOs.SaleDTO;
import se.kth.iv1500.POS.dbHandler.*;
import se.kth.iv1500.POS.model.Amount;
import se.kth.iv1500.POS.model.CashPayment;
import se.kth.iv1500.POS.model.CashRegister;
import se.kth.iv1500.POS.model.Sale;
import se.kth.iv1500.POS.model.exceptions.ItemAlreadyAddedException;
import se.kth.iv1500.POS.model.exceptions.ItemNotFoundException;
import se.kth.iv1500.POS.view.RevenueDisplay;
import se.kth.iv1500.POS.model.RevenueObserver;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Sale sale;
    private ExternalSystemGenerator extSys;
    private CashRegister cashRegister;
    private RegistryCreator regCreator;

    private List<RevenueObserver> observers = new ArrayList<>();


    /**
     * Creates an instance of Controller which connects all the calls from view to classes in model and
     * classes in integration layer
     */
    public Controller(RegistryCreator regCreator, ExternalSystemGenerator extSys, CashRegister cashRegister) {
        this.cashRegister = cashRegister;
        this.extSys = extSys;
        this.regCreator = regCreator;
    }

    /**
     * Starts a new sale by making an instance of Sale object.
     */
    public Sale startNewSale() {
        this.sale = new Sale();
        sale.addObservers(observers);
        return this.sale;
    }

    /**
     * adds an item to the current sale
     * @param itemIdentifier   the identification of an item
     * @param itemQuantity     the number of items
     * @return returns an object of type <code>SaleDTO</code>which contains information about the price of an item, VAT rate and running total
     * @throws ItemNotFoundException when the item is not registred.
     * @throws ItemAlreadyAddedException when the item is adready added to the basket
     */
    public SaleDTO addItem(String itemIdentifier, int itemQuantity) throws ItemNotFoundException, ItemAlreadyAddedException {

        ItemRegistry itemRegistry = regCreator.getItemRegistry();
        ItemDTO itemInfo = itemRegistry.findItem(itemIdentifier);
        if (itemInfo != null) {
            itemInfo.setItemQuantity(itemQuantity);
            this.sale.addItem(itemInfo);
        }
        return sale.getSaleDTO();
    }

    /**
     * Starts a discount request in current sale
     * @param CustomerID is a string that represent the Customer identification
     * @return returns the object Amount and contains the total price
     */
    public Amount discountRequest(String CustomerID) {
        CustomerRegistry customerRegistry = regCreator.getCustomerRegistry();
        Amount totalPriceAfterDiscount = sale.countDiscount(CustomerID, customerRegistry);
        return totalPriceAfterDiscount;
    }


    /**
     * Handles sale payment. Updates the cash register here
     * the payment was performed and records the payment.
     * Calculates change. Prints the receipt.
     * @param amtPaid the amount paid
     * @return change the amount of change for customer to recieve
     */
    public Amount pay(Amount amtPaid) {
        Amount change = sale.countPayment(amtPaid);
        CashPayment payment = new CashPayment(amtPaid);
        cashRegister.recordPayment(payment);
        Printer printer = extSys.getPrinter();
        sale.printReceipt(printer);
        return change;
    }

    public SaleDTO getItemSaleInfo() {
        return this.sale.getSaleDTO();
    }

    public void addRevenueObserver(RevenueDisplay revenueDisplay) {
        observers.add(revenueDisplay);
    }
}
