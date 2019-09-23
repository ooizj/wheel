package me.ooi.wheel.requesthandler;

import java.util.Map;

/**
 * hold path parameters
 * @author jun.zhao
 * @since 1.0
 */
public class PathParamHolder {
	
	public static final PathParamHolder INSTANCE = new PathParamHolder() ; 
	private PathParamHolder(){
	}
	
	private ThreadLocal<Map<String, String>> pathParamTL = new ThreadLocal<Map<String, String>>() ; 
	
	//初始化所有的path parameter
	public void init(Map<String, String> pathParamMap){
		pathParamTL.set(pathParamMap) ; 
	}
	
	//获取path parameter
	public String get(String key){ 
		Map<String, String> pathParamMap = pathParamTL.get() ; 
		return pathParamMap.get(key) ; 
	}

}
