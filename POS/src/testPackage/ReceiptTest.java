package testPackage;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1500.POS.model.Amount;
import java.util.List;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

import se.kth.iv1500.POS.DTOs.ItemDTO;
import se.kth.iv1500.POS.DTOs.SaleDTO;
import se.kth.iv1500.POS.model.Receipt;

public class ReceiptTest {
	
	Receipt receipt;
	
	
	@BeforeEach
	public void before() {
		Amount amount = new Amount(11,"KR");
		List<ItemDTO> itemInfo = new ArrayList();
		Amount change = new Amount(1,"KR");
		Amount runningTotal = new Amount(100, "kr");
		
		SaleDTO saleDTO = new SaleDTO(amount,itemInfo,change, runningTotal);
		
		receipt = new Receipt(saleDTO);
		
		
	}
	
	@Test
	public void testCreateReceiptString() {
		
		String receiptString = receipt.createReceiptString();
		
		assertTrue(receiptString.startsWith("Sale Receipt"),"Sale dose not contain sale receipt");
				
		Assertions.assertTrue(
				receiptString.contains("Total price: 11 KR"));		
					
		Assertions.assertTrue(
				receiptString.contains("Change: 1 KR"));		
		
		Assertions.assertTrue(
				receiptString.contains("Sale time: "));
		
	}


}
