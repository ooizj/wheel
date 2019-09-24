package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class TimeTypeHandler implements TypeHandler<Time> {
	
	private int sqlType ; 
	public TimeTypeHandler(int sqlType) {
		this.sqlType = sqlType;
	}

	@Override
	public Time getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType == Types.DATE ){
			java.util.Date date = rs.getDate(columnIndex) ; 
			return new java.sql.Time(date.getTime()) ; 
		}else if( sqlType == Types.TIMESTAMP ){
			java.sql.Timestamp timestamp = rs.getTimestamp(columnIndex) ; 
			return new java.sql.Time(timestamp.getTime()) ; 
		}else if( sqlType == Types.TIME ){
			return rs.getTime(columnIndex) ; 
		}else {
			throw new SQLException("unsupport type["+sqlType+"]!") ; 
		}
	}

}
