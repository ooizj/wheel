package me.ooi.wheel.query;

import java.sql.SQLException;
import java.util.List;

/**
 * @author jun.zhao
 * @since 1.0
 */
public interface Query {
	
	/**
	 * 查询
	 * @param sql SQL
	 * @param params 参数
	 */
	<T> List<T> select(String sql, Class<T> targetType, Object ...params) throws SQLException ; 
	
	/**
	 * 更新（insert/update/delete）
	 * @param sql SQL
	 * @param params 参数
	 */
	int update(String sql, Object ...params) throws SQLException ; 

}
