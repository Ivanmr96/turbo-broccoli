package tests;

import clases.basicas.PiezaImpl;

public class PiezaImplTest {

	public static void main(String[] args) 
	{
		PiezaImpl porDefecto = new PiezaImpl();
		PiezaImpl conParametros = new PiezaImpl(3, "30 TFSI 6 vel.", "Motor de inyección estratificada de combustible", 0);
		PiezaImpl deCopia = new PiezaImpl(conParametros);
		
		System.out.println("porDefecto.getID(): " + porDefecto.getID());
		
		System.out.println("conParametros.getNombre(): " + conParametros.getNombre());
		
		System.out.println("deCopia.getDescripcion(): " + deCopia.getDescripcion());
		
		System.out.println("conParametros.getPrecio(): " + conParametros.getPrecio());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setNombre(\"Pintura gris oscuro\")");
		
		System.out.println("ANTES -> " + porDefecto.getNombre());
		
		porDefecto.setNombre("Pintura gris oscuro");
		
		System.out.println("DESPUES -> " + porDefecto.getNombre());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setDescripcion(\"Color gris con acabado metalizado\")");
		
		System.out.println("ANTES -> " + porDefecto.getDescripcion());
		
		porDefecto.setDescripcion("Color gris con acabado metalizado");
		
		System.out.println("DESPUES -> " + porDefecto.getDescripcion());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("deCopia.setPrecio(4250)");
		
		System.out.println("ANTES -> " + deCopia.getPrecio());
		
		deCopia.setPrecio(4250);
		
		System.out.println("DESPUES -> " + deCopia.getPrecio());
	}

}
