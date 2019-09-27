package me.ooi.wheel.query.jdbc.typehandler;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class InputStreamTypeHandler implements TypeHandler<InputStream> {

	@Override
	public InputStream getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getBinaryStream(columnIndex) ; 
	}

}
