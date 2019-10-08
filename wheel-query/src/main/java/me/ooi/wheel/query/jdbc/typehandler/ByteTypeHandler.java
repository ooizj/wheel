package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class ByteTypeHandler implements TypeHandler<Byte> {

	@Override
	public Byte getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		Byte val = rs.getByte(columnIndex) ;
		return rs.wasNull() ? null : val ; 
	}

}
