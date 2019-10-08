package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class StringTypeHandler implements TypeHandler<String> {
	
	private int sqlType ; 
	public StringTypeHandler(int sqlType) {
		this.sqlType = sqlType;
	}

	@Override
	public String getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType == Types.CHAR || sqlType == Types.VARCHAR || sqlType == Types.LONGVARCHAR ) {
			return rs.getString(columnIndex) ; 
		}else if( sqlType == Types.VARBINARY || sqlType == Types.LONGVARBINARY ) {
			byte[] bytes = rs.getBytes(columnIndex) ;
			return new String(bytes) ; 
		}
		
		return null ; 
	}

}
