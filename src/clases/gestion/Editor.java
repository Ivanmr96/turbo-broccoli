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
	
	public String MostrarMenuEdicionConfiguracionYValidarOpcion(ConfiguracionImpl configuracion)
	{
		String opcion;
		
		Scanner teclado = new Scanner(System.in);
		PiezaImpl pieza = null;
		int opcionNumerica;
		int cantidadPiezasExtra;
		boolean correcto;
		
		//CocheImpl coche = gestion.obtenerCoche(configuracion);
		CocheImpl coche = configuracion.obtenerCoche();
		
		System.out.println("Editando " + coche.getMarca() + " " + coche.getModelo());
		
		System.out.println("0) Volver atras");
		
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
			System.out.println();
			opcionNumerica = 0;
			
			if(esNumero(opcion))
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
				if(!opcion.equals("M") && !opcion.equals("L") && !opcion.equals("P") && !opcion.equals("+"))
				{
					correcto = false;
				}
				else
				{
					correcto = true;
				}
			}
			
		}while(!correcto);
		
		return opcion;
		
	}
	
	public PiezaImpl mostrarPiezasConfiguracionYValidarEleccion(ConfiguracionImpl configuracion)
	{
		//ArrayList<PiezaImpl> piezasValidas = configuracion.obtenerCoche().obtenerPiezasValidas();
		CocheImpl coche = configuracion.obtenerCoche();
		
		
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
		
		gestion.cargarPiezasValidasEnCoche(coche);
		
		Scanner teclado = new Scanner(System.in);
		PiezaImpl pieza = null;
		String opcion;
		
		opcion = MostrarMenuEdicionConfiguracionYValidarOpcion(configuracion);

		ArrayList<PiezaImpl> piezasExtra = configuracion.obtenerPiezas();
		
		if(!opcion.equals("M") && !opcion.equals("L") && !opcion.equals("P") && !opcion.equals("+"))
		{
			if(Integer.parseInt(opcion) > 0) 
			{
				pieza = piezasExtra.get(Integer.parseInt(opcion)-1);
				configuracion.eliminarPiezaExtra(pieza);
			}
			else
				pieza = null;
		}
		else
			switch(opcion)
			{
				case "M": 
					if(configuracion.obtenerMotor() == null)
					{
						System.out.println("MOTORES DISPONIBLES");
						pieza = v.mostrarObjetosYValidarObjetoElegido(coche.obtenerMotoresValidos());
						System.out.println();
						configuracion.establecerMotor((MotorImpl)pieza);
					}
					else
						pieza = configuracion.obtenerMotor();
					break;
				case "L":
					if(configuracion.obtenerLlantas() == null)
					{
						System.out.println("LLANTAS DISPONIBLES");
						pieza = v.mostrarObjetosYValidarObjetoElegido(coche.obtenerLlantasValidas());
						System.out.println();
						configuracion.establecerLlantas((LlantasImpl)pieza);
					}
					else
						pieza = configuracion.obtenerLlantas();
					break;
				case "P":
					if(configuracion.obtenerPintura() == null)
					{
						System.out.println("PINTURAS DISPONIBLES");
						pieza = v.mostrarObjetosYValidarObjetoElegido(coche.obtenerPinturasValidas());
						System.out.println();
						configuracion.establecerPintura((PinturaImpl)pieza);
					}
					else
						pieza = configuracion.obtenerPintura();
					break;
				case "+":
					System.out.println("PIEZAS EXTRA DISPONIBLES");
					gestion.cargarPiezasValidasEnCoche(configuracion.obtenerCoche());
					pieza = v.mostrarObjetosYValidarObjetoElegido(coche.obtenerPiezasExtraValidas());
					if(pieza != null)
						configuracion.anhadirPiezaExtra(pieza);
					System.out.println();
					
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
		
		ConfiguracionImpl configuracion = gestion.obtenerConfiguracion("97D86E81-491B-46FE-9BBD-BC6D54520FFA");
		
		
		gestion.cargarRelacionesEnConfiguracion(configuracion);
		
		Editor ed = new Editor();
		PiezaImpl pz = null;
		do
		{
			pz = ed.mostrarPiezasConfiguracionYValidarEleccion(configuracion);
		}while(pz != null);
		
		System.out.println(pz.getNombre());
		
		//TODO Método que devuelva todas las piezas de una configuracion menos el motor, las llantas, y la pintura.
		
	}
}
