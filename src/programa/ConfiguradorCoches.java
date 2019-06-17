package programa;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.UUID;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.LlantasImpl;
import clases.basicas.MotorImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.PinturaImpl;
import clases.basicas.VotacionImpl;
import clases.gestion.ConexionSQL;
import clases.gestion.GestionCoche;
import clases.gestion.GestionConfiguracion;
import clases.gestion.GestionCuenta;
//import clases.gestion.GestionPieza;
import clases.gestion.GestionVotacion;
import clases.gestion.Resguardo;
import utils.Utils;
import utils.Validaciones;

/* ANALISIS
 * Programa configurador de modelos de coches:
 * Cada usuario podr� realizar configuraciones de coches, teniendo la posibilidad de elegir el modelo de coche, el motor, llantas, pinturas, piezas extra, etc...
 * Los usuarios tendr�n que ser usuarios registrados con un nombre de usuario y una contrase�a.
 * Las configuraciones ser�n p�blicas, es decir, todos los usuarios pueden ver todas las configuraciones realizadas por el resto de usuarios.
 * Las configuraciones podr�n ser calificadas con votaciones, cada usuario podr� votar una sola vez una configuraci�n.
 * 
 * Entrada:
 * 		-> Una opcion para el menu principal:
 * 			1) Iniciar sesi�n
 * 			2) Registrarse
 * 			0) Salir
 * 		
 * 		-> Una opci�n para el menu de sesi�n
 * 			1) Nueva configuraci�n
 * 			2) Ver configuraciones propias
 * 			3) Ver configuraciones de la comunidad
 * 			4) Editar cuenta
 * 			0) Cerrar sesi�n
 * 
 * 		-> En la pantalla de ver configuraciones propias: una elecci�n de configuraci�n propia elegida
 * 		
 * 		-> Una opci�n para el men� de configuraci�n propia:
 * 			1) Editar configuraci�n
 * 			2) Borrar configuraci�n
 * 			0) Volver atr�s
 * 
 * 		-> Una opci�n para el men� de edici�n de una configuraci�n propia:
 * 			M) Establecer Motor: Una elecci�n de motor elegido entre las disponibles.
 * 			L) Establecer Llantas: Una elecci�n de llantas elegidas entre las disponibles.
 * 			P) Establecer Pintura: Una elecci�n de pintura elegida entre las disponibles.
 * 			1...n) Eliminar Pieza extra																//Habr� una opc�on enumerada para cada pieza extra.
 * 			+) A�adir una pieza extra: Una elecci�n de pieza extra entre las disponibles
 * 			0) Volver atr�s
 * 
 * 		-> Una opci�n para el menu de configuraciones de la comunidad:
 * 			1) Mostrar todas las configuraciones de la comunidad
 * 			2) Mostrar configuraciones de la comunidad por marca: Una elecci�n de marca para filtrar la b�squeda.
 * 			3) Mostrar configuraciones de la comunidad por marca y modelo: Una elecci�n de marca y de modelo para filtrar la b�squeda.
 * 			4) Mostrar configuraciones de la comunidad por usuario: leer y validar un nombre de usuario para filtrar la b�squeda.
 * 			5) Mostrar configuraciones de la comunidad por rango de precio: precio minimo y precio m�ximo para filtrar la b�squeda.
 * 			6) Mostrar configuraciones de la comunidad por fecha: Una fecha para filtrar la b�squeda.
 * 			0) Volver atr�s.
 * 
 * 		-> Despu�s de elegir un filtro de b�squeda de configuraciones, aparecer� un men� con todas las configuraciones enumeradas, se debe elegir una de las configuraciones.
 * 			Aunque tambi�n estar� la opci�n de volver atr�s.
 * 
 * 		-> Una opci�n para el menu de una configuraci�n de la comunidad:
 * 			1) Calificar la configuraci�n: Una calificaci�n del 1 a 10
 * 			0) Volver atr�s.
 * 
 * 		-> Una opci�n para el menu de edici�n de cuenta:
 * 			1) Borrar cuenta: Confirmaci�n si desea borrar definitivamente la cuenta o no.
 * 			2) Cambiar contrase�a: Se pedir� contrase�a actual y nueva contrase�a.
 * 			0) Volver atr�s
 * 
 * Salida: A parte de los men�s descritos anteriormente, habr�:
 * 
 * 		-> Distintos mensajes de ayuda al usuario y eco de los datos, para que el usuario sepa en todo momento en qu� lugar de la aplicaci�n se encuentra y qu� est� haciendo.
 * 
 * 		-> Al crear una nueva configuraci�n se avisar� que puede editarla desde la pantalla de edici�n de configuraciones propias.
 * 
 * 		-> Al entrar en una configuraci�n propia podr� ver los detalles de su configuraci�n, las piezas y sus precios, la calificaci�n media de su configuraci�n.
 * 
 * 		-> Al entrar en una configuraci�n de otro usuario (de la comunidad) podr� ver algunos detalles de dicha configuraci�n, podr� ver sus piezas, los precios, la calificaci�n media...
 * 
 * Restricciones:
 * 
 * 		-> Para la opci�n del menu principal: un n�mero del 0 al 2 (0 para salir)
 * 	
 * 		-> Para la opci�n del menu de sesi�n: un n�mero del 0 al 4 (0 para cerrar sesi�n)
 * 
 * 		-> Para la opci�n del menu de configuraci�n propia: un n�mero del 0 al 2 (0 para volver atr�s)
 * 	
 * 		-> Para la opci�n del menu de edici�n de una configuraci�n propia: Una de estas opciones:
 * 																			-> M para el motor
 * 																			-> L para las llantas
 * 																			-> P para la pintura
 * 																			-> + para a�adir una pieza extra
 * 																			-> 1...n (una opci�n num�rica para cada pieza extra que tenga la configuraci�n) para eliminar la pieza extra.
 * 																			-> 0 para volver atr�s.
 * 
 * 		-> Para la opci�n del men� de configuraciones de la comunidad: un n�mero de 0 al 6 (0 para volver atr�s)
 * 
 * 		-> Para la opci�n del men� de una configuraci�n de la comunidad: 1 para calificar la configuraci�n y 0 para volver atr�s.
 * 
 * 		-> Para la opci�n del menu de edici�n de cuenta: un n�mero del 0 al 2 (0 para volver atr�s).
 * 
 * 		-> En el men� de configuraciones de la comunidad:
 * 			* Si se selecciona buscar por rango de precio: Se debe introducir un precio m�nimo que debe ser mayor que 0, y un precio m�ximo que debe ser mayor o igual que el precio minimo.
 * 			* Si se selecciona buscar por usuario: El usuario debe existir para poder ver sus configuraciones.
 * 			* Si se selecciona buscar por fecha: Se debe introducir una fecha v�lida.
 * 
 * 		-> En las distintas listas para elegir entre distintas configuraciones, coches, piezas, etc: La entrada debe ser un n�mero de esa lista. Por ejemplo:
 * 			Si la lista tiene 15 piezas, debe ser un n�mero entre 0 y 15, siendo el 0 para salir de la pantalla determinada.
 * 		
 */

