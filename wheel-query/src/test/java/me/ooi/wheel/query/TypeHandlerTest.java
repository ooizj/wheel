package me.ooi.wheel.query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import me.ooi.wheel.query.jdbc.ConnectionHolder;
import me.ooi.wheel.query.jdbc.JDBCQuery;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class TypeHandlerTest {
	
	private Connection conn ; 
	
	@Before	
	public void initConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/mytest?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useOldAliasMetadataBehavior=true", "root", "root");
	}
	
	@After
	public void destroyConnection() throws SQLException{
		conn.close();
	}
	
	@Test
	public void t1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("insert into mysqlalltype(id) values(1)") ; 
		System.out.println(updatedRowCount);
		
		List<Integer> userList3 = query.select("select testInt from mysqlalltype ", Integer.class) ; 
		System.out.println(userList3);
	}
	
	@Test
	public void t2() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testInt = ? where id = 1", 100) ; 
		System.out.println(updatedRowCount);
		
		List<Integer> userList3 = query.select("select testInt from mysqlalltype ", int.class) ; 
		System.out.println(userList3);
		
		userList3 = query.select("select testInt from mysqlalltype ", Integer.class) ; 
		System.out.println(userList3);
	}
	
}
