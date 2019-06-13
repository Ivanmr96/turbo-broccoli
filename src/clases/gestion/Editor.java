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
			System.out.println();
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
		
		Scanner teclado = new Scanner(System.in);
		PiezaImpl pieza = null;
		String opcion;
		
		opcion = MostrarMenuEdicionConfiguracionYValidarOpcion(configuracion);
		
		ArrayList<PiezaImpl> piezasValidas = gestion.obtenerPiezasValidas(configuracion.obtenerCoche());
		//ArrayList<PiezaImpl> piezasValidas = coche.obtenerPiezasValidas();
		
		ArrayList<MotorImpl> motoresValidos = new ArrayList<MotorImpl>();
		//ArrayList<MotorImpl> motoresValidos = coche.obtenerMotoresValidos();
		
		ArrayList<LlantasImpl> llantasValidas = new ArrayList<LlantasImpl>();
		//ArrayList<LlantasImpl> llantasValidas = coche.obtenerLlantasValidas();
		
		ArrayList<PinturaImpl> pinturasValidas = new ArrayList<PinturaImpl>();
		//ArrayList<PinturaImpl> pinturasValidas = coche.obtenerPinturasValidas();
		
		ArrayList<PiezaImpl> piezasExtrasValidas = new ArrayList<PiezaImpl>();
		//ArrayList<PiezaImpl> piezasExtrasValidas = coche.obtenerPiezasExtraValidas();
		
		for(PiezaImpl piezaValida:piezasValidas)
		{
			if(piezaValida instanceof MotorImpl)
				motoresValidos.add((MotorImpl)piezaValida);
			else if(piezaValida instanceof LlantasImpl)
				llantasValidas.add((LlantasImpl)piezaValida);
			else if(piezaValida instanceof PinturaImpl)
				pinturasValidas.add((PinturaImpl)piezaValida);
			else
				piezasExtrasValidas.add(piezaValida);
		}
		
		ArrayList<PiezaImpl> piezasExtra = configuracion.obtenerPiezas();
		
		int cantidadPiezasExtra = piezasExtra.size();
		
		if(!opcion.equals("M") && !opcion.equals("L") && !opcion.equals("P") && !opcion.equals("+"))
		{
			pieza = piezasExtra.get(Integer.parseInt(opcion)-1);
			configuracion.eliminarPiezaExtra(pieza);
		}
		else
			switch(opcion)
			{
				case "M": 
					//pieza = configuracion.obtenerMotor();
					if(configuracion.obtenerMotor() == null)
					{
						System.out.println("MOTORES DISPONIBLES");
						pieza = v.mostrarObjetosYValidarObjetoElegido(motoresValidos);
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
						pieza = v.mostrarObjetosYValidarObjetoElegido(llantasValidas);
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
						pieza = v.mostrarObjetosYValidarObjetoElegido(pinturasValidas);
						System.out.println();
						configuracion.establecerPintura((PinturaImpl)pieza);
					}
					else
						pieza = configuracion.obtenerPintura();
					break;
				case "+":
					System.out.println("PIEZAS EXTRA DISPONIBLES");
					gestion.cargarPiezasValidasEnCoche(configuracion.obtenerCoche());
					pieza = v.mostrarObjetosYValidarObjetoElegido(piezasExtrasValidas);
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
		
		//System.out.println(gestion.existeConfiguracion(new ConfiguracionImpl("5E825DA3-140D-4B16-BD28-31B359EA79AA", new GregorianCalendar())));
		
		//ConfiguracionImpl configuracion = gestion.obtenerConfiguracion("8722F525-3C36-4A79-B6CA-7DAB14BB23BF");
		
		ConfiguracionImpl configuracion = gestion.obtenerConfiguracion("098D14F3-4354-4E65-9DA2-2246AABFA9AF");
		
		//ConfiguracionImpl configuracion = gestion.obtenerConfiguraciones().get(1);
		
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
