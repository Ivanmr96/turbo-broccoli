package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import clases.basicas.ConfiguracionImpl;
import clases.basicas.MotorImpl;
import clases.gestion.ConexionSQL;
import clases.gestion.GestionConfiguracion;
import clases.gestion.GestionPieza;
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

}
