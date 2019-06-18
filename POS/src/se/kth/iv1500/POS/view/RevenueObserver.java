package se.kth.iv1500.POS.view;

import se.kth.iv1500.POS.model.Amount;

public interface RevenueObserver {
    void addAdditionalAmountPaid(Amount amount);
}
