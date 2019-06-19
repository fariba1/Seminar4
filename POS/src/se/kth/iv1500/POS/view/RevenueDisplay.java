package se.kth.iv1500.POS.view;

import se.kth.iv1500.POS.model.Amount;
import se.kth.iv1500.POS.model.RevenueObserver;

public class RevenueDisplay implements RevenueObserver {

    Amount totalAmountPaid = new Amount(0,"kr");

    public void printCurrentState() {
        System.out.println("----Total amount Paid in system: " + totalAmountPaid);
    }

    @Override
    public void addAdditionalAmountPaid(Amount amount) {
        addToTotal(amount);
        printCurrentState();
    }

    private void addToTotal(Amount amount) {
        totalAmountPaid = new Amount(totalAmountPaid.getAmount() + amount.getAmount(),"kr");
    }
}