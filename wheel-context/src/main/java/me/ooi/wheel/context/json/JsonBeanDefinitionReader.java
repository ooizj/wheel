package me.ooi.wheel.context.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import me.ooi.wheel.context.BeanDefinition;
import me.ooi.wheel.context.BeanDefinitionException;
import me.ooi.wheel.context.BeanDefinitionReader;
import me.ooi.wheel.context.BeanFactoryUtils;
import me.ooi.wheel.context.Scope;
import me.ooi.wheel.util.ClassUtils;
import me.ooi.wheel.util.GsonUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class JsonBeanDefinitionReader implements BeanDefinitionReader {
	
	private static Gson gson  = GsonUtils.createGson() ;  

	@Override
	public Map<String, BeanDefinition> read(InputStream input) {
		String jsonStr = null ; 
		try {
			jsonStr = IOUtils.toString(input, "utf-8") ;
		} catch (IOException e) {
			throw new BeanDefinitionException("读取资源失败！", e) ; 
		} 
		
		Map<String, BeanDefinition> beanDefinitions = new HashMap<String, BeanDefinition>() ; 
		JsonBeanDefinitionDocument document = gson.fromJson(jsonStr, JsonBeanDefinitionDocument.class) ; 
		List<JsonBeanDefinition> jsonBeanDefinitions = document.getJsonBeanDefinitions() ; 
		for (JsonBeanDefinition jsonBeanDefinition : jsonBeanDefinitions) {
			BeanDefinition bd = convert(jsonBeanDefinition) ; 
			beanDefinitions.put(bd.getName(), bd) ; 
		}
		return beanDefinitions;
	}
	
	private BeanDefinition convert(JsonBeanDefinition jsonBeanDefinition){
		BeanDefinition bd = new BeanDefinition() ; 
		
		bd.setType(ClassUtils.getClass(jsonBeanDefinition.getType()));
		
		String name = jsonBeanDefinition.getName() ; 
		if( name == null ){
			name = BeanFactoryUtils.getDefaultName(bd.getType()) ; 
		}
		bd.setName(name);
		
		Scope scope = Scope.SINGLETON ; 
		if( jsonBeanDefinition.getScope() != null ){
			scope = Scope.valueOf(jsonBeanDefinition.getScope().toUpperCase()) ;
		}
		bd.setScope(scope);
		
		bd.setProperties(jsonBeanDefinition.getProperties());
		
		return bd ; 
	}

}
