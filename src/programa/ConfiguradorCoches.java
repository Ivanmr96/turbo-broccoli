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
import clases.gestion.GestionPieza;
import clases.gestion.GestionVotacion;
import clases.gestion.Resguardo;
import utils.Utils;
import utils.Validaciones;

//TODO Comprobar si se puede hacer que despues de cada accion del usuario, se limpie la pantalla.

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
 * 				caso 3: Ver configuraciones propias
 * 				caso 4: Ver configuraciones de la comunidad
 * 				caso 5: Editar cuenta
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
 * 
 * - Nueva configuracion
 * Inicio
 * 		Mostrar coches disponibles y validar opcion de coche elegido
 * 		Si (opcionCoche no es salir)
 * 			Modulo Editar configuracion
 * 		FinSi
 * Fin
 * 
 * - Editar configuracion
 * Inicio
 * 		Mostrar menu editarConfiguracion y validar opcion menuEditarConfiguracion
 * 		Mientras (opcion menuEditarConfiguracion no sea volver atras)
 * 			Para(menuEditarConfiguracion)
 * 				caso 1: Modificar piezas
 * 				caso 2: Borrar configuracion
 * 			FinPara
 * 			Mostrar menu editarConfiguracion y validar opcion menuEditarConfiguracion
 * 		FinMientras
 * Fin
 * 
 * 
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
		GestionPieza gestionPieza = new GestionPieza(conexion);
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
										
										for(ConfiguracionImpl configuracion:configuraciones)
										{
											gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracion);
										}
										
										opcionConfiguracionPropia = validacion.mostrarObjetosYValidarObjetoElegido(configuraciones); 
										
										while(opcionConfiguracionPropia != null)
										{
											gestionConfiguracion.cargarRelacionesEnConfiguracion(opcionConfiguracionPropia);	//Cargar las relaciones en la configuración en cada iteración, para actualizar en memoria principal la información que tiene la bbdd.
											
											//Mostrar estadisticas
											utils.mostrarConfiguracion(opcionConfiguracionPropia);
											
											confirmadoEliminarConfiguracion = 'N';												//TODO ¿Esto por qué está aquí?
											
											//Mostrar submenuConfiguracionElegida y validar opcion elegida
											opcionSubMenuConfiguracionElegida = validacion.mostrarSubMenuConfiguracionElegidaYValidarOpcion();
											
											while(opcionSubMenuConfiguracionElegida != 0)
											{
												switch(opcionSubMenuConfiguracionElegida)
												{
													case 1:
														//Editar configuracion
														opcionMenuConfiguracion = validacion.mostrarMenuEdicionConfiguracionYValidarOpcion(opcionConfiguracionPropia);
														
														//TODO Modular esto
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
															System.out.println("configuración borrada con éxito.");
														}
														break;
												}
												opcionSubMenuConfiguracionElegida = (confirmadoEliminarConfiguracion == 'N') ? validacion.mostrarSubMenuConfiguracionElegidaYValidarOpcion() : 0 ;
											}
											
											configuraciones = gestionConfiguracion.obtenerConfiguraciones(cuentaSesion);
											
											for(ConfiguracionImpl configuracion:configuraciones) //TODO Meter en un método "cargarRelacionesEnConfiguraciones(ArrayList<ConfiguraciomImpl>)"
											{
												gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracion);
											}
											
											opcionConfiguracionPropia = validacion.mostrarObjetosYValidarObjetoElegido(configuraciones);
										}
									break;
									
								case 3: 
									//Ver configuraciones de la comunidad
										//Mostrar menuConfiguracionesComunidad y validar opcion elegida
										opcionMenuConfiguracionesComunidad = validacion.mostrarMenuConfiguracionesComunidadYValidarOpcion();
										
										while (opcionMenuConfiguracionesComunidad != 0)
										{
											configuraciones = null;
											
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
													cuentaBuscar = validacion.validarUsuario();
													
													configuraciones = gestionConfiguracion.obtenerConfiguraciones(cuentaBuscar);
													
													break;
													
												case 5:
													//Buscar por rango de precio
													precioMinimo = validacion.validarPrecioMinimo();
													precioMaximo = validacion.validarPrecioMaximo(precioMinimo);
													
													configuraciones = gestionConfiguracion.obtenerConfiguraciones(precioMinimo, precioMaximo);
													
													break;
													
												case 6:
													//Buscar por fecha
													fechaBuscar = validacion.leerYValidarFecha();
													configuraciones = resguardo.obtenerConfiguraciones(fechaBuscar);
													
													break;
													
											}
											
											for(ConfiguracionImpl configuracion:configuraciones)
											{
												gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracion);
											}
											
											configuracionComunidadElegida = validacion.mostrarObjetosYValidarObjetoElegido(configuraciones);
											
											while(configuracionComunidadElegida != null)
											{
												utils.mostrarConfiguracion(configuracionComunidadElegida);
												
												//Mostrar menu de configuracion de la comunidad elegida
												opcionMenuConfiguracionComunidadElegida = validacion.mostarMenuConfiguracionComunidadElegida();
												
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
														System.out.println("Votación realizada con éxito.");
														gestionConfiguracion.cargarRelacionesEnConfiguracion(configuracionComunidadElegida);
													} 
													catch (SQLServerException e) 
													{
														if(e.getErrorCode() == 2726)
															System.out.println("Esta votacion ya existe, no se realizará.");
														else
															e.printStackTrace();
													}
													
													utils.mostrarConfiguracion(configuracionComunidadElegida);
													
													//Mostrar menu de configuracion de la comunidad elegida
													opcionMenuConfiguracionComunidadElegida = validacion.mostarMenuConfiguracionComunidadElegida();
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
														System.out.println("No se pudo borrar la cuenta, intentelo más tarde.");
												}
												break;
											case 2:
												//Cambiar contraseña
												if(validacion.validarContrasenaActual(cuentaSesion))
												{
													nuevaContrasena = validacion.validarNuevaContrasena(cuentaSesion.getContrasena());
													cuentaSesion.setContrasena(nuevaContrasena);
													
													if(gestionCuenta.actualizarCuenta(cuentaSesion))
														System.out.println("Contraseña cambiada correctamente.");
													else
														System.out.println("La contraseña no puedo cambiarse, intentelo más tarde.");
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
	}
}
