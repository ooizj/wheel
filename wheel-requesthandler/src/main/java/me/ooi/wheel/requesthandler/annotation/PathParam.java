package me.ooi.wheel.requesthandler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注HTTP请求地址中的参数名<br>
 * 参数格式：<code>{参数名}</code>。如：http://a.com/user/{id}
 * @author jun.zhao
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.PARAMETER)
public @interface PathParam {
	
	/**
	 * HTTP请求路径中的参数名
	 */
	String value() ; 

}
