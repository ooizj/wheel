package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class EnumTypeHandler<T extends Enum<T>> implements TypeHandler<T> {
	
	private int sqlType ; 
	private Class<T> enumType ; 
	public EnumTypeHandler(Class<T> enumType, int sqlType) {
		this.enumType = enumType;
		this.sqlType = sqlType;
	}
	
	@Override
	public T getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType != Types.VARCHAR && sqlType != Types.CHAR && sqlType != Types.LONGVARCHAR ){
			throw new SQLException("unsupport type["+sqlType+"]!") ; 
		}
		String str = rs.getString(columnIndex) ; 
		if( str == null ){
			return null ; 
		}
		return Enum.valueOf(enumType, str) ; 
	}

}
