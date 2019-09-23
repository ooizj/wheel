package me.ooi.wheel.requesthandler;

import java.lang.reflect.Method;

import me.ooi.wheel.requesthandler.RequestHandlerMapping.Handler;
import me.ooi.wheel.requesthandler.RequestHandlerMapping.Request;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class RequestHandlerMappingBuilder {
	
	private RequestHandlerMapping requestHandlerMapping ; 
	
	public RequestHandlerMappingBuilder() {
		requestHandlerMapping = new RequestHandlerMapping() ; 
	}
	
	public RequestHandlerMapping build(){
		return requestHandlerMapping ; 
	}
	
	private Request getOrCreateRequest(){
		if( requestHandlerMapping.getRequest() == null ){
			requestHandlerMapping.setRequest(new Request());
		}
		return requestHandlerMapping.getRequest() ; 
	}
	
	private Handler getOrCreateHandler(){
		if( requestHandlerMapping.getHandler() == null ){
			requestHandlerMapping.setHandler(new Handler());
		}
		return requestHandlerMapping.getHandler() ; 
	}
	
	public RequestHandlerMappingBuilder requestMethod(String httpMethod){
		getOrCreateRequest().setMethod(httpMethod) ; 
		return this ; 
	}
	
	public RequestHandlerMappingBuilder requestPath(String path){
		getOrCreateRequest().setPath(path) ; 
		return this ; 
	}
	
	public RequestHandlerMappingBuilder produces(String[] produces){
		getOrCreateHandler().setProduces(produces) ; 
		return this ; 
	}
	
	public RequestHandlerMappingBuilder isResponseBody(Boolean isResponseBody){
		getOrCreateHandler().setIsResponseBody(isResponseBody); ; 
		return this ; 
	}
	
	public RequestHandlerMappingBuilder handlerObject(Object object){
		getOrCreateHandler().setObject(object) ; 
		return this ; 
	}
	
	public RequestHandlerMappingBuilder handlerMethod(Method method){
		getOrCreateHandler().setMethod(method);
		return this ; 
	}
	
	public RequestHandlerMappingBuilder handlerMethodParameters(MethodParameter[] methodParameters){
		getOrCreateHandler().setMethodParameters(methodParameters);
		return this ; 
	}

}
