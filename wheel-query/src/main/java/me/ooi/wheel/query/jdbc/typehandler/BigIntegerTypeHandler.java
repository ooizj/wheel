package me.ooi.wheel.query.jdbc.typehandler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class BigIntegerTypeHandler implements TypeHandler<BigInteger> {

	@Override
	public BigInteger getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		BigDecimal bigDecimal = rs.getBigDecimal(columnIndex) ;
		return bigDecimal == null ? null : bigDecimal.toBigInteger() ; 
	}

}
