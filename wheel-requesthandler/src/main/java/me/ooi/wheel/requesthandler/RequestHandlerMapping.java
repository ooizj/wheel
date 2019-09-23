package me.ooi.wheel.requesthandler;

import java.lang.reflect.Method;

import lombok.Data;

/**
 * “HTTP请求” -> “处理HTTP请求的方法” 映射关系配置
 * @author jun.zhao
 * @since 1.0
 */
@Data
public class RequestHandlerMapping {
	
	@Data
	public static class Request{
		
		//HTTP method
		private String method ; 
		
		//请求路径
		private String path ; 
	}
	
	@Data
	public static class Handler{
		
		//服务端设置的响应类型
		private String[] produces ; 
		
		//是否将返回值作为响应内容
		private Boolean isResponseBody ; 
		
		//处理请求的对象
		private Object object ;
		
		//处理请求的方法
		private Method method ;
		
		//处理请求的方法的参数
		private MethodParameter[] methodParameters ; 
	}
	
	private Request request ; 
	private Handler handler ; 

}
