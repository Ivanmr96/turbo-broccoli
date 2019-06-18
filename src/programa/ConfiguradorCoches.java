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
 * Cada usuario podrá realizar configuraciones de coches, teniendo la posibilidad de elegir el modelo de coche, el motor, llantas, pinturas, piezas extra, etc...
 * Los usuarios tendrán que ser usuarios registrados con un nombre de usuario y una contraseña.
 * Las configuraciones serán públicas, es decir, todos los usuarios pueden ver todas las configuraciones realizadas por el resto de usuarios.
 * Las configuraciones podrán ser calificadas con votaciones, cada usuario podrá votar una sola vez una configuración.
 * 
 * Entrada:
 * 		-> Una opcion para el menu principal:
 * 			1) Iniciar sesión
 * 			2) Registrarse
 * 			0) Salir
 * 		
 * 		-> Una opción para el menu de sesión
 * 			1) Nueva configuración
 * 			2) Ver configuraciones propias
 * 			3) Ver configuraciones de la comunidad
 * 			4) Editar cuenta
 * 			0) Cerrar sesión
 * 
 * 		-> En la pantalla de ver configuraciones propias: una elección de configuración propia elegida
 * 		
 * 		-> Una opción para el menú de configuración propia:
 * 			1) Editar configuración
 * 			2) Borrar configuración
 * 			0) Volver atrás
 * 
 * 		-> Una opción para el menú de edición de una configuración propia:
 * 			M) Establecer Motor: Una elección de motor elegido entre las disponibles.
 * 			L) Establecer Llantas: Una elección de llantas elegidas entre las disponibles.
 * 			P) Establecer Pintura: Una elección de pintura elegida entre las disponibles.
 * 			1...n) Eliminar Pieza extra																//Habrá una opcíon enumerada para cada pieza extra.
 * 			+) Añadir una pieza extra: Una elección de pieza extra entre las disponibles
 * 			0) Volver atrás
 * 
 * 		-> Una opción para el menu de configuraciones de la comunidad:
 * 			1) Mostrar todas las configuraciones de la comunidad
 * 			2) Mostrar configuraciones de la comunidad por marca: Una elección de marca para filtrar la búsqueda.
 * 			3) Mostrar configuraciones de la comunidad por marca y modelo: Una elección de marca y de modelo para filtrar la búsqueda.
 * 			4) Mostrar configuraciones de la comunidad por usuario: leer y validar un nombre de usuario para filtrar la búsqueda.
 * 			5) Mostrar configuraciones de la comunidad por rango de precio: precio minimo y precio máximo para filtrar la búsqueda.
 * 			6) Mostrar configuraciones de la comunidad por fecha: Una fecha para filtrar la búsqueda.
 * 			0) Volver atrás.
 * 
 * 		-> Después de elegir un filtro de búsqueda de configuraciones, aparecerá un menú con todas las configuraciones enumeradas, se debe elegir una de las configuraciones.
 * 			Aunque también estará la opción de volver atrás.
 * 
 * 		-> Una opción para el menu de una configuración de la comunidad:
 * 			1) Calificar la configuración: Una calificación del 1 a 10
 * 			0) Volver atrás.
 * 
 * 		-> Una opción para el menu de edición de cuenta:
 * 			1) Borrar cuenta: Confirmación si desea borrar definitivamente la cuenta o no.
 * 			2) Cambiar contraseña: Se pedirá contraseña actual y nueva contraseña.
 * 			0) Volver atrás
 * 
 * Salida: A parte de los menús descritos anteriormente, habrá:
 * 
 * 		-> Distintos mensajes de ayuda al usuario y eco de los datos, para que el usuario sepa en todo momento en qué lugar de la aplicación se encuentra y qué está haciendo.
 * 
 * 		-> Al crear una nueva configuración se avisará que puede editarla desde la pantalla de edición de configuraciones propias.
 * 
 * 		-> Al entrar en una configuración propia podrá ver los detalles de su configuración, las piezas y sus precios, la calificación media de su configuración.
 * 
 * 		-> Al entrar en una configuración de otro usuario (de la comunidad) podrá ver algunos detalles de dicha configuración, podrá ver sus piezas, los precios, la calificación media...
 * 
 * Restricciones:
 * 
 * 		-> Para la opción del menu principal: un número del 0 al 2 (0 para salir)
 * 	
 * 		-> Para la opción del menu de sesión: un número del 0 al 4 (0 para cerrar sesión)
 * 
 * 		-> Para la opción del menu de configuración propia: un número del 0 al 2 (0 para volver atrás)
 * 	
 * 		-> Para la opción del menu de edición de una configuración propia: Una de estas opciones:
 * 																			-> M para el motor
 * 																			-> L para las llantas
 * 																			-> P para la pintura
 * 																			-> + para añadir una pieza extra
 * 																			-> 1...n (una opción numérica para cada pieza extra que tenga la configuración) para eliminar la pieza extra.
 * 																			-> 0 para volver atrás.
 * 
 * 		-> Para la opción del menú de configuraciones de la comunidad: un número de 0 al 6 (0 para volver atrás)
 * 
 * 		-> Para la opción del menú de una configuración de la comunidad: 1 para calificar la configuración y 0 para volver atrás.
 * 
 * 		-> Para la opción del menu de edición de cuenta: un número del 0 al 2 (0 para volver atrás).
 * 
 * 		-> En el menú de configuraciones de la comunidad:
 * 			* Si se selecciona buscar por rango de precio: Se debe introducir un precio mínimo que debe ser mayor que 0, y un precio máximo que debe ser mayor o igual que el precio minimo.
 * 			* Si se selecciona buscar por usuario: El usuario debe existir para poder ver sus configuraciones.
 * 			* Si se selecciona buscar por fecha: Se debe introducir una fecha válida.
 * 
 * 		-> En las distintas listas para elegir entre distintas configuraciones, coches, piezas, etc: La entrada debe ser un número de esa lista. Por ejemplo:
 * 			Si la lista tiene 15 piezas, debe ser un número entre 0 y 15, siendo el 0 para salir de la pantalla determinada.
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
	Mostrar coches disponibles y validar opción de coche elegido
	Si(opcion no es volver atras)
		Insertar configuración nueva.
	FinSin
