package se.kth.iv1500.POS.DTOs;

import java.util.ArrayList;
import se.kth.iv1500.POS.model.Amount;
import java.util.List;


/**
 * this is a place holder for sale information 
 * @author Sadeq
 *
 */
public class SaleDTO {
	private Amount runningTotal;
	private List<ItemDTO> itemsInCurrentSale = new ArrayList<>();
	private Amount change;
	private Amount totalPriceAfterDiscount;
	
	/**
	 * creates a new instance representing a new sale; 
	 * @param  runningTotal     total price after scanning each item
	 * @param  itemInfo         an instance of itemDTO which is a place holder for information about an item      	  
	 */
	private SaleDTO(
			Amount runningTotal,
			List<ItemDTO> itemInfo,
			Amount change,
			Amount totalPriceAfterDiscount) {
		this.runningTotal = runningTotal;
		this.itemsInCurrentSale = itemInfo;
		this.change = change;
		this.totalPriceAfterDiscount = totalPriceAfterDiscount;
	}

	/**
	 * returns the running total 
	 *@return the running total after each sale  
	 */
	public Amount getRunningTotal() {
		return this.runningTotal;
	}
	
	public Amount getChange() {
		return this.change;
	}
	
	/**
	 * returns information about an item as an instance of ItemDTO
	 *@return the name of an item  
	 */
	public List<ItemDTO> getItemInfo() {
		return this.itemsInCurrentSale;
	}
	
	
	public Amount getTotalPriceAfterDiscount() {
		return this.totalPriceAfterDiscount;
	}


	public static class Builder {
		private Amount runningTotal;
		private List<ItemDTO> itemInfo;
		private Amount change;
		private Amount totalPriceAfterDiscount;

		public Builder setRunningTotal(Amount runningTotal) {
			this.runningTotal = runningTotal;
			return this;
		}

		public Builder setItemInfo(List<ItemDTO> itemInfo) {
			this.itemInfo = itemInfo;
			return this;
		}

		public Builder setChange(Amount change) {
			this.change = change;
			return this;
		}

		public Builder setTotalPriceAfterDiscount(Amount totalPriceAfterDiscount) {
			this.totalPriceAfterDiscount = totalPriceAfterDiscount;
			return this;
		}

		public SaleDTO createSaleDTO() {
			return new SaleDTO(runningTotal, itemInfo, change, totalPriceAfterDiscount);
		}
	}
}
