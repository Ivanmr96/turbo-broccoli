package utils;

import java.io.Console;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.UUID;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.VotacionImpl;
import clases.gestion.GestionCoche;
import clases.gestion.GestionConfiguracion;
import clases.gestion.GestionCuenta;
import clases.gestion.GestionPieza;
import clases.gestion.GestionVotacion;

public class Validaciones 
{
	private GestionCoche gestionCoche;
	private GestionConfiguracion gestionConfiguracion;
	private GestionCuenta gestionCuenta;
	private GestionPieza gestionPieza;
	private GestionVotacion gestionVotacion;
	
	public Validaciones(Connection conexion)
	{
		gestionCoche = new GestionCoche(conexion);
		gestionConfiguracion = new GestionConfiguracion(conexion);
		gestionCuenta = new GestionCuenta(conexion);
		gestionPieza = new GestionPieza(conexion);
		gestionVotacion = new GestionVotacion(conexion);
	}
	
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
		Console cons = System.console();
		String usuario;
		Utils utils = new Utils();
		String contrasena;
		boolean contrasenaCorrecta = false;
		
		do
		{
			System.out.print("Introduce nombre de usuario: ");
			usuario = teclado.nextLine();
			
			cuenta = gestionCuenta.obtenerCuenta(usuario);
			
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
			
		}while(gestionCuenta.existeUsuario(usuario));
		
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
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("0) Volver atras");
		System.out.println("1) Votar esta configuracion");
		
		do
		{
			System.out.print("Elige una opcion: ");
			opcion = teclado.nextInt();
			
		}while(opcion < 0 || opcion > 1);
		
