package me.ooi.wheel.query.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.ooi.wheel.query.Query;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class JDBCQuery implements Query {
	
	private ConnectionHolder connectionHolder ; 

	public void setConnectionHolder(ConnectionHolder connectionHolder) {
		this.connectionHolder = connectionHolder;
	}

	@Override
	public <T> List<T> select(String sql, Class<T> targetType, Object... params) throws SQLException {
		return select(getConnection(), sql, targetType, params) ; 
	}

	@Override
	public int update(String sql, Object... params) throws SQLException {
		return update(getConnection(), sql, params) ; 
	}
	
	public <T> List<T> select(Connection con, String sql, Class<T> targetType, Object... params) throws SQLException {
		if( con == null ){
			throw new SQLException("Connection can't be NULL!") ; 
		}
		
		List<T> ret = new ArrayList<T>() ; 
		PreparedStatement stat = null ; 
		ResultSet rs = null ; 
		try {
			stat = con.prepareStatement(sql) ; 
			setStatementParams(stat, params) ; 
			rs = stat.executeQuery() ; 
			RowHandler<T> rowHandler = new BasicRowHandler<T>(targetType) ;  
			while( rs.next() ){
				T rowInstance = rowHandler.handler(rs) ;
				ret.add(rowInstance) ; 
			}
		} finally {
			close(rs);
			close(stat);
		}
		return ret;
	}
	
	public int update(Connection con, String sql, Object... params) throws SQLException {
		if( con == null ){
			throw new SQLException("Connection can't be NULL!") ; 
		}
		
		PreparedStatement stat = null ; 
		try {
			stat = con.prepareStatement(sql) ; 
			setStatementParams(stat, params) ; 
			return stat.executeUpdate() ; 
		} finally {
			close(stat);
		}
	}
	
	private void setStatementParams(PreparedStatement stat, Object... params) throws SQLException{
		if( params == null || params.length == 0 ){
			return ; 
		}
		
		for (int i = 0; i < params.length; i++) {
			Object param = params[i] ; 
			stat.setObject(i+1, param); 
		}
	}
	
	private void close(ResultSet rs) throws SQLException{
		if( rs != null ){
			rs.close();
		}
	}
	
	private void close(PreparedStatement stat) throws SQLException{
		if( stat != null ){
			stat.close();
		}
	}
	
	private Connection getConnection(){
		return connectionHolder.getConnection() ; 
	}

}
