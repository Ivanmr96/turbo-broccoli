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
import utils.Validaciones;

public class Editor 
{
	public boolean esNumero(String caracteres)
	{
		boolean esNumero;
		
		try
		{
			Integer.parseInt(caracteres);
			
			esNumero = true;
		}
		catch(NumberFormatException e)
		{
			esNumero = false;
		}
		
		return esNumero;
	}
	
	public PiezaImpl mostrarPiezasConfiguracionYValidarEleccion(ConfiguracionImpl configuracion)
	{
		Editor e = new Editor();
		Validaciones v = new Validaciones("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		v.abrirConexion();
		AObjeto gestion = new AObjeto("jdbc:sqlserver://localhost;"
						  + "database=Coches;"
						  + "user=usuarioCoches;"
						  + "password=123;");
		gestion.abrirConexion();
		
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
		
		System.out.println("+) A�ade una pieza extra");
		
		do
		{
			System.out.print("Elige una opcion: ");
			opcion = teclado.next();
			opcionNumerica = 0;
			
			if(e.esNumero(opcion))
			{
				opcionNumerica = Integer.parseInt(opcion);
				
				if(opcionNumerica < 0 || opcionNumerica > cantidadPiezasExtra)
				{
					correcto = false;
				}
				else
				{
					correcto = true;
				}
			}
			else
			{
				if(!opcion.equals("M") && !opcion.equals("L") && !opcion.equals("P"))
				{
					correcto = false;
				}
				else
				{
					correcto = true;
				}
			}
			
		}while(!correcto);
		
		ArrayList<PiezaImpl> piezasValidas = gestion.obtenerPiezasValidas(configuracion.obtenerCoche());
		
		ArrayList<MotorImpl> motoresValidos = new ArrayList<MotorImpl>();
		
		for(PiezaImpl piezaValida:piezasValidas)
		{
			if(piezaValida instanceof MotorImpl)
				motoresValidos.add((MotorImpl)piezaValida);
		}
		
		if(!opcion.equals("M") && !opcion.equals("L") && !opcion.equals("P"))
			pieza = piezasExtra.get(opcionNumerica-1);
		else
			switch(opcion)
			{
				case "M": 
					//pieza = configuracion.obtenerMotor();
					if(configuracion.obtenerMotor() == null)
						pieza = v.mostrarObjetosYValidarObjetoElegido(motoresValidos);
					else
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
		
		ConfiguracionImpl configuracion = gestion.obtenerConfiguracion("7D5B2482-4411-4A96-AB1B-0BC2E6D5E87C");
		
		//ConfiguracionImpl configuracion = gestion.obtenerConfiguraciones().get(1);
		
		gestion.cargarRelacionesEnConfiguracion(configuracion);
		
		Editor ed = new Editor();
		
		PiezaImpl pz = ed.mostrarPiezasConfiguracionYValidarEleccion(configuracion);
		
		System.out.println(pz.getNombre());
		
		//TODO M�todo que devuelva todas las piezas de una configuracion menos el motor, las llantas, y la pintura.
		
	}
}
