package me.ooi.wheel.requesthandler;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

import lombok.Data;

/**
 * 方法参数信息
 * @author jun.zhao
 * @since 1.0
 */
@Data
public class MethodParameter {
	
	//参数下标
	private int index ;
	
	//参数
	private Parameter parameter ;
	
	//参数类型
	private Type parameterTpye ;  

}
