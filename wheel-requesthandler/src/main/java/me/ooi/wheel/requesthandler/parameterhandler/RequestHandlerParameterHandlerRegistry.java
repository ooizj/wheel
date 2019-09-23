package me.ooi.wheel.requesthandler.parameterhandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class RequestHandlerParameterHandlerRegistry {
	
	public static final RequestHandlerParameterHandlerRegistry INSTANCE = new RequestHandlerParameterHandlerRegistry() ; 
	
	private List<RequestHandlerParameterHandler> parameterHandlers = new ArrayList<RequestHandlerParameterHandler>() ; 
	
	private RequestHandlerParameterHandlerRegistry(){
		registry(new RequestParamParameterHandler());
		registry(new PathParamParameterHandler());
		registry(new HttpServletRequestParameterHandler());
		registry(new HttpServletResponsetParameterHandler());
	}
	
	
	public List<RequestHandlerParameterHandler> getParameterHandlers() {
		return parameterHandlers;
	}


	private void registry(RequestHandlerParameterHandler parameterHandler){
		if( !parameterHandlers.contains(parameterHandler) ){
			parameterHandlers.add(parameterHandler) ; 
		}
	}

}
