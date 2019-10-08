package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class ShortTypeHandler implements TypeHandler<Short> {
	
	private int sqlType ; 
	public ShortTypeHandler(int sqlType) {
		this.sqlType = sqlType;
	}

	@Override
	public Short getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType == Types.SMALLINT ){
			Short val = rs.getShort(columnIndex) ;
			return rs.wasNull() ? null : val ; 
		}else if( sqlType == Types.TINYINT ){
			Byte val = rs.getByte(columnIndex) ; 
			return val == null ? null : val.shortValue() ; 
		}
		
		return null ; 
	}

}
