package me.ooi.wheel.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class Test1 {
	
	@Test
	public void t1(){
		final NumberFormat RATE_FORMAT = new DecimalFormat("0.0%") ;
		System.out.println(RATE_FORMAT.format(0.01234));
	}
	
	@Test
	public void t2(){
		List<String> list = new ArrayList<String>() ; 
		list.add("1") ; 
		list.add("2") ; 
		String[] strs = list.toArray(new String[list.size()]) ;
		for (String string : strs) {
			System.out.println(string);
		}
	}
	
//	@Test
//	public void t3(){
//		Method[] methods = TestRequestHandler.class.getDeclaredMethods() ; 
//		for (Method method : methods) {
//			Annotation[][] annotationss = method.getParameterAnnotations() ; 
//			for (Annotation[] annotations : annotationss) {
//				for (Annotation annotation : annotations) {
//					if( annotation instanceof PathParam ){
//						System.out.println(((PathParam)annotation).value());
//					}
//				}
//			}
//		}
//	}
	
	@Test
	public void t4(){
		System.out.println(Integer.MAX_VALUE);
	}

//	@Test
//	public void t5(){
//		Method[] methods = TestRequestHandler.class.getDeclaredMethods() ; 
//		for (Method method : methods) {
//			System.out.println(method);
//			for (Parameter parameter : method.getParameters()) {
//				System.out.println(parameter.getParameterizedType());
//				if( parameter.isAnnotationPresent(PathParam.class) ){
//					System.out.println(parameter.getAnnotation(PathParam.class));
//				}
//				if( parameter.isAnnotationPresent(RequestParam.class) ){
//					System.out.println(parameter.getAnnotation(RequestParam.class));
//				}
//			}
//		}
//	}
	
	@Test
	public void t6(){
		System.out.println(Object.class.isAssignableFrom(String.class));
		System.out.println(String.class.isAssignableFrom(Object.class));
	}
	
	@Test
	public void t7(){
		Object[] objs = new Object[1] ;
		Object obj = "abc" ; 
		objs[0] = obj ; 
		obj = "11" ; 
		System.out.println(objs[0]);
	}
	
	@Test
	public void t8(){
		String str = "*/*;q=0.8" ; 
		String[] ary = str.split(";") ;
		for (String element : ary) {
			System.out.println(element);
		}
		System.out.println("====================");
		
		str = "*/*" ; 
		ary = str.split(";") ;
		for (String element : ary) {
			System.out.println(element);
		}
		System.out.println("====================");
	}
	
	@Test
	public void t9(){
		SortedMap<Float, String> map = new TreeMap<Float, String>() ; 
		map.put(1.0F, "v1.0") ; 
		map.put(2.0F, "v2.0_1") ; 
		map.put(2.0F, "v2.0_3") ; 
		map.put(2.0F, "v2.0_2") ; 
		map.put(0.5F, "v0.5") ; 
		map.put(0.01F, "v0.01") ; 
		System.out.println(map);
		System.out.println(map.lastKey());
		System.out.println(map.get(map.lastKey()));
	}
	
	@Data
	@AllArgsConstructor 
	private static class AcceptType implements Comparable<AcceptType> {
		
		private String acceptType;
		private float quality;

		@Override
		public int compareTo(AcceptType other) {
			float ret = other.getQuality() - this.getQuality();
			if (ret == 0) {
				return 0;
			} else {
				return ret > 0 ? 1 : -1;
			}
		}
	}
	
	
	@Test
	public void t10(){
		
		Pattern p = Pattern.compile("^q=(1|1\\.[0]+|0\\.[0-9]{1,})$") ;
		
		String acceptHeader = "application/xhtml+xml,text/html,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3" ; 
		
		String[] accepts = acceptHeader.split(",") ;  
		if( accepts == null ){
			return ; 
		}
		
		List<AcceptType> qualityAccepts = new ArrayList<AcceptType>() ;
		for (String accept : accepts) {
			String[] acceptAndQuality = accept.trim().split(";") ; 
			String acceptType = acceptAndQuality[0].trim() ; 
			float quality = 1F ; 
			
			//查询权重
			if( acceptAndQuality.length > 1 ){
				for (int i = 1; i < acceptAndQuality.length; i++) {
					String item = acceptAndQuality[i].trim() ; 
					Matcher m = p.matcher(item) ; 
					if( m.find() ){
						quality = Float.valueOf(m.group(1)) ; 
					}
				}
			}
			
			qualityAccepts.add(new AcceptType(acceptType, quality)) ; 
		}
		
		Collections.sort(qualityAccepts);
		
		System.out.println(qualityAccepts);
	}
	
	@Test
	public void t11(){
		List<String> keys = new ArrayList<String>() ; 
		Map<String, String> map = new HashMap<String, String>() ; 
		for (int i = 0; i < 10000; i++) {
			String id = UUID.randomUUID().toString() ;
			if( new Random().nextInt(100) == 50 ){
				keys.add(id) ; 
			}
			map.put(id , i+"");
		}
		
		long st = System.currentTimeMillis() ; 
		
		for (int i = 0; i < 10; i++) {
			System.out.println(map.get(keys.get(new Random().nextInt(keys.size()))));
		}
		
		long et = System.currentTimeMillis() ; 
		
		System.out.println((et-st)/10.0); 
		
	}
	
	@Test
	public void t12(){
		Pattern p = Pattern.compile("^q=(1|1\\.[0]+|0\\.[0-9]{1,})$") ;
		Matcher m = p.matcher("q=1") ; 
		System.out.println(m.find());
		
		m = p.matcher("q=1.0") ; 
		System.out.println(m.find());
		
		m = p.matcher("q=0.2") ; 
		System.out.println(m.find());
		
		m = p.matcher("q=3.2") ; 
		System.out.println(m.find());
		
		m = p.matcher("q=0.2234") ; 
		System.out.println(m.find());

		m = p.matcher("q=0.2a") ; 
		System.out.println(m.find());
		
		m = p.matcher("q=1.00") ; 
		System.out.println(m.find());
		
		m = p.matcher("iq=1.00") ; 
		System.out.println(m.find());
	}
	
	@Test
	public void t13(){
		Pattern p = Pattern.compile("^q=(1|1\\.[0]+|0\\.[0-9]{1,})$") ;
		Matcher m = p.matcher("q=1") ; 
		if( m.find() ){
			System.out.println(m.group(1));
		}
		
		m = p.matcher("q=1.01") ; 
		if( m.find() ){
			System.out.println(m.group(1));
		}
		
		m = p.matcher("q=0.32") ; 
		if( m.find() ){
			System.out.println(m.group(1));
		}
	}
	
	@Test
	public void t14(){
		List<String> list1 = new ArrayList<String>(Arrays.asList("1", "2", "3"))  ; 
		List<String> list2 = new ArrayList<String>(Arrays.asList("2", "4", "5")) ; 
		
		list1.retainAll(list2);
		System.out.println(list1);
		System.out.println(list2);
	}
	
}
