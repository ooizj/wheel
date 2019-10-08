package me.ooi.wheel.query.jdbc.typehandler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class BigIntegerTypeHandler implements TypeHandler<BigInteger> {
	
	private int sqlType ; 
	public BigIntegerTypeHandler(int sqlType) {
		this.sqlType = sqlType;
	}

	@Override
	public BigInteger getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType == Types.NUMERIC ){
			BigDecimal bigDecimal = rs.getBigDecimal(columnIndex) ;
			return bigDecimal == null ? null : bigDecimal.toBigInteger() ; 
		}else if( sqlType == Types.INTEGER ){
			Integer val = rs.getInt(columnIndex) ; 
			return (val == null || rs.wasNull()) ? null : BigInteger.valueOf(val) ; 
		}else if( sqlType == Types.SMALLINT ){
			Short val = rs.getShort(columnIndex) ; 
			return (val == null || rs.wasNull()) ? null : BigInteger.valueOf(val.longValue()) ; 
		}else if( sqlType == Types.BIGINT ){
			Long val = rs.getLong(columnIndex) ;
			return (val == null || rs.wasNull()) ? null : BigInteger.valueOf(val) ; 
		}else if( sqlType == Types.TINYINT ){
			Byte val = rs.getByte(columnIndex) ; 
			return (val == null || rs.wasNull()) ? null : BigInteger.valueOf(val) ; 
		}
		
		return null ; 
		
		
	}

}
