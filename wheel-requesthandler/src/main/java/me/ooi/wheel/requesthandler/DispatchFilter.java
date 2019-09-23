package me.ooi.wheel.requesthandler;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import me.ooi.wheel.requesthandler.annotation.RequestHandler;
import me.ooi.wheel.requesthandler.parameterhandler.RequestHandlerParameterHandler;
import me.ooi.wheel.requesthandler.parameterhandler.RequestHandlerParameterHandlerRegistry;
import me.ooi.wheel.requesthandler.returnvaluehandler.RequestHandlerReturnValueHandler;
import me.ooi.wheel.requesthandler.returnvaluehandler.RequestHandlerReturnValueHandlerRegistry;
import me.ooi.wheel.util.ScanUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class DispatchFilter implements Filter {
	
	public static final Logger LOG = Logger.getLogger(DispatchFilter.class) ; 
	
	public static final String MEDIA_TYPE_PAGE = "text/html" ; 

	//映射关系
	private List<RequestHandlerMapping> requestHandlerMappings = new ArrayList<RequestHandlerMapping>() ;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		//扫描所有的RequestHandler
		String scanPackage = filterConfig.getInitParameter("scanPackage") ; 
		Set<Class<?>> requestHandlerClasses = ScanUtils.findClass(scanPackage, RequestHandler.class) ;
		if( LOG.isInfoEnabled() ){
			LOG.info("scan all requestHandler: "+requestHandlerClasses);
		}
		
		//加载请求与处理方法的映射关系
		for (Class<?> requestHandlerClass : requestHandlerClasses) {
			RequestHandlerMappingDocument doc = new RequestHandlerMappingDocument() ; 
			new AnnotationRequestHandlerMappingProvider(requestHandlerClass).init(doc) ; 
			requestHandlerMappings.addAll(doc.getRequestHandlerMappings()) ; 
		}
		if( LOG.isInfoEnabled() ){
			for (RequestHandlerMapping requestHandlerMapping : requestHandlerMappings) {
				LOG.info("foud RequestHandlerMapping: "+requestHandlerMapping.getRequest().getMethod()+" "+requestHandlerMapping.getRequest().getPath()+" -> "+
						requestHandlerMapping.getHandler().getMethod());
			}
		}
		
	}
	
	private boolean isMethodMatch(String method, RequestHandlerMapping mapping){
		return method.equals(mapping.getRequest().getMethod()) ; 
	}
	
	private boolean isURIMatch(String requestURI, RequestHandlerMapping mapping){
		return RequestHandlerMappingUtils.isRequestPathMatchRequestHandlerMappingPath(requestURI, mapping.getRequest().getPath()) ; 
	}
	
	//根据用户请求的URI获取RequestMapping
	private RequestHandlerMapping getRequestMapping(String method, String requestURI){
		for (RequestHandlerMapping requestMapping : requestHandlerMappings) {
			if( isMethodMatch(method, requestMapping) && isURIMatch(requestURI, requestMapping) ){
				return requestMapping ; 
			}
		}
		return null ;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) servletRequest ; 
		HttpServletResponse response = (HttpServletResponse) servletResponse ; 
		
		String method = request.getMethod() ; 
		String path = request.getRequestURI().substring(request.getContextPath().length()) ; 
		if( !path.endsWith("/") ){
			path += "/" ; 
		}
		if( LOG.isDebugEnabled() ){
			LOG.debug("user request is “"+(method+" "+path)+"”");
		}
		
		RequestHandlerMapping requestHandlerMapping = getRequestMapping(method, path) ; 
		if( requestHandlerMapping == null ){
			chain.doFilter(servletRequest, servletResponse);
		}else {
			if( LOG.isDebugEnabled() ){
				LOG.debug("foud requestHandler["+requestHandlerMapping.getHandler().getMethod()+"] for "+(method+" "+path));
			}
			
			//初始化path parameter
			PathParamHolder.INSTANCE.init(RequestHandlerMappingUtils.getPathParams(path, requestHandlerMapping.getRequest().getPath())); 
			
			//调用请求对应的RequestHandler的方法
			Object returnValue = invokeRequestHandler(requestHandlerMapping, request, response) ;
			
			//响应客户端请求
			boolean foudHandler = response(requestHandlerMapping, returnValue, request, response);
			if( !foudHandler ){
				chain.doFilter(servletRequest, servletResponse);
			}
		}
	}
	
	private Object invokeRequestHandler(RequestHandlerMapping mapping, HttpServletRequest request, HttpServletResponse response){
		Object handlerObject = mapping.getHandler().getObject() ; 
		
		//处理请求的方法
		Method handlerMethod = mapping.getHandler().getMethod() ; 
		
		//实参列表
		Object[] arguments = new Object[mapping.getHandler().getMethodParameters().length] ; 
		
		//根据用户请求设置的“Accept”和RequestHandler方法中设置的“produces”和“ResponseBody”来决定响应类型
		for (MethodParameter methodParameter : mapping.getHandler().getMethodParameters()) {
			for (RequestHandlerParameterHandler parameterHandler : RequestHandlerParameterHandlerRegistry.INSTANCE.getParameterHandlers()) {
				if( parameterHandler.canHandle(methodParameter) ){
					parameterHandler.handleParameter(arguments, methodParameter, request, response);
					break ; 
				}
			}
		}
		
		//invoke the method
		try {
			return handlerMethod.invoke(handlerObject, arguments) ;
		} catch (Exception e) {
			throw new InvocationRequestHandlerException(e) ; 
		} 
	}
	
	private boolean response(RequestHandlerMapping mapping, Object returnValue, HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException{
		if( returnValue == null ){
			return false ; 
		}
		
		//RequestHandler的方法声明的响应类型列表
		List<String> produces = Arrays.asList(mapping.getHandler().getProduces()) ;
		
		//客户端想要的类型列表
		List<String> mediaTypes = HttpAcceptTypeUtils.getAcceptTypes(request.getHeader("accept")) ; 
		
		//有声明响应类型，则取交集
		if( !produces.isEmpty() ){
			mediaTypes.retainAll(produces) ; 
			
			//交集为空，则取声明的响应类型的第一个
			if( mediaTypes.isEmpty() ){
				mediaTypes.add(produces.get(0)) ; 
			}
		}else {
			if( mapping.getHandler().getIsResponseBody() ){ //有RequestBody注解，则返回非页面
				mediaTypes.remove(MEDIA_TYPE_PAGE) ; 
			}else { //没有声明的produces（响应类型列表），也没有ResponseBody注解，则输出页面
				mediaTypes.clear();
				mediaTypes.add(MEDIA_TYPE_PAGE) ; 
			}
		}
		
		//响应客户端请求
		if( !mediaTypes.isEmpty() ){
			for (String mediaType : mediaTypes) {
				for (RequestHandlerReturnValueHandler returnValueHandler : RequestHandlerReturnValueHandlerRegistry.INSTANCE.getReturnValueHandlers()) {
					if( returnValueHandler.canAccept(mediaType) ){
						returnValueHandler.handleReturnValue(returnValue, request, response) ; 
						return true ; 
					}
				}
			}
		}
		
		return false ; 
	}

	@Override
	public void destroy() {
		
	}

}
