package me.ooi.wheel.requesthandler;

import java.util.HashMap;
import java.util.Map;

import me.ooi.wheel.util.ClassUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class RequestHandlerFactory {
	
	public static final RequestHandlerFactory INSTANCE = new RequestHandlerFactory() ; 
	private RequestHandlerFactory(){
	}
	
	private Map<Class<?>, Object> requestHandlers = new HashMap<Class<?>, Object>() ; 
	
	/**
	 * 创建或获取RequestHandler。
	 * 根据RequestHandler的Class来判断，有创建过则返回上次创建的；否则创建一个
	 * @param requestHandlerClass
	 * @return
	 */
	public Object getOrCreate(Class<?> requestHandlerClass) {
		if( requestHandlers.containsKey(requestHandlerClass) ){
			return requestHandlers.get(requestHandlerClass) ; 
		}else {
			Object requestHandler = create(requestHandlerClass) ; 
			requestHandlers.put(requestHandlerClass, requestHandler) ; 
			return requestHandler ; 
		}
	}
	
	private Object create(Class<?> requestHandlerClass) {
		return ClassUtils.newInstance(requestHandlerClass) ; 
	}

}
