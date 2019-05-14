package se.kth.iv1500.POS.model.exceptions;

/**
 * 
 * @author faribayazdani
 *
 *
 *	An exception that should be thrown when an item can not be faund in the Item registry.
 */

public class ItemNotFoundException  extends Exception{

	
	/**
	 * constructor of the ItemNotFoundException class.
	 * 
	 * @param message  message to be send to the receiver of the exception.
	 */
	public ItemNotFoundException(String message) {
		super(message);
	}
	
}
