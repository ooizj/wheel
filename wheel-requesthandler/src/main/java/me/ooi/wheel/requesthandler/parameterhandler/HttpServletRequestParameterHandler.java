package me.ooi.wheel.requesthandler.parameterhandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ooi.wheel.requesthandler.MethodParameter;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class HttpServletRequestParameterHandler implements RequestHandlerParameterHandler {

	@Override
	public boolean canHandle(MethodParameter methodParameter) {
		Class<?> paramClass = (Class<?>) methodParameter.getParameter().getParameterizedType() ; 
		return HttpServletRequest.class.isAssignableFrom(paramClass) ; 
	}

	@Override
	public void handleParameter(Object[] arguments, MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
		int parameterIndex = methodParameter.getIndex() ; 
		arguments[parameterIndex] = request ; 
	}

}
