package utils;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.UUID;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.LlantasImpl;
import clases.basicas.MotorImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.PinturaImpl;
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
		
		System.out.println("Editando " + coche.getMarca() + " " + coche.getModelo() + " - Total: " + configuracion.obtenerPrecioTotal() + " €");
		
		System.out.println("0) Volver atras");
		
		if(configuracion.obtenerMotor() != null)
			System.out.println("M) Motor: " + configuracion.obtenerMotor().getNombre() + " - " + configuracion.obtenerMotor().getPrecio() + " €");
		else
			System.out.println("M) Motor: Elige uno!");
		
		if(configuracion.obtenerLlantas() != null)
			System.out.println("L) Llantas: " + configuracion.obtenerLlantas().getNombre() + " - " + configuracion.obtenerLlantas().getPrecio() + " €");
		else
			System.out.println("L) Llantas: Elige unas!");
		
		if(configuracion.obtenerPintura() != null)
			System.out.println("P) Pintura: " + configuracion.obtenerPintura().getNombre() + " - " + configuracion.obtenerPintura().getPrecio() + " €");
		else
			System.out.println("P) Pintura: Elige una!");
		
		ArrayList<PiezaImpl> piezasExtra = configuracion.obtenerPiezas();
		
		cantidadPiezasExtra = piezasExtra.size();
		
		for(int i = 0 ; i < piezasExtra.size() ; i++ )
		{
			System.out.println((i+1) + ") Eliminar " + piezasExtra.get(i).getNombre() + " - " + piezasExtra.get(i).getPrecio() + " €");
		}
		
		System.out.println("+) Añade una pieza extra");
		
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
		gestion.cargarPiezasValidasEnCoche(configuracion.obtenerCoche());
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
