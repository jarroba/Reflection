package main;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class Inicio {
	// public class Inicio extends Tipos {

	public static void main(String[] args) {
		MiClase objetoDeMiClase = new MiClase();
		Class<? extends MiClase> objetoDeClassConInfoDeMiClase = objetoDeMiClase.getClass();

		// Obtener el valor de la variable pública
		Field variableString = null;
		String textoObtenido = null;
		try {
			variableString = objetoDeClassConInfoDeMiClase.getField("unaVariableString");
			textoObtenido = (String) variableString.get(objetoDeMiClase);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		System.out.println("\nValor de la variable de tipo String: " + textoObtenido);

		// Obtener el valor de la variable privada
		Field variableInt = null;
		int intObtenido = -1;
		try {
			variableInt = objetoDeClassConInfoDeMiClase.getDeclaredField("unaVariableInt");
			variableInt.setAccessible(true);
			intObtenido = variableInt.getInt(objetoDeMiClase);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		System.out.println("\nValor de la variable de tipo int: " + intObtenido);

		// Recorrer todas las variables de la clase
		Field[] todasLasVariablesDeclaradas = objetoDeClassConInfoDeMiClase.getDeclaredFields();
		for (Field variable : todasLasVariablesDeclaradas) {
			String nombreVariable = variable.getName();
			System.out.println("\nNombre de la VARIABLE GLOBAL: " + nombreVariable);

			Type tipo = variable.getGenericType();
			String nombreTipoVariable = tipo.getTypeName();
			System.out.println("  Tipo: " + nombreTipoVariable);

			int modificador = variable.getModifiers();

			Boolean esPublic = Modifier.isPublic(modificador);
			System.out.println("  Es public: " + esPublic);

			Boolean esPrivate = Modifier.isPrivate(modificador);
			System.out.println("  Es private: " + esPrivate);

			Boolean esFinal = Modifier.isFinal(modificador);
			System.out.println("  Es final: " + esFinal);

			Boolean esStatic = Modifier.isStatic(modificador);
			System.out.println("  Es static: " + esStatic);

			Object valorVariable = null;
			try {
				if (Modifier.isPrivate(variable.getModifiers())) {
					variable.setAccessible(true);
				}
				valorVariable = variable.get(objetoDeMiClase);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println("  Valor de la variable " + nombreVariable + " es: " + valorVariable);

			// TODO falta cambiar el valor
			try {
				if (String.class == tipo) {
					variable.set(objetoDeMiClase, "Texto cambiado en la variable");

					System.out.println("  Valor cambiado de la variable " + nombreVariable + " es: " + objetoDeMiClase.unaVariableString);
				} else if (int.class == tipo) {
					variable.setInt(objetoDeMiClase, 12345);

					variableInt.setAccessible(true);
					intObtenido = variableInt.getInt(objetoDeMiClase);
					System.out.println("  Valor cambiado de la variable " + nombreVariable + " es: " + intObtenido);
				}

			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

		}

		// Recorrer todos los métodos de la clase
		final Method[] metodos = objetoDeClassConInfoDeMiClase.getDeclaredMethods();
		for (final Method metodo : metodos) {
			System.out.println("\nNombre del MÉTODO: " + metodo.getName());
			System.out.println("  Cantidad de parámetros: " + metodo.getParameterCount());

			System.out.println("  Es public: " + Modifier.isPublic(metodo.getModifiers()));
			System.out.println("  Es private: " + Modifier.isPrivate(metodo.getModifiers()));

			System.out.println("  Tipo del return: " + metodo.getGenericReturnType().getTypeName());

			Type[] tipos = metodo.getGenericParameterTypes();
			System.out.println("  Tipos de los parámetros:");
			for (Type tipo : tipos) {
				System.out.println("    " + tipo.getTypeName());
			}

			try {
				if (Modifier.isPrivate(metodo.getModifiers())) {
					metodo.setAccessible(true);
				}

				Object valorParametro = null;

				if (metodo.getName() == "getUnaVariableString") {
					valorParametro = " Añadir otra cosa";
				} else if (metodo.getName() == "getUnaVariableInt") {
					valorParametro = 100000000;
				}

				Object valorRetornoMetodoInvocado = metodo.invoke(objetoDeMiClase, valorParametro);
				System.out.println("  Valor del método invocado: " + valorRetornoMetodoInvocado);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}

		}

		// Crear instancia de la clase
		try {
			Constructor<?> constructorSinParametros = objetoDeClassConInfoDeMiClase.getConstructor();
			
			MiClase nuevoObjetoDeMiClase = (MiClase) constructorSinParametros.newInstance();

			Field variableString2 = nuevoObjetoDeMiClase.getClass().getField("unaVariableString");
			variableString2.set(nuevoObjetoDeMiClase, "Texto para nuevo objeto");

			System.out.println("\nNuevo objeto creado");
			System.out.println("  Valor de la variable asignada: " + nuevoObjetoDeMiClase.unaVariableString);

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | SecurityException | NoSuchMethodException e) {
			e.printStackTrace();
		}

	}

}