		return opcion;
	}
	
	public VotacionImpl validarCalificacion()
	{
		VotacionImpl votacion = null;
		int valoracion;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Ingresa tu valoracion (0 - 10): ");
			valoracion = teclado.nextInt();
		}while(valoracion < 0 || valoracion > 10);
		
		votacion = new VotacionImpl(UUID.randomUUID().toString(), new GregorianCalendar(), valoracion);
		
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
		
		gestionCoche.obtenerModelos(marca);
		
		return modelo;
	}
	
	public CuentaImpl validarUsuario()
	{
		CuentaImpl cuenta = null;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Nombre de usuario: ");
			cuenta = gestionCuenta.obtenerCuenta(teclado.nextLine());
		}while(cuenta == null);
		
		return cuenta;
	}
	
	public double validarPrecioMinimo()
	{
		double precio;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Inserta precio minimo a buscar: ");
			precio = teclado.nextDouble();
		}while(precio < 0.0);
		
		return precio;
	}
	
	public double validarPrecioMaximo(double precioMinimo)
	{
		double precio;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Inserta precio maximo a buscar: ");
			precio = teclado.nextDouble();
		}while(precio < precioMinimo);
		
		return precio;
	}
	
	public GregorianCalendar leerYValidarFecha()
	{
		GregorianCalendar fecha = new GregorianCalendar();
		
		return fecha;
	}
	
	public String MostrarMenuEdicionConfiguracionYValidarOpcion(ConfiguracionImpl configuracion)
	{
		String opcion;
		Utils utils = new Utils();
		Scanner teclado = new Scanner(System.in);
		PiezaImpl pieza = null;
		int opcionNumerica;
		int cantidadPiezasExtra;
		boolean correcto;
		
		//CocheImpl coche = gestion.obtenerCoche(configuracion);
		CocheImpl coche = configuracion.obtenerCoche();
		
		System.out.println("Editando " + coche.getMarca() + " " + coche.getModelo() + " - Total: " + configuracion.obtenerPrecioTotal() + " �");
		
		System.out.println("0) Volver atras");
		
		if(configuracion.obtenerMotor() != null)
			System.out.println("M) Motor: " + configuracion.obtenerMotor().getNombre() + " - " + configuracion.obtenerMotor().getPrecio() + " �");
		else
			System.out.println("M) Motor: Elige uno!");
		
		if(configuracion.obtenerLlantas() != null)
			System.out.println("L) Llantas: " + configuracion.obtenerLlantas().getNombre() + " - " + configuracion.obtenerLlantas().getPrecio() + " �");
		else
			System.out.println("L) Llantas: Elige unas!");
		
		if(configuracion.obtenerPintura() != null)
			System.out.println("P) Pintura: " + configuracion.obtenerPintura().getNombre() + " - " + configuracion.obtenerPintura().getPrecio() + " �");
		else
			System.out.println("P) Pintura: Elige una!");
		
		ArrayList<PiezaImpl> piezasExtra = configuracion.obtenerPiezas();
		
		cantidadPiezasExtra = piezasExtra.size();
		
		for(int i = 0 ; i < piezasExtra.size() ; i++ )
		{
			System.out.println((i+1) + ") Eliminar " + piezasExtra.get(i).getNombre() + " - " + piezasExtra.get(i).getPrecio() + " �");
		}
		
		System.out.println("+) A�ade una pieza extra");
		
		do
		{
			System.out.print("Elige una opcion: ");
			opcion = teclado.next();
			System.out.println();
			opcionNumerica = 0;
			
			if(utils.esNumero(opcion))
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
	
	public PiezaImpl mostrarMotoresDisponiblesYElegirMotor(ConfiguracionImpl configuracion)
	{
		PiezaImpl piezaElegida;
		//if(configuracion.obtenerMotor() == null)
		//{
			System.out.println("MOTORES DISPONIBLES");
			//piezaElegida = mostrarObjetosYValidarObjetoElegido(cocheEdicionConfiguracion.obtenerMotoresValidos());
			piezaElegida = mostrarObjetosYValidarObjetoElegido(configuracion.obtenerCoche().obtenerMotoresValidos());
			System.out.println();
			//configuracion.establecerMotor((MotorImpl)piezaElegida);
			//}
		//else
		//piezaElegida = configuracion.obtenerMotor();
		
		return piezaElegida;
	}
	
	public PiezaImpl mostrarLlantasDisponiblesYElegirLlantas(ConfiguracionImpl configuracion)
	{
		PiezaImpl piezaElegida;
		
			System.out.println("LLANTAS DISPONIBLES");
			piezaElegida = mostrarObjetosYValidarObjetoElegido(configuracion.obtenerCoche().obtenerLlantasValidas());
			System.out.println();
			//configuracion.establecerLlantas((LlantasImpl)piezaElegida);
			
		return piezaElegida;
	}
	
	public PiezaImpl mostrarPinturasDisponiblesYElegirPintura(ConfiguracionImpl configuracion)
	{
		PiezaImpl piezaElegida;
		
			System.out.println("PINTURAS DISPONIBLES");
			piezaElegida = mostrarObjetosYValidarObjetoElegido(configuracion.obtenerCoche().obtenerPinturasValidas());
			System.out.println();
			//configuracion.establecerPintura((PinturaImpl)piezaElegida);
		
		return piezaElegida;
	}
	
	public PiezaImpl mostrarPiezasExtraDisponiblesYElegirPiezaExtra(ConfiguracionImpl configuracion)
	{
		PiezaImpl piezaElegida;
		
		System.out.println("PIEZAS EXTRA DISPONIBLES");
		gestionCoche.cargarPiezasValidasEnCoche(configuracion.obtenerCoche());
		piezaElegida = mostrarObjetosYValidarObjetoElegido(configuracion.obtenerCoche().obtenerPiezasExtraValidas());
		
		if(piezaElegida != null)
			configuracion.anhadirPiezaExtra(piezaElegida);
		System.out.println();
		
		return piezaElegida;
	}
	
	public char confirmarBorrarConfiguracion()
	{
		char confirmadoEliminarConfiguracion;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.println("Estas seguro que quieres borrar la configuracion?(S/N): ");
			confirmadoEliminarConfiguracion = Character.toUpperCase(teclado.next().charAt(0));
		}while(confirmadoEliminarConfiguracion != 'S' && confirmadoEliminarConfiguracion != 'N');
		
		return confirmadoEliminarConfiguracion;
	}
	
	public char confirmarGuardarConfiguracion()
	{
		char confirmarGuardarConfiguracion;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.println("Quieres guardar la configuracion?(S/N)");
			confirmarGuardarConfiguracion = Character.toUpperCase(teclado.next().charAt(0));
			
		}while(confirmarGuardarConfiguracion != 'S' && confirmarGuardarConfiguracion != 'N');
		
		return confirmarGuardarConfiguracion;
	}
	
	public boolean confirmarBorrarCuenta()
	{
		boolean confirmar = false;
		char respuesta;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Est�s segur@ que quieres borrar tu cuenta? Se borrar�n todas las configuraciones y votaciones realizadas. (S/N): ");
			respuesta = Character.toUpperCase(teclado.next().charAt(0));
			
		}while(respuesta != 'S' && respuesta != 'N');
		
		if(respuesta == 'S')
			confirmar = true;
		
		return confirmar;
	}
	
	public boolean validarContrasenaActual(CuentaImpl cuenta)
	{
		boolean contrasenaCorrecta = false;
		String contrasenaActual;
		Scanner teclado = new Scanner(System.in);
		Utils utils = new Utils();
		int intentos = 5;
		
		do
		{
			System.out.print("(" + intentos + " intentos restantes) Introduce la contrase�a actual de la cuenta: ");
			contrasenaActual = teclado.nextLine();
			contrasenaActual = utils.obtenerMD5(contrasenaActual);
			
			intentos--;
			
		}while(!contrasenaActual.equals(cuenta.getContrasena()) && intentos > 0);
		
		if(contrasenaActual.equals(cuenta.getContrasena()))
			contrasenaCorrecta = true;
		else
			System.out.println("Ya no te quedan intentos.");
		
		return contrasenaCorrecta;
	}
	
	public String validarNuevaContrasena(String contrasenaActual)
	{
		String nuevaContrasena;
		Scanner teclado = new Scanner(System.in);
		Utils utils = new Utils();
		
		do
		{
			System.out.print("Introduce tu nueva contrase�a: ");
			nuevaContrasena = teclado.nextLine();
			nuevaContrasena = utils.obtenerMD5(nuevaContrasena);
			
		}while(nuevaContrasena.equals(contrasenaActual));
		
		return nuevaContrasena;
	}
	
	public int mostrarMenuEditarCuentaYValidarOpcion()
	{
		int opcion;
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("0) Volver atras");
		System.out.println("1) Borrar cuenta");
		System.out.println("2) Cambiar contrase�a");
		
		do
		{
			System.out.print("Elige una opcion: ");
			opcion = teclado.nextInt();
			System.out.println();
		}while(opcion < 0 || opcion > 2);
		
		return opcion;
	}
}
