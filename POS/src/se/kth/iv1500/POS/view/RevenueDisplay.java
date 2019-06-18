package se.kth.iv1500.POS.view;

import se.kth.iv1500.POS.model.Amount;

public class RevenueDisplay implements RevenueObserver {

    Amount totalAmountPaid = new Amount(0,"kr");

    public void printCurrentState() {
        System.out.println("----Total amount Paid in system: " + totalAmountPaid);
    }

    @Override
    public void addNewTotalAmount(Amount amount) {
        addToTotal(amount);
        printCurrentState();
    }

    private void addToTotal(Amount amount) {
        totalAmountPaid = new Amount(totalAmountPaid.getAmount() + amount.getAmount(),"kr");
    }
}