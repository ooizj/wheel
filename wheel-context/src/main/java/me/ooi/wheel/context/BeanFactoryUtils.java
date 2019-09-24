package me.ooi.wheel.context;

import me.ooi.wheel.util.StringUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class BeanFactoryUtils {
	
	/**
	 * 获取Bean默认名称
	 * @param clazz bean类型
	 */
	public static String getDefaultName(Class<?> clazz){
		String classSimpleName = clazz.getSimpleName() ;
		String name = StringUtils.firstLowercase(classSimpleName) ; 
		return name ; 
	}
	
}
