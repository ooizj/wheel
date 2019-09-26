package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class IntegerTypeHandler implements TypeHandler<Integer> {
	
	private int sqlType ; 
	public IntegerTypeHandler(int sqlType) {
		this.sqlType = sqlType;
	}

	@Override
	public Integer getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType == Types.INTEGER ){
			return rs.getInt(columnIndex) ; 
		}else if( sqlType == Types.SMALLINT ){
			Short val = rs.getShort(columnIndex) ; 
			return val == null ? null : val.intValue() ; 
		}
		
		return null ;
	}

}
