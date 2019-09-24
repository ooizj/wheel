package me.ooi.wheel.query.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author jun.zhao
 * @since 1.0
 */
public interface RowHandler<T> {

	T handler(ResultSet rs) throws SQLException ; 
	
}
