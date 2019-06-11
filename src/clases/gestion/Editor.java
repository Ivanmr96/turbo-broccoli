package clases.gestion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.LlantasImpl;
import clases.basicas.MotorImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.PinturaImpl;
import utils.Utils;

public class Editor 
{
	public PiezaImpl mostrarPiezasConfiguracionYValidarEleccion(ConfiguracionImpl configuracion)
	{
		Scanner teclado = new Scanner(System.in);
		PiezaImpl pieza = null;
		String opcion;
		int opcionNumerica;
		int cantidadPiezasExtra;
		boolean correcto;
		
		//CocheImpl coche = gestion.obtenerCoche(configuracion);
		CocheImpl coche = configuracion.obtenerCoche();
		
		System.out.println("Editando " + coche.getMarca() + " " + coche.getModelo());
		
		if(configuracion.obtenerMotor() != null)
			System.out.println("M) Motor: " + configuracion.obtenerMotor().getNombre());
		else
			System.out.println("M) Motor: Elige uno!");
		
		if(configuracion.obtenerLlantas() != null)
			System.out.println("L) Llantas: " + configuracion.obtenerLlantas().getNombre());
		else
			System.out.println("L) Llantas: Elige unas!");
		
		if(configuracion.obtenerPintura() != null)
			System.out.println("P) Pintura: " + configuracion.obtenerPintura().getNombre());
		else
			System.out.println("P) Pintura: Elige una!");
		
		ArrayList<PiezaImpl> piezasExtra = configuracion.obtenerPiezas();
		
		cantidadPiezasExtra = piezasExtra.size();
		
		for(int i = 0 ; i < piezasExtra.size() ; i++ )
		{
			System.out.println((i+1) + ") " + piezasExtra.get(i).getNombre());
		}
		
		System.out.println("+) Añade una pieza extra");
		
		do
		{
			System.out.print("Elige una opcion: ");
			opcion = teclado.next();
			
			/*if(opcion.equals("M") || opcion.equals("L") || opcion.equals("P"))
				opcionNumerica = 1;
			else
				opcionNumerica = Integer.parseInt(opcion);*/
			
			try
			{
				opcionNumerica = Integer.parseInt(opcion);
				
				//Es numero
				if(opcionNumerica < 0 || opcionNumerica > cantidadPiezasExtra)
					correcto = false;
				else
					correcto = true;
			}
			catch(NumberFormatException e)
			{
				//Es letra
				if(!opcion.equals("M") && !opcion.equals("L") && !opcion.equals("P"))
					correcto = false;
				else
					correcto = true;
			}
				
		
		}while(!correcto);
		
		if(!opcion.equals("M") && !opcion.equals("L") && !opcion.equals("P"))
			pieza = piezasExtra.get(opcionNumerica-1);
		else
			switch(opcion)
			{
				case "M": 
					pieza = configuracion.obtenerMotor();
					break;
				case "L":
					pieza = configuracion.obtenerLlantas();
					break;
				case "P":
					pieza = configuracion.obtenerPintura();
					break;
			}
		
		return pieza;
	}
	
	public static void main(String[] args) 
	{
		String URLConexion = "jdbc:sqlserver://localhost;"
						  + "database=Coches;"
						  + "user=usuarioCoches;"
						  + "password=123;";
		AObjeto gestion = new AObjeto(URLConexion);
		gestion.abrirConexion();
		
		
		
		//System.out.println(gestion.existeConfiguracion(new ConfiguracionImpl("5E825DA3-140D-4B16-BD28-31B359EA79AA", new GregorianCalendar())));
		
		//ConfiguracionImpl configuracion = gestion.obtenerConfiguracion("8722F525-3C36-4A79-B6CA-7DAB14BB23BF");
		
		ConfiguracionImpl configuracion = gestion.obtenerConfiguracion("45ABC141-4B82-48B9-A4C5-830A3EC3BE34");
		
		//ConfiguracionImpl configuracion = gestion.obtenerConfiguraciones().get(1);
		
		gestion.cargarRelacionesEnConfiguracion(configuracion);
		
		Editor ed = new Editor();
		
		PiezaImpl pz = ed.mostrarPiezasConfiguracionYValidarEleccion(configuracion);
		
		System.out.println(pz.getNombre());
		
		//TODO Método que devuelva todas las piezas de una configuracion menos el motor, las llantas, y la pintura.
		
	}
}
