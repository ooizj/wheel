package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class SqlDateTypeHandler implements TypeHandler<java.sql.Date> {
	
	private int sqlType ; 
	public SqlDateTypeHandler(int sqlType) {
		this.sqlType = sqlType;
	}

	@Override
	public java.sql.Date getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType == Types.DATE ){
			return rs.getDate(columnIndex) ; 
		}else if( sqlType == Types.TIMESTAMP ){
			java.sql.Timestamp timestamp = rs.getTimestamp(columnIndex) ; 
			return new java.sql.Date(timestamp.getTime()) ; 
		}else if( sqlType == Types.TIME ){
			java.sql.Time time = rs.getTime(columnIndex) ; 
			return new java.sql.Date(time.getTime()) ; 
		}else {
			throw new SQLException("unsupport type["+sqlType+"]!") ; 
		}
	}

}
