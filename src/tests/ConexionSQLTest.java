package tests;

import java.sql.Connection;
import java.sql.SQLException;

import clases.gestion.ConexionSQL;

public class ConexionSQLTest {

	public static void main(String[] args) 
	{
		ConexionSQL conexion = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		System.out.println("conexion.abrirConexion()");
		
		conexion.abrirConexion();
		
		Connection con = conexion.getConexion();
		
		System.out.println(con.toString());
		

		try {
			System.out.println("Esta cerrada: " + con.isClosed());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("conexion.cerrarConexion()");
		
		conexion.cerrarConexion();
		
		try {
			System.out.println("Esta cerrada: " + con.isClosed());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
