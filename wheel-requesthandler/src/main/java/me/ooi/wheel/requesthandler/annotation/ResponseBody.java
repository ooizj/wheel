package me.ooi.wheel.requesthandler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注处理HTTP请求的方法的返回值作为响应内容
 * @author jun.zhao
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value=ElementType.METHOD)
public @interface ResponseBody {

}
