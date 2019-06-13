package utils;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.VotacionImpl;
import clases.gestion.AObjeto;

public class Validaciones 
{
	private AObjeto gestion;
	
	public Validaciones(String URLConexion)
	{
		gestion = new AObjeto(URLConexion);
	}
	
	public void abrirConexion() { gestion.abrirConexion(); }
	public void cerrarConexion() { gestion.cerrarConexion(); }
	
	public int mostrarMenuPrincipalYValidarOpcion()
	{
		int opcion;
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("MENU PRINCIPAL");
		System.out.println("1) Iniciar Sesion");
		System.out.println("2) Registrarse");
		System.out.println("0) Salir");
		
		do
		{
			
			System.out.print("Elige una opcion: ");
			
			opcion = teclado.nextInt();
			
		}while(opcion < 0 || opcion > 2);
		
		return opcion;
	}
	
	/* INTERFAZ
	 * Comentario: Realiza el inicio de sesion comprobando que existe en la base de datos
	 * Prototipo: public CuentaImpl iniciarSesion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Una CuentaImpl con la cuenta que ha iniciado sesion
	 * Postcondiciones: Asociado al nombre devuelve una CuentaImpl con la cuenta que ha iniciado sesion
	 * 					Si el usuario no inicio sesion, la CuentaImpl es null.
	 */
	public CuentaImpl iniciarSesion()
	{
		CuentaImpl cuenta;
		Scanner teclado = new Scanner(System.in);
		String usuario;
		Utils utils = new Utils();
		String contrasena;
		boolean contrasenaCorrecta = false;
		
		do
		{
			System.out.print("Introduce nombre de usuario: ");
			usuario = teclado.nextLine();
			
			cuenta = gestion.obtenerCuenta(usuario);
			
		}while(cuenta == null);
		
		do
		{
			System.out.print("Introduce contrasena: ");
			contrasena = teclado.nextLine();
			contrasena = utils.obtenerMD5(contrasena);
			
			if(contrasena.equals(cuenta.getContrasena()))
				contrasenaCorrecta = true;
			
		}while(!contrasenaCorrecta);
		
		return cuenta;
	}
	
	public int mostrarMenuSesionYValidarOpcion()
	{
		int opcion;
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("MENU SESION");
		System.out.println("1) Nueva configuracion");
		System.out.println("2) Ver configuraciones propias");
		System.out.println("3) Ver configuraciones de la comunidad");
		System.out.println("4) Editar cuenta");
		System.out.println("0) Cerrar sesion");
		
		do
		{
			System.out.print("Elige una opcion: ");
			opcion = teclado.nextInt();
			
		}while(opcion < 0 || opcion > 4);
		
		return opcion;
	}
	
	public String validarNuevoNombreUsuario()
	{
		String usuario;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Introduce nuevo nombre de usuario: ");
			
			usuario = teclado.nextLine();
			
		}while(gestion.existeUsuario(usuario));
		
		return usuario;
	}
	
	public String contrasena()
	{
		Scanner teclado = new Scanner(System.in);
		
		String contrasena = "";
		Utils utils = new Utils();
		
		System.out.print("Introduce contrasena: ");
		contrasena = teclado.nextLine();
		
		contrasena = utils.obtenerMD5(contrasena);
		
		return contrasena;
	}
	
	public <T> T mostrarObjetosYValidarObjetoElegido(ArrayList<T> lista)
	{
		T objeto = null;
		Scanner teclado = new Scanner(System.in);
		int opcion;
		
		System.out.println("0) Volver atras");
		
		for(int i = 0 ; i < lista.size() ; i++)
		{
			System.out.println((i+1) + ") " + lista.get(i).toString());
		}
		
		do
		{
			System.out.print("Elige una opcion: ");
			opcion = teclado.nextInt();
		}while(opcion < 0 || opcion > (lista.size()));
		
		if(opcion > 0)
			objeto = lista.get(opcion-1);
		
		return objeto;
	}
	
	public int mostrarSubMenuConfiguracionElegidaYValidarOpcion()
	{
		int opcion;
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("0) Volver atras");
		System.out.println("1) Editar configurcion");
		System.out.println("2) Borrar configuracion");
		
		do
		{
			System.out.println("Elige una opcion: ");
			opcion = teclado.nextInt();
		}while(opcion < 0 || opcion > 2);
		
		return opcion;
	}
	
	public int mostrarMenuConfiguracionesComunidadYValidarOpcion()
	{
		int opcion;		
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("1) Todas las configuraciones");
		System.out.println("2) Buscar por marca");
		System.out.println("3) Buscar por marca y modelo");
		System.out.println("4) Buscar por usuario");
		System.out.println("5) Buscar por rango de precio");
		System.out.println("6) Buscar por fecha");
		System.out.println("0) Volver atras");
		
		do
		{
			System.out.print("Elige una opcion: ");
			opcion = teclado.nextInt();
			
		}while(opcion < 0 || opcion > 6);
		
		return opcion;
	}
	
	public int mostarMenuConfiguracionComunidadElegida()
	{
		int opcion;
		
		opcion = 0;
		
		return opcion;
	}
	
	public VotacionImpl validarCalificacion()
	{
		VotacionImpl votacion = null;
		
		return votacion;
	}
	
	public String mostrarListaMarcasYValidarMarcaElegida()
	{
		String marca = null;
		
		return marca;
	}
	
	public ConfiguracionImpl mostrarConfiguracionesYValidar(ArrayList<ConfiguracionImpl> configuraciones)
	{
		ConfiguracionImpl configuracionElegida = null;
		
		return configuracionElegida;
	}
	
	public String mostrarListaModelosYValidarModeloElegido(String marca)
	{
		String modelo = null;
		
		gestion.obtenerModelos(marca);
		
		return modelo;
	}
	
	public CuentaImpl validarUsuario()
	{
		CuentaImpl cuenta = null;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Nombre de usuario: ");
			cuenta = gestion.obtenerCuenta(teclado.nextLine());
		}while(cuenta == null);
		
		return cuenta;
	}
	
	public double validarPrecio()
	{
		double precio;
		
		precio = 0.0;
		
		return precio;
	}
	
	public GregorianCalendar leerYValidarFecha()
	{
		GregorianCalendar fecha = null;
		
		return fecha;
	}
	
	public static void main(String[] args)
	{
		Validaciones val = new Validaciones("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		AObjeto gestion = new AObjeto("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		val.abrirConexion();
		
		gestion.abrirConexion();
		
		CuentaImpl cuenta = gestion.obtenerCuenta("testuser");
		
		System.out.println(cuenta.getNombreUsuario());
		
		ArrayList<ConfiguracionImpl> lista = gestion.obtenerConfiguraciones(cuenta);
		
		for(ConfiguracionImpl confi:lista)
		{
			gestion.cargarRelacionesEnConfiguracion(confi);
		}
		
		//val.mostrarObjetosYValidarObjetoElegido(lista);
		
		//CocheImpl cochesito = val.mostrarObjetosYValidarObjetoElegido(gestion.obtenerCochesValidos(gestion.obtenerPieza(2)));
		
		//System.out.println(cochesito.getModelo());
		
		lista = gestion.obtenerConfiguraciones(gestion.obtenerCoche("AUDI", "A1"));
		
		for(ConfiguracionImpl confi:lista)
		{
			gestion.cargarRelacionesEnConfiguracion(confi);
		}
		
		ConfiguracionImpl opcion = val.mostrarObjetosYValidarObjetoElegido(lista);
		
		System.out.println(opcion.getFecha().getTime());
	}
}
