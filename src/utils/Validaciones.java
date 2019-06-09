package utils;

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
		
		opcion = 0;
		
		return opcion;
	}
	
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
			contrasena = utils.obtenerMD5(teclado.nextLine());
			
			if(contrasena.equals(cuenta.getContrasena()))
				contrasenaCorrecta = true;
			
		}while(!contrasenaCorrecta);
		
		cuenta = new CuentaImpl();
		
		return cuenta;
	}
	
	public int mostrarMenuSesionYValidarOpcion()
	{
		int opcion;
		
		opcion = 0;
		
		return opcion;
	}
	
	public String validarNuevoNombreUsuario()
	{
		String usuario;
		
		usuario = "";
		
		return usuario;
	}
	
	public String contrasena()
	{
		String contrasena = "";
		Utils utils = new Utils();
		
		contrasena = utils.obtenerMD5(contrasena);
		
		return contrasena;
	}
	
	public CocheImpl mostrarCochesDisponiblesYValidarOpcionCocheElegido()
	{
		CocheImpl cocheElegido = null;
		
		return cocheElegido;
	}
	
	public ConfiguracionImpl mostrarConfiguracionesPropiasYValidarOpcionElegida(CuentaImpl cuentaSesion)
	{
		ConfiguracionImpl configuracionElegida = null;
		
		return configuracionElegida;
	}
	
	public int mostrarSubMenuConfiguracionElegidaYValidarOpcion()
	{
		int opcion;
		
		opcion = 0;
		
		return opcion;
	}
	
	public int mostrarMenuConfiguracionesComunidadYValidarOpcion()
	{
		int opcion;
		
		opcion = 0;
		
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
	
	public static void main(String[] args)
	{
		Validaciones val = new Validaciones("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		val.abrirConexion();
		val.iniciarSesion();
	}
}
