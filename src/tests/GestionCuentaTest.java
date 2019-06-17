package tests;

import clases.basicas.ConfiguracionImpl;
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
	}

}
