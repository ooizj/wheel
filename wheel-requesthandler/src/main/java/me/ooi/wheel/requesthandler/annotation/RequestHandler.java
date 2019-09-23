package me.ooi.wheel.requesthandler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注处理HTTP请求的Class<br>
 * @author jun.zhao
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.TYPE)
public @interface RequestHandler {
	
	/**
	 * 命名空间，可为空。如：http://a.com/命名空间/1
	 */
	String value() default ""; 

}