/* PSEUDOCODIGO GENERALIZADO (Nivel 0)
 * 
 * Inicio
 * 		Mostrar menu principal y validar opcion
 * 		Mientras(opcionMenuPrincipal no sea salir)
 * 			Para(opcionMenuPrincipal)
 * 				caso 1: Iniciar sesion
 * 				caso 2: Registrarse
 * 			FinPara
 * 			Mostrar menu principal y validar opcion
 * 		FinMientras
 * Fin
 */

/* PSEUDOCODIGO MODULOS Nivel 1
 * 
 * - Iniciar sesion
 * Inicio
 * 		Leer y validar cuenta
 * 		Mostrar menu sesion y validar opcion
 * 		Mientras (opcionSesion no sea salir)
 * 			Para(opcionSesion)
 * 				caso 1: Nueva configuracion
 * 				caso 2: Ver configuraciones propias
 * 				caso 3: Ver configuraciones de la comunidad
 * 				caso 4: Editar cuenta
 * 			FinPara
 * 			Mostrar menu sesion y validar opcion
 * 		FinMientras
 * Fin
 * 
 * - Registrarse
 * Inicio
 * 		Leer y validar nombre de usuario
 * 		Leer contrasena
 * 		Leer confirmacion
 * 		Si(ha confirmado)
 * 			Registrar cuenta
 * 		FinSi
 * Fin
 */

