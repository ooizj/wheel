package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class BooleanTypeHandler implements TypeHandler<Boolean> {

	@Override
	public Boolean getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		Boolean val = rs.getBoolean(columnIndex) ; 
		return rs.wasNull() ? null : val ; 
	}

}
