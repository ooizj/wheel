package me.ooi.wheel.requesthandler.returnvaluehandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jun.zhao
 * @since 1.0
 */
public interface RequestHandlerReturnValueHandler {
	
	boolean canAccept(String mediaType) ; 
	
	void handleReturnValue(Object returnValue, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException ; 

}
