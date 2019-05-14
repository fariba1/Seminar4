package se.kth.iv1500.POS.model;

public class AmountBuilder {


    int amount;
    String currency;

    public AmountBuilder withAmount(int amount) {
        this.amount = amount;
        return this;

    }

    public AmountBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Amount build() {
        Amount ret = new Amount(amount, currency);
        return ret;
    }

}
