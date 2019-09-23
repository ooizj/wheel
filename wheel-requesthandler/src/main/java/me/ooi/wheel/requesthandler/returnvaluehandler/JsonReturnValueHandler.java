package me.ooi.wheel.requesthandler.returnvaluehandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import me.ooi.wheel.requesthandler.ResponseUtils;
import me.ooi.wheel.util.GsonUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class JsonReturnValueHandler extends AbstractRequestHandlerReturnValueHandler {
	
	private Gson gson = GsonUtils.createGson() ; 
	
	public static final String MEDIA_TYPE = "application/json" ; 
	
	@Override
	public boolean canAccept(String mediaType) {
		return MEDIA_TYPE.equals(mediaType) ; 
	}

	@Override
	public void handleReturnValue(Object returnValue, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType(MEDIA_TYPE);
		response.setCharacterEncoding("utf-8");
		String jsonStr = gson.toJson(returnValue) ; 
		ResponseUtils.writeAll(response, jsonStr) ; 
	}

}
