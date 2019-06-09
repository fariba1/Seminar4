package se.kth.iv1500.POS.model;

import se.kth.iv1500.POS.view.RevenueObserver;

public class TotalAmountPaid {
    private Amount amount;
    private RevenueObserver observers = null;



    public TotalAmountPaid(){
        amount = new Amount(0,"kr");
        observers = new RevenueObserver(this);
    }

    public void notifyObservers() {
        observers.update();

    }

    public void attach(RevenueObserver observer) {
        observers = observer;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
