package clases.gestion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.LlantasImpl;
import clases.basicas.MotorImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.PinturaImpl;
import utils.Utils;

public class Editor 
{
	public static void main(String[] args) 
	{
		String URLConexion = "jdbc:sqlserver://localhost;"
						  + "database=Coches;"
						  + "user=usuarioCoches;"
						  + "password=123;";
		AObjeto gestion = new AObjeto(URLConexion);
		gestion.abrirConexion();
		
		
		
		//System.out.println(gestion.existeConfiguracion(new ConfiguracionImpl("5E825DA3-140D-4B16-BD28-31B359EA79AA", new GregorianCalendar())));
		
		ConfiguracionImpl configuracion = gestion.obtenerConfiguracion("B8187208-D872-4832-B901-40FA672B12E3");
		
		//ConfiguracionImpl configuracion = gestion.obtenerConfiguraciones().get(1);
		
		gestion.cargarRelacionesEnConfiguracion(configuracion);
		
		CocheImpl coche = gestion.obtenerCoche(configuracion);
		
		System.out.println("Editando " + coche.getMarca() + " " + coche.getModelo());
		
		if(gestion.obtenerPiezaMotor(configuracion) != null)
			System.out.println("M) Motor: " + gestion.obtenerPiezaMotor(configuracion).getNombre());
		else
			System.out.println("M) Motor: Elige uno!");
		
		if(gestion.obtenerPiezaLlantas(configuracion) != null)
			System.out.println("L) Llantas: " + gestion.obtenerPiezaLlantas(configuracion).getNombre());
		else
			System.out.println("L) Llantas: Elige unas!");
		
		if(gestion.obtenerPiezaPintura(configuracion) != null)
			System.out.println("P) Pintura: " + gestion.obtenerPiezaPintura(configuracion).getNombre());
		else
			System.out.println("P) Pintura: Elige una!");
		
		
		
		System.out.println("+) Añade una pieza extra");
		
		//TODO Método que devuelva todas las piezas de una configuracion menos el motor, las llantas, y la pintura.
		
		//System.out.println(configuracion.obtenerCoche().getMarca());
		
		System.out.println("---------------------------------");
		
		ArrayList<PiezaImpl> piezasExtra = new ArrayList<PiezaImpl>();
		
		for(PiezaImpl pieza:configuracion.obtenerPiezas())
		{
			if(!(pieza instanceof MotorImpl) &&
			   !(pieza instanceof LlantasImpl) &&
			   !(pieza instanceof PinturaImpl))
				piezasExtra.add(pieza);
		}
		
		
		for(PiezaImpl piezaExtra:piezasExtra)
		{
			System.out.println(piezaExtra.getNombre());
		}
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		System.out.println(configuracion.obtenerMotor().getNombre());
		
		System.out.println(configuracion.obtenerLlantas().getNombre());
		
		System.out.println(configuracion.obtenerPintura().getNombre());
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		for(ConfiguracionImpl confi:gestion.obtenerConfiguraciones("AUDI"))
		{
			System.out.println(confi.getID());
		}
		
		System.out.println();
		
		GregorianCalendar fechaActual = new GregorianCalendar();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		System.out.println(sdf.format(fechaActual.getTime()));
		
		System.out.println("-------------------------");
		
		Utils utils = new Utils();
		
	}
}
