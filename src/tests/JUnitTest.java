package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.MotorImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.VotacionImpl;
import clases.gestion.ConexionSQL;
import clases.gestion.GestionCoche;
import clases.gestion.GestionConfiguracion;
import clases.gestion.GestionCuenta;
import clases.gestion.GestionPieza;
import clases.gestion.GestionVotacion;
import utils.Utils;

class JUnitTest {
	
	ConexionSQL conexion;
	
	@Test
	void testObtenerMD5()
	{
		Utils utils = new Utils();
		
		assertEquals(utils.obtenerMD5("123"), "202cb962ac59075b964b07152d234b70");
	}
	
	@Test
	void testGregorianCalendarToDateTime()
	{
		Utils utils = new Utils();
		
		assertEquals(utils.GregorianCalendarToDateTime(new GregorianCalendar(2003, 6, 3, 15, 27, 33)), "2003-3-7 15:27:33");
	}
	
	@Test
	void testdateTimeToGregorianCalendar()
	{
		Utils utils = new Utils();
		
		assertEquals(utils.dateTimeToGregorianCalendar("1998-4-16 17:55:12").getTime(), new GregorianCalendar(1998, 4, 16, 17, 55, 12).getTime());
	}
	
	@Test
	void testObtenerPieza()
	{
		ConexionSQL SQL = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		SQL.abrirConexion();
		
		GestionPieza gestionPieza = new GestionPieza(SQL.getConexion());
		
		PiezaImpl pieza = gestionPieza.obtenerPieza(46);
		
		assertEquals(pieza.getID(), 46);
		
		assertEquals(pieza.getNombre(), "Llantas 16 pulgadas");
		
		assertEquals(pieza.getDescripcion(), "Llantas de aleacion ligera  de 10 radios");
		
		assertEquals(pieza.getPrecio(), 765.00);
	}
	
	@Test
	void testObtenerCuenta()
	{
		ConexionSQL SQL = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		SQL.abrirConexion();
		
		GestionCuenta gestionCuenta = new GestionCuenta(SQL.getConexion());
		
		CuentaImpl cuenta = gestionCuenta.obtenerCuenta("testuser");
		
		assertEquals(cuenta.getNombreUsuario(), "testuser");
		
		assertEquals(cuenta.getContrasena(), "202cb962ac59075b964b07152d234b70");
	}
	
	@Test
	void testObtenerConfiguracion()
	{
		ConexionSQL SQL = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		SQL.abrirConexion();
		
		GestionConfiguracion gestionConfiguracion = new GestionConfiguracion(SQL.getConexion());
		
		ConfiguracionImpl configuracion = gestionConfiguracion.obtenerConfiguracion("EF49FC93-2EEF-4F9A-BBBD-B4CF232D17A9");
		
		assertEquals(configuracion.getID(), "EF49FC93-2EEF-4F9A-BBBD-B4CF232D17A9");
		
		assertEquals(configuracion.getFecha(), new GregorianCalendar(2019, 6, 15, 16, 21, 8));
	}
	
	@Test
	void testObtenerCoche()
	{
		ConexionSQL SQL = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		SQL.abrirConexion();
		
		GestionCoche gestionCoche = new GestionCoche(SQL.getConexion());
		
		CocheImpl coche = gestionCoche.obtenerCoche("Mercedes", "Clase A");
		
		assertEquals(coche.getMarca(), "Mercedes");
		
		assertEquals(coche.getModelo(), "Clase A");
		
		assertEquals(coche.getPrecioBase(), 29225.00);
	}
	
	@Test
	void testObtenerVotacion()
	{
		ConexionSQL SQL = new ConexionSQL("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		SQL.abrirConexion();
		
		GestionVotacion gestionVotacion = new GestionVotacion(SQL.getConexion());
		GestionCuenta gestionCuenta = new GestionCuenta(SQL.getConexion());
		
		CuentaImpl cuenta = gestionCuenta.obtenerCuenta("testuser");
		
		ArrayList<VotacionImpl> votaciones = gestionVotacion.obtenerVotaciones(cuenta);
		
		VotacionImpl votacion = votaciones.get(0);
		
		assertEquals(votacion.getID(), "9557B742-02C6-40DC-9F5F-02F6A4E1C720");
		
		assertEquals(votacion.getCalificacion(), 3);
		
		assertEquals(votacion.getFecha(), new GregorianCalendar(2019, 6, 14, 21, 10, 39));
	}

}
