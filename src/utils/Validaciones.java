package utils;

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
//import clases.gestion.GestionConfiguracion;
import clases.gestion.GestionCuenta;
//import clases.gestion.GestionPieza;
//import clases.gestion.GestionVotacion;

//TODO Javadoc
public class Validaciones 
{
	private GestionCoche gestionCoche;
	//private GestionConfiguracion gestionConfiguracion;
	private GestionCuenta gestionCuenta;
	//private GestionPieza gestionPieza;
	//private GestionVotacion gestionVotacion;
	
	public Validaciones(Connection conexion)
	{
		gestionCoche = new GestionCoche(conexion);
		//gestionConfiguracion = new GestionConfiguracion(conexion);
		gestionCuenta = new GestionCuenta(conexion);
		//gestionPieza = new GestionPieza(conexion);
		//gestionVotacion = new GestionVotacion(conexion);
	}
	
	/* INTERFAZ
	 * Comentario: Muestra el menu principal y valida una opcion elegida
	 * Prototipo: public int mostrarMenuPrincipalYValidarOpcion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un int indicando la opción elegida.
	 * Postcondiciones: Asociado al nombre devuelve un int con la opcion elegida:
	 * 						- 1 para iniciar sesión
	 * 						- 2 para registrarse
	 * 						- 0 para salir
	 */
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
		
		teclado.close();
		
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
		
		teclado.close();
		
		return cuenta;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra en pantalla el menu de la sesión y valida una opcion elegida
	 * Prototipo: public int mostrarMenuSesionYValidarOpcion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un int indicando la opción del menu de la sesión elegida.
	 * Postcondiciones: Asociado al nombre devuelve un int con la opción elegida:
	 * 						- 1 para una nueva configuración
	 * 						- 2 para ver las configuraciones propias
	 * 						- 3 para ver las configuraciones de la comunidad
	 * 						- 4 para editar la cuenta
	 * 						- 0 para salir
	 */
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
		
		teclado.close();
		
