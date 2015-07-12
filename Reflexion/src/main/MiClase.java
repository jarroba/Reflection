package main;

public class MiClase {

	public String unaVariableString = "Un Texto";
	private int unaVariableInt = 5;

	public String getUnaVariableString(String concatenar) {
		return unaVariableString + concatenar;
	}

	private int getUnaVariableInt(int suma) {
		return unaVariableInt + suma;
	}

}