package testPackage;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1500.POS.DTOs.ItemDTO;
import se.kth.iv1500.POS.DTOs.SaleDTO;
import se.kth.iv1500.POS.dbHandler.CustomerRegistry;
import se.kth.iv1500.POS.dbHandler.ExternalSystemGenerator;
import se.kth.iv1500.POS.dbHandler.Printer;
import se.kth.iv1500.POS.dbHandler.RegistryCreator;
import se.kth.iv1500.POS.model.Amount;
import se.kth.iv1500.POS.model.CashPayment;
import se.kth.iv1500.POS.model.Receipt;
import se.kth.iv1500.POS.model.Sale;
import se.kth.iv1500.POS.model.exceptions.ItemAlreadyAddedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.Assert.assertTrue;

public class PrinterTest {
    private ByteArrayOutputStream outContent;
    private PrintStream originalSysOut;

    @BeforeEach
    public void setUpStreams() {
        originalSysOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void cleanUpStreams() {
        outContent = null;
        System.setOut(originalSysOut);
    }

    @Test
    public void testCreateReceiptString() throws ItemAlreadyAddedException {
        Amount paidAmt = new Amount(5000, "kr");
        //Amount totalPrice = new Amount(1000, "kr");
        String customerID = "123ABC";
        RegistryCreator regSys = new RegistryCreator();
        CustomerRegistry inst = regSys.getCustomerRegistry();
        ItemDTO item = new ItemDTO("bread", new Amount(30, "kr"), 0.2, "123654789");
        int quantity = 4;
        item.setItemQuantity(quantity);
        Sale paidSale = new Sale();

        paidSale.countPayment(paidAmt);
        paidSale.countDiscount(customerID, inst);

        SaleDTO saleInfo = paidSale.addItem(item);
        List<ItemDTO> itemInfo = saleInfo.getItemInfo();

        CashPayment payment = new CashPayment(paidAmt);

        //Amount change = paidSale.countPayment(paidAmt);
        //paidSale.recordPayment(payment);

        Receipt receipt = new Receipt(saleInfo);
        ExternalSystemGenerator extSys = new ExternalSystemGenerator();
        Printer instance = extSys.getPrinter();
        instance.printReceipt(receipt);
        LocalDateTime saleTime = LocalDateTime.now();

        String expResult = "\n\nSale Receipt: " + "\nSale time: " + saleTime
                + "\nBought item: " + item.getName()
                + "\nPrice: " + item.getPrice()
                + "\nAmount paid: " + paidAmt + "\n\n\n";


        String result = receipt.createReceiptString();

//    assertTrue("Wrong printout.", result.contains(expResult));

        String ss =
                Integer.toString(saleTime.getYear());
        assertTrue("Wrong sale year.", result.contains(
                Integer.toString(saleTime.getYear())));
        assertTrue("Wrong sale month.", result.contains(
                Integer.toString(saleTime.getMonthValue())));
        assertTrue("Wrong sale day.", result.contains(
                Integer.toString(saleTime.getDayOfMonth())));
        assertTrue("Wrong sale hour.", result.contains(
                Integer.toString(saleTime.getHour())));
        assertTrue("Wrong sale minute.", result.contains(
                Integer.toString(saleTime.getMinute())));
    }
}