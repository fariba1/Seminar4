package se.kth.iv1500.POS.model.exceptions;

/**
 * 
 * @author faribayazdani
 *
 * Exception class that sbhould be thrown when an item already exists in the sale.
 */
public class ItemAlreadyAddedException extends Exception{
	
	
	/**
	 * constructor of the ItemAlreadyAddedException class.
	 * 
	 * @param message  message to be send to the receiver of the exception.
	 */
	public ItemAlreadyAddedException(String message) {
		super(message);
	}
	

}
