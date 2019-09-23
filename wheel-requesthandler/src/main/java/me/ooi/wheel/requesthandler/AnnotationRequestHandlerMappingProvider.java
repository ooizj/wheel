package me.ooi.wheel.requesthandler;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.ooi.wheel.requesthandler.annotation.RequestHandler;
import me.ooi.wheel.requesthandler.annotation.ResponseBody;
import me.ooi.wheel.requesthandler.annotation.method.DELETE;
import me.ooi.wheel.requesthandler.annotation.method.GET;
import me.ooi.wheel.requesthandler.annotation.method.POST;
import me.ooi.wheel.requesthandler.annotation.method.PUT;
import me.ooi.wheel.util.ClassUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class AnnotationRequestHandlerMappingProvider implements RequestHandlerMappingDocumentProvider {
	
	@Data
	@AllArgsConstructor
	public static class RequestAnnotation{
		//http method
		private String method ;
		//请求路径
		private String path ;
		//RequestHandler方法设置的响应类型
		private String[] produces ; 
	}
	
	private Class<?> requestHandlerClass ; 
	public AnnotationRequestHandlerMappingProvider(Class<?> requestHandlerClass) {
		this.requestHandlerClass = requestHandlerClass;
	}

	@Override
	public void init(RequestHandlerMappingDocument document) {
		if( !requestHandlerClass.isAnnotationPresent(RequestHandler.class) ){
			throw new RequestHandlerMappingParseException("class["+requestHandlerClass.getName()+"] must have a 'RequestHandler' annotation!") ; 
		}
		
		//获取namespace
		RequestHandler requestHandlerAnnotation = requestHandlerClass.getAnnotation(RequestHandler.class) ; 
		String namespace = requestHandlerAnnotation.value() ; 
		if( namespace == null || "/".equals(namespace) ){
			namespace = "" ; 
		}
		
		//创建或获取“RequestHandler”
		Object requestHandler = RequestHandlerFactory.INSTANCE.getOrCreate(requestHandlerClass) ; 
		
		//创建RequestHandlerMappings
		if( document.getRequestHandlerMappings() == null ){
			document.setRequestHandlerMappings(new ArrayList<RequestHandlerMapping>());
		}
		document.getRequestHandlerMappings().addAll(getMethodsParse(requestHandler, namespace)) ; 
		document.getRequestHandlerMappings().addAll(postMethodsParse(requestHandler, namespace)) ; 
		document.getRequestHandlerMappings().addAll(putMethodsParse(requestHandler, namespace)) ; 
		document.getRequestHandlerMappings().addAll(deleteMethodsParse(requestHandler, namespace)) ; 
	}
	
	private List<RequestHandlerMapping> getMethodsParse(Object handlerObject, String namespace){
		List<RequestHandlerMapping> list = new ArrayList<RequestHandlerMapping>() ; 
		List<Method> methods = ClassUtils.getDeclaredMethodsByAnnotation(requestHandlerClass, GET.class) ; 
		for (Method method : methods) {
			GET annotation = method.getAnnotation(GET.class) ;
			String path = annotation.value() ; 
			String[] produces = annotation.produces() ; 
			list.add(methodParse(handlerObject, method, new RequestAnnotation("GET", namespace+path, produces))) ; 
		}
		return list ; 
	}
	
	private List<RequestHandlerMapping> postMethodsParse(Object handlerObject, String namespace){
		List<RequestHandlerMapping> list = new ArrayList<RequestHandlerMapping>() ; 
		List<Method> methods = ClassUtils.getDeclaredMethodsByAnnotation(requestHandlerClass, POST.class) ; 
		for (Method method : methods) {
			POST annotation = method.getAnnotation(POST.class) ;
			String path = annotation.value() ; 
			String[] produces = annotation.produces() ; 
			list.add(methodParse(handlerObject, method, new RequestAnnotation("POST", namespace+path, produces))) ; 
		}
		return list ; 
	}
	
	private List<RequestHandlerMapping> putMethodsParse(Object handlerObject, String namespace){
		List<RequestHandlerMapping> list = new ArrayList<RequestHandlerMapping>() ; 
		List<Method> methods = ClassUtils.getDeclaredMethodsByAnnotation(requestHandlerClass, PUT.class) ; 
		for (Method method : methods) {
			PUT annotation = method.getAnnotation(PUT.class) ;
			String path = annotation.value() ; 
			String[] produces = annotation.produces() ; 
			list.add(methodParse(handlerObject, method, new RequestAnnotation("PUT", namespace+path, produces))) ; 
		}
		return list ; 
	}
	
	private List<RequestHandlerMapping> deleteMethodsParse(Object handlerObject, String namespace){
		List<RequestHandlerMapping> list = new ArrayList<RequestHandlerMapping>() ; 
		List<Method> methods = ClassUtils.getDeclaredMethodsByAnnotation(requestHandlerClass, DELETE.class) ; 
		for (Method method : methods) {
			DELETE annotation = method.getAnnotation(DELETE.class) ;
			String path = annotation.value() ; 
			String[] produces = annotation.produces() ; 
			list.add(methodParse(handlerObject, method, new RequestAnnotation("DELETE", namespace+path, produces))) ; 
		}
		return list ; 
	}

	private RequestHandlerMapping methodParse(Object handlerObject, Method handlerMethod, RequestAnnotation requestAnnotation){
		
		String path = requestAnnotation.getPath() ; 
		if( !path.endsWith("/") ){
			path += "/" ; 
		}
		
		if( !RequestHandlerMappingUtils.validateRequestHandlerMappingPath(path) ){
			throw new RequestHandlerMappingParseException(requestHandlerClass.getName()+"."+handlerMethod.getName()+" path is invalid") ; 
		}
		
		//检查声明的URI中的参数是否正确
		if( !RequestHandlerMappingUtils.validateRequestHandlerPathParam(path, handlerMethod) ){
			throw new RequestHandlerMappingParseException(requestHandlerClass.getName()+"."+handlerMethod.getName()+" path param is invalid") ; 
		}
		
		RequestHandlerMappingBuilder builder = new RequestHandlerMappingBuilder() ; 
		builder.requestMethod(requestAnnotation.getMethod())
			.requestPath(path)
			.produces(requestAnnotation.getProduces())
			.isResponseBody(handlerMethod.isAnnotationPresent(ResponseBody.class))
			.handlerObject(handlerObject)
			.handlerMethod(handlerMethod) ; 
		
		//设置方法形参
		Parameter[] parameters = handlerMethod.getParameters() ; 
		MethodParameter[] handleMethodParameters = new MethodParameter[parameters.length] ; 
		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i] ; 
			MethodParameter mp = new MethodParameter() ; 
			mp.setIndex(i);
			mp.setParameter(parameter);
			mp.setParameterTpye(parameter.getParameterizedType());
			handleMethodParameters[i] = mp ; 
		}
		builder.handlerMethodParameters(handleMethodParameters);
		return builder.build() ; 
	}
	
}
