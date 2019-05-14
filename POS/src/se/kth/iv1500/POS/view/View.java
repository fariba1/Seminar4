package se.kth.iv1500.POS.view;

import se.kth.iv1500.POS.controller.*;
import se.kth.iv1500.POS.model.*;
import se.kth.iv1500.POS.model.exceptions.ItemAlreadyAddedException;
import se.kth.iv1500.POS.model.exceptions.ItemNotFoundException;
import se.kth.iv1500.POS.DTOs.*;

public class View {
	private Controller contr;

	public View(Controller contr) {
		this.contr = contr;
	}

	/**
	 * a fake method which is used instead of real user interface
	 * 
	 * @throws Exception
	 */

	public void runFakeSale() {
		contr.startNewSale();
		System.out.println("New sale was started.");

		this.addItem("123654789555", 4);

		this.addItem("987654321", 4);

		this.addItem("987654321", 4);

		int value = contr.getItemSaleInfo().getRunningTotal().getAmount();

		String customerID = "ABC123";
		Amount priceAfterDiscount = contr.discountRequest(customerID);
		System.out.println("Discount has been added successfully Discount Amount is:" + priceAfterDiscount);
		Amount change = contr.pay(new Amount(200, "kr"));
		System.out.println("The pay has been handled. Change is equal to " + change);
	}

	private void addItem(String ItemIdentifier, int quantity) {
		try {
			contr.addItem(ItemIdentifier, quantity);
			System.out.println(ItemIdentifier + " was added successfully \n " + "running total is:"
					+ contr.getItemSaleInfo().getRunningTotal().getAmount());
			
		} catch (ItemNotFoundException e) {
			System.out.println("Item is not found.");

			System.out.println("\\nlog-------Begin----");
			e.printStackTrace();
			System.out.println("\nlog-------End------\n");

		} catch (ItemAlreadyAddedException e) {
			System.out.println("Item is already in the basket.");

			System.out.println("\\nlog-------Begin----");
			e.printStackTrace();
			System.out.println("\nlog-------End------\n");

		}
	}
}