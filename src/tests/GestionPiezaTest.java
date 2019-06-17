package tests;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.PiezaImpl;
import clases.gestion.ConexionSQL;
import clases.gestion.GestionCoche;
import clases.gestion.GestionConfiguracion;
import clases.gestion.GestionPieza;

public class GestionPiezaTest 
{
	public static void main(String[] args)
	{
		ConexionSQL SQL = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		SQL.abrirConexion();
		
		GestionPieza gestionPieza = new GestionPieza(SQL.getConexion());
		GestionConfiguracion gestionConfiguracion = new GestionConfiguracion(SQL.getConexion());
		GestionCoche gestionCoche = new GestionCoche(SQL.getConexion());
		
		ConfiguracionImpl configuracionTest = gestionConfiguracion.obtenerConfiguracion("EF49FC93-2EEF-4F9A-BBBD-B4CF232D17A9");
		
		CocheImpl cocheTest = gestionCoche.obtenerCoche("Audi", "A1");
		
		System.out.println("obtenerPieza(1): " + gestionPieza.obtenerPieza(1).toString());
		System.out.println("obtenerPiezaLlantas(32): "+ gestionPieza.obtenerPiezaLlantas(32).toString());
		System.out.println("obtenerPiezaMotor(27): " + gestionPieza.obtenerPiezaMotor(27).toString());
		System.out.println("obtenerPiezaPintura(44): " + gestionPieza.obtenerPiezaPintura(44).toString());
		System.out.println("obtenerPiezaLlantas(configuracionTest): " + gestionPieza.obtenerPiezaLlantas(configuracionTest).toString());
		
		try 
		{
			System.out.println("obtenerPiezaMotor(configuracionTest): " + gestionPieza.obtenerPiezaMotor(configuracionTest).toString());
		}
		catch(NullPointerException e)
		{
			System.out.println("obtenerPiezaMotor(configuracionTest): No tiene");
		}
		
		System.out.println("obtenerPiezaPintura(configuracionTest): " + gestionPieza.obtenerPiezaPintura(configuracionTest).toString());
		
		System.out.println();
		System.out.println("obtenerPiezas(configuracionTest)");
		for(PiezaImpl pieza:gestionPieza.obtenerPiezas(configuracionTest))
		{
			System.out.println(pieza.toString());
		}
		System.out.println();
		
		System.out.println("obtenerPiezasValidas(cocheTest)");
		for(PiezaImpl pieza:gestionPieza.obtenerPiezasValidas(cocheTest))
		{
			System.out.println(pieza.toString());
		}
		System.out.println();
		
//		insertarPieza(Pieza)
//		insertarPiezaMotor(Motor)
	
		System.out.println("cargarCochesValidosEnPieza(pieza)");
		
		PiezaImpl pieza = gestionPieza.obtenerPieza(2);
		
		gestionPieza.cargarCochesValidosEnPieza(pieza);
		
		for(CocheImpl coche:pieza.obtenerCochesValidos())
		{
			coche.toString();
		}
	}
}
