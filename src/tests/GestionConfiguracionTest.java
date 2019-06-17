package tests;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.LlantasImpl;
import clases.basicas.MotorImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.PinturaImpl;
import clases.basicas.VotacionImpl;
import clases.gestion.ConexionSQL;
import clases.gestion.GestionConfiguracion;

public class GestionConfiguracionTest {

	public static void main(String[] args) 
	{
		ConexionSQL SQL = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		SQL.abrirConexion();
		
		GestionConfiguracion gestionConfiguracion = new GestionConfiguracion(SQL.getConexion());
		
		ConfiguracionImpl configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
		gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
		
		System.out.println("obtenerConfiguracion(\"A6221DF6-D42D-4F2C-84C6-CC153AF80EFE\"): " + configuracionTest.toString());
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("obtenerConfiguraciones()");
		
		ArrayList<ConfiguracionImpl> configuracionesTest = gestionConfiguracion.obtenerConfiguraciones();
		gestionConfiguracion.cargarRelacionesEnConfiguraciones(configuracionesTest);
		
		for(ConfiguracionImpl configuracion:configuracionesTest)
		{
			System.out.println(configuracion.toString());
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("obtenerConfiguraciones(new CocheImpl(\"Mercedes\", \"Clase A\", 23500))");
		
		configuracionesTest = gestionConfiguracion.obtenerConfiguraciones(new CocheImpl("Mercedes", "Clase A", 23500));		//El precio no es importante para buscar en este caso
		gestionConfiguracion.cargarRelacionesEnConfiguraciones(configuracionesTest);
		
		for(ConfiguracionImpl configuracion:configuracionesTest)
		{
			System.out.println(configuracion.toString());
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("obtenerConfiguraciones(new CuentaImpl(\"testuser\", \"123\")");
		
		configuracionesTest = gestionConfiguracion.obtenerConfiguraciones(new CuentaImpl("testuser", "123"));		//La contraseña no es importante para buscar en este caso
		gestionConfiguracion.cargarRelacionesEnConfiguraciones(configuracionesTest);
		
		for(ConfiguracionImpl configuracion:configuracionesTest)
		{
			System.out.println(configuracion.toString());
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("obtenerConfiguraciones(31000, 33000)");
		
		configuracionesTest = gestionConfiguracion.obtenerConfiguraciones(31000, 33000);
		gestionConfiguracion.cargarRelacionesEnConfiguraciones(configuracionesTest);
		
		for(ConfiguracionImpl configuracion:configuracionesTest)
		{
			System.out.println(configuracion.toString());
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("obtenerConfiguraciones(\"Audi\")");
		
		configuracionesTest = gestionConfiguracion.obtenerConfiguraciones("Audi");
		gestionConfiguracion.cargarRelacionesEnConfiguraciones(configuracionesTest);
		
		for(ConfiguracionImpl configuracion:configuracionesTest)
		{
			System.out.println(configuracion.toString());
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("eliminarConfiguracion(configuracionTest)");
		
		try
		{
			configuracionTest = gestionConfiguracion.obtenerConfiguracion(configuracionTest.getID());
			gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
			
			System.out.println("ANTES -> " + configuracionTest.toString());
			
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION"); 		//Se realiza en una transacción para no modificar los datos al ejecutar el test.
			
			gestionConfiguracion.eliminarConfiguracion(configuracionTest);
			
			try
			{
				configuracionTest = gestionConfiguracion.obtenerConfiguracion(configuracionTest.getID());
				gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
				
				System.out.println("DESPUES -> " + configuracionTest.toString());
			}
			catch(NullPointerException e)
			{
				System.out.println("DESPUES -> NULL");
			}
			
			statement.execute("ROLLBACK");					//No se guardan los cambios
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("insertarConfiguracion(new ConfiguracionImpl(\"b9158b40-912a-11e9-bc42-526af7764f64\", new GregorianCalendar())");
		
		try
		{
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION");					//Se hará en una transacción para que no se guarden los cambios.
			
			try
			{
				configuracionTest = gestionConfiguracion.obtenerConfiguracion("b9158b40-912a-11e9-bc42-526af7764f64");
				gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
				
				System.out.println("ANTES -> " + configuracionTest.toString());
			}
			catch(NullPointerException e)
			{
				System.out.println("ANTES -> NULL");
			}
			
			configuracionTest = new ConfiguracionImpl("b9158b40-912a-11e9-bc42-526af7764f64", new GregorianCalendar());
			configuracionTest.establecerCoche(new CocheImpl("Mercedes", "Clase A", 23500));
			configuracionTest.establecerCuenta(new CuentaImpl("testuser", "123"));
			
			gestionConfiguracion.insertarConfiguracion(configuracionTest);
			
			try
			{
				configuracionTest = gestionConfiguracion.obtenerConfiguracion("b9158b40-912a-11e9-bc42-526af7764f64");
				gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
				
				System.out.println("DESPUES -> " + configuracionTest.toString());
			}
			catch(NullPointerException e)
			{
				System.out.println("DESPUES -> NULL");
			}
			
			statement.execute("ROLLBACK");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("actualizarConfiguracion(configuracionTest)");
		
		System.out.println("configuracionTest = gestionConfiguracion.obtenerConfiguracion(\"A6221DF6-D42D-4F2C-84C6-CC153AF80EFE\");");
		
		configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
		gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
		
		System.out.println();
		
		System.out.println("Se cambia la pintura: configuracionTest.establecerPintura(new PinturaImpl(40, \"Bianco Cortina\", null, 0, \"blanco\", \"solido\"));");
		
		configuracionTest.establecerPintura(new PinturaImpl(40, "Bianco Cortina", null, 0, "blanco", "solido"));
		
		try
		{	
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION");						//Se abre una transacción para no modificar los datos.
			
			System.out.println();
			
			System.out.println("gestionConfiguracion.actualizarConfiguracion(configuracionTest)");
	
			gestionConfiguracion.actualizarConfiguracion(configuracionTest);
			
			configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
			gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
			
			System.out.println("DESPUES -> " + configuracionTest.obtenerPintura().toString());
			
			statement.execute("ROLLBACK");								//No se guardarán los cambios realizados.
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("actualizarLlantasDeConfiguracion(configuracionTest)");
		
		System.out.println("configuracionTest = gestionConfiguracion.obtenerConfiguracion(\"A6221DF6-D42D-4F2C-84C6-CC153AF80EFE\");");
		
		configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
		gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
		
		System.out.println();
		
		System.out.println("Se cambian las llantas: configuracionTest.establecerLlantas(new LlantasImpl(45, \"Llantas 15 pulgadas\", null, 0, 15));");
		
		configuracionTest.establecerLlantas(new LlantasImpl(45, "Llantas 15 pulgadas", null, 0, 15));
		
		try
		{	
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION");						//Se abre una transacción para no modificar los datos.
			
			System.out.println();
			
			System.out.println("gestionConfiguracion.actualizarLlantasDeConfiguracion(configuracionTest);");
	
			gestionConfiguracion.actualizarLlantasDeConfiguracion(configuracionTest);
			
			configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
			gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
			
			System.out.println("DESPUES -> " + configuracionTest.obtenerLlantas().toString());
			
			statement.execute("ROLLBACK");								//No se guardarán los cambios realizados.
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("actualizarMotorDeConfiguracion(configuracionTest)");
		
		System.out.println("configuracionTest = gestionConfiguracion.obtenerConfiguracion(\"A6221DF6-D42D-4F2C-84C6-CC153AF80EFE\");");
		
		configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
		gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
		
		System.out.println();
		
		System.out.println("Se cambia el motor: configuracionTest.establecerMotor(new MotorImpl(38, \"Motor 25 TFS 5 vel.\", null, 0, 'D', 5, 700, 95,\"G\"));");
		
		configuracionTest.establecerMotor(new MotorImpl(38, "Motor 25 TFS 5 vel.", null, 0, 'D', 5, 700, 95,"G"));
		
		try
		{	
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION");						//Se abre una transacción para no modificar los datos.
			
			System.out.println();
			
			System.out.println("gestionConfiguracion.actualizarMotorDeConfiguracion(configuracionTest);");
	
			gestionConfiguracion.actualizarMotorDeConfiguracion(configuracionTest);
			
			configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
			gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
			
			System.out.println("DESPUES -> " + configuracionTest.obtenerMotor().toString());
			
			statement.execute("ROLLBACK");								//No se guardarán los cambios realizados.
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("actualizarPinturaDeConfiguracion(configuracionTest)");
		
		System.out.println("configuracionTest = gestionConfiguracion.obtenerConfiguracion(\"A6221DF6-D42D-4F2C-84C6-CC153AF80EFE\");");
		
		configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
		gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
		
		System.out.println();
		
		System.out.println("Se cambia la pintura: configuracionTest.establecerPintura(new PinturaImpl(41, \"Verde tioman\", null, 325, \"verde\", \"solido\"));");
		
		configuracionTest.establecerPintura(new PinturaImpl(41, "Verde tioman", null, 325, "verde", "solido"));
		
		try
		{	
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION");						//Se abre una transacción para no modificar los datos.
			
			System.out.println();
			
			System.out.println("gestionConfiguracion.actualizarPinturaDeConfiguracion(configuracionTest);");
	
			gestionConfiguracion.actualizarPinturaDeConfiguracion(configuracionTest);
			
			configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
			gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionTest);
			
			System.out.println("DESPUES -> " + configuracionTest.obtenerPintura().toString());
			
			statement.execute("ROLLBACK");								//No se guardarán los cambios realizados.
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
		
		gestionConfiguracion.cargarCocheEnConfiguracion(configuracionTest);
		gestionConfiguracion.cargarCuentaEnConfiguracion(configuracionTest);
		gestionConfiguracion.cargarLlantasEnConfiguracion(configuracionTest);
		gestionConfiguracion.cargarMotorEnConfiguracion(configuracionTest);
		gestionConfiguracion.cargarPinturaEnConfiguracion(configuracionTest);
		gestionConfiguracion.cargarPiezasExtraEnConfiguracion(configuracionTest);
		gestionConfiguracion.cargarVotacionesEnConfiguracion(configuracionTest);
		
		System.out.println("cargarCocheEnConfiguracion(configuracionTest): " + configuracionTest.obtenerCoche().toString());
		System.out.println("gestionConfiguracion.cargarCuentaEnConfiguracion(configuracionTest): " + configuracionTest.obtenerCuenta());
		System.out.println("gestionConfiguracion.cargarLlantasEnConfiguracion(configuracionTest): " + configuracionTest.obtenerLlantas());
		System.out.println("gestionConfiguracion.cargarMotorEnConfiguracion(configuracionTest): " + configuracionTest.obtenerMotor());
		System.out.println("gestionConfiguracion.cargarPinturaEnConfiguracion(configuracionTest): " + configuracionTest.obtenerPintura());
		
		System.out.println();
		
		System.out.println("gestionConfiguracion.cargarPiezasExtraEnConfiguracion(configuracionTest)");
		for(PiezaImpl pieza:configuracionTest.obtenerPiezas())
		{
			System.out.println(pieza.toString());
		}
		
		System.out.println();
		
		System.out.println("gestionConfiguracion.cargarVotacionesEnConfiguracion(configuracionTest)");
		for(VotacionImpl votacion:configuracionTest.obtenerVotaciones())
		{
			System.out.println(votacion.getID() + " - calificacion: " + votacion.getCalificacion());
		}

		System.out.println("-------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		
		System.out.println("existeConfiguracion(configuracionTest): " + gestionConfiguracion.existeConfiguracion(configuracionTest));
	}
}
