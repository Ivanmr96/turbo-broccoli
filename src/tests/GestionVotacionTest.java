package tests;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.VotacionImpl;
import clases.gestion.ConexionSQL;
import clases.gestion.GestionConfiguracion;
import clases.gestion.GestionCuenta;
import clases.gestion.GestionVotacion;

public class GestionVotacionTest 
{
	public static void main(String[] args) 
	{
		ConexionSQL SQL = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		SQL.abrirConexion();
		
		GestionVotacion gestionVotacion = new GestionVotacion(SQL.getConexion());
		GestionConfiguracion gestionConfiguracion = new GestionConfiguracion(SQL.getConexion());
		GestionCuenta gestionCuenta = new GestionCuenta(SQL.getConexion());
		
		ConfiguracionImpl configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
		CuentaImpl cuentaTest = gestionCuenta.obtenerCuenta("testuser");
		
		System.out.println("obtenerVotaciones(configuracionTest)");
		for(VotacionImpl votacion:gestionVotacion.obtenerVotaciones(configuracionTest))
		{
			System.out.println(votacion.getFecha().getTime() +  " - calificacion: " + votacion.getCalificacion());
		}
		System.out.println();
		
		System.out.println("obtenerVotaciones(cuentaTest)");
		for(VotacionImpl votacion:gestionVotacion.obtenerVotaciones(cuentaTest))
		{
			System.out.println(votacion.getFecha().getTime() +  " - calificacion: " + votacion.getCalificacion());
		}
		System.out.println();
	
		System.out.println("insertarVotacion(new VotacionImpl(\"5813a7ac-84d2-40bf-87ca-9f8dd35183af\", new GregorianCalendar(), 7)");
		
		try 
		{
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION"); 			//Se realiza una transacción para que no se guarden los cambios.
			
			VotacionImpl votacionTest = new VotacionImpl("5813a7ac-84d2-40bf-87ca-9f8dd35183af", new GregorianCalendar(), 7);
			votacionTest.establecerConfiguracion(configuracionTest);
			votacionTest.establecerCuenta(cuentaTest);
			
			boolean insertada = gestionVotacion.insertarVotacion(votacionTest);
			
			if(insertada)
				System.out.println("Votacion insertada.");
			else
				System.out.println("No se insertó la votación.");
			
			statement.execute("ROLLBACK");						//No se guardarán los cambios
		}
		catch(SQLServerException e)
		{
			System.out.println(e.getMessage());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
