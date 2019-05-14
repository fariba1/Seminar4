package se.kth.iv1500.POS.model.exceptions;


/**
 *  
 * @author faribayazdani
 *
 * an exception class that representing problem with the database connectivity
 */
public class DatabaseException extends RuntimeException {
	
	/**
	 * constructor of the Database Exception class.
	 * 
	 * @param message  message to be send to the receiver of the exception.
	 */
	public DatabaseException(String message) {
		super(message);
	}
	

}
