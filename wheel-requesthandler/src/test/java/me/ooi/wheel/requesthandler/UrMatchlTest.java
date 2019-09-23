package me.ooi.wheel.requesthandler;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import me.ooi.wheel.requesthandler.annotation.method.GET;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class UrMatchlTest {
	
	@Test
	public void t1(){
		
		// /testHandler/{id}/
		// /testHandler/1/
		
		String url = "/testHandler/{id}/" ; 
		Pattern p = Pattern.compile("/[^/]{1,}") ; 
		
		long st = System.currentTimeMillis() ; 
		Matcher m = p.matcher(url) ; 
		while( m.find() ){
			System.out.println("math: "+m.group(0));
		}
		System.out.println(System.currentTimeMillis()-st);
	}
	
	@Test
	public void t2(){
		String url = "/testHandler/{id}/" ; 
		
		long st = System.currentTimeMillis() ; 
		String[] paths = url.split("/") ; 
		System.out.println(paths.length);
		for (String path : paths) {
			System.out.println("path: "+path);
		}
		System.out.println(System.currentTimeMillis()-st);
	}
	
	@Test
	public void t3(){
		System.out.println(RequestHandlerMappingUtils.isRequestPathMatchRequestHandlerMappingPath("/a/", "/a/"));
		System.out.println(RequestHandlerMappingUtils.isRequestPathMatchRequestHandlerMappingPath("/a/b/", "/a/b/c/"));
		System.out.println(RequestHandlerMappingUtils.isRequestPathMatchRequestHandlerMappingPath("/a/b/", "/a/c/"));
		System.out.println(RequestHandlerMappingUtils.isRequestPathMatchRequestHandlerMappingPath("/a/b/", "/a/{id}/"));
		System.out.println(RequestHandlerMappingUtils.isRequestPathMatchRequestHandlerMappingPath("/a/b/", "/a/"));
		System.out.println(RequestHandlerMappingUtils.isRequestPathMatchRequestHandlerMappingPath("/a/b/", "/a/{id}/c"));
	}
	
	@Test
	public void t4(){
		Method[] methods = TestRequestHandler.class.getDeclaredMethods() ; 
		for (Method method : methods) {
			GET annotation = method.getAnnotation(GET.class) ;
			if( annotation != null ){
				System.out.println(method);
				String path = annotation.value() ; 
				System.out.println(RequestHandlerMappingUtils.validateRequestHandlerPathParam("/testHandler"+path, method));
			}
		}
	}
	

}
