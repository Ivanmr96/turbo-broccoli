package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;

import clases.basicas.ConfiguracionImpl;
import clases.basicas.MotorImpl;
import clases.gestion.ConexionSQL;
import clases.gestion.GestionConfiguracion;
import clases.gestion.GestionPieza;

public class xd {

	public static void main(String[] args) 
	{
		ConexionSQL conexion = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		conexion.abrirConexion();
		
		
		
		GestionConfiguracion gestionConfiguracion = new GestionConfiguracion(conexion.getConexion());
		
		GestionPieza gestionPieza = new GestionPieza(conexion.getConexion());
		
		ConfiguracionImpl configuracion = gestionConfiguracion.obtenerConfiguracion("ef49fc93-2eef-4f9a-bbbd-b4cf232d17a9");
		
		boolean motorActualizado = gestionConfiguracion.actualizarMotorDeConfiguracion(configuracion);
		
		gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracion);
		
		System.out.println(configuracion.toString());
		
		MotorImpl motor = gestionPieza.obtenerPiezaMotor(configuracion);
		
		System.out.println(motor.toString());
	}

}
