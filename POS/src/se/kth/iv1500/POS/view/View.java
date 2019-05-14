package se.kth.iv1500.POS.view;

import se.kth.iv1500.POS.controller.*;
import se.kth.iv1500.POS.model.*;
import se.kth.iv1500.POS.model.exceptions.ItemAlreadyAddedException;
import se.kth.iv1500.POS.model.exceptions.ItemNotFoundException;
import se.kth.iv1500.POS.DTOs.*;

public class View {
    private Controller contr;
    private TotalRevenueView totalRevenueView = new TotalRevenueView();

    public View(Controller contr) {
        this.contr = contr;
        new RevenueObserver(totalRevenueView);
    }

    /**
     * a fake method which is used instead of real user interface
     *
     * @throws Exception
     */

    public void runFakeSale() {
        contr.startNewSale();
        System.out.println("New sale was started.");

        this.addItem("123654789555", 4);

        this.addItem("987654321", 4);

        this.addItem("987654321", 4);

        int value = contr.getItemSaleInfo().getRunningTotal().getAmount();

        String customerID = "ABC123";
        Amount priceAfterDiscount = contr.discountRequest(customerID);
        System.out.println("Discount has been added successfully Discount Amount is:" + priceAfterDiscount);
        final Amount amountPaid = new Amount(200, "kr");
        Amount change = contr.pay(amountPaid);

        System.out.println("The pay has been handled. Change is equal to " + change);

        totalRevenueView.addTotalAmountPaid(change, amountPaid);


        contr.startNewSale();
        System.out.println("New sale was started.");

        this.addItem("123654789555", 4);

        this.addItem("987654321", 4);

        this.addItem("987654321", 4);

        int value2 = contr.getItemSaleInfo().getRunningTotal().getAmount();

        String customerID2 = "ABC123";
        Amount priceAfterDiscount2 = contr.discountRequest(customerID2);
        System.out.println("Discount has been added successfully Discount Amount is:" + priceAfterDiscount2);
        final Amount amountPaid2 = new Amount(200, "kr");
        Amount change2 = contr.pay(amountPaid2);

        System.out.println("The pay has been handled. Change is equal to " + change2);

        totalRevenueView.addTotalAmountPaid(change2, amountPaid2);








    }

    private void addItem(String ItemIdentifier, int quantity) {
        try {
            contr.addItem(ItemIdentifier, quantity);
            System.out.println(ItemIdentifier + " was added successfully \n " + "running total is:"
                    + contr.getItemSaleInfo().getRunningTotal().getAmount());

        } catch (ItemNotFoundException e) {
            System.out.println("Item is not found.");

            System.out.println("\\nlog-------Begin----");
            e.printStackTrace();
            System.out.println("\nlog-------End------\n");

        } catch (ItemAlreadyAddedException e) {
            System.out.println("Item is already in the basket.");

            System.out.println("\\nlog-------Begin----");
            e.printStackTrace();
            System.out.println("\nlog-------End------\n");

        }
    }
}