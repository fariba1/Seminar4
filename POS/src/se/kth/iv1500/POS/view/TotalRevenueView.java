package se.kth.iv1500.POS.view;

import se.kth.iv1500.POS.model.Amount;

public class TotalRevenueView {

    private RevenueObserver observers = null;

    int totalAmountPaid = 0;

    public int getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void addTotalAmountPaid(Amount change, Amount amountPaid) {
        this.totalAmountPaid = this.totalAmountPaid + (amountPaid.getAmount() - change.getAmount());
        notifyRevenueObserver();
    }

    public void attach(RevenueObserver observer){
        observers = observer;
    }

    public void notifyRevenueObserver(){
        observers.update();

    }


}
