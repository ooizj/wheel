package me.ooi.wheel.context;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Test;

import me.ooi.wheel.context.json.JsonBeanDefinitionReader;
import me.ooi.wheel.context.json.JsonBeanFactory;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class JsonBeanFactoryTest {
	
	@Test
	public void testJsonReader() throws FileNotFoundException{
		JsonBeanDefinitionReader reader = new JsonBeanDefinitionReader() ; 
		Map<String, BeanDefinition> map = reader.read(JsonBeanFactoryTest.class.getResourceAsStream("/test.json")) ; 
		System.out.println(map);
	}
	
	@Test
	public void testGetBean() throws FileNotFoundException{
		JsonBeanFactory f = new JsonBeanFactory(JsonBeanFactoryTest.class.getResourceAsStream("/test.json")) ; 
		Department d = (Department) f.getBean("d1") ; 
		System.out.println(d);
		
		User user1 = (User) f.getBean("user1") ; 
		System.out.println(user1);
		
		User user = (User) f.getBean("user") ; 
		System.out.println(user);
	}
	
	@Test
	public void testGetBean2() throws FileNotFoundException{
		JsonBeanFactory f = new JsonBeanFactory(JsonBeanFactoryTest.class.getResourceAsStream("/test.json")) ; 
		
		User user1 = (User) f.getBean("user1") ; 
		System.out.println(user1);
		
		Department d = (Department) f.getBean("d1") ; 
		System.out.println(d);
		
		for (int i = 0; i < 10000; i++) {
			User user = f.getBean(User.class) ; 
			System.out.println(user.hashCode());
		}
	}

}
