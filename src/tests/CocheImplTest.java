package tests;

import clases.basicas.CocheImpl;
import clases.basicas.PiezaImpl;
import clases.gestion.ConexionSQL;
import clases.gestion.GestionCoche;

public class CocheImplTest {

	public static void main(String[] args) 
	{
		String URLConexion = "jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;";
		
		ConexionSQL gestionConexion = new ConexionSQL(URLConexion);
		gestionConexion.abrirConexion();
		GestionCoche gestion = new GestionCoche(gestionConexion.getConexion());
		
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
		
		System.out.println("-------------------------------------------------------");
		System.out.println("-------------------------------------------------------");
		
		System.out.println("conParametros.obtenerPiezasValidas()");
		gestion.cargarPiezasValidasEnCoche(conParametros);
		
		for(PiezaImpl pieza:conParametros.obtenerPiezasValidas())
		{
			System.out.println(pieza.toString());
		}
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("conParametros.obtenerPiezasExtraValidas()");
		
		for(PiezaImpl piezaExtra:conParametros.obtenerPiezasExtraValidas())
		{
			System.out.println(piezaExtra.toString());
		}
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("conParametros.obtenerMotoresValidos()");
		
		for(PiezaImpl motor:conParametros.obtenerMotoresValidos())
		{
			System.out.println(motor.toString());
		}
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("conParametros.obtenerLlantasValidas()");
		
		for(PiezaImpl llantas:conParametros.obtenerLlantasValidas())
		{
			System.out.println(llantas.toString());
		}
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("conParametros.obtenerPinturasValidas()");
		
		for(PiezaImpl pintura:conParametros.obtenerPinturasValidas())
		{
			System.out.println(pintura.toString());
		}
	}

}
