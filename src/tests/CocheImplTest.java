package tests;

import clases.basicas.CocheImpl;

public class CocheImplTest {

	public static void main(String[] args) 
	{
		CocheImpl porDefecto = new CocheImpl();
		CocheImpl conParametros = new CocheImpl("Audi", "A1", 27500);
		CocheImpl deCopia = new CocheImpl(conParametros);
		
		System.out.println("conParametros.getMarca(): " + conParametros.getMarca());
		
		System.out.println("deCopia.getModelo(): " + deCopia.getModelo());
		
		System.out.println("porDefecto.getPrecioBase(): " + porDefecto.getPrecioBase());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setMarca(\"Seat\")");
		
		System.out.println("ANTES -> " + porDefecto.getMarca());
		
		porDefecto.setMarca("Seat");
		
		System.out.println("DESPUES -> " + porDefecto.getMarca());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setModelo(\"Ibiza\")");
		
		System.out.println("ANTES -> " + porDefecto.getModelo());
		
		porDefecto.setModelo("Ibiza");
		
		System.out.println("DESPUES -> " + porDefecto.getModelo());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("deCopia.setPrecioBase(24220)");
		
		System.out.println("ANTES -> " + deCopia.getPrecioBase());
		
		porDefecto.setPrecioBase(24220);
		
		System.out.println("DESPUES -> " + deCopia.getPrecioBase());
	}

}
