package me.ooi.wheel.query.jdbc.typehandler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class BigDecimalTypeHandler implements TypeHandler<BigDecimal> {
	
	private int sqlType ; 
	public BigDecimalTypeHandler(int sqlType) {
		this.sqlType = sqlType;
	}

	@Override
	public BigDecimal getColumnValue(ResultSet rs, int columnIndex) throws SQLException {
		if( sqlType == Types.DECIMAL || sqlType == Types.NUMERIC ){
			return rs.getBigDecimal(columnIndex) ;
		}else if( sqlType == Types.INTEGER ){
			Integer val = rs.getInt(columnIndex) ; 
			return (val == null || rs.wasNull()) ? null : new BigDecimal(val) ; 
		}else if( sqlType == Types.SMALLINT ){
			Short val = rs.getShort(columnIndex) ; 
			return (val == null || rs.wasNull()) ? null : new BigDecimal(val.intValue()) ;  
		}else if( sqlType == Types.BIGINT ){
			Long val = rs.getLong(columnIndex) ;
			return (val == null || rs.wasNull()) ? null : new BigDecimal(val) ;  
		}else if( sqlType == Types.TINYINT ){
			Byte val = rs.getByte(columnIndex) ; 
			return (val == null || rs.wasNull()) ? null : new BigDecimal(val) ; 
		}
		
		return null ; 
	}

}
