package me.ooi.wheel.requesthandler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class ResponseUtils {
	
	private ResponseUtils(){
	}

	/**
	 * 写出字符串内容并flush
	 * @param response
	 * @param content
	 * @throws IOException
	 */
	public static void writeAll(HttpServletResponse response, String content) throws IOException{
		if( content != null ){
			PrintWriter pw = null ; 
			try {
				pw = response.getWriter() ; 
				pw.write(content) ; 
			}  finally {
				if( pw != null ){
					pw.flush();
				}
			}
		}
	}
	
}
