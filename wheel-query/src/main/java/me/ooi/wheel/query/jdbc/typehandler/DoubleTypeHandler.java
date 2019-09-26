package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class DoubleTypeHandler implements TypeHandler<Double> {
	
	private int sqlType ; 
	public DoubleTypeHandler(int sqlType) {
		this.sqlType = sqlType;
	}

	@Override
	public Double getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType == Types.DOUBLE ){
			return rs.getDouble(columnIndex) ;
		}
		
		return null ; 
	}

}
