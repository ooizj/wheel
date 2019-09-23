package me.ooi.wheel.requesthandler.annotation.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注处理<code>HTTP DELETE method</code>的方法
 * @author jun.zhao
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface DELETE {

	/**
	 * mapping path
	 */
	String value() default "" ; 

	String[] produces() default {} ; 
	
}
