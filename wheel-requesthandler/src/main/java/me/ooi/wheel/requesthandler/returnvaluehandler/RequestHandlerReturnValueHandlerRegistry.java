package me.ooi.wheel.requesthandler.returnvaluehandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class RequestHandlerReturnValueHandlerRegistry {

	public static final RequestHandlerReturnValueHandlerRegistry INSTANCE = new RequestHandlerReturnValueHandlerRegistry() ; 
	
	private List<RequestHandlerReturnValueHandler> returnValueHandlers = new ArrayList<RequestHandlerReturnValueHandler>() ; 
	
	private RequestHandlerReturnValueHandlerRegistry(){
		registry(new PageReturnValueHandler());
		registry(new JsonReturnValueHandler());
	}
	
	public List<RequestHandlerReturnValueHandler> getReturnValueHandlers() {
		return returnValueHandlers;
	}

	private void registry(RequestHandlerReturnValueHandler returnValueHandler){
		if( !returnValueHandlers.contains(returnValueHandler) ){
			returnValueHandlers.add(returnValueHandler) ; 
		}
	}
}
