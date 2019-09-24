package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class ArrayTypeHandler implements TypeHandler<Array> {

	@Override
	public Array getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getArray(columnIndex) ; 
	}

}
