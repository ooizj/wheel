package me.ooi.wheel.requesthandler.returnvaluehandler;
/**
 * @author jun.zhao
 * @since 1.0
 */
public class ReturnTypeMismatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ReturnTypeMismatchException(String message) {
		super(message) ; 
	}
	
	public ReturnTypeMismatchException(String message, Throwable cause) {
		super(message, cause) ; 
	}
	
	public ReturnTypeMismatchException(Throwable cause) {
		super(cause) ; 
	}

}
