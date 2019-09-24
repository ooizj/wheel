package me.ooi.wheel.query.jdbc;

import java.sql.Connection;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class ConnectionHolder {
	
	private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>() ; 
	
	public void setConnection(Connection connection){
		threadLocal.set(connection) ; 
	}
	
	public Connection getConnection(){
		return threadLocal.get() ; 
	}

}
