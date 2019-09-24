package me.ooi.wheel.context;
/**
 * @author jun.zhao
 * @since 1.0
 */
public class BeanCreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BeanCreationException(String message) {
		super(message) ; 
	}
	
	public BeanCreationException(String message, Throwable cause) {
		super(message, cause) ; 
	}
	
	public BeanCreationException(Throwable cause) {
		super(cause) ; 
	}

}
