package me.ooi.wheel.util;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class ClassUtilsTest {
	
	public static class C1{
		private void privateMethod1(){
		}
		private static void privateMethod2(){
		}
		private final void privateMethod3(){
		}
		public void publicMethod1(){
		}
		public static void publicMethod2(){
		}
		public final void publicMethod3(){
		}
	}
	
	public static class C1_1 extends C1{
		private void privateMethod1(){
		}
		private static void privateMethod2(){
		}
		private final void privateMethod3(){
		}
		public void publicMethod1(){
		}
		public static void publicMethod2(){
		}
	}
	
	public static class C1_2 extends C1{
		private void c_privateMethod1(){
		}
		private static void c_privateMethod2(){
		}
		private final void c_privateMethod3(){
		}
		public void c_publicMethod1(){
		}
		public static void c_publicMethod2(){
		}
	}
	
	@Test
	public void t1(){
		displayMethodInfo(C1_1.class.getDeclaredMethods());
		displayMethodInfo(C1_2.class.getDeclaredMethods());
		displayMethodInfo(C1_1.class.getMethods());
		displayMethodInfo(C1_2.class.getMethods());
	}
	
	private void displayMethodInfo(Method ...methods){
		System.out.println("============================");
		if( methods == null ){
			return ; 
		}
		for (int i = 0; i < methods.length; i++) {
			System.out.println(methods[i]);
		}
	}

}
