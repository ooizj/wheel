package me.ooi.wheel.context.json;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import me.ooi.wheel.context.AbstractBeanFactory;
import me.ooi.wheel.context.BeanCreationException;
import me.ooi.wheel.context.BeanDefinition;
import me.ooi.wheel.context.BeanFactoryUtils;
import me.ooi.wheel.context.Scope;
import me.ooi.wheel.util.ClassUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class JsonBeanFactory extends AbstractBeanFactory {
	
	private JsonBeanDefinitionReader reader = new JsonBeanDefinitionReader() ; 
	
	//key is BeanDefinition's name 
	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>() ; 
	
	//single object cache
	private Map<String, Object> singletonObjectMap = new HashMap<String, Object>() ; 
	
	public JsonBeanFactory(InputStream input) {
		beanDefinitionMap = Collections.unmodifiableMap(reader.read(input)) ;  
	}

	@Override
	public Object getBean(String name) {
		BeanDefinition bd = beanDefinitionMap.get(name) ; 
		if( bd.getScope() == Scope.SINGLETON ){
			synchronized (singletonObjectMap) {
				if( singletonObjectMap.containsKey(name) ){
					return singletonObjectMap.get(name) ; 
				}else {
					Object newObj = createBean(bd) ; 
					singletonObjectMap.put(name, newObj) ; 
					return newObj ; 
				}
			}
		}else if( bd.getScope() == Scope.PROTOTYPE ){
			Object newObj = createBean(bd) ; 
			return newObj ; 
		}else {
			throw new BeanCreationException("不支持的Scope["+bd.getScope()+"]！") ; 
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(Class<T> type) {
		String beanName = BeanFactoryUtils.getDefaultName(type) ;
		return (T) getBean(beanName) ; 
	}
	
	private Object createBean(BeanDefinition bd){
		Class<?> clazz = bd.getType() ; 
		Object instance = ClassUtils.newInstance(clazz) ;
		
		//initial properties
		//set reference
		//properties值中首字母位置的“&”为关键字，表示引用；若需输出“&”，则可以使用“\\&”
		if( bd.getProperties() != null ){
			for (Map.Entry<String, Object> property : bd.getProperties().entrySet()) {
				String key = property.getKey() ; 
				Object value = property.getValue() ; 
				if( value instanceof String ){
					String val = (String) value ; 
					if( val.startsWith("\\\\&") ){ //“\\&”
						//若需输出“&”，则可以使用“\\&”，所以这里将其转换为想要的结果
						bd.getProperties().put(key, "&"+val.substring(3)) ; 
					}else if( val.startsWith("&") ){ // “&”
						//首字母位置的“&”为关键字，表示引用
						String refBeanName = val.substring(1) ; 
						bd.getProperties().put(key, getBean(refBeanName)) ; 
					}
				}
			}
			
			//copy properties
			try {
				BeanUtils.copyProperties(instance, bd.getProperties());
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new BeanCreationException(e) ; 
			}
			
		}
		
		return instance ; 
	}

}
