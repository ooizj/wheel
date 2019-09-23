package me.ooi.wheel.requesthandler;
/**
 * @author jun.zhao
 * @since 1.0
 */
public class RequestHandlerMappingParseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RequestHandlerMappingParseException(String message) {
		super(message) ; 
	}
	
	public RequestHandlerMappingParseException(String message, Throwable cause) {
		super(message, cause) ; 
	}
	
	public RequestHandlerMappingParseException(Throwable cause) {
		super(cause) ; 
	}

}
