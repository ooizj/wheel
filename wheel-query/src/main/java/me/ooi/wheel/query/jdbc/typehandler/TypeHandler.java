package me.ooi.wheel.query.jdbc.typehandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public interface TypeHandler<T> {
	
	T getColumnValue(ResultSet rs, int columnIndex) throws SQLException ; 

}
