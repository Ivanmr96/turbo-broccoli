package tests;

import clases.basicas.LlantasImpl;

public class LlantasImplTest {

	public static void main(String[] args) 
	{
		LlantasImpl porDefecto = new LlantasImpl();
		LlantasImpl conParametros = new LlantasImpl(2, "Llantas 5 brazos", "Llantas gris metalizado de 15 pulgadas", 975, 15);
		LlantasImpl deCopia = new LlantasImpl(conParametros);
		
		System.out.println("conParametros.getPulgadas(): " + conParametros.getPulgadas());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setPulgadas(16)");
		
		System.out.println("ANTES -> " + porDefecto.getPulgadas());
		
		porDefecto.setPulgadas(16);
		
		System.out.println("DESPUES -> " + porDefecto.getPulgadas());
	}

}
