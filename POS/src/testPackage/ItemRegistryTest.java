package testPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.kth.iv1500.POS.DTOs.*;
import se.kth.iv1500.POS.dbHandler.*;
import se.kth.iv1500.POS.model.exceptions.DatabaseException;
import se.kth.iv1500.POS.model.exceptions.ItemNotFoundException;

class ItemRegistryTest {

	@Test
	void testFindItem() throws Exception {
		String breadIdentifier = "123654789";
		ItemRegistry instance = new ItemRegistry();
		ItemDTO returnedObject = instance.findItem(breadIdentifier);
		String expRes = breadIdentifier;
		String result = returnedObject.getItemIdentifier();
		assertEquals(expRes, result, "Available item was not found");
	}

	@Test
	void testFindItemWithWrongId() throws Exception {

		Assertions.assertThrows(ItemNotFoundException.class, () -> {
			String wrongId = "someWrongId";
			ItemRegistry instance = new ItemRegistry();
			ItemDTO expRes = null;
			ItemDTO result = instance.findItem(wrongId);
			assertEquals(expRes, result, "An item with wrong identification was found");

		});
	}

	@Test
	public void shouldCastExceptionWhenDBConnectionFails() {

		assertThrows(DatabaseException.class, () -> {
			String wrongId = "000000000";
			ItemRegistry instance = new ItemRegistry();
			ItemDTO result = instance.findItem(wrongId);
		});
	}
}
