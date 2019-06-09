package se.kth.iv1500.POS.view;

import se.kth.iv1500.POS.model.TotalAmountPaid;

public class RevenueObserver implements Observer {

    TotalAmountPaid totalAmountPaid;

    public RevenueObserver(TotalAmountPaid totalAmountPaid){
        this.totalAmountPaid = totalAmountPaid;
        this.totalAmountPaid.attach(this);
    }

    @Override
    public void update() {
        System.out.println("----Total amount Paid in system: "+totalAmountPaid.getAmount().getAmount());
    }
}