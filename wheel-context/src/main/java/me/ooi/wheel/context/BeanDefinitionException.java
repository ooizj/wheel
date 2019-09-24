package me.ooi.wheel.context;
/**
 * @author jun.zhao
 * @since 1.0
 */
public class BeanDefinitionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BeanDefinitionException(String message) {
		super(message) ; 
	}
	
	public BeanDefinitionException(String message, Throwable cause) {
		super(message, cause) ; 
	}
	
	public BeanDefinitionException(Throwable cause) {
		super(cause) ; 
	}

}
