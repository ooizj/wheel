package me.ooi.wheel.requesthandler;

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
