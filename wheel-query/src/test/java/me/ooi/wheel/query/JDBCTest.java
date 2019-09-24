package me.ooi.wheel.query;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import me.ooi.wheel.query.jdbc.ConnectionHolder;
import me.ooi.wheel.query.jdbc.JDBCQuery;
import me.ooi.wheel.util.ClassUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class JDBCTest {
	
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/mytest?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useOldAliasMetadataBehavior=true", "root", "root");
		return conn ; 
	}
	
	private Connection getH2Connection() throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection(
				"jdbc:h2:mem:mytest;DB_CLOSE_DELAY=-1");
		return conn ; 
	}
	
	@Test
	public void t1() throws ClassNotFoundException, SQLException{
		Connection con = getConnection() ; 
		PreparedStatement ps = con.prepareStatement("select * from user") ; 
		ResultSet rs = ps.executeQuery() ;
		ResultSetMetaData metaData = rs.getMetaData() ; 
		System.out.println(metaData);
		int columnCount = metaData.getColumnCount() ; 
		while( rs.next() ){
			for (int i = 1; i <= columnCount; i++) {
				Object obj = rs.getObject(i) ;
				System.out.println(metaData.getColumnName(i) +"\t"+ obj);
			}
			System.out.println("--------------------------------");
		}
		rs.close();
		ps.close();
		con.close();
	}
	
	@Test
	public void t1_2() throws ClassNotFoundException, SQLException{
		Connection con = getConnection() ; 
		PreparedStatement ps = con.prepareStatement("select * from user where name = ? ") ; 
		ps.setString(1, "小明");
		ResultSet rs = ps.executeQuery() ;
		ResultSetMetaData metaData = rs.getMetaData() ; 
//		System.out.println(metaData);
		int columnCount = metaData.getColumnCount() ; 
		while( rs.next() ){
			for (int i = 1; i <= columnCount; i++) {
				Object obj = rs.getObject(i) ;
				System.out.println(metaData.getColumnName(i) +"\t"+ obj);
			}
			System.out.println("--------------------------------");
		}
		rs.close();
		ps.close();
		con.close();
	}
	
	@Test
	public void t2(){
		System.out.println(Integer.class.isPrimitive());
		System.out.println(int.class.isPrimitive());
	}
	
	@Test
	public void t3(){
		String s = new String() ; 
		System.out.println(String.class.isInstance(s));
		System.out.println(Object.class.isInstance(s));
	}

	private Set<String> findClass(File dir, String packageName){
		Set<String> classes = new HashSet<String>() ; 
		File[] files = dir.listFiles() ;
		for (File file : files) {
			if( file.isDirectory() ){
				classes.addAll(findClass(file, packageName+"."+file.getName())) ; 
			}else {
				String fileName = file.getName() ; 
				if( fileName.endsWith(".class") ){
					classes.add(packageName+"."+fileName.substring(0, fileName.length()-6)) ; 
				}
			}
		}
		return classes ; 
	}
	
	@Test
	public void t4() throws IOException{
		String packageName = "me.ooi.testjar" ; 
		ClassLoader cl = Thread.currentThread().getContextClassLoader() ;

        String packagePath = packageName.replace(".", "/") ;
        Enumeration<URL> urls = cl.getResources(packagePath) ;
        while( urls.hasMoreElements() ){
        	URL url = urls.nextElement() ; 
        	Set<String> classes = findClass(new File(url.getFile()), packageName) ; 
        	for (String clazz : classes) {
        		System.out.println(ClassUtils.getClass(clazz));
			}
        	System.out.println("=====================");
        	
        }
	}
	
	public static enum EnumABC{
		A, B, C
	}
	
	@Test
	public void t5(){
		EnumABC e = Enum.valueOf(EnumABC.class.asSubclass(Enum.class), "A") ;
		System.out.println(e == EnumABC.A);
		
		EnumABC e2 = Enum.valueOf(EnumABC.class, "A") ;
		System.out.println(e2 == EnumABC.A);
	}
	
	@Test
	public void t6(){
		
//		Enum  e = String2EnumConverter.INSTANCE.convert("A", EnumABC.class) ;
//		System.out.println(e == EnumABC.A);
	}
	
	@Test
	public void t7(){
		System.out.println(TimeUnit.MILLISECONDS.toSeconds(1000));
		
		System.out.println(ClassUtils.getPropertyDescriptors(String.class)[0]);
		
		
	}
	
	@Test
	public void t8() throws ClassNotFoundException, SQLException{
		JDBCQuery query = new JDBCQuery() ; 
		Connection con = getConnection() ; 
		
		List<User2> userList = query.select(con, "select * from user", User2.class, new Object[]{}) ; 
		System.out.println(userList);
		
		List<User2> userList2 = query.select(con, "select * from user where id = ? ", User2.class, 1) ; 
		System.out.println(userList2);
		
		List<User2> userList3 = query.select(con, "select * from user where name = ? ", User2.class, "小红") ; 
		System.out.println(userList3);
		
		con.close();
	}

	@Test
	public void t9() throws ClassNotFoundException, SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		Connection con = getConnection() ; 
		connectionHolder.setConnection(con); 
		
		List<User2> userList3 = query.select("select * from user where name = ? ", User2.class, "小红") ; 
		System.out.println(userList3);
		
		int updatedRowCount = query.update("update user set name = ? where name = ? ", "小红2", "小红") ; 
		System.out.println(updatedRowCount);
		
		List<User2> userList4 = query.select("select * from user where name = ? ", User2.class, "小红2") ; 
		System.out.println(userList4);
		
		
		con.close();
	}
	
	private static int getRangeRandom(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	@Test
	public void t10(){
		for (int i = 0; i < 10; i++) {
			System.out.println(getRangeRandom(3,8));
		}
	}
	
	@Test
	public void t11() throws ClassNotFoundException, SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		Connection con = getConnection() ; 
		connectionHolder.setConnection(con); 
		
		List<User2> userList3 = query.select("select * from user where name = ? ", User2.class, "小明") ; 
		System.out.println(userList3);
		
		con.close();
	}
	
	public void createTestData() throws ClassNotFoundException, SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		Connection con = getH2Connection() ; 
		connectionHolder.setConnection(con); 
		
		query.update("create table user( "
				+ " id int primary key AUTO_INCREMENT, "
				+ " name varchar(50), "
				+ " age int, "
				+ " bothday timestamp)", new Object[]{}) ; 
		
		query.update("insert into user(name, age, bothday) "
				+ " values('小明', 10, '2019-07-26') ", new Object[]{}) ; 
		
		query.update("insert into user(name, age, bothday) "
				+ " values('小明', null, '2019-07-26') ", new Object[]{}) ; 
		
		con.close();
	}
	
	@Test
	public void t12() throws ClassNotFoundException, SQLException{
		createTestData();
		
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		Connection con = getH2Connection() ; 
		connectionHolder.setConnection(con); 
		
		List<User2> userList3 = query.select("select * from user where name = ? ", User2.class, "小明") ; 
		System.out.println(userList3);
		
		List<String> names = query.select("select name from user where name = ? ", String.class, "小明") ; 
		System.out.println(names);
		
		con.close();
	}
	
}
