package tests;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.VotacionImpl;
import clases.gestion.ConexionSQL;
import clases.gestion.GestionConfiguracion;
import clases.gestion.GestionCuenta;

public class GestionCuentaTest {

	public static void main(String[] args) 
	{
		ConexionSQL SQL = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		SQL.abrirConexion();

		GestionCuenta gestionCuenta = new GestionCuenta(SQL.getConexion());
		GestionConfiguracion gestionConfiguracion = new GestionConfiguracion(SQL.getConexion());
		
		ConfiguracionImpl configuracionTest = gestionConfiguracion.obtenerConfiguracion("A6221DF6-D42D-4F2C-84C6-CC153AF80EFE");
		
		System.out.println("obtenerCuenta(configuracionTest): " + gestionCuenta.obtenerCuenta(configuracionTest).toString());
		
		System.out.println("obtenerCuenta(\"testuser\"): " + gestionCuenta.obtenerCuenta("testuser").toString());
		
		System.out.println("existeUsuario(\"testuser\"): " + gestionCuenta.existeUsuario("testuser"));
		
		System.out.println("------------------------------------------------------------------------------------");
		
		CuentaImpl cuentaTest = gestionCuenta.obtenerCuenta("testuser");
		
		gestionCuenta.cargarConfiguracionesEnCuenta(cuentaTest);
		gestionConfiguracion.cargarRelacionesEnConfiguraciones(cuentaTest.obtenerConfiguraciones());
		
		gestionCuenta.cargarVotacionesEnCuenta(cuentaTest);
		
		System.out.println("cargarConfiguracionesEnCuenta(cuentaTest)");
		
		for(ConfiguracionImpl configuracion:cuentaTest.obtenerConfiguraciones())
		{
			System.out.println(configuracion.toString());
		}
		
		System.out.println();
		
		System.out.println("cargarVotacionesEnCuenta(cuentaTest)");
		
		for(VotacionImpl votacion:cuentaTest.obtenerVotaciones())
		{
			System.out.println(votacion.getID() + " - calificacion: " + votacion.getCalificacion());
		}
		
		System.out.println("------------------------------------------------------------------------------------");
		
		System.out.println("insertarCuenta(new CuentaImpl(\"nuevaCuentaTest\", \"123\"))");
		
		try
		{
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION");					//Se abre una transacción para que no se guarden los cambios al ejecutar el test.
			
			System.out.println("ANTES (existeUsuario \"nuevaCuentaTest\") -> " + gestionCuenta.existeUsuario("nuevaCuentaTest"));
			
			gestionCuenta.insertarCuenta(new CuentaImpl("nuevaCuentaTest", "123"));
			
			System.out.println("DESPUES (existeUsuario \"nuevaCuentaTest\") -> " + gestionCuenta.existeUsuario("nuevaCuentaTest"));
			
			statement.execute("ROLLBACK");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("------------------------------------------------------------------------------------");
		
		System.out.println("actualizarCuenta(new CuentaImpl(\"testuser\", \"7ad4109292636b7498a9f5042081becf\"))");
		
		try
		{
			Statement statement = SQL.getConexion().createStatement();
			
			statement.execute("BEGIN TRANSACTION");					//Se abre una transacción para que no se guarden los cambios al ejecutar el test.
			
			System.out.println("ANTES (contraseña) -> " + gestionCuenta.obtenerCuenta("testuser").getContrasena());
			
			gestionCuenta.actualizarCuenta(new CuentaImpl("testuser", "7ad4109292636b7498a9f5042081becf"));
			
			System.out.println("DESPUES (contraseña) -> " + gestionCuenta.obtenerCuenta("testuser").getContrasena());
			
			statement.execute("ROLLBACK");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

}