Fin

- Ver configuraciones propias
	Mostrar configguraciones propias y validar opcion de configuración elegida
	Mientras(opcion no sea volver atras)
		Mostrar configuración
		Mostrar submenu de configuración elegida y validar opcion elegida
		Mientras (opcion submenu de configuracion no sea volver atras)
			Para(opcionMenuConfiguracion)
				caso 1: Editar configuración
				caso 2: Borrar configuración
			FinPara
			Si ha borrado la configuración
				Volver atrás
			SiNo
				Mostrar submenu de configuración elegida y validar opcion elegida
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
				Mostrar llantas y validar elección de llantas
				Establecer llantas elegidas en la configuracion
			caso P:
				Mostrar pinturas y validar elección de pintura
				Establecer pintura elegida en la configuración.
			caso +:
				Mostrar piezas extra disponibles y validar eleccion de pieza.
				Añadir pieza extra.
		FinPara
	FinSino
	Mostrar submenu de configuración elegida y validar opcion elegida
	Si(la opcion elegida es salir)
		Preguntar si se desea guardar la configuracion
		Si(confirma guardado)
			Guardar la configuración
		FinSi
	FinSi
	SiNo
		Cargar de nuevo la configuración desde la base de datos.
	FinSiNo
Fin

- Borar configuración
	Leer y validar si confirma el borrado de la configuración
	Si(confirma el borrado)
		Borrar la configuración
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
				Mostrar marcas y validar elección de marca de coche
				Mostrar todas las configuraciones de la marca de coche
			caso 3:
				Mostrar marcas y validar elección de marca de coche
				Mostrar modelos de la marca y validar elección de modelo de coche
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
		Mostrar configuraciones y validar elección de configuración
		Mientras(opción elegida no sea salir)
			Mostrar la configuración
			Mostrar menu de configuración de la comunidad para la configuración elegida
			Mientras(opcion del menu no sea salir)
				Puntuar la calificacion
				Guardar la calificacion
				Mostrar la configuración
				Mostrar menu de configuración de la comunidad para la configuración elegida
			FinMientras
			Mostrar configuraciones y validar elección de configuración
		FinMientras
		Mostrar menuConfiguracionesComunidad y validar opcion elegida
	FinMientras
