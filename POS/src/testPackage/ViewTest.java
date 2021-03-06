package testPackage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1500.POS.controller.Controller;
import se.kth.iv1500.POS.dbHandler.ExternalSystemGenerator;
import se.kth.iv1500.POS.dbHandler.RegistryCreator;
import se.kth.iv1500.POS.model.CashRegister;
import se.kth.iv1500.POS.view.View;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ViewTest {
    private View instance;
    private ByteArrayOutputStream printOut;
    private PrintStream originalSysOut;

    @BeforeEach
    void setUp() {
        RegistryCreator regCreator = new RegistryCreator();
        ExternalSystemGenerator extSys = new ExternalSystemGenerator();
        CashRegister cashRegister = new CashRegister();
        Controller cont = new Controller(regCreator, extSys, cashRegister);
        instance = new View(cont);

        printOut = new ByteArrayOutputStream();
        PrintStream inMemSysOut = new PrintStream(printOut);
        originalSysOut = System.out;
        System.setOut(inMemSysOut);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalSysOut);
        printOut = null;
    }


    @Test
    void testRunFakeSale() throws Exception {
        instance.runFakeSale();
        String result = printOut.toString();
        String expRes = "started";
        assertTrue(result.contains(expRes), "Wrong print out after calling startNewSale");
    }

    @Test
    void testRunFakeSaleAddItem() throws Exception {
        instance.runFakeSale();
        String result = printOut.toString();
        String expRes = "item";
        assertTrue(result.contains(expRes), "Wrong print out after calling addItem()");
    }

}
