package me.ooi.wheel.requesthandler;
/**
 * @author jun.zhao
 * @since 1.0
 */
public class InvocationRequestHandlerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvocationRequestHandlerException(String message) {
		super(message) ; 
	}
	
	public InvocationRequestHandlerException(String message, Throwable cause) {
		super(message, cause) ; 
	}
	
	public InvocationRequestHandlerException(Throwable cause) {
		super(cause) ; 
	}

}
