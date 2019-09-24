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
		
		register(Boolean.class, Types.BIT, new BooleanTypeHandler()) ; 
		register(Boolean.class, Types.BOOLEAN, new BooleanTypeHandler()) ; 
		register(Blob.class, Types.BLOB, new BlobTypeHandler()) ; 
		
		register(Array.class, Types.ARRAY, new ArrayTypeHandler()) ; 
		register(Byte.class, Types.BINARY, new ByteTypeHandler()) ; 
		register(Byte.TYPE, Types.BINARY, new ByteTypeHandler()) ; 
		register(Byte.class, Types.TINYINT, new ByteTypeHandler()) ; 
		register(Byte.TYPE, Types.TINYINT, new ByteTypeHandler()) ; 
		
		register(byte[].class, Types.BINARY, new BytesTypeHandler()) ; 
		
		register(Double.class, Types.DOUBLE, new DoubleTypeHandler()) ; 
		register(Double.TYPE, Types.DOUBLE, new DoubleTypeHandler()) ; 
		register(Float.class, Types.FLOAT, new FloatTypeHandler()) ; 
		register(Float.TYPE, Types.FLOAT, new FloatTypeHandler()) ; 
		register(Float.class, Types.REAL, new FloatTypeHandler()) ; 
		register(Float.TYPE, Types.REAL, new FloatTypeHandler()) ; 
		register(Integer.class, Types.INTEGER, new IntegerTypeHandler()) ; 
		register(Integer.TYPE, Types.INTEGER, new IntegerTypeHandler()) ; 
		register(Long.class, Types.BIGINT, new LongTypeHandler()) ; 
		register(Long.TYPE, Types.BIGINT, new LongTypeHandler()) ; 
		register(Short.class, Types.SMALLINT, new ShortTypeHandler()) ; 
		register(Short.TYPE, Types.SMALLINT, new ShortTypeHandler()) ; 
		register(BigDecimal.class, Types.NUMERIC, new BigDecimalTypeHandler()) ; 
		register(BigDecimal.class, Types.DECIMAL, new BigDecimalTypeHandler()) ; 
		register(BigInteger.class, Types.NUMERIC, new BigIntegerTypeHandler()) ; 
		
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
