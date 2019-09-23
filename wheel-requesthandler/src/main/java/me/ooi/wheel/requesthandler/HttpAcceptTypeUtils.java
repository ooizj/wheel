package me.ooi.wheel.requesthandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class HttpAcceptTypeUtils {
	
	@Data
	@AllArgsConstructor 
	private static class AcceptHeader implements Comparable<AcceptHeader> {
		
		private String acceptType;
		
		//权重
		private float quality;

		@Override
		public int compareTo(AcceptHeader other) {
			float ret = other.getQuality() - this.getQuality();
			if (ret == 0) {
				return 0;
			} else {
				return ret > 0 ? 1 : -1;
			}
		}
	}
	
	/**
	 * HTTP头部的accept的权重部分正则表达式
	 */
	public static final Pattern ACCEPT_TYPE_QUALITY = Pattern.compile("^q=(1|1\\.[0]+|0\\.[0-9]{1,})$") ;
	
	/**
	 * 获取HttpHeader中所有的AcceptType，并按权重顺序排列（权重相同则按出现的先后顺序）
	 * @param acceptHeaderStr
	 * @return
	 */
	public static List<String> getAcceptTypes(String acceptHeaderStr){
		List<String> ret = new ArrayList<String>() ;
		List<AcceptHeader> acceptHeaders = new ArrayList<AcceptHeader>() ;
		
		String[] acceptStrs = acceptHeaderStr.split(",") ;  
		if( acceptStrs == null ){
			return ret; 
		}
		
		for (String acceptStr : acceptStrs) {
			String[] acceptAndQuality = acceptStr.trim().split(";") ; 
			
			String acceptType = acceptAndQuality[0].trim() ; 
			
			//权重默认为1
			float quality = 1F ; 
			
			//查询权重
			if( acceptAndQuality.length > 1 ){
				for (int i = 1; i < acceptAndQuality.length; i++) {
					String item = acceptAndQuality[i].trim() ; 
					Matcher m = ACCEPT_TYPE_QUALITY.matcher(item) ; 
					if( m.find() ){
						quality = Float.valueOf(m.group(1)) ; 
					}
				}
			}
			
			acceptHeaders.add(new AcceptHeader(acceptType, quality)) ; 
		}
		
		Collections.sort(acceptHeaders);
		
		for (AcceptHeader acceptHeader : acceptHeaders) {
			ret.add(acceptHeader.getAcceptType()) ;
		}
		return ret ; 
	}
	
//	/**
//	 * 获取合适的Accept
//	 * @param acceptHeaderStr
//	 * @param acceptTypes
//	 * @return
//	 */
//	public static String getSuitableAccept(String acceptHeaderStr, List<String> acceptTypes){
//		List<String> headerAcceptTypes = getAcceptTypes(acceptHeaderStr) ; 
//		//拿http头部的Accept与指定Accept对比，之所以这样比较是由于权重的问题
//		for (String headerAcceptType : headerAcceptTypes) { 
//			for (String acceptType : acceptTypes) {
//				if( headerAcceptType.equals(acceptType) ){
//					return acceptType ; 
//				}
//			}
//		}
//		return null ; 
//	}
//	
//	/**
//	 * 获取合适的Accept
//	 * @param acceptHeaderStr
//	 * @param acceptType
//	 * @return
//	 */
//	public static String getSuitableAccept(String acceptHeaderStr, String acceptType){
//		return getSuitableAccept(acceptHeaderStr, Collections.singletonList(acceptType)) ; 
//	}
	
}