/* PSEUDOCODIGO MODULOS Nivel 2

********************************************************************************************************************************

- Nueva configuracion
	Mostrar coches disponibles y validar opci�n de coche elegido
	Si(opcion no es volver atras)
		Insertar configuraci�n nueva.
	FinSin
Fin

- Ver configuraciones propias
	Mostrar configguraciones propias y validar opcion de configuraci�n elegida
	Mientras(opcion no sea volver atras)
		Mostrar configuraci�n
		Mostrar submenu de configuraci�n elegida y validar opcion elegida
		Mientras (opcion submenu de configuracion no sea volver atras)
			Para(opcionMenuConfiguracion)
				caso 1: Editar configuraci�n
				caso 2: Borrar configuraci�n
			FinPara
			Si ha borrado la configuraci�n
				Volver atr�s
			SiNo
				Mostrar submenu de configuraci�n elegida y validar opcion elegida
		FinMientras
	FinMientras
Fin

********************************************************************************************************************************

- Editar configuracion
	Si(La opcion del submenu elegida es eliminar una pieza extra)
		Eliminar pieza elegida
	FinSi
	Sino
		Para(opcion elegida)
			caso M:
				Mostrar motores y validar eleccion de motor
				Establecer motor elegido en la configuracion
			caso L:
				Mostrar llantas y validar elecci�n de llantas
				Establecer llantas elegidas en la configuracion
			caso P:
				Mostrar pinturas y validar elecci�n de pintura
				Establecer pintura elegida en la configuraci�n.
			caso +:
				Mostrar piezas extra disponibles y validar eleccion de pieza.
				A�adir pieza extra.
		FinPara
	FinSino
	Mostrar submenu de configuraci�n elegida y validar opcion elegida
	Si(la opcion elegida es salir)
		Preguntar si se desea guardar la configuracion
		Si(confirma guardado)
			Guardar la configuraci�n
		FinSi
	FinSi
	SiNo
		Cargar de nuevo la configuraci�n desde la base de datos.
	FinSiNo
Fin

- Borar configuraci�n
	Leer y validar si confirma el borrado de la configuraci�n
	Si(confirma el borrado)
		Borrar la configuraci�n
	FinSi
Fin

********************************************************************************************************************************

Ver configuraciones de la comunidad
	Mostrar menuConfiguracionesComunidad y validar opcion elegida
	Mientras(opcionMenuConfiguracionesComunidad  no sea salir)
		Para(opcionMenuConfiguracionesComunidad)
			caso 1: 
				Mostrar todas las configuraciones de la comunidad
			caso 2:
				Mostrar marcas y validar elecci�n de marca de coche
				Mostrar todas las configuraciones de la marca de coche
			caso 3:
				Mostrar marcas y validar elecci�n de marca de coche
				Mostrar modelos de la marca y validar elecci�n de modelo de coche
				Mostrar todas las configuraciones del modelo de coche.
			caso 4:
				Leer y validar nombre de usuario
				Mostrar todas las configuraciones del usuario dado.
			caso 5:
				Leer y validar rango de precio
				Mostrar todas las configuraciones comprendidas en el rango de precio dado.
			caso 6:
				Leer y validar fecha
				Mostrar todas las configuraciones de la fecha especificada
		FinPara
		Cargar las relaciones en las configuraciones filtradas
		Mostrar configuraciones y validar elecci�n de configuraci�n
		Mientras(opci�n elegida no sea salir)
			Mostrar la configuraci�n
			Mostrar menu de configuraci�n de la comunidad para la configuraci�n elegida
			Mientras(opcion del menu no sea salir)
				Puntuar la calificacion
				Guardar la calificacion
				Mostrar la configuraci�n
				Mostrar menu de configuraci�n de la comunidad para la configuraci�n elegida
			FinMientras
			Mostrar configuraciones y validar elecci�n de configuraci�n
		FinMientras
		Mostrar menuConfiguracionesComunidad y validar opcion elegida
	FinMientras
Fin

********************************************************************************************************************************

- Editar cuenta
	Mostrar menu edici�n de cuenta y validar opcion
	Mientras(opcion no sea salir)
		Para(opcion)
			caso 1: Borrar cuenta
			caso 2: Cambiar contrase�a
		FinPara
		Si se ha borrado la cuenta
			Salir
		FinSi
		SiNo
			Mostrar menu edici�n de cuenta y validar opcion
		FinSiNo
	FinMientras
Fin

********************************************************************************************************************************

 */

