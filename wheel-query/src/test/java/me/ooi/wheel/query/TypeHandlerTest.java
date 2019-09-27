package me.ooi.wheel.query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		  
		  `UNSIGNED_testSmallInt_` smallint(11) UNSIGNED,
		  testBit2_ bit(1) DEFAULT NULL,
		  
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
		
//		int updatedRowCount = query.update("alter table mysqlalltype add column(`UNSIGNED_testSmallInt_` smallint(11) UNSIGNED )") ;
//		int updatedRowCount = query.update("alter table mysqlalltype add column(testBit2_ bit(1) DEFAULT NULL)") ;
		int updatedRowCount = query.update("alter table mysqlalltype add column(testNUMERIC2_ NUMERIC)") ;
		
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
		
		List<Long> list3 = query.select("select testBigint_ from mysqlalltype ", Long.class) ; 
		System.out.println("Long\t"+list3);
		List<Long> list4 = query.select("select testBigint_ from mysqlalltype ", long.class) ; 
		System.out.println("long\t"+list4);
		
		List<BigDecimal> list5 = query.select("select testBigint_ from mysqlalltype ", BigDecimal.class) ; 
		System.out.println("BigDecimal\t"+list5);
		
		List<BigInteger> list6 = query.select("select testBigint_ from mysqlalltype ", BigInteger.class) ; 
		System.out.println("BigInteger\t"+list6);
	}
	
	@Test
	public void testTinyInt1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testTinyInt_ = ? where id = 1", 127) ; 
		System.out.println(updatedRowCount);
		
		List<Byte> list9 = query.select("select testTinyInt_ from mysqlalltype ", Byte.class) ; 
		System.out.println("Byte\t"+list9);
		List<Byte> list10 = query.select("select testTinyInt_ from mysqlalltype ", byte.class) ; 
		System.out.println("byte\t"+list10);
		
		List<Short> list7 = query.select("select testTinyInt_ from mysqlalltype ", Short.class) ; 
		System.out.println("Short\t"+list7);
		List<Short> list8 = query.select("select testTinyInt_ from mysqlalltype ", short.class) ; 
		System.out.println("short\t"+list8);
		
		List<Integer> list2 = query.select("select testTinyInt_ from mysqlalltype ", Integer.class) ; 
		System.out.println("Integer\t"+list2);
		List<Integer> list1 = query.select("select testTinyInt_ from mysqlalltype ", int.class) ; 
		System.out.println("int\t"+list1);
		
		List<Long> list3 = query.select("select testTinyInt_ from mysqlalltype ", Long.class) ; 
		System.out.println("Long\t"+list3);
		List<Long> list4 = query.select("select testTinyInt_ from mysqlalltype ", long.class) ; 
		System.out.println("long\t"+list4);
		
		List<BigDecimal> list5 = query.select("select testTinyInt_ from mysqlalltype ", BigDecimal.class) ; 
		System.out.println("BigDecimal\t"+list5);
		
		List<BigInteger> list6 = query.select("select testTinyInt_ from mysqlalltype ", BigInteger.class) ; 
		System.out.println("BigInteger\t"+list6);
	}
	
	@Test
	public void testTinyInt2() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testTinyInt_ = ? where id = 1", 2) ; 
		System.out.println(updatedRowCount);
		
		List<Boolean> list11 = query.select("select testTinyInt_ from mysqlalltype ", Boolean.class) ; 
		System.out.println("Boolean\t"+list11.get(0).booleanValue());
	}
	
	
	@Test
	public void testBit2() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testBit2_ = ? where id = 1", 5) ; 
		System.out.println(updatedRowCount);
		
		List<Boolean> list11 = query.select("select testBit2_ from mysqlalltype ", Boolean.class) ; 
		System.out.println("Boolean\t"+list11.get(0).booleanValue());
		
	}
	
	@Test
	public void testBit1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testBit_ = ? where id = 1", 1) ; 
		System.out.println(updatedRowCount);
		
		List<Boolean> list11 = query.select("select testBit_ from mysqlalltype ", Boolean.class) ; 
		System.out.println("Boolean\t"+list11.get(0).booleanValue());
		
	}
	
	@Test
	public void testBigDecimal1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testDecimal = ? where id = 1", new BigDecimal(15.2)) ; 
		System.out.println(updatedRowCount);
		
		List<BigDecimal> list1 = query.select("select testDecimal from mysqlalltype ", BigDecimal.class) ; 
		System.out.println("BigDecimal\t"+list1);
		
	}
	
	@Test
	public void testF1oat1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testFloat = ? where id = 1", 15.2F) ; 
		System.out.println(updatedRowCount);
		
		List<Float> list1 = query.select("select testFloat from mysqlalltype ", Float.class) ; 
		System.out.println("Float\t"+list1);
	}
	
	
	@Test
	public void testDouble1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testDouble = ? where id = 1", 15.2D) ; 
		System.out.println(updatedRowCount);
		
		List<Double> list1 = query.select("select testDouble from mysqlalltype ", Double.class) ; 
		System.out.println("Double\t"+list1);
	}
	
	@Test
	public void testDate1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testDate = ? where id = 1", getTestErrorDate()) ; 
		System.out.println(updatedRowCount);
		
		List<Date> list1 = query.select("select testDate from mysqlalltype ", Date.class) ; 
		printDate(list1.get(0));
		
		List<java.sql.Date> list2 = query.select("select testDate from mysqlalltype ", java.sql.Date.class) ; 
		printDate(list2.get(0));
		
		List<java.sql.Timestamp> list3 = query.select("select testDate from mysqlalltype ", java.sql.Timestamp.class) ; 
		printDate(list3.get(0));
		
		List<java.sql.Time> list4 = query.select("select testDate from mysqlalltype ", java.sql.Time.class) ; 
		printDate(list4.get(0));
	}
	
	@Test
	public void testDatetime1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testDatetime = ? where id = 1", new Date()) ; 
		System.out.println(updatedRowCount);
		
		List<Date> list1 = query.select("select testDatetime from mysqlalltype ", Date.class) ; 
		printDate(list1.get(0));
		
		List<java.sql.Date> list2 = query.select("select testDatetime from mysqlalltype ", java.sql.Date.class) ; 
		printDate(list2.get(0));
		
		List<java.sql.Timestamp> list3 = query.select("select testDatetime from mysqlalltype ", java.sql.Timestamp.class) ; 
		printDate(list3.get(0));
		
		List<java.sql.Time> list4 = query.select("select testDatetime from mysqlalltype ", java.sql.Time.class) ; 
		printDate(list4.get(0));
	}
	
	
	@Test
	public void testTimestamp1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testTimestamp_ = ? where id = 1", new Date()) ; 
		System.out.println(updatedRowCount);
		
		List<Date> list1 = query.select("select testTimestamp_ from mysqlalltype ", Date.class) ; 
		printDate(list1.get(0));
		
		List<java.sql.Date> list2 = query.select("select testTimestamp_ from mysqlalltype ", java.sql.Date.class) ; 
		printDate(list2.get(0));
		
		List<java.sql.Timestamp> list3 = query.select("select testTimestamp_ from mysqlalltype ", java.sql.Timestamp.class) ; 
		printDate(list3.get(0));
		
		List<java.sql.Time> list4 = query.select("select testTimestamp_ from mysqlalltype ", java.sql.Time.class) ; 
		printDate(list4.get(0));
	}
	
	@Test
	public void testTime1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testTime = ? where id = 1", new Date()) ; 
		System.out.println(updatedRowCount);
		
		List<Date> list1 = query.select("select testTime from mysqlalltype ", Date.class) ; 
		printDate(list1.get(0));
		
		List<java.sql.Date> list2 = query.select("select testTime from mysqlalltype ", java.sql.Date.class) ; 
		printDate(list2.get(0));
		
		List<java.sql.Timestamp> list3 = query.select("select testTime from mysqlalltype ", java.sql.Timestamp.class) ; 
		printDate(list3.get(0));
		
		List<java.sql.Time> list4 = query.select("select testTime from mysqlalltype ", java.sql.Time.class) ; 
		printDate(list4.get(0));
	}
	
	@Test
	public void testYear1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testYear_ = ? where id = 1", 2019) ; 
		System.out.println(updatedRowCount);
		
		List<Date> list1 = query.select("select testYear_ from mysqlalltype ", Date.class) ; 
		printDate(list1.get(0));
		
		List<java.sql.Date> list2 = query.select("select testYear_ from mysqlalltype ", java.sql.Date.class) ; 
		printDate(list2.get(0));
		
		List<java.sql.Timestamp> list3 = query.select("select testYear_ from mysqlalltype ", java.sql.Timestamp.class) ; 
		printDate(list3.get(0));
		
		List<java.sql.Time> list4 = query.select("select testYear_ from mysqlalltype ", java.sql.Time.class) ; 
		printDate(list4.get(0));
	}
	
	@Test
	public void testChar1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testChar_ = ? where id = 1", "123方法") ; 
		System.out.println(updatedRowCount);
		
		List<String> list1 = query.select("select testChar_ from mysqlalltype ", String.class) ; 
		System.out.println("String\t"+list1);
	}
	
	@Test
	public void testVarchar1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testVarchar_ = ? where id = 1", "123方法1") ; 
		System.out.println(updatedRowCount);
		
		List<String> list1 = query.select("select testVarchar_ from mysqlalltype ", String.class) ; 
		System.out.println("String\t"+list1);
	}
	
	@Test
	public void testBinary1() throws SQLException{
		JDBCQuery query = new JDBCQuery() ;
		ConnectionHolder connectionHolder = new ConnectionHolder() ; 
		query.setConnectionHolder(connectionHolder);
		connectionHolder.setConnection(conn); 
		
		int updatedRowCount = query.update("update mysqlalltype set testBinary = ? where id = 1", "123方法2") ; 
		System.out.println(updatedRowCount);
		
		List<String> list1 = query.select("select testBinary from mysqlalltype ", String.class) ; 
		System.out.println("String\t"+list1);
	}
	
	
	
	private void printBytes(byte[] bytes){
		if( bytes == null ){
			System.out.println("bytes is null ");
		}else {
			String str = "" ; 
			for (byte b : bytes) {
				str += b + "\t" ; 
			}
			System.out.println("bytes is "+str);
		}
	}
	
	private Date getDate(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		try {
			return sdf.parse(dateStr) ;
		} catch (ParseException e) {
			e.printStackTrace();
			return null ; 
		} 
	}
	
	private Date getTestErrorDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		try {
			return sdf.parse("1000-01-01") ;
		} catch (ParseException e) {
			e.printStackTrace();
			return null ; 
		} 
	}
	
	private void printDate(Date date){
		if( date == null ){
			System.out.println("date is null");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		System.out.println("date is "+sdf.format(date));
	}
	
}
