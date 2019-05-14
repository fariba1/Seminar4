package se.kth.iv1500.POS.view;

public class RevenueObserver {

    TotalRevenueView totalRevenueView;

    public RevenueObserver(TotalRevenueView totalRevenueView){
        this.totalRevenueView = totalRevenueView;
        this.totalRevenueView.attach(this);
    }

    public void update() {
        System.out.println("----Total amount Paid in system: "+totalRevenueView.totalAmountPaid);
    }
}