		return opcion;
	}
	
	/* INTERFAZ
	 * Comentario: Valida un nuevo nombre de usuario, de forma que solo se puede escoger uno que no exista en la base de datos.
	 * Prototipo: public String validarNuevoNombreUsuario()
	 * Entrada: No hay
	 * Precondiciones: La conexión con la base de datos debe estar abierta.
	 * Salida: Un String con el nombre de usuario nuevo.
	 * Postcondiciones: Asociado al nombre devuelve un String con el nuevo nombre de usuario, validado de forma que no existe en la base de datos.
	 */
	public String validarNuevoNombreUsuario()
	{
		String usuario;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Introduce nuevo nombre de usuario: ");
			
			usuario = teclado.nextLine();
			
		}while(gestionCuenta.existeUsuario(usuario));
		
		teclado.close();
		
		return usuario;
	}
	
	/* INTERFAZ
	 * Comentario: Pide al usuario una contraseña y la devuelve encriptada en MD5.
	 * Prototipo: public String contrasena()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un String con la contraseña introducida, encriptada en MD5.
	 * Postcondiciones: Asociado al nombre devuelve un String con la contraseña introducida, encriptada en MD5.
	 */
	public String contrasena()
	{
		Scanner teclado = new Scanner(System.in);
		
		String contrasena = "";
		Utils utils = new Utils();
		
		System.out.print("Introduce contrasena: ");
		contrasena = teclado.nextLine();
		
		contrasena = utils.obtenerMD5(contrasena);
		
		teclado.close();
		
		return contrasena;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra enumerados la lista de objetos determinada y valida la elección de una de ellos.
	 * 				También muestra una opción de volver atrás.
	 * Prototipo: public <T> T mostrarObjetosYValidarObjetoElegido(ArrayList<T> lista)
	 * Entrada: Un ArrayList<T> con la lista de objetos que se desean mostrar enumerados y validar la elección de uno de ellos.
	 * Precondiciones: No hay
	 * Salida: El Objeto elegido de la lista
	 * Postcondiciones: Asociado al nombre devuelve un objeto del mismo tipo que la lista (T), que será el objeto elegido.
	 * 					- Si el usuario selecciona la opción 0 (volver atrás), el objeto devuelto será null.
	 */
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
		
		teclado.close();
		
		return objeto;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra el SubMenu de la configuración y valida una opción elegida:
	 * Prototipo: public int mostrarSubMenuConfiguracionElegidaYValidarOpcion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un int indicando la opción elegida.
	 * Postcondiciones: Asociado al nombre devuelve un int indicando la opción elegida.
	 * 						-> 1 para editar la configuración
	 * 						-> 2 para borrar la configuración
	 * 						-> 0 para volver atrás.
	 */
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
		
		teclado.close();
		
		return opcion;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra el menu de las configuraciones de la comunidad y valida una opción elegida.
	 * Prototipo: public int mostrarMenuConfiguracionesComunidadYValidarOpcion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un int indicando la opción elegida.
	 * Postcondiciones: Asociado al nombre devuelve un int indicando la opción elegida:
	 * 						-> 1 para ver todas las configuraciones
	 * 						-> 2 para buscar configuraciones por marca
	 * 						-> 3 para buscar configuraciones por marca y modelo
	 * 						-> 4 para buscar configuraciones por usuario
	 * 						-> 5 para buscar configuraciones por rango de precio
	 * 						-> 6 para buscar configuraciones por fecha
	 * 						-> 0 para volver atras
	 */
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
		
		teclado.close();
		
		return opcion;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra el menu de la configuración de la comunidad elegida y valida la opción elegida
	 * Prototipo: public int mostarMenuConfiguracionComunidadElegidaYValidarOpcion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un int indicando la opción elegida
	 * Postcondiciones: Asociado al nombre devuelve un int con la opción elegida:
	 * 							-> 1 para votar la configuracion
	 * 							-> 0 para volver atras
	 */
	public int mostarMenuConfiguracionComunidadElegidaYValidarOpcion()
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
		
		teclado.close();
		
		return opcion;
	}
	
	/* INTERFAZ
	 * Comentario: Valida la calificación de una configuración.
	 * Prototipo: public VotacionImpl validarCalificacion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Una VotacionImpl con la calificación realizada.
	 * Postcondiciones: Asociado al nombre devuelve una VotacionImpl con la calificación realizada.
	 */
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
		
		teclado.close();
		
		return votacion;
	}
	
	/* INTERFAZ
	 * Comentario: Lee y valida un nombre de usuario
	 * Prototipo: public CuentaImpl leerYValidarUsuario()
	 * Entrada: No hay
	 * Precondiciones: La conexión con la base de datos debe estar abierta.
	 * Salida: Una CuentaImpl con la cuenta del usuario leido.
	 * Postcondiciones: Asociado al nombre devuelve una CuentaImpl con el usuario leido y validado.
	 */
	public CuentaImpl leerYValidarUsuario()
	{
		CuentaImpl cuenta = null;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Nombre de usuario: ");
			cuenta = gestionCuenta.obtenerCuenta(teclado.nextLine());
		}while(cuenta == null);
		
		teclado.close();
		
		return cuenta;
	}
	
	/* INTERFAZ
	 * Comentario: Lee y valida el precio mínimo para buscar en las configuraciones de la comunidad
	 * Prototipo: public double leerYValidarPrecioMinimo()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un double con el precio mínimo validado.
	 * Postcondiciones: Asociado al nombre devuelve un double con el precio mínimo validado.
	 */
	public double leerYValidarPrecioMinimo()
	{
		double precio;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Inserta precio minimo a buscar: ");
			precio = teclado.nextDouble();
		}while(precio < 0.0);
		
		teclado.close();
		
		return precio;
	}
	
	/* INTERFAZ
	 * Comentario: Lee y valida el precio máximo para buscar en las configuraciones de la comunidad. Que no podrá ser mayor que el precio mínimo.
	 * Prototipo: public double leerYValidarPrecioMaximo(double precioMinimo)
	 * Entrada: Un double con el precio mínimo.
	 * Precondiciones: No hay
	 * Salida: Un double con el precio máximo para buscar en las configuraciones de la comunidad.
	 * Postcondiciones: Asociado al nombre devuelve un doble con el precio máximo para buscar en las configuraciones de la comunidad.
	 */
	public double leerYValidarPrecioMaximo(double precioMinimo)
	{
		double precio;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Inserta precio maximo a buscar: ");
			precio = teclado.nextDouble();
		}while(precio < precioMinimo);
		
		teclado.close();
		
		return precio;
	}
	
	/* INTERFAZ
	 * Comentario: Lee y valida una fecha (dia, mes y año)
	 * Prototipo: public GregorianCalendar leerYValidarFec
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un GregorianCalendar con la fecha validada
	 * Postcondiciones: Asociado al nombre devuelve un GregorianCalendar con la fecha validada.
	 */
	public GregorianCalendar leerYValidarFecha()
	{
		GregorianCalendar fecha = new GregorianCalendar();
		
		System.out.println("leerYValidarFecha en construccion");
		
		return fecha;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra en pantalla el menu de edición de una configuración y valida una opción elegida.
	 * Prototipo: public String mostrarMenuEdicionConfiguracionYValidarOpcion(ConfiguracionImpl configuracion)
	 * Entrada: La ConfiguracionImpl de la cual se desea mostrar el menu de edición.
	 * Precondiciones: La ConfiguracionImpl ha de tener un CocheImpl (no puede ser null). @see ConfiguracionImpl
	 * Salida: Un String indicando la opción elegida.
	 * Postcondiciones: Asociado al nombre devuelve un String indiciando la opción elegida:
	 * 						-> 0 para volver atrás
	 * 						-> M para entrar en la configuración del motor
	 * 						-> L para entrar en la configuración de las llantas
	 * 						-> P para entrar en la configuración de la pintura
	 * 						-> de 1 a N por cada pieza extra existente para eliminarla de la configuracion.
	 * 						-> + para añadir una nueva pieza extra.
	 */
	public String mostrarMenuEdicionConfiguracionYValidarOpcion(ConfiguracionImpl configuracion)
	{
		String opcion;
		Utils utils = new Utils();
		Scanner teclado = new Scanner(System.in);
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
		
		teclado.close();
		
		return opcion;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra los motores disponibles para el coche de una configuración y valida la opción de motor elegido.
	 * Prototipo: public PiezaImpl mostrarMotoresDisponiblesYElegirMotor(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl a la cual se le quiere elegir un motor válido.
	 * Precondiciones: La configuración ha de tener un CocheImpl (no puede ser null) @see ConfiguracionImpl
	 * Salida: Una PiezaImpl con el motor elegido.
	 * Postcondiciones: Asociado al nombre devuelve una PiezaImpl con el motor elegido.
	 */
	public PiezaImpl mostrarMotoresDisponiblesYElegirMotor(ConfiguracionImpl configuracion)
	{
		PiezaImpl piezaElegida;
		
			System.out.println("MOTORES DISPONIBLES");
			
			piezaElegida = mostrarObjetosYValidarObjetoElegido(configuracion.obtenerCoche().obtenerMotoresValidos());
			System.out.println();
		
		return piezaElegida;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra las llantas disponibles para el coche de una configuración y valida la opción de llantas elegidas.
	 * Prototipo: public PiezaImpl mostrarLlantasDisponiblesYElegirLlantas(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl a la cual se le quiere elegir unas llantas válidas.
	 * Precondiciones: La configuración ha de tener un CocheImpl (no puede ser null) @see ConfiguracionImpl
	 * Salida: Unas PiezaImpl con las llantas elegidas.
	 * Postcondiciones: Asociado al nombre devuelve una PiezaImpl con las llantas elegidas.
	 */
	public PiezaImpl mostrarLlantasDisponiblesYElegirLlantas(ConfiguracionImpl configuracion)
	{
		PiezaImpl piezaElegida;
		
			System.out.println("LLANTAS DISPONIBLES");
			
			piezaElegida = mostrarObjetosYValidarObjetoElegido(configuracion.obtenerCoche().obtenerLlantasValidas());
			System.out.println();
			
		return piezaElegida;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra las pinturas disponibles para el coche de una configuración y valida la opción de pintura elegida.
	 * Prototipo: public PiezaImpl mostrarPinturasDisponiblesYElegirPintura(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl a la cual se le quiere elegir una pintura válida.
	 * Precondiciones: La configuración ha de tener un CocheImpl (no puede ser null) @see ConfiguracionImpl
	 * Salida: Una PiezaImpl con la pintura elegida.
	 * Postcondiciones: Asociado al nombre devuelve una PiezaImpl con la pintura elegida.
	 */
	public PiezaImpl mostrarPinturasDisponiblesYElegirPintura(ConfiguracionImpl configuracion)
	{
		PiezaImpl piezaElegida;
		
			System.out.println("PINTURAS DISPONIBLES");
			
			piezaElegida = mostrarObjetosYValidarObjetoElegido(configuracion.obtenerCoche().obtenerPinturasValidas());
			System.out.println();
		
		return piezaElegida;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra las piezas extra disponibles para el coche de una configuración y valida la opción de pieza extra elegida.
	 * Prototipo: public PiezaImpl mostrarPiezasExtraDisponiblesYElegirPiezaExtra(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl a la cual se le quiere elegir una pieza extra.
	 * Precondiciones: 
	 * 				- La configuración ha de tener un CocheImpl (no puede ser null) @see ConfiguracionImpl
	 * 				- La conexión con la base de datos ha de estar abierta.
	 * Salida: Una PiezaImpl con la pieza extra elegida.
	 * Postcondiciones: Asociado al nombre devuelve una PiezaImpl con la pieza extra elegida.
	 */
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
	
	/* INTERFAZ
	 * Comentario: Lee y validar la confirmación para borrar una configuración o no.
	 * Prototipo: public char confirmarBorrarConfiguracion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: un char indicando si se confirma el borrado o no.
	 * Postcondiciones: Asociado al nombre devuelve un char:
	 * 						- 'S' si se confirma el borrado
	 * 						- 'N' si no se confirma
	 */
	public char confirmarBorrarConfiguracion()
	{
		char confirmadoEliminarConfiguracion;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.println("Estas seguro que quieres borrar la configuracion?(S/N): ");
			confirmadoEliminarConfiguracion = Character.toUpperCase(teclado.next().charAt(0));
		}while(confirmadoEliminarConfiguracion != 'S' && confirmadoEliminarConfiguracion != 'N');
		
		teclado.close();
		
		return confirmadoEliminarConfiguracion;
	}
	
	/* INTERFAZ
	 * Comentario: Lee y validar si se desea guardar en la base de datos una configuración o no.
	 * Prototipo: public char confirmarGuardarConfiguracion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: un char indicando si se confirma el guardado de la configuración en la base de datos o no.
	 * Postcondiciones: Asociado al nombre devuelve un char:
	 * 						- 'S' si se confirma el guardado.
	 * 						- 'N' si no se confirma
	 */
	public char confirmarGuardarConfiguracion()
	{
		char confirmarGuardarConfiguracion;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.println("Quieres guardar la configuracion?(S/N)");
			confirmarGuardarConfiguracion = Character.toUpperCase(teclado.next().charAt(0));
			
		}while(confirmarGuardarConfiguracion != 'S' && confirmarGuardarConfiguracion != 'N');
		
		teclado.close();
		
		return confirmarGuardarConfiguracion;
	}
	
	/* INTERFAZ
	 * Comentario: Lee y validar la confirmación para borrar una cuenta o no.
	 * Prototipo: public boolean confirmarBorrarCuenta()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: un boolean indicando si se confirma el borrado de la cuenta o no.
	 * Postcondiciones: Asociado al nombre devuelve un char:
	 * 						- true si se confirma que se desea borrar
	 * 						- false si no se quiere borrar la cuenta
	 */
	public boolean confirmarBorrarCuenta()
	{
		boolean confirmar = false;
		char respuesta;
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Estás segur@ que quieres borrar tu cuenta? Se borrarán todas las configuraciones y votaciones realizadas. (S/N): ");
			respuesta = Character.toUpperCase(teclado.next().charAt(0));
			
		}while(respuesta != 'S' && respuesta != 'N');
		
		if(respuesta == 'S')
			confirmar = true;
		
		teclado.close();
		
		return confirmar;
	}
	
	/* INTERFAZ
	 * Comentario: Lee y valida la contraseña actual de la cuenta. El usuario tendrá 5 intentos.
	 * Prototipo: public boolean leerYValidarContrasenaActual(CuentaImpl cuenta)
	 * Entrada: la CuentaImpl de la que se desea leer y validar su contraseña actual.
	 * Precondiciones: No hay.
	 * Salida: Un boolean indicando si se introdujo la contraseña actual correctamente o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 						- true si se introdujo bien la contraseña actual de la cuenta
	 * 						- false si no se introdujo correctamente la contraseña después de los 5 intentos.
	 */
	public boolean leerYValidarContrasenaActual(CuentaImpl cuenta)
	{
		boolean contrasenaCorrecta = false;
		String contrasenaActual;
		Scanner teclado = new Scanner(System.in);
		Utils utils = new Utils();
		int intentos = 5;
		
		do
		{
			System.out.print("(" + intentos + " intentos restantes) Introduce la contraseña actual de la cuenta: ");
			contrasenaActual = teclado.nextLine();
			contrasenaActual = utils.obtenerMD5(contrasenaActual);
			
			intentos--;
			
		}while(!contrasenaActual.equals(cuenta.getContrasena()) && intentos > 0);
		
		if(contrasenaActual.equals(cuenta.getContrasena()))
			contrasenaCorrecta = true;
		else
			System.out.println("Ya no te quedan intentos.");
		
		teclado.close();
		
		return contrasenaCorrecta;
	}
	
	/* INTERFAZ
	 * Comentario: Lee y validar una nueva contraseña. La contraseña nueva será valida cuando no sea igual a la actual.
	 * Prototipo: public String LeerYValidarNuevaContrasena(String contrasenaActual)
	 * Entrada: Un String con la contraseña actual
	 * Precondiciones: No hay
	 * Salida: Un String con la contraseña nueva.
	 * Postcondiciones: Asociado al nombre devuelve un String con la nueva contraseña elegida.
	 */
	public String LeerYValidarNuevaContrasena(String contrasenaActual)
	{
		String nuevaContrasena;
		Scanner teclado = new Scanner(System.in);
		Utils utils = new Utils();
		
		do
		{
			System.out.print("Introduce tu nueva contraseña: ");
			nuevaContrasena = teclado.nextLine();
			nuevaContrasena = utils.obtenerMD5(nuevaContrasena);
			
		}while(nuevaContrasena.equals(contrasenaActual));
		
		teclado.close();
		
		return nuevaContrasena;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra el menu de edición de la cuenta y valida una opción elegida
	 * Prototipo: public int mostrarMenuEditarCuentaYValidarOpcion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un int indiciando la opción elegida
	 * Postcondiciones: Asociado al nombre devuelve un int indicando la opción elegida:
	 * 						-> 1 para borrar la cuenta
	 * 						-> 2 para cambiar la contraseña
	 * 						-> 0 para volver atrás.
	 */
	public int mostrarMenuEditarCuentaYValidarOpcion()
	{
		int opcion;
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("0) Volver atrás");
		System.out.println("1) Borrar cuenta");
		System.out.println("2) Cambiar contraseña");
		
		do
		{
			System.out.print("Elige una opcion: ");
			opcion = teclado.nextInt();
			System.out.println();
		}while(opcion < 0 || opcion > 2);
		
		teclado.close();
		
		return opcion;
	}
}
