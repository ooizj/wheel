
test
```java

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.ooi.wheel.requesthandler.annotation.PathParam;
import me.ooi.wheel.requesthandler.annotation.RequestHandler;
import me.ooi.wheel.requesthandler.annotation.RequestParam;
import me.ooi.wheel.requesthandler.annotation.ResponseBody;
import me.ooi.wheel.requesthandler.annotation.method.GET;

/**
 * @author jun.zhao
 * @since 1.0
 */
@RequestHandler("/testHandler")
public class TestRequestHandler {
	
	@GET
	@ResponseBody
	public Map<String, Object> test(@RequestParam("name") String name, 
			HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("test");
		System.out.println(name);
		System.out.println(request);
		System.out.println(response);
		
		return Collections.singletonMap("test", "hhahhs问问") ; 
	}
	
	@GET("/{id}")
	@ResponseBody
	public Map<String, Object> test2(@PathParam("id") String id){
		
		System.out.println("test2");
		System.out.println(id);
		
		return Collections.singletonMap("test2", "hhahhs问问") ; 
	}
	
	@GET(value="/a/b", produces="application/json")
	public Map<String, Object> t1(){
		return Collections.singletonMap("t1", "t1问问") ; 
	}
	
	@GET(value="/a/c")
	public String t2(){
		return "/index.html" ; 
	}

}
```

start embed tomcat
```java

import java.io.File;

import org.apache.catalina.startup.Tomcat;

public class EmbedTomcatTest {
	
	public static void main(String[] args) throws Exception {

		String webappDirLocation = "webroot";
	    Tomcat tomcat = new Tomcat();
	    tomcat.setPort(7890);

	    tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

	    tomcat.start();
	    tomcat.getServer().await();
    }

}
```



