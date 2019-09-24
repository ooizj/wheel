package me.ooi.wheel.context;

import java.io.InputStream;
import java.util.Map;

/**
 * @author jun.zhao
 * @since 1.0
 */
public interface BeanDefinitionReader {
	
	Map<String, BeanDefinition> read(InputStream input) ; 

}
