package me.ooi.wheel.requesthandler.returnvaluehandler;
/**
 * @author jun.zhao
 * @since 1.0
 */
public class PageNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PageNotExistException(String message) {
		super(message) ; 
	}
	
	public PageNotExistException(String message, Throwable cause) {
		super(message, cause) ; 
	}
	
	public PageNotExistException(Throwable cause) {
		super(cause) ; 
	}

}
