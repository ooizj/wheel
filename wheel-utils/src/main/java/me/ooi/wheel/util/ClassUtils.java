package me.ooi.wheel.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jun.zhao
 * @since 1.0
 */
@Slf4j
public class ClassUtils {
	
	public static Class<?> getClass(String className){
		try {
			return Class.forName(className) ;
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		} 
		return null ; 
	}
	
	public static <T> T newInstance(Class<T> clazz){
		try {
			return clazz.newInstance() ;
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return null; 
	}
	
	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz){
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			return beanInfo.getPropertyDescriptors() ; 
		} catch (IntrospectionException e) {
			log.error(e.getMessage(), e);
		} 
		return null ; 
	}
	
	public static List<Method> getDeclaredMethodsByAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass){
		List<Method> methods = new ArrayList<Method>(Arrays.asList(clazz.getDeclaredMethods())) ;
		Iterator<Method> it = methods.iterator() ; 
		while( it.hasNext() ){
			Method method = it.next() ; 
			if( !method.isAnnotationPresent(annotationClass) ){
				it.remove();
			}
		}
		return methods ; 
	}
	
	public static <T extends Annotation> List<T> getParameterAnnotations(Method method, Class<T> annotationClass){
		List<T> ret = new ArrayList<T>() ; 
		Annotation[][] annotationss = method.getParameterAnnotations() ; 
		for (Annotation[] annotations : annotationss) {
			for (Annotation annotation : annotations) {
				if( annotation.annotationType() == annotationClass ){
					ret.add(annotationClass.cast(annotation)) ; 
				}
			}
		}
		return ret ; 
	}

}
