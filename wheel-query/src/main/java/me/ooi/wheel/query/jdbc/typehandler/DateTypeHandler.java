package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class DateTypeHandler implements TypeHandler<java.util.Date> {
	
	private int sqlType ; 
	public DateTypeHandler(int sqlType) {
		this.sqlType = sqlType;
	}

	@Override
	public java.util.Date getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType == Types.DATE ){
			java.sql.Date date = rs.getDate(columnIndex) ; 
			return new java.util.Date(date.getTime()) ; 
		}else if( sqlType == Types.TIMESTAMP ){
			java.sql.Timestamp timestamp = rs.getTimestamp(columnIndex) ; 
			return new java.util.Date(timestamp.getTime()) ; 
		}else if( sqlType == Types.TIME ){
			java.sql.Time time = rs.getTime(columnIndex) ; 
			return new java.util.Date(time.getTime()) ; 
		}else {
			throw new SQLException("unsupport type["+sqlType+"]!") ; 
		}
	}

}
