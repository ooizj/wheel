package me.ooi.wheel.context;
/**
 * @author jun.zhao
 * @since 1.0
 */
public interface BeanFactory {
	
	Object getBean(String name) ; 
	
	<T> T getBean(Class<T> type) ; 

}
