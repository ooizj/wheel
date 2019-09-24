package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class FloatTypeHandler implements TypeHandler<Float> {

	@Override
	public Float getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getFloat(columnIndex) ;
	}

}
