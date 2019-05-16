package testPackage;

import org.junit.jupiter.api.Test;
import se.kth.iv1500.POS.dbHandler.CustomerRegistry;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CustomerRegistryTest {

    @Test
    void testisEligibleRightCustomerID() {
        String customerID = "ABC123";
        CustomerRegistry instance = CustomerRegistry.getCustomerRegistry();
        boolean returnedObject = instance.isEligible(customerID);
        boolean expRes = true;
        boolean result = returnedObject;
        assertEquals(expRes, result, "Available customer was not found");
    }

    @Test
    void testisEligibleWrongCustomerID() {
        String customerID = "ABBBB3";
        CustomerRegistry instance = CustomerRegistry.getCustomerRegistry();
        boolean returnedObject = instance.isEligible(customerID);
        boolean expRes = false;
        boolean result = returnedObject;
        assertEquals(expRes, result, "Available customer was found");
    }

}
