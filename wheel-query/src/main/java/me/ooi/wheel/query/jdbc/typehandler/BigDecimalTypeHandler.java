package me.ooi.wheel.query.jdbc.typehandler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class BigDecimalTypeHandler implements TypeHandler<BigDecimal> {

	@Override
	public BigDecimal getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getBigDecimal(columnIndex) ;
	}

}
