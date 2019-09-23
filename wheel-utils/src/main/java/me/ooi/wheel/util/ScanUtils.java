package me.ooi.wheel.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jun.zhao
 * @since 1.0
 */
@Slf4j
public class ScanUtils {
	
	public static Set<Class<?>> findClass(String packageName, Class<? extends Annotation> annotationClass) {
		Set<Class<?>> classes = findClass(packageName) ; 
		Iterator<Class<?>> it = classes.iterator() ; 
		while( it.hasNext() ){
			Class<?> clazz = it.next() ; 
			if( !clazz.isAnnotationPresent(annotationClass) ){
				it.remove();
			}
		}
		return classes ; 
	}

    /**
     * 扫描package下面的类
     * @param packageName 包名
     * @return 扫描到的类
     */
    public static Set<Class<?>> findClass(String packageName) {
        Set<Class<?>> ret = new HashSet<Class<?>>();

        ClassLoader cl = Thread.currentThread().getContextClassLoader() ;
        String packagePath = packageName.replace(".", "/") ;
        Enumeration<URL> urls = null;
        try {
            urls = cl.getResources(packagePath);
        } catch (IOException e) {
            throw new RuntimeException("扫描packageName["+packageName+"]失败！", e);
        }

        while( urls.hasMoreElements() ){
            URL url = urls.nextElement() ;

            url = convertVFS(url) ;

            if( url.getProtocol().equals("jar") ){
				Set<Class<?>> classes = findClassInJar(url, packageName) ;
                ret.addAll(classes) ;
            }else if( url.getProtocol().equals("file") ){
                Set<Class<?>> classes = findClass(new File(url.getFile()), packageName) ;
                ret.addAll(classes) ;
            }

        }

        return ret ;
    }

    /**
     * 在jboss中比较特殊，需要特殊处理
     * @param url
     * @return
     */
    private static URL convertVFS(URL url)
    {
        // If the URL is a "vfs" URL (JBoss 7.1 uses a Virtual File System)...

        if (url != null && url.getProtocol().startsWith("vfs"))
        {
            // Ask the VFS what the physical URL is...

            try
            {
                String urlString = url.toString();

                // If the virtual URL involves a JAR file,
                // we have to figure out its physical URL ourselves because
                // in JBoss 7.1 the JAR files exploded into the VFS are empty
                // (see https://issues.jboss.org/browse/JBAS-8786).
                // Our workaround is that they are available, unexploded,
                // within the otherwise exploded WAR file.

                if (urlString.contains(".jar")) {

                    // An example URL: "vfs:/devel/jboss-7.1.0.Final/server/default/deploy/myapp.ear/myapp.war/WEB-INF/lib/tapestry-core-5.3.3.jar/org/apache/tapestry5/corelib/components/"
                    // Break the URL into its WAR part, the JAR part,
                    // and the Java package part.

                    int warPartEnd = urlString.indexOf(".war") + 4;
                    String warPart = urlString.substring(0, warPartEnd);
                    int jarPartEnd = urlString.indexOf(".jar") + 4;
                    String jarPart = urlString.substring(warPartEnd, jarPartEnd);
                    String packagePart = urlString.substring(jarPartEnd);

                    // Ask the VFS where the exploded WAR is.

                    URL warURL = new URL(warPart);
                    URLConnection warConnection = warURL.openConnection();
                    Object jBossVirtualWarDir = warConnection.getContent();
                    // Use reflection so that we don't need JBoss in the classpath at compile time.
                    File physicalWarDir = (File) invokerGetter(jBossVirtualWarDir, "getPhysicalFile");
                    String physicalWarDirStr = physicalWarDir.toURI().toString();

                    // Return a "jar:" URL constructed from the parts
                    // eg. "jar:file:/devel/jboss-7.1.0.Final/server/default/tmp/vfs/automount40a6ed1db5eabeab/myapp.war-43e2c3dfa858f4d2//WEB-INF/lib/tapestry-core-5.3.3.jar!/org/apache/tapestry5/corelib/components/".

                    String actualJarPath = "jar:" + physicalWarDirStr + jarPart + "!" + packagePart;
                    return new URL(actualJarPath);
                }

                // Otherwise, ask the VFS what the physical URL is...

                else {

                    URLConnection connection = url.openConnection();
                    Object jBossVirtualFile = connection.getContent();
                    // Use reflection so that we don't need JBoss in the classpath at compile time.
                    File physicalFile = (File) invokerGetter(jBossVirtualFile, "getPhysicalFile");
                    URL physicalFileURL = physicalFile.toURI().toURL();
                    return physicalFileURL;
                }

            }
            catch (Exception e)
            {
                throw new RuntimeException("处理VFS["+url+"]失败！", e);
            }
        }

        return url;
    }

    private static Object invokerGetter(Object target, String getter) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Class<?> type = target.getClass();
        Method method;
        try
        {
            method = type.getMethod(getter);
        }
        catch (NoSuchMethodException e)
        {
            method = type.getDeclaredMethod(getter);
            method.setAccessible(true);
        }
        return method.invoke(target);
    }

    private static Set<Class<?>> findClass(File dir, String packageName){
        Set<Class<?>> ret = new HashSet<Class<?>>();
        Set<String> classNames = new HashSet<String>() ;
        File[] files = dir.listFiles() ;
        if( files != null ){
            for (File file : files) {
                if( file.isDirectory() ){
                    ret.addAll(findClass(file, packageName+"."+file.getName())) ;
                }else {
                    String fileName = file.getName() ;
                    if( fileName.endsWith(".class") ){
                        classNames.add(packageName+"."+fileName.substring(0, fileName.length()-6)) ;
                    }
                }
            }
        }
        ret.addAll(className2class(classNames));
        return ret;
    }

    private static Set<Class<?>> findClassInJar(URL jarUrl, String packageName) {
        Set<Class<?>> ret = new HashSet<Class<?>>();
        JarURLConnection con = null;
        JarFile jar = null ;
        try {
            con = (JarURLConnection) jarUrl.openConnection();
            jar = con.getJarFile() ;
            Set<Class<?>> classes = findClass(jar, packageName) ;
            ret.addAll(classes) ;
        } catch (IOException e) {
            throw new RuntimeException("扫描packageName["+packageName+"]失败！", e);
        } finally {
            if( jar != null ){
                try {
                    jar.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return ret ;
    }

    private static Set<Class<?>> findClass(JarFile jar, String packageName) {
        final String PACKAGE_PATH = packageName.replace(".", "/") ;
        Set<String> classNames = new HashSet<String>();
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                continue;
            }

            String fileName = entry.getName();
            if( !fileName.startsWith(PACKAGE_PATH) ){
                continue;
            }

            if (fileName.endsWith(".class")) {
                classNames.add(fileName.substring(0, fileName.length() - 6).replace("/", "."));
            }
        }
        return className2class(classNames);
    }

    private static Set<Class<?>> className2class(Set<String> classes){
        Set<Class<?>> ret = new HashSet<Class<?>>();
        for (String clazz : classes) {
            ret.add(ClassUtils.getClass(clazz));
        }
        return ret ;
    }

}
