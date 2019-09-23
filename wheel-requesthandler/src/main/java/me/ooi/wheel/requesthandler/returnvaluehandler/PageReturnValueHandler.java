package me.ooi.wheel.requesthandler.returnvaluehandler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ooi.wheel.util.StringUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class PageReturnValueHandler extends AbstractRequestHandlerReturnValueHandler {

	public static final String MEDIA_TYPE = "text/html" ; 
	
	@Override
	public boolean canAccept(String mediaType) {
		return MEDIA_TYPE.equals(mediaType) ; 
	}

	@Override
	public void handleReturnValue(Object returnValue, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		if( !(returnValue instanceof String) ){
			throw new ReturnTypeMismatchException("the return value '"+returnValue+"' must be a string. ") ; 
		}
		
		String path = (String) returnValue ; 
		if( StringUtils.isEmpty(path) ){
			throw new PageNotExistException("page '"+path+"' does not exist !") ; 
		}
		
		response.setContentType(MEDIA_TYPE);
		RequestDispatcher rd = request.getRequestDispatcher(path) ;
		rd.forward(request, response) ;
	}

}
