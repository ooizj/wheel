package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class BytesTypeHandler implements TypeHandler<byte[]> {

	@Override
	public byte[] getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getBytes(columnIndex) ; 
	}

}
