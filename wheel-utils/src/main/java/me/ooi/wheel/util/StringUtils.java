package me.ooi.wheel.util;
/**
 * @author jun.zhao
 * @since 1.0
 */
public class StringUtils {
	
	public static boolean isEmpty(String str){
		return (str == null || str.length() == 0) ; 
	}
	
	public static String firstLowercase(String str){
		if( isEmpty(str) ){
			return str ; 
		}
		String first = String.valueOf(str.charAt(0)).toLowerCase() ;
		if( str.length() == 1 ){
			return first ;  
		}else {
			return first + str.substring(1) ;
		}
	}

}
