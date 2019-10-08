package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class ClobTypeHandler implements TypeHandler<Clob> {

	@Override
	public Clob getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getClob(columnIndex) ; 
	}

}
