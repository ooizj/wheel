package me.ooi.wheel.query.jdbc.typehandler;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Array;
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
		
		
		register(java.util.Date.class, Types.DATE, new DateTypeHandler(Types.DATE)) ; 
		register(java.util.Date.class, Types.TIMESTAMP, new DateTypeHandler(Types.TIMESTAMP)) ; 
		register(java.util.Date.class, Types.TIME, new DateTypeHandler(Types.TIME)) ; 
		
		register(java.sql.Date.class, Types.DATE, new SqlDateTypeHandler(Types.DATE)) ; 
		register(java.sql.Date.class, Types.TIMESTAMP, new SqlDateTypeHandler(Types.TIMESTAMP)) ; 
		register(java.sql.Date.class, Types.TIME, new SqlDateTypeHandler(Types.TIME)) ; 
		
		register(java.sql.Timestamp.class, Types.DATE, new TimestampTypeHandler(Types.DATE)) ; 
		register(java.sql.Timestamp.class, Types.TIMESTAMP, new TimestampTypeHandler(Types.TIMESTAMP)) ; 
		register(java.sql.Timestamp.class, Types.TIME, new TimestampTypeHandler(Types.TIME)) ; 
		
		register(java.sql.Time.class, Types.DATE, new TimeTypeHandler(Types.DATE)) ; 
		register(java.sql.Time.class, Types.TIMESTAMP, new TimeTypeHandler(Types.TIMESTAMP)) ; 
		register(java.sql.Time.class, Types.TIME, new TimeTypeHandler(Types.TIME)) ; 
		
		register(String.class, Types.VARCHAR, new StringTypeHandler()) ; 
		register(String.class, Types.CHAR, new StringTypeHandler()) ; 
		register(String.class, Types.LONGVARCHAR, new StringTypeHandler()) ; 
		
		
		
		register(Blob.class, Types.BLOB, new BlobTypeHandler()) ; 
		
		register(Array.class, Types.ARRAY, new ArrayTypeHandler()) ; 
		register(Byte.class, Types.BINARY, new ByteTypeHandler()) ; 
		register(Byte.TYPE, Types.BINARY, new ByteTypeHandler()) ; 
		
		
		register(byte[].class, Types.BINARY, new BytesTypeHandler()) ; 
		
		
		
	}
	
	private void registerNumeric(){
		
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
		
		//BIGINT[(M)] [UNSIGNED] [ZEROFILL]
		//A large integer. The signed range is -9223372036854775808 to 9223372036854775807. The unsigned range is 0 to 18446744073709551615.
		register(Long.class, Types.BIGINT, new LongTypeHandler(Types.BIGINT)) ; 
		register(Long.TYPE, Types.BIGINT, new LongTypeHandler(Types.BIGINT)) ; 
		register(BigDecimal.class, Types.BIGINT, new BigDecimalTypeHandler(Types.BIGINT)) ; 
		register(BigInteger.class, Types.BIGINT, new BigIntegerTypeHandler(Types.BIGINT)) ; 
		
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
		
		//BIT[(M)]
		//A bit-value type. M indicates the number of bits per value, from 1 to 64. The default is 1 if M is omitted.
		register(Boolean.class, Types.BIT, new BooleanTypeHandler()) ; //M must be 1; value = 0 then false, value = 1 then true, value = other then error
		
		//BOOL, BOOLEAN
		//These types are synonyms for TINYINT(1). A value of zero is considered false. Nonzero values are considered true.
		register(Boolean.class, Types.BOOLEAN, new BooleanTypeHandler()) ; 
		
		//DECIMAL[(M[,D])] [UNSIGNED] [ZEROFILL]
		register(BigDecimal.class, Types.DECIMAL, new BigDecimalTypeHandler(Types.DECIMAL)) ; 
		register(BigDecimal.class, Types.NUMERIC, new BigDecimalTypeHandler(Types.NUMERIC)) ; 
		
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
