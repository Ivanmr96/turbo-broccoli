package tests;

import clases.basicas.PinturaImpl;

public class PinturaImplTest {

	public static void main(String[] args) 
	{
		PinturaImpl porDefecto = new PinturaImpl();
		PinturaImpl conParametros = new PinturaImpl(1, "gris oscuro", "Color gris con acabado metalizado", 450, "gris", "metalizado");
		PinturaImpl deCopia = new PinturaImpl(conParametros);
		
		System.out.println("conParametros.getColor(): " + conParametros.getColor());
		
		System.out.println("deCopia.getAcabado(): " + deCopia.getAcabado());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setColor(\"rojo\")");
		
		System.out.println("ANTES -> " + porDefecto.getColor());
		
		porDefecto.setColor("rojo");
		
		System.out.println("DESPUES -> " + porDefecto.getColor());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setAcabado(\"mate\")");
		
		System.out.println("ANTES -> " + porDefecto.getAcabado());
		
		porDefecto.setAcabado("mate");
		
		System.out.println("DESPUES -> " + porDefecto.getAcabado());
	}

}
