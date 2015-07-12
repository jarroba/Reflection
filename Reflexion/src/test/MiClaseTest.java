package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import main.MiClase;

public class MiClaseTest {

	@Test
	public void testGetUnaVariableString() {
		MiClase objetoDeMiClase = new MiClase();
		String valor = objetoDeMiClase.getUnaVariableString(" Cualquiera");
		
		assertNotNull(valor);
		assertEquals("Un Texto Cualquiera", valor);
	}
	
	@Test
	public void testGetUnaVariableInt() {
		MiClase objetoDeMiClase = new MiClase();
		
		Class<? extends MiClase> objetoDeClassConInfoDeMiClase = objetoDeMiClase.getClass();
		Method metodo;
		int valor;
		try {
			metodo = objetoDeClassConInfoDeMiClase.getDeclaredMethod("getUnaVariableInt", int.class);
			metodo.setAccessible(true);
			valor = (int) metodo.invoke(objetoDeMiClase, 2);
			
			assertNotNull(valor);
			assertEquals(7, valor);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}