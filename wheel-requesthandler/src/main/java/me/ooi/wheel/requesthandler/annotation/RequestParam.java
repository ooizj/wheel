package me.ooi.wheel.requesthandler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注HTTP请求参数名
 * @author jun.zhao
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.PARAMETER)
public @interface RequestParam {
	
	/**
	 * request parameter name
	 */
	String value() ; 

}
