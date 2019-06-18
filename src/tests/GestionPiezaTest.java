package tests;

import java.sql.SQLException;
import java.sql.Statement;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.MotorImpl;
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
		
		ConfiguracionImpl configuracionTest = gestionConfiguracion.obtenerConfiguracion("5E825DA3-140D-4B16-BD28-31B359EA79AA");
		
		CocheImpl cocheTest = gestionCoche.obtenerCoche("Audi", "A1");
		
		System.out.println("obtenerPieza(1): " + gestionPieza.obtenerPieza(1).toString());
		System.out.println("obtenerPiezaLlantas(32): "+ gestionPieza.obtenerPiezaLlantas(32).toString());
		System.out.println("obtenerPiezaMotor(27): " + gestionPieza.obtenerPiezaMotor(27).toString());
		System.out.println("obtenerPiezaPintura(44): " + gestionPieza.obtenerPiezaPintura(44).toString());
		
		try
		{
			System.out.println("obtenerPiezaLlantas(configuracionTest): " + gestionPieza.obtenerPiezaLlantas(configuracionTest).toString());
		}
		catch(NullPointerException e)
		{
			System.out.println("obtenerPiezaLlantas(configuracionTest): No tiene");
		}
		
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
		
		System.out.println("-------------------------------------------------------------------------");
		PiezaImpl piezaTest = new PiezaImpl(110, "Pieza test", "Descripcion test", 1024.33);
		
		System.out.println("piezaTest -> " + piezaTest.toString());
		
		System.out.println();
		
		System.out.println("insertarPieza(piezaTest)");
		
		try
		{
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION");
			
			try
			{
				System.out.println("ANTES -> obtenerPieza(110) -> " + gestionPieza.obtenerPieza(110).toString());
			}
			catch(NullPointerException e)
			{
				System.out.println("ANTES -> obtenerPieza(110) -> No existe");
			}
			
			System.out.println();
			
			if(gestionPieza.insertarPieza(piezaTest))
				System.out.println("Pieza insertada correctamente");
			else
				System.out.println("La pieza no se insertó.");
			
			System.out.println();
			
			System.out.println("DESPUES -> obtenerPieza(110) -> " + gestionPieza.obtenerPieza(110).toString());
			
			statement.execute("ROLLBACK");
			
		}
		catch(SQLException e)
		{
			
		}
		
		System.out.println("-------------------------------------------------------------------------");
		
		MotorImpl motorTest = new MotorImpl(110, "Motor test", "Descripcion motor test", 1024.33, 'T', 6, 760, 135, "D");
		
		System.out.println("motorTest -> " + motorTest.toString());
		
		System.out.println();
		
		System.out.println("insertarPiezaMotor(motorTest)");
		
		try
		{
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION");
			
			try
			{
				System.out.println("ANTES -> obtenerPiezaMotor(110) -> " + gestionPieza.obtenerPiezaMotor(110).toString());
			}
			catch(NullPointerException e)
			{
				System.out.println("ANTES -> obtenerPiezaMotor(110) -> No existe");
			}
			
			System.out.println();
			
			if(gestionPieza.insertarPiezaMotor(motorTest))
				System.out.println("Motor insertado correctamente");
			else
				System.out.println("El motor no se insertó.");
			
			System.out.println();
			
			System.out.println("DESPUES -> obtenerPiezaMotor(110) -> " + gestionPieza.obtenerPiezaMotor(110).toString());
			
			statement.execute("ROLLBACK");
			
		}
		catch(SQLException e)
		{
			
		}
		
		System.out.println("-------------------------------------------------------------------------");
	
		System.out.println("cargarCochesValidosEnPieza(pieza)");
		
		PiezaImpl pieza = gestionPieza.obtenerPieza(2);
		
		gestionPieza.cargarCochesValidosEnPieza(pieza);
		
		for(CocheImpl coche:pieza.obtenerCochesValidos())
		{
			coche.toString();
		}
	}
}
