package me.ooi.wheel.context;

import java.util.Map;

import lombok.Data;

/**
 * @author jun.zhao
 * @since 1.0
 */
@Data
public class BeanDefinition {
	
	/**
	 * bean的名称<br>
	 * 不可重复，不同的name即使是同一类型也对应不同的bean<br>
	 * 默认值是将Class名字的首字母小写后形成的name
	 */
	private String name ; 
	
	/**
	 * bean的类型<br>
	 * 不可为空
	 */
	private Class<?> type ;
	
	/**
	 * bean的边界<br>
	 * 默认值为<code>SINGLETON</code>
	 */
	private Scope scope ; 
	
	/**
	 * bean的属性
	 */
	private Map<String, Object> properties ; 

}
