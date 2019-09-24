package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class TimestampTypeHandler implements TypeHandler<Timestamp> {
	
	private int sqlType ; 
	public TimestampTypeHandler(int sqlType) {
		this.sqlType = sqlType;
	}

	@Override
	public Timestamp getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType == Types.DATE ){
			java.sql.Date date = rs.getDate(columnIndex) ; 
			return new java.sql.Timestamp(date.getTime()) ; 
		}else if( sqlType == Types.TIMESTAMP ){
			return rs.getTimestamp(columnIndex) ; 
		}else if( sqlType == Types.TIME ){
			java.sql.Time time = rs.getTime(columnIndex) ; 
			return new java.sql.Timestamp(time.getTime()) ; 
		}else {
			throw new SQLException("unsupport type["+sqlType+"]!") ; 
		}
	}

}
