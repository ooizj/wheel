package me.ooi.wheel.query.jdbc;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import me.ooi.wheel.query.jdbc.typehandler.TypeHandler;
import me.ooi.wheel.query.jdbc.typehandler.TypeHandlerRegistry;
import me.ooi.wheel.util.ClassUtils;
import me.ooi.wheel.util.StringUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class BasicRowHandler<T> implements RowHandler<T> {
    
    private Class<T> rowObjectType ; 

	public BasicRowHandler(Class<T> rowObjectType) {
		this.rowObjectType = rowObjectType;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T handler(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData() ; 
		
		//single column
		if( rsmd.getColumnCount() == 1 && (TypeHandlerRegistry.INSTANCE.isPrimitiveType(rowObjectType) || 
				Enum.class.isAssignableFrom(rowObjectType)/*dynamic register type handler*/) ){
			int sqlType = rsmd.getColumnType(1) ; 
			TypeHandler<?> handler = TypeHandlerRegistry.INSTANCE.getTypeHandler(rowObjectType, sqlType) ; 
			return (T) handler.getColumnValue(rs, 1) ; 
		}
		
		PropertyDescriptor[] propertyDescriptors = ClassUtils.getPropertyDescriptors(rowObjectType) ; 
		//column index -> PropertyDescriptor
		Map<Integer, PropertyDescriptor> column2PropertyDescriptorMap = column2PropertyDescriptor(rsmd, propertyDescriptors) ; 
		T ret = processRow(rsmd, rs, propertyDescriptors, column2PropertyDescriptorMap) ;
		return ret ; 
	}
	
	private T processRow(ResultSetMetaData rsmd, ResultSet rs, PropertyDescriptor[] propertyDescriptors, Map<Integer, PropertyDescriptor> column2PropertyDescriptorMap) throws SQLException {
		
		T rowInstance = ClassUtils.newInstance(rowObjectType) ; 
		
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			PropertyDescriptor prop = column2PropertyDescriptorMap.get(i) ; 
			if( prop == null ){
				continue ; 
			}
			
			int sqlType = rsmd.getColumnType(i) ; 
			processColumns(rs, i, rowInstance, prop, sqlType);
		}
		
		return rowInstance;
	}
	
	private void processColumns(ResultSet rs, int columnIndex, T rowInstance, PropertyDescriptor prop, int sqlType) throws SQLException {
		
		Class<?> propType = prop.getPropertyType() ;
		if( propType == null ){
			return ; 
		}
		
		Object columnValue = getColumnValue(rs, columnIndex, propType, sqlType);
        if( columnValue == null ){
        	return ; 
        }
		
        //set field value
        if( isCompatibleType(columnValue, propType) ){
        	try {
        		Method setter = prop.getWriteMethod() ; 
				setter.invoke(rowInstance, new Object[]{columnValue}) ;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage()) ; 
			} 
        }else {
        	throw new SQLException(
	            "Cannot set " + prop.getName() + ": incompatible types, cannot convert "
	            + columnValue.getClass().getName() + " to " + propType.getName());
	            // value cannot be null here because isCompatibleType allows null
        }
	}

	/**
     * Convert a <code>ResultSet</code> column into an object.  Simple
     * implementations could just call <code>rs.getObject(index)</code> while
     * more complex implementations could perform type manipulation to match
     * the column's type to the bean property type.
     *
     * <p>
     * This implementation calls the appropriate <code>ResultSet</code> getter
     * method for the given property type to perform the type conversion.  If
     * the property type doesn't match one of the supported
     * <code>ResultSet</code> types, <code>getObject</code> is called.
     * </p>
     *
     * @param rs The <code>ResultSet</code> currently being processed.  It is
     * positioned on a valid row before being passed into this method.
     *
     * @param index The current column index being processed.
     *
     * @param propType The bean property type that this column needs to be
     * converted into.
     * 
     * @param sqlType The current column jdbcType.
     *
     * @throws SQLException if a database access error occurs
     *
     * @return The object from the <code>ResultSet</code> at the given column
     * index after optional type processing or <code>null</code> if the column
     * value was SQL NULL.
     */
    protected Object getColumnValue(ResultSet rs, int index, Class<?> propType, int sqlType) throws SQLException {
    	
    	TypeHandler<?> columnHandler = TypeHandlerRegistry.INSTANCE.getTypeHandler(propType, sqlType) ; 
    	if( columnHandler != null ){
    		return columnHandler.getColumnValue(rs, index) ; 
    	}else {
    		return rs.getObject(index) ; 
    	}
    }
    
    /**
     * ResultSet.getObject() returns an Integer object for an INT column.  The
     * setter method for the property might take an Integer or a primitive int.
     * This method returns true if the value can be successfully passed into
     * the setter method.  Remember, Method.invoke() handles the unwrapping
     * of Integer into an int.
     *
     * @param value The value to be passed into the setter method.
     * @param type The setter's parameter type (non-null)
     * @return boolean True if the value is compatible (null => true)
     */
    private boolean isCompatibleType(Object value, Class<?> type) {
        // Do object check first, then primitives
        if (value == null || type.isInstance(value)) {
            return true;

        } else if (type.equals(Integer.TYPE) && value instanceof Integer) {
            return true;

        } else if (type.equals(Long.TYPE) && value instanceof Long) {
            return true;

        } else if (type.equals(Double.TYPE) && value instanceof Double) {
            return true;

        } else if (type.equals(Float.TYPE) && value instanceof Float) {
            return true;

        } else if (type.equals(Short.TYPE) && value instanceof Short) {
            return true;

        } else if (type.equals(Byte.TYPE) && value instanceof Byte) {
            return true;

        } else if (type.equals(Character.TYPE) && value instanceof Character) {
            return true;

        } else if (type.equals(Boolean.TYPE) && value instanceof Boolean) {
            return true;

        }
        return false;

    }
    
	//column index -> PropertyDescriptor
	private Map<Integer, PropertyDescriptor> column2PropertyDescriptor(ResultSetMetaData rsmd, PropertyDescriptor[] propertyDescriptors) throws SQLException{
		Map<Integer, PropertyDescriptor> map = new HashMap<Integer, PropertyDescriptor>() ; 
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			String columnName = rsmd.getColumnLabel(i) ;
			if( StringUtils.isEmpty(columnName) ){
				columnName = rsmd.getColumnName(i) ; 
			}
			
			PropertyDescriptor propertyDescriptor = getPropertyDescriptorByColumnName(columnName, propertyDescriptors) ; 
			map.put(i, propertyDescriptor) ; 
		}
		return map ; 
	}
	
	private PropertyDescriptor getPropertyDescriptorByColumnName(String columnName, PropertyDescriptor[] propertyDescriptors){
		if( StringUtils.isEmpty(columnName) ){
			return null ; 
		}
		
		for (int i = 0; i < propertyDescriptors.length; i++) {
			String propertyName = propertyDescriptors[i].getName() ; 
			if( columnName.equalsIgnoreCase(propertyName) ){
				return propertyDescriptors[i] ; 
			}
		}
		return null ; 
	}
    
}
