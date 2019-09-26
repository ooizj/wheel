package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class LongTypeHandler implements TypeHandler<Long> {
	
	private int sqlType ; 
	public LongTypeHandler(int sqlType) {
		this.sqlType = sqlType;
	}

	@Override
	public Long getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType == Types.BIGINT ){
			return rs.getLong(columnIndex) ;
		}else if( sqlType == Types.INTEGER ){
			Integer val = rs.getInt(columnIndex) ; 
			return val == null ? null : val.longValue() ; 
		}else if( sqlType == Types.SMALLINT ){
			Short val = rs.getShort(columnIndex) ; 
			return val == null ? null : val.longValue(); 
		}else if( sqlType == Types.TINYINT ){
			Byte val = rs.getByte(columnIndex) ; 
			return val == null ? null : val.longValue() ; 
		}
		
		return null ; 
	}

}
