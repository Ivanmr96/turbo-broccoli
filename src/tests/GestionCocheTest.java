package tests;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.PiezaImpl;
import clases.gestion.ConexionSQL;
import clases.gestion.GestionCoche;

public class GestionCocheTest {

	public static void main(String[] args) 
	{
		ConexionSQL SQL = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		SQL.abrirConexion();
		
		GestionCoche gestionCoche = new GestionCoche(SQL.getConexion());
		
		System.out.println("obtenerCoche(\"Audi\", \"A1\"): " + gestionCoche.obtenerCoche("Audi", "A1").toString());
		
		
		ConfiguracionImpl configuracionTest = new ConfiguracionImpl("EF49FC93-2EEF-4F9A-BBBD-B4CF232D17A9", new GregorianCalendar(2019, 6, 15));
		
		CocheImpl cocheTest = gestionCoche.obtenerCoche(configuracionTest);
		
		System.out.println("obtenerCoche(configuracionTest): " + cocheTest.toString());
		
		System.out.println();
		
		System.out.println("obtenerCoches()");
		
		for(CocheImpl coche:gestionCoche.obtenerCoches())
		{
			System.out.println(coche.toString());
		}
		
		System.out.println();
		
		PiezaImpl piezaTest = new PiezaImpl(11, "Motor E-CVT 125H Automatico", null, 0);
		
		System.out.println("obtenerCochesValidos(piezaTest)");
		
		for(CocheImpl coche:gestionCoche.obtenerCochesValidos(piezaTest))
		{
			System.out.println(coche.toString());
		}
		
		System.out.println();
		
		System.out.println("obtenerMarcas()");
		
		for(String marca:gestionCoche.obtenerMarcas())
		{
			System.out.println(marca);
		}
		
		System.out.println();
		
		System.out.println("obtenerModelos(\"Toyota\")");
		
		for(String modelo:gestionCoche.obtenerModelos("Toyota"))
		{
			System.out.println(modelo);
		}
		
		System.out.println("---------------------------------------------------------------------------");
		
		try
		{
			Statement statement = SQL.getConexion().createStatement();
			CocheImpl cocheNuevoTest = new CocheImpl("Seat", "Leon", 19500);
			
			statement.execute("BEGIN TRANSACTION");			//Transacción necesaria para que no se modifiquen los datos al ejecutar el test.
			
			System.out.println("insertarCoche(new CocheImpl(\"Seat\", \"Leon\", 19500))");
			
			try 
			{
				System.out.println("ANTES -> " + gestionCoche.obtenerCoche("Seat", "Leon").toString());
			}
			catch(NullPointerException e)
			{
				System.out.println("ANTES -> NULL");
			}
			
			gestionCoche.insertarCoche(cocheNuevoTest);
			
			try
			{
				System.out.println("DESPUES -> " + gestionCoche.obtenerCoche("Seat", "Leon").toString());
			}
			catch(NullPointerException e)
			{
				System.out.println("DESPUES -> NULL");
			}
			
			statement.execute("ROLLBACK");					//No se guardan los cambios
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("---------------------------------------------------------------------------");
		
		try
		{
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION");			//Transacción necesaria para que no se modifiquen los datos al ejecutar el test.
			
			System.out.println("actualizarCoche(new CocheImpl(\"Mercedes\", \"Clase A\", 55000))");
			
			try 
			{
				System.out.println("ANTES -> " + gestionCoche.obtenerCoche("Mercedes", "Clase A").toString());
			}
			catch(NullPointerException e)
			{
				System.out.println("ANTES -> NULL");
			}
			
			gestionCoche.actualizarCoche(new CocheImpl("Mercedes", "Clase A", 55000));
			
			try
			{
				System.out.println("DESPUES -> " + gestionCoche.obtenerCoche("Mercedes", "Clase A").toString());
			}
			catch(NullPointerException e)
			{
				System.out.println("DESPUES -> NULL");
			}
			
			statement.execute("ROLLBACK");					//No se guardan los cambios
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
			
		System.out.println("---------------------------------------------------------------------------");
		
		System.out.println("cargarPiezasValidasEnCoche(cocheTest)");
		
		System.out.println("ANTES");
		
		try
		{
			for(PiezaImpl pieza:cocheTest.obtenerPiezasValidas())
			{
				System.out.println(pieza.toString());
			}
		}
		catch(NullPointerException e)
		{
			System.out.println("No hay piezas");
		}
		
		gestionCoche.cargarPiezasValidasEnCoche(cocheTest);
		
		System.out.println();
		
		System.out.println("DESPUES");
		
		try
		{
			for(PiezaImpl pieza:cocheTest.obtenerPiezasValidas())
			{
				System.out.println(pieza.toString());
			}
		}
		catch(NullPointerException e)
		{
			System.out.println("No hay piezas");
		}
	}

}