Fin

********************************************************************************************************************************

- Editar cuenta
	Mostrar menu edición de cuenta y validar opcion
	Mientras(opcion no sea salir)
		Para(opcion)
			caso 1: Borrar cuenta
			caso 2: Cambiar contraseña
		FinPara
		Si se ha borrado la cuenta
			Salir
		FinSi
		SiNo
			Mostrar menu edición de cuenta y validar opcion
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
											//Insertar configuracion nueva
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
										configuraciones = gestionConfiguracion.obtenerConfiguraciones(cuentaSesion);	//Obtiene la configuración.
										
										gestionConfiguracion.cargarRelacionesEnConfiguraciones(configuraciones);		//Carga las relaciones necesarias
										
										//Mostrar configuraciones propias y validar opcion de configuracion elegida
										opcionConfiguracionPropia = validacion.mostrarObjetosYValidarObjetoElegido(configuraciones); 
										
										while(opcionConfiguracionPropia != null)
										{
											confirmadoEliminarConfiguracion = 'N';		//Se reinicia el indicador de configuración eliminada.
											
											gestionConfiguracion.cargarRelacionesEnConfiguracion(opcionConfiguracionPropia);	//Cargar las relaciones en la configuración en cada iteración, para actualizar en memoria principal la información que tiene la bbdd.
											
											//Mostrar configuración
											utils.mostrarConfiguracion(opcionConfiguracionPropia);
											
											//Mostrar submenuConfiguracionElegida y validar opcion elegida
											opcionSubMenuConfiguracionElegida = validacion.mostrarSubMenuConfiguracionElegidaYValidarOpcion();
											
											while(opcionSubMenuConfiguracionElegida != 0)
											{
												switch(opcionSubMenuConfiguracionElegida)
												{
													case 1:
														//Editar configuracion
														
														//Mostrar menu edicion configuracion y validar opción
														opcionMenuConfiguracion = validacion.mostrarMenuEdicionConfiguracionYValidarOpcion(opcionConfiguracionPropia);
														
														while(!opcionMenuConfiguracion.equals("0"))
														{
															//Obtener el coche de la configuración elegida y cargar las piezas validas
															cocheEdicionConfiguracion = opcionConfiguracionPropia.obtenerCoche();
															gestionCoche.cargarPiezasValidasEnCoche(cocheEdicionConfiguracion);
															
															if(utils.esNumero(opcionMenuConfiguracion))
															{
																if(Integer.parseInt(opcionMenuConfiguracion) > 0)
																{
																	//Eliminar pieza extra elegida
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
																		//Mostrar motores y validar elección motor
																		piezaElegidaEdicion = validacion.mostrarMotoresDisponiblesYElegirMotor(opcionConfiguracionPropia);
																		if(piezaElegidaEdicion != null)
																			//Establecer motor elegido en la configuración
																			opcionConfiguracionPropia.establecerMotor((MotorImpl)piezaElegidaEdicion);
																		break;
																	case "L":
																		//Mostrar llantas y validar elección de llantas
																		piezaElegidaEdicion = validacion.mostrarLlantasDisponiblesYElegirLlantas(opcionConfiguracionPropia);
																		if(piezaElegidaEdicion != null)
																			//Establecer llantas elegidas en la configuracion
																			opcionConfiguracionPropia.establecerLlantas((LlantasImpl)piezaElegidaEdicion);
																		break;
																	case "P":
																		//Mostrar pinturas y validar elección de pintura
																		piezaElegidaEdicion = validacion.mostrarPinturasDisponiblesYElegirPintura(opcionConfiguracionPropia);
																		if(piezaElegidaEdicion != null)
																			//Establecer pintura elegida en la configuración.
																			opcionConfiguracionPropia.establecerPintura((PinturaImpl)piezaElegidaEdicion);
																		break;
																	case "+":
																		//Mostrar piezas extra disponibles y validar eleccion de pieza.
																		piezaElegidaEdicion = validacion.mostrarPiezasExtraDisponiblesYElegirPiezaExtra(opcionConfiguracionPropia);
																		if(piezaElegidaEdicion != null)
																			//Añadir pieza extra.
																			opcionConfiguracionPropia.anhadirPiezaExtra(piezaElegidaEdicion);
																		break;
																}
															}
															
															//Mostrar menu edición configuración y validar opción
															opcionMenuConfiguracion = validacion.mostrarMenuEdicionConfiguracionYValidarOpcion(opcionConfiguracionPropia);
															
															if(opcionMenuConfiguracion.equals("0")) //Si sale de la pantalla, preguntar si desea guardar la configuracion o no.
															{
																//Confirmar si desea guardar
																confirmarGuardarConfiguracion = validacion.confirmarGuardarConfiguracion();
																
																if(confirmarGuardarConfiguracion == 'S')
																	//Guardar configuración
																	gestionConfiguracion.actualizarConfiguracion(opcionConfiguracionPropia);
																else
																{
																	//Cargar de nuevo la configuración
																	opcionConfiguracionPropia = gestionConfiguracion.obtenerConfiguracion(opcionConfiguracionPropia.getID());	//Tiene que coger de nuevo de la base de datos la configuracion para que no se queden los cambios guardados(hasta salir) en memoria principal.
																	gestionConfiguracion.cargarRelacionesEnConfiguracion(opcionConfiguracionPropia);
																}
															}
														}
														break;
													case 2:
														//Borrar configuracion
														//Leer y validar si confirma el borrado de la configuración
														confirmadoEliminarConfiguracion = validacion.confirmarBorrarConfiguracion();
														if(confirmadoEliminarConfiguracion == 'S')
														{
															//Borrar la configuración
															gestionConfiguracion.eliminarConfiguracion(opcionConfiguracionPropia);
															System.out.println("configuración borrada con éxito.");
														}
														break;
												}
												
												opcionSubMenuConfiguracionElegida = (confirmadoEliminarConfiguracion == 'N') ? validacion.mostrarSubMenuConfiguracionElegidaYValidarOpcion() : 0 ;
											}
											
											configuraciones = gestionConfiguracion.obtenerConfiguraciones(cuentaSesion);		//Obtiene de nuevo la configuración de la base de datos.
											
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
													
													//Mostrar marcas y validar elección de marca de coche
													marca = validacion.mostrarObjetosYValidarObjetoElegido(gestionCoche.obtenerMarcas());
													configuraciones = gestionConfiguracion.obtenerConfiguraciones(marca);
													
													break;
													
												case 3:
													//Buscar por marca y modelo
													
													//Mostrar modelos de la marca y validar elección de modelo de coche
													marca = validacion.mostrarObjetosYValidarObjetoElegido(gestionCoche.obtenerMarcas());
													
													modelo = validacion.mostrarObjetosYValidarObjetoElegido(gestionCoche.obtenerModelos(marca));
													
													coche = gestionCoche.obtenerCoche(marca, modelo);
													
													configuraciones = gestionConfiguracion.obtenerConfiguraciones(coche);
													
													break;
													
												case 4:
													//Buscar por usuario
													
													//Leer y validar nombre de usuario
													cuentaBuscar = validacion.leerYValidarUsuario();
													
													configuraciones = gestionConfiguracion.obtenerConfiguraciones(cuentaBuscar);
													
													break;
													
												case 5:
													//Buscar por rango de precio
													
													//Leer y validar rango de precio
													precioMinimo = validacion.leerYValidarPrecioMinimo();
													precioMaximo = validacion.leerYValidarPrecioMaximo(precioMinimo);
													
													configuraciones = gestionConfiguracion.obtenerConfiguraciones(precioMinimo, precioMaximo);
													
													break;
													
												case 6:
													//Buscar por fecha
													
													//Leer y validar fecha
													fechaBuscar = validacion.leerYValidarFecha();
													configuraciones = resguardo.obtenerConfiguraciones(fechaBuscar);
													
													break;
													
											}
											
											//Cargar las relaciones en las configuraciones filtradas
											gestionConfiguracion.cargarRelacionesEnConfiguraciones(configuraciones);
											
											//Mostrar configuraciones y validar elección de configuración
											configuracionComunidadElegida = validacion.mostrarObjetosYValidarObjetoElegido(configuraciones);
											
											while(configuracionComunidadElegida != null)
											{
												//Mostrar la configuración
												utils.mostrarConfiguracion(configuracionComunidadElegida);
												
												//Mostrar menu de configuración de la comunidad para la configuración elegida
												opcionMenuConfiguracionComunidadElegida = validacion.mostarMenuConfiguracionComunidadElegidaYValidarOpcion();
												
												while(opcionMenuConfiguracionComunidadElegida != 0)
												{
													//Puntuar la configuracion
													calificacion = validacion.validarCalificacion();
													calificacion.establecerConfiguracion(configuracionComunidadElegida);
													calificacion.establecerCuenta(cuentaSesion);
													
													//Guardar calificacion
													try 
													{
														gestionVotacion.insertarVotacion(calificacion);
														System.out.println("Votación realizada con éxito.");
														
														gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionComunidadElegida);	//Actualiza de nuevo la configuracion con la votacion nueva realizada
													} 
													catch (SQLServerException e) 
													{
														if(e.getErrorCode() == 2627)
															System.out.println("No puedes votar una configuración que ya has votado!");
														else if(e.getErrorCode() == 50000)
															System.out.println(e.getMessage());
														else
															e.printStackTrace();
													}
													
													//Mostrar la configuración
													utils.mostrarConfiguracion(configuracionComunidadElegida);
													
													//Mostrar menu de configuracion de la comunidad elegida
													opcionMenuConfiguracionComunidadElegida = validacion.mostarMenuConfiguracionComunidadElegidaYValidarOpcion();
												}
												
												//Mostrar configuraciones y validar elección de configuración
												configuracionComunidadElegida = validacion.mostrarObjetosYValidarObjetoElegido(configuraciones);
											}
											
											//Mostrar menuConfiguracionesComunidad y validar opcion elegida
											opcionMenuConfiguracionesComunidad = validacion.mostrarMenuConfiguracionesComunidadYValidarOpcion();
										}
									break;
									
								case 4: 
									//Editar cuenta
									
									//Mostrar menu edición de cuenta y validar opcion
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
													gestionCuenta.cargarRelacionesEnCuenta(cuentaSesion);
													if(gestionCuenta.eliminarCuenta(cuentaSesion))
														System.out.println("La cuenta se borro correctamente.");
													else
														System.out.println("No se pudo borrar la cuenta, intentelo más tarde.");
												}
												break;
											case 2:
												//Cambiar contraseña
												if(validacion.leerYValidarContrasenaActual(cuentaSesion))
												{
													nuevaContrasena = validacion.LeerYValidarNuevaContrasena(cuentaSesion.getContrasena());
													cuentaSesion.setContrasena(nuevaContrasena);
													
													if(gestionCuenta.actualizarCuenta(cuentaSesion))
														System.out.println("Contraseña cambiada correctamente.");
													else
														System.out.println("La contraseña no puedo cambiarse, intentelo más tarde.");
												}
										}
										
										if(confirmarBorrarCuenta)
											//Salir
											opcionMenuEditarCuenta = 0;
										else
											//Mostrar menu edición de cuenta y validar opcion
											opcionMenuEditarCuenta = validacion.mostrarMenuEditarCuentaYValidarOpcion();
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