public class ConfiguradorCoches 
{
	public static void main(String[] args)
	{
		//Conexion
		String URLConexion = "jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;";
		
		ConexionSQL gestionConexion = new ConexionSQL(URLConexion);
		gestionConexion.abrirConexion();
		Connection conexion = gestionConexion.getConexion();
		
		//Gestion
		GestionConfiguracion gestionConfiguracion = new GestionConfiguracion(conexion);
		GestionCoche gestionCoche = new GestionCoche(conexion);
		GestionCuenta gestionCuenta = new GestionCuenta(conexion);
		//GestionPieza gestionPieza = new GestionPieza(conexion);
		GestionVotacion gestionVotacion = new GestionVotacion(conexion);
		Validaciones validacion = new Validaciones(conexion);
			
		Utils utils = new Utils();
		Scanner teclado = new Scanner(System.in);
		Resguardo resguardo = new Resguardo();
		
		//Menu principal
		int opcionMenuPrincipal;
		
		//Menu Sesion
		int opcionSesion;
		CuentaImpl cuentaSesion;
		
		//Nueva configuracion
		CocheImpl opcionCoche;
		ConfiguracionImpl configuracionNueva;
		
		//Ver configuraciones propias
		ArrayList<ConfiguracionImpl> configuraciones;
		char confirmadoEliminarConfiguracion, confirmarGuardarConfiguracion;
		int opcionSubMenuConfiguracionElegida;
		String opcionMenuConfiguracion;
		CocheImpl cocheEdicionConfiguracion;
		PiezaImpl piezaElegidaEdicion;
		ConfiguracionImpl opcionConfiguracionPropia;
		
		//Ver configuraciones de la comunidad
		int opcionMenuConfiguracionesComunidad;
		CocheImpl coche;
		String marca, modelo;
		CuentaImpl cuentaBuscar;
		double precioMinimo, precioMaximo;
		ConfiguracionImpl configuracionComunidadElegida;
		int opcionMenuConfiguracionComunidadElegida;
		GregorianCalendar fechaBuscar;
		VotacionImpl calificacion;
		
		//Registro
		char confirmadoRegistro;
		String usuario, contrasena;
		
		//Editar cuenta
		int opcionMenuEditarCuenta;
		boolean confirmarBorrarCuenta = false;
		String nuevaContrasena;
		
/* ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		
		//Mostrar menu principal y validar opcion
		opcionMenuPrincipal = validacion.mostrarMenuPrincipalYValidarOpcion();
		
		while(opcionMenuPrincipal != 0)
		{
			switch(opcionMenuPrincipal)
			{
				case 1: 
					//Iniciar sesion
						//Leer y validar cuenta
						cuentaSesion = validacion.iniciarSesion();
					
						//Mostrar menu sesion y validar opcion
						opcionSesion = validacion.mostrarMenuSesionYValidarOpcion();
						
						while(opcionSesion != 0)
						{
							switch(opcionSesion)
							{
								case 1: 
									//Nueva configuracion
										//Mostrar coches disponibles y validar opcion de coche elegido
										opcionCoche = validacion.mostrarObjetosYValidarObjetoElegido(gestionCoche.obtenerCoches());
										
										configuracionNueva = new ConfiguracionImpl(UUID.randomUUID().toString(), new GregorianCalendar());		//Instancia de nueva configuracion
										configuracionNueva.establecerCoche(opcionCoche);											//Establecer la relacion del coche elegido con la nueva configuracion creada.
										configuracionNueva.establecerCuenta(cuentaSesion);
										
										if(opcionCoche != null)
										{
											try 
											{
												if(gestionConfiguracion.insertarConfiguracion(configuracionNueva));
													System.out.println();
													System.out.println("Configuracion creada con exito, puedes editarla desde la pantalla \"Ver configuraciones propias\"");
													System.out.println();
											} 
											catch (SQLServerException e) 
											{
												if(e.getErrorCode() == 2627)
													System.out.println("Esta configuracion ya existe");
												else
													e.printStackTrace();
											}
										}
									break;
									
								case 2: 
									//Ver configuraciones propias
										//Mostrar configuraciones propias y validar opcion de configuracion elegida
										configuraciones = gestionConfiguracion.obtenerConfiguraciones(cuentaSesion);
										
										gestionConfiguracion.cargarRelacionesEnConfiguraciones(configuraciones);
										
										opcionConfiguracionPropia = validacion.mostrarObjetosYValidarObjetoElegido(configuraciones); 
										
										while(opcionConfiguracionPropia != null)
										{
											gestionConfiguracion.cargarRelacionesEnConfiguracion(opcionConfiguracionPropia);	//Cargar las relaciones en la configuraci�n en cada iteraci�n, para actualizar en memoria principal la informaci�n que tiene la bbdd.
											
											//Mostrar configuraci�n
											utils.mostrarConfiguracion(opcionConfiguracionPropia);
											
											confirmadoEliminarConfiguracion = 'N';												//TODO �Esto por qu� est� aqu�?
											
											//Mostrar submenuConfiguracionElegida y validar opcion elegida
											opcionSubMenuConfiguracionElegida = validacion.mostrarSubMenuConfiguracionElegidaYValidarOpcion();
											
											while(opcionSubMenuConfiguracionElegida != 0)
											{
												switch(opcionSubMenuConfiguracionElegida)
												{
													case 1:
														//Editar configuracion
														opcionMenuConfiguracion = validacion.mostrarMenuEdicionConfiguracionYValidarOpcion(opcionConfiguracionPropia);
														
														while(!opcionMenuConfiguracion.equals("0"))
														{
															cocheEdicionConfiguracion = opcionConfiguracionPropia.obtenerCoche();
															gestionCoche.cargarPiezasValidasEnCoche(cocheEdicionConfiguracion);
															
															if(utils.esNumero(opcionMenuConfiguracion))
															{
																if(Integer.parseInt(opcionMenuConfiguracion) > 0)
																{
																	piezaElegidaEdicion = opcionConfiguracionPropia.obtenerPiezas().get(Integer.parseInt(opcionMenuConfiguracion)-1);
																	opcionConfiguracionPropia.eliminarPiezaExtra(piezaElegidaEdicion);
																}
																else
																	piezaElegidaEdicion = null;
															}
															else
															{
																switch(opcionMenuConfiguracion)
																{
																	case "M": 
																		piezaElegidaEdicion = validacion.mostrarMotoresDisponiblesYElegirMotor(opcionConfiguracionPropia);
																		if(piezaElegidaEdicion != null)
																			opcionConfiguracionPropia.establecerMotor((MotorImpl)piezaElegidaEdicion);
																		break;
																	case "L":
																		piezaElegidaEdicion = validacion.mostrarLlantasDisponiblesYElegirLlantas(opcionConfiguracionPropia);
																		if(piezaElegidaEdicion != null)
																			opcionConfiguracionPropia.establecerLlantas((LlantasImpl)piezaElegidaEdicion);
																		break;
																	case "P":
																		piezaElegidaEdicion = validacion.mostrarPinturasDisponiblesYElegirPintura(opcionConfiguracionPropia);
																		if(piezaElegidaEdicion != null)
																			opcionConfiguracionPropia.establecerPintura((PinturaImpl)piezaElegidaEdicion);
																		break;
																	case "+":
																		piezaElegidaEdicion = validacion.mostrarPiezasExtraDisponiblesYElegirPiezaExtra(opcionConfiguracionPropia);
																		if(piezaElegidaEdicion != null)
																			opcionConfiguracionPropia.anhadirPiezaExtra(piezaElegidaEdicion);
																		break;
																}
															}
															
															opcionMenuConfiguracion = validacion.mostrarMenuEdicionConfiguracionYValidarOpcion(opcionConfiguracionPropia);
															
															if(opcionMenuConfiguracion.equals("0")) //Si sale de la pantalla, preguntar si desea guardar la configuracion o no.
															{
																confirmarGuardarConfiguracion = validacion.confirmarGuardarConfiguracion();
																
																if(confirmarGuardarConfiguracion == 'S')
																	gestionConfiguracion.actualizarConfiguracion(opcionConfiguracionPropia);
																else
																{
																	opcionConfiguracionPropia = gestionConfiguracion.obtenerConfiguracion(opcionConfiguracionPropia.getID());	//Tiene que coger de nuevo de la base de datos la configuracion para que no se queden los cambios guardados(hasta salir) en memoria principal.
																	gestionConfiguracion.cargarRelacionesEnConfiguracion(opcionConfiguracionPropia);
																}
															}
														}
														break;
													case 2:
														//Borrar configuracion
														confirmadoEliminarConfiguracion = validacion.confirmarBorrarConfiguracion();
														if(confirmadoEliminarConfiguracion == 'S')
														{
															gestionConfiguracion.eliminarConfiguracion(opcionConfiguracionPropia);
															System.out.println("configuraci�n borrada con �xito.");
														}
														break;
												}
												opcionSubMenuConfiguracionElegida = (confirmadoEliminarConfiguracion == 'N') ? validacion.mostrarSubMenuConfiguracionElegidaYValidarOpcion() : 0 ;
											}
											
											configuraciones = gestionConfiguracion.obtenerConfiguraciones(cuentaSesion);
											
											gestionConfiguracion.cargarRelacionesEnConfiguraciones(configuraciones);
											
											opcionConfiguracionPropia = validacion.mostrarObjetosYValidarObjetoElegido(configuraciones);
										}
									break;
									
								case 3: 
									//Ver configuraciones de la comunidad
										//Mostrar menuConfiguracionesComunidad y validar opcion elegida
										opcionMenuConfiguracionesComunidad = validacion.mostrarMenuConfiguracionesComunidadYValidarOpcion();
										
										while (opcionMenuConfiguracionesComunidad != 0)
										{
											configuraciones = null;	//TODO esto puede ir arriba
											
											switch(opcionMenuConfiguracionesComunidad)
											{
												case 1:
													//Mostrar todas las configuraciones de la comunidad
													configuraciones = gestionConfiguracion.obtenerConfiguraciones();
													break;
													
												case 2:
													//Buscar por marca
													marca = validacion.mostrarObjetosYValidarObjetoElegido(gestionCoche.obtenerMarcas());
													
													configuraciones = gestionConfiguracion.obtenerConfiguraciones(marca);
													
													break;
													
												case 3:
													//Buscar por marca y modelo

													marca = validacion.mostrarObjetosYValidarObjetoElegido(gestionCoche.obtenerMarcas());
													
													//modelo = validacion.mostrarListaModelosYValidarModeloElegido(marca);
													modelo = validacion.mostrarObjetosYValidarObjetoElegido(gestionCoche.obtenerModelos(marca));
													
													coche = gestionCoche.obtenerCoche(marca, modelo);
													
													configuraciones = gestionConfiguracion.obtenerConfiguraciones(coche);
													
													break;
													
												case 4:
													//Buscar por usuario
													cuentaBuscar = validacion.leerYValidarUsuario();
													
													configuraciones = gestionConfiguracion.obtenerConfiguraciones(cuentaBuscar);
													
													break;
													
												case 5:
													//Buscar por rango de precio
													precioMinimo = validacion.leerYValidarPrecioMinimo();
													precioMaximo = validacion.leerYValidarPrecioMaximo(precioMinimo);
													
													configuraciones = gestionConfiguracion.obtenerConfiguraciones(precioMinimo, precioMaximo);
													
													break;
													
												case 6:
													//Buscar por fecha
													fechaBuscar = validacion.leerYValidarFecha();
													configuraciones = resguardo.obtenerConfiguraciones(fechaBuscar);
													
													break;
													
											}
											
											gestionConfiguracion.cargarRelacionesEnConfiguraciones(configuraciones);
											
											configuracionComunidadElegida = validacion.mostrarObjetosYValidarObjetoElegido(configuraciones);
											
											while(configuracionComunidadElegida != null)
											{
												utils.mostrarConfiguracion(configuracionComunidadElegida);
												
												//Mostrar menu de configuracion de la comunidad elegida
												opcionMenuConfiguracionComunidadElegida = validacion.mostarMenuConfiguracionComunidadElegidaYValidarOpcion();
												
												while(opcionMenuConfiguracionComunidadElegida != 0)
												{
													//Puntuar la configuracion
													calificacion = validacion.validarCalificacion();
													calificacion.establecerConfiguracion(configuracionComunidadElegida);
													calificacion.establecerCuenta(cuentaSesion);
													
													//Insertar calificacion
													try 
													{
														gestionVotacion.insertarVotacion(calificacion);
														System.out.println("Votaci�n realizada con �xito.");
														
														gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionComunidadElegida);	//Actualiza de nuevo la configuracion con la votacion nueva realizada
													} 
													catch (SQLServerException e) 
													{
														if(e.getErrorCode() == 2627)
															System.out.println("No puedes votar una configuraci�n que ya has votado!");
														else if(e.getErrorCode() == 50000)
															System.out.println(e.getMessage());
														else
															e.printStackTrace();
													}
													
													utils.mostrarConfiguracion(configuracionComunidadElegida);
													
													//Mostrar menu de configuracion de la comunidad elegida
													opcionMenuConfiguracionComunidadElegida = validacion.mostarMenuConfiguracionComunidadElegidaYValidarOpcion();
												}
												
												configuracionComunidadElegida = validacion.mostrarObjetosYValidarObjetoElegido(configuraciones);
											}
											
											opcionMenuConfiguracionesComunidad = validacion.mostrarMenuConfiguracionesComunidadYValidarOpcion();
										}
									break;
									
								case 4: 
									//Editar cuenta
									
									opcionMenuEditarCuenta = validacion.mostrarMenuEditarCuentaYValidarOpcion();
									
									while(opcionMenuEditarCuenta != 0)
									{
										switch(opcionMenuEditarCuenta)
										{
											case 1:
												//Borrar cuenta
												confirmarBorrarCuenta = validacion.confirmarBorrarCuenta();
												
												if(confirmarBorrarCuenta)
												{
													if(gestionCuenta.eliminarCuenta(cuentaSesion))
														System.out.println("La cuenta se borro correctamente.");
													else
														System.out.println("No se pudo borrar la cuenta, intentelo m�s tarde.");
												}
												break;
											case 2:
												//Cambiar contrase�a
												if(validacion.leerYValidarContrasenaActual(cuentaSesion))
												{
													nuevaContrasena = validacion.LeerYValidarNuevaContrasena(cuentaSesion.getContrasena());
													cuentaSesion.setContrasena(nuevaContrasena);
													
													if(gestionCuenta.actualizarCuenta(cuentaSesion))
														System.out.println("Contrase�a cambiada correctamente.");
													else
														System.out.println("La contrase�a no puedo cambiarse, intentelo m�s tarde.");
												}
										}
										
										if(!confirmarBorrarCuenta)
											opcionMenuEditarCuenta = validacion.mostrarMenuEditarCuentaYValidarOpcion();
										else
											opcionMenuEditarCuenta = 0;
									}
									break;
									
							}
							
							//Mostrar menu sesion y validar opcion
							if(!confirmarBorrarCuenta)
								opcionSesion = validacion.mostrarMenuSesionYValidarOpcion();
							else
							{
								opcionSesion = 0;
								confirmarBorrarCuenta = false;		//Se reinicia el indicador de cuenta borrada para el proximo inicio de sesion.
							}
						}
					break;
				case 2: 
					//Registrarse
						//Leer y validar nombre de usuario
						usuario = validacion.validarNuevoNombreUsuario();
						
						//Leer contrasena
						contrasena = validacion.contrasena();
						
						//Leer confirmacion
						do
						{
							System.out.print("Confirmar el registro? (S/N): ");
							
							confirmadoRegistro = teclado.next().charAt(0);
							
							confirmadoRegistro = Character.toUpperCase(confirmadoRegistro);
							
						}while(confirmadoRegistro != 'S' && confirmadoRegistro != 'N');
						
						if(confirmadoRegistro == 'S')
							//Registrar cuenta
							try 
							{
								if(gestionCuenta.insertarCuenta(new CuentaImpl(usuario, contrasena)))
									System.out.println("Cuenta creada con exito.");
								else
									System.out.println("La cuenta no ha podido crearse, intentalo de nuevo.");
							} 
							catch (SQLServerException e) 
							{
								if(e.getErrorCode() == 2627)
									System.out.println("Este usuario ya existe, no se creara la cuenta.");
								else
									e.printStackTrace();
							}
					break;
			}
			
			//Mostrar menu principal y validar opcion
			opcionMenuPrincipal = validacion.mostrarMenuPrincipalYValidarOpcion();
		}
		
		gestionConexion.cerrarConexion();
		teclado.close();
	}
}
