package me.ooi.wheel.query;

import java.math.BigDecimal;
import java.math.BigInteger;
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
		/*
		CREATE TABLE `mysqlalltype` (
		  `id` int(11) NOT NULL,
		  `testInt` int(11) DEFAULT NULL,
		  `testVarchar_` varchar(100) DEFAULT NULL,
		  `testDecimal` decimal(9,2) DEFAULT NULL,
		  `testDatetime` datetime DEFAULT NULL,
		  `testBlob` blob,
		  `testBinary` binary(255) DEFAULT NULL,
		  `testBlob_` blob,
		  `testLongBlob` longblob,
		  `testMediumBlob` mediumblob,
		  `testTinyBlob` tinyblob,
		  `testVarBinary_` varbinary(1000) DEFAULT NULL,
		  `testDate` date DEFAULT NULL,
		  `testTime` time DEFAULT NULL,
		  `testTimestamp_` timestamp(6) NULL DEFAULT NULL,
		  `testYear_` year(4) DEFAULT NULL,
		  `testGeometry` geometry DEFAULT NULL,
		  `testGeometrycollection` geometrycollection DEFAULT NULL,
		  `testLinestring` linestring DEFAULT NULL,
		  `testMultiLinestring` multilinestring DEFAULT NULL,
		  `testMultiPoint` multipoint DEFAULT NULL,
		  `testMutiPolygon` multipolygon DEFAULT NULL,
		  `testPoint` point DEFAULT NULL,
		  `testPolygon` polygon DEFAULT NULL,
		  `testBigint_` bigint(20) DEFAULT NULL,
		  `testDouble` double DEFAULT NULL,
		  `testFloat` float DEFAULT NULL,
		  `testInt_` int(11) DEFAULT NULL,
		  `testMediumInt_` mediumint(11) DEFAULT NULL,
		  `testReal` double DEFAULT NULL,
		  `testSmallInt_` smallint(11) DEFAULT NULL,
		  `testTinyInt_` tinyint(11) DEFAULT NULL,
		  `testChar_` char(10) DEFAULT NULL,
		  `testJson` json DEFAULT NULL,
		  `testNchar_` char(10) DEFAULT NULL,
		  `testNvarchar_` varchar(1000) DEFAULT NULL,
		  `testLongText` longtext,
		  `testMediumText` mediumtext,
		  `testText_` text,
		  `testTinyText` tinytext,
		  `testBit_` bit(10) DEFAULT NULL,
		  `testEnum_` enum('A','B') DEFAULT NULL,
		  `testSet_` set('A','B') DEFAULT NULL,
		  PRIMARY KEY (`id`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8;

		 */
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
	public void t1_1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("alter table mysqlalltype add column(`UNSIGNED_testSmallInt_` smallint(11) UNSIGNED )") ; 
		System.out.println(updatedRowCount);
	}
	
	@Test
	public void testInt1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testInt = ? where id = 1", 100) ; 
		System.out.println(updatedRowCount);
		
		List<Integer> list2 = query.select("select testInt from mysqlalltype ", Integer.class) ; 
		System.out.println("Integer\t"+list2);
		List<Integer> list1 = query.select("select testInt from mysqlalltype ", int.class) ; 
		System.out.println("int\t"+list1);
		
		List<Long> list3 = query.select("select testInt from mysqlalltype ", Long.class) ; 
		System.out.println("Long\t"+list3);
		List<Long> list4 = query.select("select testInt from mysqlalltype ", long.class) ; 
		System.out.println("long\t"+list4);
		
		List<BigDecimal> list5 = query.select("select testInt from mysqlalltype ", BigDecimal.class) ; 
		System.out.println("BigDecimal\t"+list5);
		
		List<BigInteger> list6 = query.select("select testInt from mysqlalltype ", BigInteger.class) ; 
		System.out.println("BigInteger\t"+list6);
	}
	
	@Test
	public void testOutOfRange() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount2 = query.update("update mysqlalltype set UNSIGNED_testSmallInt_ = ? where id = 1", 65535) ; 
		System.out.println(updatedRowCount2);
		
		List<Short> list9 = query.select("select UNSIGNED_testSmallInt_ from mysqlalltype ", Short.class) ; 
		System.out.println("Unsigned Short\t"+list9);
	}
	
	@Test
	public void testSmallInt1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testSmallInt_ = ? where id = 1", 100) ; 
		System.out.println(updatedRowCount);
		
		List<Short> list7 = query.select("select testSmallInt_ from mysqlalltype ", Short.class) ; 
		System.out.println("Short\t"+list7);
		List<Short> list8 = query.select("select testSmallInt_ from mysqlalltype ", short.class) ; 
		System.out.println("short\t"+list8);
		
		List<Integer> list2 = query.select("select testSmallInt_ from mysqlalltype ", Integer.class) ; 
		System.out.println("Integer\t"+list2);
		List<Integer> list1 = query.select("select testSmallInt_ from mysqlalltype ", int.class) ; 
		System.out.println("int\t"+list1);
		
		List<Long> list3 = query.select("select testSmallInt_ from mysqlalltype ", Long.class) ; 
		System.out.println("Long\t"+list3);
		List<Long> list4 = query.select("select testSmallInt_ from mysqlalltype ", long.class) ; 
		System.out.println("long\t"+list4);
		
		List<BigDecimal> list5 = query.select("select testSmallInt_ from mysqlalltype ", BigDecimal.class) ; 
		System.out.println("BigDecimal\t"+list5);
		
		List<BigInteger> list6 = query.select("select testSmallInt_ from mysqlalltype ", BigInteger.class) ; 
		System.out.println("BigInteger\t"+list6);
	}
	
	@Test
	public void testBigInt1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testBigint_ = ? where id = 1", 9223372036854775807L) ; 
		System.out.println(updatedRowCount);
		
//		List<Integer> list2 = query.select("select testBigint_ from mysqlalltype ", Integer.class) ; 
//		System.out.println("Integer\t"+list2);
//		List<Integer> list1 = query.select("select testBigint_ from mysqlalltype ", int.class) ; 
//		System.out.println("int\t"+list1);
		
		List<Long> list3 = query.select("select testBigint_ from mysqlalltype ", Long.class) ; 
		System.out.println("Long\t"+list3);
		List<Long> list4 = query.select("select testBigint_ from mysqlalltype ", long.class) ; 
		System.out.println("long\t"+list4);
		
		List<BigDecimal> list5 = query.select("select testBigint_ from mysqlalltype ", BigDecimal.class) ; 
		System.out.println("BigDecimal\t"+list5);
		
		List<BigInteger> list6 = query.select("select testBigint_ from mysqlalltype ", BigInteger.class) ; 
		System.out.println("BigInteger\t"+list6);
	}
	
}