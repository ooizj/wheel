

test.json  
```json
{
	"beans":[
		{
			"name": "d1",
			"class": "me.ooi.wheel.context.Department",
			"scope": "singleton",
			"properties": {
				"name": "d1111"
			}
		},
		{
			"name": "user1",
			"class": "me.ooi.wheel.context.User",
			"scope": "singleton",
			"properties": {
				"name": "aaa",
				"label": ["lable1", "lable2"],
				"attr": {
					"a1": "sadfasfd",
					"a2": "ffffffffffff"
				},
				"department": "&d1"
			}
		},
		{
			"class": "me.ooi.wheel.context.User",
			"scope": "prototype"
		}
	]
}


```

User.java
```java
@Getter
@Setter
@ToString
public class User {

	private String name ; 
	
	private List<String> label ; 
	
	private Map<String, String> attr ; 
	
	private Department department ; 
	
}
```
Department.java
```java
@Data
public class Department {
	
	private String id ; 
	private String name ; 

}

```

test
```java
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
```
