package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class BlobTypeHandler implements TypeHandler<Boolean> {

	@Override
	public Boolean getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getBoolean(columnIndex) ; 
	}

}
