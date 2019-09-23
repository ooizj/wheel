package me.ooi.wheel.requesthandler.parameterhandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ooi.wheel.requesthandler.MethodParameter;
import me.ooi.wheel.requesthandler.PathParamHolder;
import me.ooi.wheel.requesthandler.annotation.PathParam;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class PathParamParameterHandler implements RequestHandlerParameterHandler {

	@Override
	public boolean canHandle(MethodParameter methodParameter) {
		return methodParameter.getParameter().isAnnotationPresent(PathParam.class) ; 
	}

	@Override
	public void handleParameter(Object[] arguments, MethodParameter methodParameter, HttpServletRequest request, HttpServletResponse response) {
		PathParam pathParam = methodParameter.getParameter().getAnnotation(PathParam.class) ; 
		
//		Type paramType = parameter.getParameterizedType() ; 
		String value = PathParamHolder.INSTANCE.get(pathParam.value()) ; 
		//TODO String -> int/long/double/float/Integer/Long/Double/Float/String/...
		
		arguments[methodParameter.getIndex()] = value ; 
	}

}
