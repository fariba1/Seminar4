package testPackage;

import org.junit.jupiter.api.Test;
import se.kth.iv1500.POS.DTOs.ItemDTO;
import se.kth.iv1500.POS.DTOs.SaleDTO;
import se.kth.iv1500.POS.dbHandler.DiscountRules;
import se.kth.iv1500.POS.model.Amount;
import se.kth.iv1500.POS.model.AmountBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DiscountRulesTest {


    @Test
    void testDiscountRateMember() {
        Amount runningtotal = new AmountBuilder()
                .withAmount(200)
                .withCurrency("kr").build();
        Amount MILKPRICE = new Amount(10, "kr");
        double VAT10PERCENT = 0.1;
        String MILKITEMIDENTIFIER = "123456789";
        List<ItemDTO> itemlist = new ArrayList<>();
        itemlist.add(new ItemDTO("bread", MILKPRICE, VAT10PERCENT, MILKITEMIDENTIFIER));
        itemlist.add(new ItemDTO("bread", MILKPRICE, VAT10PERCENT, MILKITEMIDENTIFIER));
        itemlist.add(new ItemDTO("bread", MILKPRICE, VAT10PERCENT, MILKITEMIDENTIFIER));
        itemlist.add(new ItemDTO("bread", MILKPRICE, VAT10PERCENT, MILKITEMIDENTIFIER));
        itemlist.add(new ItemDTO("milk", MILKPRICE, VAT10PERCENT, MILKITEMIDENTIFIER));


        Amount change = new Amount(0, "kr");
        Amount totalPriceAfterDiscount = new Amount(0, "kr");

        SaleDTO saleinfo = new SaleDTO.Builder().setRunningTotal(runningtotal).setItemInfo(itemlist).setChange(change).setTotalPriceAfterDiscount(totalPriceAfterDiscount).createSaleDTO();
        DiscountRules instance = new DiscountRules();
        double returnedObject = instance.discountRateMember(saleinfo);
        double expRes = (0.1 + 0.2);
        double result = returnedObject;
        assertEquals(expRes, result, "Discount percentage that should be added is not added");
    }

    @Test
    void testDiscountRateNotMember() {
        Amount runningtotal = new Amount(200, "kr");
        Amount MILKPRICE = new Amount(10, "kr");
        double VAT10PERCENT = 0.1;
        String MILKITEMIDENTIFIER = "123456789";
        List<ItemDTO> itemlist = new ArrayList<>();
        itemlist.add(new ItemDTO("milk", MILKPRICE, VAT10PERCENT, MILKITEMIDENTIFIER));
        Amount change = new Amount(0, "kr");
        Amount totalPriceAfterDiscount = new Amount(0, "kr");
        SaleDTO saleinfo = new SaleDTO.Builder().setRunningTotal(runningtotal).setItemInfo(itemlist).setChange(change).setTotalPriceAfterDiscount(totalPriceAfterDiscount).createSaleDTO();
        DiscountRules instance = new DiscountRules();
        double returnedObject = instance.discountRateNotMember(saleinfo);
        double expRes = 0.05;
        double result = returnedObject;
        assertEquals(expRes, result, "Discount percentage that should be added is not added");
    }


}
