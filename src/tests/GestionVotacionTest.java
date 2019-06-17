package tests;

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
	
//		insertarVotacion(Votacion)
	}

}
