package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class BooleanTypeHandler implements TypeHandler<Blob> {

	@Override
	public Blob getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getBlob(columnIndex) ; 
	}

}
