package se.kth.iv1500.POS.view;

public class RevenueObserver implements Observer {

    View view;

    public RevenueObserver(View view){
        this.view = view;
        this.view.attach(this);
    }

    @Override
    public void update() {
        System.out.println("----Total amount Paid in system: "+view.getTotalAmountPaid());
    }
}