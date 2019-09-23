package me.ooi.wheel.requesthandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.ooi.wheel.requesthandler.annotation.PathParam;
import me.ooi.wheel.util.ClassUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class RequestHandlerMappingUtils {
	
	/**
	 * 请求路径正则。<br>
	 *   如：<br>
	 *     /user/1/		<br>
	 *     /user/{id}/	<br>
	 */
	private static final Pattern REQUEST_PATH_PATTERN = Pattern.compile("/[^/]{1,}") ; 
	
	/**
	 * 将请求路径按“/”分隔为数组
	 * @param pathStr
	 * @return
	 */
	private static String[] paths(String pathStr){
		List<String> paths = new ArrayList<String>() ; 
		Matcher matcher = REQUEST_PATH_PATTERN.matcher(pathStr) ; 
		while( matcher.find() ){
			String path = matcher.group(0) ; 
			paths.add(path.substring(1)) ;  //sub first char “/”
		}
		return paths.toArray(new String[paths.size()]) ; 
	}
	
	/**
	 * 获取分隔后的请求路径中的参数名。如：“{id}/”中的“id”
	 * @param path 分隔后的请求路径，格式为：<code>{参数名称}</code>
	 */
	private static String getPathParamName(String path) {
		return path.substring(1, path.length()-1).trim() ; 
	}
	
	/**
	 * 获取RequestHandler方法的匹配路径中的参数列表
	 * @param mappingPathStr
	 */
	public static List<String> getRequestHandlerMappingPathParamNames(String mappingPathStr) {
		List<String> ret = new ArrayList<String>() ; 
		if( mappingPathStr == null ){
			return ret ; 
		}
		
		String[] mappingPaths = paths(mappingPathStr) ; 
		for (int i = 0; i < mappingPaths.length; i++) {
			String mappingPath = mappingPaths[i] ; 
			if( mappingPath.startsWith("{") && mappingPath.endsWith("}") && 
					getPathParamName(mappingPath).length() > 0 ){
				ret.add(getPathParamName(mappingPath)) ; 
			}
		}
		
		return ret ; 
	}
	
	/**
	 * 校验RequestHandler方法的匹配路径
	 * @param mappingPathStr
	 * @return
	 */
	public static boolean validateRequestHandlerMappingPath(String mappingPathStr) {
		if( mappingPathStr == null ){
			return false ; 
		}
		
		String[] mappingPaths = paths(mappingPathStr) ; 
		for (int i = 0; i < mappingPaths.length; i++) {
			String mappingPath = mappingPaths[i] ; 
			
			//不可为空
			if( mappingPath == null || mappingPath.trim().length() == 0 ){
				return false ; 
			}
			
			//如果是参数。如：“{id}”
			if( mappingPath.startsWith("{") && mappingPath.endsWith("}") && 
					getPathParamName(mappingPath).length() < 1 ){
				return false ; 
			}
		}
		
		return true ; 
	}
	
	/**
	 * 检查RequestHandler方法参数中的<code>PathParam</code>注解是否正确
	 * @param mappingPathStr
	 * @param method
	 * @return
	 */
	public static boolean validateRequestHandlerPathParam(String mappingPathStr, Method method){
		
		//RequestHandler方法的匹配路径中的所有参数名
		List<String> mappingPathParamNames = getRequestHandlerMappingPathParamNames(mappingPathStr) ; 
		
		//方法参数中所有PathParam注解的参数名
		Set<String> methodParameterPathParamNames = new HashSet<String>() ; 
		List<PathParam> pathParmaAnnotations = ClassUtils.getParameterAnnotations(method, PathParam.class) ; 
		for (PathParam pathParam : pathParmaAnnotations) {
			methodParameterPathParamNames.add(pathParam.value()) ; 
		}
		
		//在RequestHandler方法的匹配路径中有的参数名也应该包含在方法参数中所有PathParam注解中的参数名中
		for (String mappingPathParamName : mappingPathParamNames) {
			if( !methodParameterPathParamNames.contains(mappingPathParamName) ){
				return false ; 
			}
		}
		return true ; 
	}
	
	/**
	 * 检查用户请求的路径和RequestHandler方法的匹配路径是否匹配
	 * @param requestPathStr
	 * @param requestHandlerMappingPathStr
	 * @return
	 */
	public static boolean isRequestPathMatchRequestHandlerMappingPath(String requestPathStr, String requestHandlerMappingPathStr){
		if( requestPathStr.equals(requestHandlerMappingPathStr) ){
			return true ; 
		}
		
		String[] requestHandlerMappingPaths = paths(requestHandlerMappingPathStr) ; 
		String[] requestPaths = paths(requestPathStr) ; 
		
		if( requestHandlerMappingPaths.length != requestPaths.length ){
			return false ; 
		}
		
		for (int i = 0; i < requestHandlerMappingPaths.length; i++) {
			
			String requestHandlerMappingPath = requestHandlerMappingPaths[i] ; 
			
			//path parameter不用和对应的请求路径相同
			if( requestHandlerMappingPath.startsWith("{") && requestHandlerMappingPath.endsWith("}") ){
				continue ; 
			}
			
			if( !requestHandlerMappingPath.equals(requestPaths[i]) ){
				return false ; 
			}
		}
		
		return true ; 
	}
	
	/**
	 * 获取 path parameter
	 */
	public static Map<String, String> getPathParams(String requestPathStr, String requestHandlerMappingPathStr){
		Map<String, String> ret = new HashMap<String, String>() ; 
		String[] requestHandlerMappingPaths = paths(requestHandlerMappingPathStr) ; 
		String[] requestPaths = paths(requestPathStr) ; 
		for (int i = 0; i < requestHandlerMappingPaths.length; i++) {
			String requestHandlerMappingPath = requestHandlerMappingPaths[i] ; 
			String requestPath = requestPaths[i] ; 
			if( requestHandlerMappingPath.startsWith("{") && requestHandlerMappingPath.endsWith("}") ){
				ret.put(getPathParamName(requestHandlerMappingPath), requestPath) ; 
			}
		}
		return ret ; 
	}
	
}
