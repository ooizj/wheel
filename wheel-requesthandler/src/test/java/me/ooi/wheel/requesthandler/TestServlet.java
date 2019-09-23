package me.ooi.wheel.requesthandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class TestServlet extends javax.servlet.http.HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("ffffff");
		System.out.println("req.getContextPath()="+req.getContextPath());
		System.out.println("req.getQueryString()="+req.getQueryString());
		System.out.println("req.getRequestURI()="+req.getRequestURI());
		System.out.println("req.getRequestURL()="+req.getRequestURL());
		resp.getWriter().println("hello");
	}

}
