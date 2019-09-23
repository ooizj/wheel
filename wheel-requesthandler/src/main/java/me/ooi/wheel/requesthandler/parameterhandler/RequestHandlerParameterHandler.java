package me.ooi.wheel.requesthandler.parameterhandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ooi.wheel.requesthandler.MethodParameter;

/**
 * @author jun.zhao
 * @since 1.0
 */
public interface RequestHandlerParameterHandler {
	
	boolean canHandle(MethodParameter methodParameter) ; 
	
	void handleParameter(Object[] arguments, MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) ; 

}
