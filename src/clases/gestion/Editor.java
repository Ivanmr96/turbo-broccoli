package clases.gestion;

import java.util.GregorianCalendar;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;

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
		
		ConfiguracionImpl configuracion = gestion.obtenerConfiguracion("5E825DA3-140D-4B16-BD28-31B359EA79AA");
		
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
	}

}
