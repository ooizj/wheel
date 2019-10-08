package me.ooi.wheel.query.jdbc.typehandler;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class TypeHandlerRegistry {
	
	public static final TypeHandlerRegistry INSTANCE = new TypeHandlerRegistry() ; 
	
	//Type -> { sqlType -> TypeHandler }
	private Map<Type, Map<Integer, TypeHandler<?>>> typeColumnHandlersMap = new HashMap<Type, Map<Integer, TypeHandler<?>>>() ; 
	
	private TypeHandlerRegistry(){
		registerNumeric();
		registerDate();
		registerString();
	}
	
	private void registerNumeric(){
		
		//BIT[(M)]
		//A bit-value type. M indicates the number of bits per value, from 1 to 64. The default is 1 if M is omitted.
		register(Boolean.class, Types.BIT, new BooleanTypeHandler()) ; //M must be 1; value = 0 then false, value = 1 then true, value = other then error
		
		//TINYINT[(M)] [UNSIGNED] [ZEROFILL]
		//A very small integer. The signed range is -128 to 127. The unsigned range is 0 to 255.
		register(Byte.class, Types.TINYINT, new ByteTypeHandler()) ; 
		register(Byte.TYPE, Types.TINYINT, new ByteTypeHandler()) ; 
		register(Short.class, Types.TINYINT, new ShortTypeHandler(Types.TINYINT)) ; 
		register(Short.TYPE, Types.TINYINT, new ShortTypeHandler(Types.TINYINT)) ; 
		register(Integer.class, Types.TINYINT, new IntegerTypeHandler(Types.TINYINT)) ; 
		register(Integer.TYPE, Types.TINYINT, new IntegerTypeHandler(Types.TINYINT)) ; 
		register(Long.class, Types.TINYINT, new LongTypeHandler(Types.TINYINT)) ; 
		register(Long.TYPE, Types.TINYINT, new LongTypeHandler(Types.TINYINT)) ; 
		register(BigDecimal.class, Types.TINYINT, new BigDecimalTypeHandler(Types.TINYINT)) ; 
		register(BigInteger.class, Types.TINYINT, new BigIntegerTypeHandler(Types.TINYINT)) ; 
		register(Boolean.class, Types.TINYINT, new BooleanTypeHandler()) ; //A value of zero is considered false. Nonzero values are considered true.
		
		//SMALLINT[(M)] [UNSIGNED] [ZEROFILL]
		//A small integer. The signed range is -32768 to 32767. The unsigned range is 0 to 65535.
		register(Short.class, Types.SMALLINT, new ShortTypeHandler(Types.SMALLINT)) ; 
		register(Short.TYPE, Types.SMALLINT, new ShortTypeHandler(Types.SMALLINT)) ; 
		register(Integer.class, Types.SMALLINT, new IntegerTypeHandler(Types.SMALLINT)) ; 
		register(Integer.TYPE, Types.SMALLINT, new IntegerTypeHandler(Types.SMALLINT)) ; 
		register(Long.class, Types.SMALLINT, new LongTypeHandler(Types.SMALLINT)) ; 
		register(Long.TYPE, Types.SMALLINT, new LongTypeHandler(Types.SMALLINT)) ; 
		register(BigDecimal.class, Types.SMALLINT, new BigDecimalTypeHandler(Types.SMALLINT)) ; 
		register(BigInteger.class, Types.SMALLINT, new BigIntegerTypeHandler(Types.SMALLINT)) ; 
		
		//INT[(M)] [UNSIGNED] [ZEROFILL]
		//A normal-size integer. The signed range is -2147483648 to 2147483647. The unsigned range is 0 to 4294967295.
		//INTEGER[(M)] [UNSIGNED] [ZEROFILL]
		//This type is a synonym for INT.
		register(Integer.class, Types.INTEGER, new IntegerTypeHandler(Types.INTEGER)) ; 
		register(Integer.TYPE, Types.INTEGER, new IntegerTypeHandler(Types.INTEGER)) ; 
		register(Long.class, Types.INTEGER, new LongTypeHandler(Types.INTEGER)) ; 
		register(Long.TYPE, Types.INTEGER, new LongTypeHandler(Types.INTEGER)) ; 
		register(BigDecimal.class, Types.INTEGER, new BigDecimalTypeHandler(Types.INTEGER)) ; 
		register(BigInteger.class, Types.INTEGER, new BigIntegerTypeHandler(Types.INTEGER)) ; 
		
		//BIGINT[(M)] [UNSIGNED] [ZEROFILL]
		//A large integer. The signed range is -9223372036854775808 to 9223372036854775807. The unsigned range is 0 to 18446744073709551615.
		register(Long.class, Types.BIGINT, new LongTypeHandler(Types.BIGINT)) ; 
		register(Long.TYPE, Types.BIGINT, new LongTypeHandler(Types.BIGINT)) ; 
		register(BigDecimal.class, Types.BIGINT, new BigDecimalTypeHandler(Types.BIGINT)) ; 
		register(BigInteger.class, Types.BIGINT, new BigIntegerTypeHandler(Types.BIGINT)) ; 
		
		
		//FLOAT[(M,D)] [UNSIGNED] [ZEROFILL]
		//A small (single-precision) floating-point number. Permissible values are -3.402823466E+38 to -1.175494351E-38, 0, and 1.175494351E-38 to 3.402823466E+38. These are the theoretical limits, based on the IEEE standard. The actual range might be slightly smaller depending on your hardware or operating system.
		register(Float.class, Types.FLOAT, new FloatTypeHandler()) ; 
		register(Float.TYPE, Types.FLOAT, new FloatTypeHandler()) ; 
		register(Float.class, Types.REAL, new FloatTypeHandler()) ; 
		register(Float.TYPE, Types.REAL, new FloatTypeHandler()) ; 
		
		//DOUBLE[(M,D)] [UNSIGNED] [ZEROFILL]
		//A normal-size (double-precision) floating-point number. Permissible values are -1.7976931348623157E+308 to -2.2250738585072014E-308, 0, and 2.2250738585072014E-308 to 1.7976931348623157E+308. These are the theoretical limits, based on the IEEE standard. The actual range might be slightly smaller depending on your hardware or operating system.
		register(Double.class, Types.DOUBLE, new DoubleTypeHandler(Types.DOUBLE)) ; 
		register(Double.TYPE, Types.DOUBLE, new DoubleTypeHandler(Types.DOUBLE)) ; 
		
		//DECIMAL[(M[,D])] [UNSIGNED] [ZEROFILL]
		register(BigDecimal.class, Types.NUMERIC, new BigDecimalTypeHandler(Types.NUMERIC)) ; 
		register(BigDecimal.class, Types.DECIMAL, new BigDecimalTypeHandler(Types.DECIMAL)) ; 
		
		
		//BOOL, BOOLEAN
		//These types are synonyms for TINYINT(1). A value of zero is considered false. Nonzero values are considered true.
		register(Boolean.class, Types.BOOLEAN, new BooleanTypeHandler()) ; 
		
		
		
		
		
		
	}
	
	private void registerDate(){
		//DATE
		//A date. The supported range is '1000-01-01' to '9999-12-31'. MySQL displays DATE values in 'YYYY-MM-DD' format, but permits assignment of values to DATE columns using either strings or numbers.
		register(java.util.Date.class, Types.DATE, new DateTypeHandler(Types.DATE)) ; 
		register(java.sql.Date.class, Types.DATE, new SqlDateTypeHandler(Types.DATE)) ; 
		register(java.sql.Timestamp.class, Types.DATE, new TimestampTypeHandler(Types.DATE)) ; 
		register(java.sql.Time.class, Types.DATE, new TimeTypeHandler(Types.DATE)) ; 
		
		//TIMESTAMP[(fsp)]
		//A timestamp. The range is '1970-01-01 00:00:01.000000' UTC to '2038-01-19 03:14:07.999999' UTC. TIMESTAMP values are stored as the number of seconds since the epoch ('1970-01-01 00:00:00' UTC). A TIMESTAMP cannot represent the value '1970-01-01 00:00:00' because that is equivalent to 0 seconds from the epoch and the value 0 is reserved for representing '0000-00-00 00:00:00', the “zero” TIMESTAMP value.
		register(java.util.Date.class, Types.TIMESTAMP, new DateTypeHandler(Types.TIMESTAMP)) ; 
		register(java.sql.Date.class, Types.TIMESTAMP, new SqlDateTypeHandler(Types.TIMESTAMP)) ; 
		register(java.sql.Timestamp.class, Types.TIMESTAMP, new TimestampTypeHandler(Types.TIMESTAMP)) ; 
		register(java.sql.Time.class, Types.TIMESTAMP, new TimeTypeHandler(Types.TIMESTAMP)) ; 
		
		//TIME[(fsp)]
		//A time. The range is '-838:59:59.000000' to '838:59:59.000000'. MySQL displays TIME values in 'hh:mm:ss[.fraction]' format, but permits assignment of values to TIME columns using either strings or numbers.
		register(java.util.Date.class, Types.TIME, new DateTypeHandler(Types.TIME)) ; 
		register(java.sql.Date.class, Types.TIME, new SqlDateTypeHandler(Types.TIME)) ; 
		register(java.sql.Timestamp.class, Types.TIME, new TimestampTypeHandler(Types.TIME)) ; 
		register(java.sql.Time.class, Types.TIME, new TimeTypeHandler(Types.TIME)) ; 
	}
	
	private void registerString(){
		
		//[NATIONAL] CHAR[(M)] [CHARACTER SET charset_name] [COLLATE collation_name]
		//A fixed-length string that is always right-padded with spaces to the specified length when stored. M represents the column length in characters. The range of M is 0 to 255. If M is omitted, the length is 1.
		register(String.class, Types.CHAR, new StringTypeHandler(Types.CHAR)) ; 
		
		//[NATIONAL] VARCHAR(M) [CHARACTER SET charset_name] [COLLATE collation_name]
		//A variable-length string. M represents the maximum column length in characters. The range of M is 0 to 65,535. The effective maximum length of a VARCHAR is subject to the maximum row size (65,535 bytes, which is shared among all columns) and the character set used. For example, utf8 characters can require up to three bytes per character, so a VARCHAR column that uses the utf8 character set can be declared to be a maximum of 21,844 characters. 
		register(String.class, Types.VARCHAR, new StringTypeHandler(Types.VARCHAR)) ; 
		
		//BINARY[(M)]
		//The BINARY type is similar to the CHAR type, but stores binary byte strings rather than nonbinary character strings. An optional length M represents the column length in bytes. If omitted, M defaults to 1.
		register(byte[].class, Types.BINARY, new BytesTypeHandler()) ; 
		
		//VARBINARY(M)
		//The VARBINARY type is similar to the VARCHAR type, but stores binary byte strings rather than nonbinary character strings. M represents the maximum column length in bytes.
		register(byte[].class, Types.VARBINARY, new BytesTypeHandler()) ; 
		register(String.class, Types.VARBINARY, new StringTypeHandler(Types.VARBINARY)) ; 
		register(Blob.class, Types.VARBINARY, new BlobTypeHandler()) ; 
		register(byte[].class, Types.LONGVARBINARY, new BytesTypeHandler()) ; 
		register(String.class, Types.LONGVARBINARY, new StringTypeHandler(Types.LONGVARBINARY)) ; 
		register(Blob.class, Types.LONGVARBINARY, new BlobTypeHandler()) ; 
		
		//TEXT[(M)] [CHARACTER SET charset_name] [COLLATE collation_name]
		//A TEXT column with a maximum length of 65,535 (216 − 1) characters. The effective maximum length is less if the value contains multibyte characters. Each TEXT value is stored using a 2-byte length prefix that indicates the number of bytes in the value.
		//TINYTEXT [CHARACTER SET charset_name] [COLLATE collation_name]
		//A TEXT column with a maximum length of 255 (28 − 1) characters. The effective maximum length is less if the value contains multibyte characters. Each TINYTEXT value is stored using a 1-byte length prefix that indicates the number of bytes in the value.
		//MEDIUMTEXT [CHARACTER SET charset_name] [COLLATE collation_name]
		//A TEXT column with a maximum length of 16,777,215 (224 − 1) characters. The effective maximum length is less if the value contains multibyte characters. Each MEDIUMTEXT value is stored using a 3-byte length prefix that indicates the number of bytes in the value.
		//LONGTEXT [CHARACTER SET charset_name] [COLLATE collation_name]
		//A TEXT column with a maximum length of 4,294,967,295 or 4GB (232 − 1) characters. The effective maximum length is less if the value contains multibyte characters. The effective maximum length of LONGTEXT columns also depends on the configured maximum packet size in the client/server protocol and available memory. Each LONGTEXT value is stored using a 4-byte length prefix that indicates the number of bytes in the value.
		register(String.class, Types.LONGVARCHAR, new StringTypeHandler(Types.LONGVARCHAR)) ; 
		register(Blob.class, Types.LONGVARCHAR, new BlobTypeHandler()) ; 
		
		//TINYBLOB
		//A BLOB column with a maximum length of 255 (28 − 1) bytes. Each TINYBLOB value is stored using a 1-byte length prefix that indicates the number of bytes in the value.
		//BLOB[(M)]
		//A BLOB column with a maximum length of 65,535 (216 − 1) bytes. Each BLOB value is stored using a 2-byte length prefix that indicates the number of bytes in the value.
		//MEDIUMBLOB
		//A BLOB column with a maximum length of 16,777,215 (224 − 1) bytes. Each MEDIUMBLOB value is stored using a 3-byte length prefix that indicates the number of bytes in the value.
		//LONGBLOB
		//A BLOB column with a maximum length of 4,294,967,295 or 4GB (232 − 1) bytes. The effective maximum length of LONGBLOB columns depends on the configured maximum packet size in the client/server protocol and available memory. Each LONGBLOB value is stored using a 4-byte length prefix that indicates the number of bytes in the value.
		register(byte[].class, Types.BLOB, new BytesTypeHandler()) ; 
		register(Blob.class, Types.BLOB, new BlobTypeHandler()) ; 
		
		
		//ENUM('value1','value2',...) [CHARACTER SET charset_name] [COLLATE collation_name]
		//An enumeration. A string object that can have only one value, chosen from the list of values 'value1', 'value2', ..., NULL or the special '' error value. ENUM values are represented internally as integers.
		//An ENUM column can have a maximum of 65,535 distinct elements. (The practical limit is less than 3000.) A table can have no more than 255 unique element list definitions among its ENUM and SET columns considered as a group. For more information on these limits, see Limits Imposed by .frm File Structure.
		
		//SET('value1','value2',...) [CHARACTER SET charset_name] [COLLATE collation_name]
		//A set. A string object that can have zero or more values, each of which must be chosen from the list of values 'value1', 'value2', ... SET values are represented internally as integers.
		//A SET column can have a maximum of 64 distinct members. A table can have no more than 255 unique element list definitions among its ENUM and SET columns considered as a group. For more information on this limit, see Limits Imposed by .frm File Structure.
		
//		register(String.class, Types.LONGVARCHAR, new StringTypeHandler()) ; 
		
//		register(Array.class, Types.ARRAY, new ArrayTypeHandler()) ; 
//		register(byte[].class, Types.BINARY, new BytesTypeHandler()) ; 
	}
	
	public boolean isPrimitiveType(Type type){
		return typeColumnHandlersMap.containsKey(type) ; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TypeHandler<?> getTypeHandler(Type type, int sqlType){
		TypeHandler<?> handler = doGetTypeHandler(type, sqlType) ; 
		if( handler == null ){
			// 如果Type为枚举，则动态添加TypeHandler
			if( type instanceof Class ){
				Class<?> enumClass = (Class<?>) type ; 
				if( Enum.class.isAssignableFrom(enumClass) ){
					handler = new EnumTypeHandler(enumClass, sqlType) ; 
					register(type, sqlType, handler) ; 
				}
			}
		}
		return handler ; 
	}
	
	private TypeHandler<?> doGetTypeHandler(Type type, int sqlType){
		Map<Integer, TypeHandler<?>> sqlTypeColumnHandlerMap = typeColumnHandlersMap.get(type) ; 
		if( sqlTypeColumnHandlerMap == null ){
			return null ; 
		}else {
			return sqlTypeColumnHandlerMap.get(sqlType) ; 
		}
	}
	
	private void register(Type type, int sqlType, TypeHandler<?> handler){
		Map<Integer, TypeHandler<?>> sqlTypeColumnHandlerMap = typeColumnHandlersMap.get(type) ; 
		if( sqlTypeColumnHandlerMap == null ){
			sqlTypeColumnHandlerMap = new HashMap<Integer, TypeHandler<?>>() ; 
			typeColumnHandlersMap.put(type, sqlTypeColumnHandlerMap) ; 
		}
		sqlTypeColumnHandlerMap.put(sqlType, handler) ; 
	}

}
