package testPackage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1500.POS.DTOs.ItemDTO;
import se.kth.iv1500.POS.DTOs.SaleDTO;
import se.kth.iv1500.POS.model.Amount;
import se.kth.iv1500.POS.model.Receipt;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReceiptTest {

    Receipt receipt;


    @BeforeEach
    public void before() {
        Amount amount = new Amount(11, "KR");
        List<ItemDTO> itemInfo = new ArrayList();
        Amount change = new Amount(1, "KR");
        Amount runningTotal = new Amount(100, "kr");

        SaleDTO saleDTO = new SaleDTO.Builder().setRunningTotal(amount).setItemInfo(itemInfo).setChange(change).setTotalPriceAfterDiscount(runningTotal).createSaleDTO();

        receipt = new Receipt(saleDTO);


    }

    @Test
    public void testCreateReceiptString() {

        String receiptString = receipt.createReceiptString();

        assertTrue(receiptString.startsWith("Sale Receipt"), "Sale dose not contain sale receipt");

        Assertions.assertTrue(
                receiptString.contains("Total price: 11 KR"));

        Assertions.assertTrue(
                receiptString.contains("Change: 1 KR"));

        Assertions.assertTrue(
                receiptString.contains("Sale time: "));

    }


}
