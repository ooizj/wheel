package me.ooi.wheel.requesthandler.parameterhandler;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ooi.wheel.requesthandler.MethodParameter;
import me.ooi.wheel.requesthandler.annotation.RequestParam;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class RequestParamParameterHandler implements RequestHandlerParameterHandler {

	@Override
	public boolean canHandle(MethodParameter methodParameter) {
		return methodParameter.getParameter().isAnnotationPresent(RequestParam.class) ; 
	}

	@Override
	public void handleParameter(Object[] arguments, MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
		Parameter parameter = methodParameter.getParameter() ; 
		int parameterIndex = methodParameter.getIndex() ; 
		RequestParam requestParam = parameter.getAnnotation(RequestParam.class) ; 

		Type paramType = parameter.getParameterizedType() ; 
		if( paramType == String.class ){
			arguments[parameterIndex] = request.getParameter(requestParam.value()) ; 
		}else if( paramType == String[].class ){
			arguments[parameterIndex] = request.getParameterValues(requestParam.value()) ; 
		}else {
			//TODO String -> int/long/double/float/Integer/Long/Double/Float/String/...
			arguments[parameterIndex] = null ; 
		}
	}

}
