package me.ooi.wheel.query;

import java.util.List;	
import java.util.Map;

import org.h2.engine.User;

import me.ooi.wheel.query.annotation.Delete;
import me.ooi.wheel.query.annotation.Insert;
import me.ooi.wheel.query.annotation.Select;
import me.ooi.wheel.query.annotation.Statement;
import me.ooi.wheel.query.annotation.Update;

/**
 * @author jun.zhao
 * @since 1.0
 */
public interface UserDao {
	
	@Select("select * from User where name like ?")
	public List<User> findUser(String name) ; 

	@Insert("insert into User(name, sex, describle, age) values(?,?,?,?)")
	public void save(User user) ; 
	
	@Update("update User set name=? where id=?")
	public int update(User user) ; 
	
	@Delete("delete from User where id=?")
	public int delete(Integer id) ; 
	
	@Select("select * from User")
	public List<User> findAll() ; 
	
//	@Ftl
//	@Select("select * from User <#if name??> where name = ? </#if>")
//	public List<User> findUser4(User user) ;
	
	//@template
	@Statement("create table t2(id int, tname varchar(50))")
	public int create() ; 
	
	@Insert("insert into t2(id,tname) values(:id,:tname)")
	public int addT2(Map<String, Object> params) ; 
	
	@SuppressWarnings("rawtypes")
	@Select("select * from t2 ")
	public List findT2() ;
	
	@Select("select * from t2 ")
	public Object[] findT22() ;
	
	@Select("select * from t2 ")
	public List<Object[]> findT23() ;

}
