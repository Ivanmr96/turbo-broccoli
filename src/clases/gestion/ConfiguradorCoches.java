package clases.gestion;

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
import utils.Utils;
import utils.Validaciones;

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
		String URLConexion = "jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;";
		
		Utils utils = new Utils();
		Scanner teclado = new Scanner(System.in);
		Resguardo resguardo = new Resguardo();
		Validaciones validacion = new Validaciones(URLConexion);
		int opcionMenuPrincipal, opcionSesion, opcionSubMenuConfiguracionElegida, opcionMenuConfiguracionesComunidad, opcionMenuConfiguracionComunidadElegida;
		CocheImpl opcionCoche, coche, cocheEdicionConfiguracion;
		CuentaImpl cuentaSesion, cuentaBuscar;
		char confirmado, confirmadoEliminarConfiguracion, confirmarGuardarConfiguracion;
		String usuario, contrasena, marca, modelo, opcionMenuConfiguracion;
		ConfiguracionImpl configuracionNueva, opcionConfiguracionPropia, configuracionComunidadElegida;
		VotacionImpl calificacion;
		ArrayList<ConfiguracionImpl> configuraciones;
		AObjeto gestion = new AObjeto(URLConexion);
		double precioMinimo, precioMaximo;
		GregorianCalendar fechaBuscar;
		PiezaImpl piezaElegidaEdicion;
		
		gestion.abrirConexion();
		validacion.abrirConexion();
		
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
										opcionCoche = validacion.mostrarObjetosYValidarObjetoElegido(gestion.obtenerCoches());
										
										configuracionNueva = new ConfiguracionImpl(UUID.randomUUID().toString(), new GregorianCalendar());		//Instancia de nueva configuracion
										configuracionNueva.establecerCoche(opcionCoche);											//Establecer la relacion del coche elegido con la nueva configuracion creada.
										configuracionNueva.establecerCuenta(cuentaSesion);
										
										if(opcionCoche != null)
										{
											try 
											{
												if(gestion.insertarConfiguracion(configuracionNueva));
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
											
											//Modulo Editar configuracion
										}
									break;
									
								case 2: 
									//Ver configuraciones propias
										//Mostrar configuraciones propias y validar opcion de configuracion elegida
										configuraciones = gestion.obtenerConfiguraciones(cuentaSesion);
										
										for(ConfiguracionImpl configuracion:configuraciones)
										{
											gestion.cargarRelacionesEnConfiguracion(configuracion);
										}
										
										opcionConfiguracionPropia = validacion.mostrarObjetosYValidarObjetoElegido(configuraciones); 
										
										
									
										while(opcionConfiguracionPropia != null)
										{
											gestion.cargarRelacionesEnConfiguracion(opcionConfiguracionPropia);
											//Mostrar estadisticas
											utils.mostrarConfiguracion(opcionConfiguracionPropia);
											
											confirmadoEliminarConfiguracion = 'N';
											
											//Mostrar submenuConfiguracionElegida y validar opcion elegida
											opcionSubMenuConfiguracionElegida = validacion.mostrarSubMenuConfiguracionElegidaYValidarOpcion();
											
											while(opcionSubMenuConfiguracionElegida != 0)
											{
												switch(opcionSubMenuConfiguracionElegida)
												{
													case 1:
														//Editar configuracion
														opcionMenuConfiguracion = validacion.MostrarMenuEdicionConfiguracionYValidarOpcion(opcionConfiguracionPropia);
														
														//TOOD Modular esto
														while(!opcionMenuConfiguracion.equals("0"))
														{
															cocheEdicionConfiguracion = opcionConfiguracionPropia.obtenerCoche();
															gestion.cargarPiezasValidasEnCoche(cocheEdicionConfiguracion);
															
															//TODO Ver si esto ese puede meter en el switch de abajo
															if(utils.esNumero(opcionMenuConfiguracion))
															//if(!opcionMenuConfiguracion.equals("M") && !opcionMenuConfiguracion.equals("L") && !opcionMenuConfiguracion.equals("P") && !opcionMenuConfiguracion.equals("+"))
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
															
															opcionMenuConfiguracion = validacion.MostrarMenuEdicionConfiguracionYValidarOpcion(opcionConfiguracionPropia);
															
															//Si sale de la pantalla, preguntar si desea guardar la configuracion o no.
															if(opcionMenuConfiguracion.equals("0"))
															{
																confirmarGuardarConfiguracion = validacion.confirmarGuardarConfiguracion();
																
																if(confirmarGuardarConfiguracion == 'S')
																	gestion.actualizarConfiguracion(opcionConfiguracionPropia);
																else
																{
																	opcionConfiguracionPropia = gestion.obtenerConfiguracion(opcionConfiguracionPropia.getID());
																	gestion.cargarRelacionesEnConfiguracion(opcionConfiguracionPropia);
																}
															}
														}
														
														break;
													case 2:
														//Borrar configuracion
														confirmadoEliminarConfiguracion = validacion.confirmarBorrarConfiguracion();
														if(confirmadoEliminarConfiguracion == 'S')
														{
															gestion.eliminarConfiguracion(opcionConfiguracionPropia);
															System.out.println("configuracion borrada con exito");
														}
														break;
												}
												opcionSubMenuConfiguracionElegida = (confirmadoEliminarConfiguracion == 'N') ? validacion.mostrarSubMenuConfiguracionElegidaYValidarOpcion() : 0 ;
											}
											
											configuraciones = gestion.obtenerConfiguraciones(cuentaSesion);
											for(ConfiguracionImpl configuracion:configuraciones)
											{
												gestion.cargarRelacionesEnConfiguracion(configuracion);
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
													configuraciones = gestion.obtenerConfiguraciones();
													break;
													
												case 2:
													//Buscar por marca
													marca = validacion.mostrarObjetosYValidarObjetoElegido(gestion.obtenerMarcas());
													
													configuraciones = gestion.obtenerConfiguraciones(marca);
													
													break;
													
												case 3:
													//Buscar por marca y modelo

													marca = validacion.mostrarObjetosYValidarObjetoElegido(gestion.obtenerMarcas());
													
													//modelo = validacion.mostrarListaModelosYValidarModeloElegido(marca);
													modelo = validacion.mostrarObjetosYValidarObjetoElegido(gestion.obtenerModelos(marca));
													
													coche = gestion.obtenerCoche(marca, modelo);
													
													configuraciones = gestion.obtenerConfiguraciones(coche);
													
													break;
													
												case 4:
													//Buscar por usuario
													cuentaBuscar = validacion.validarUsuario();
													
													configuraciones = gestion.obtenerConfiguraciones(cuentaBuscar);
													
													break;
													
												case 5:
													//Buscar por rango de precio
													precioMinimo = validacion.validarPrecioMinimo();
													precioMaximo = validacion.validarPrecioMaximo(precioMinimo);
													
													configuraciones = gestion.obtenerConfiguraciones(precioMinimo, precioMaximo);
													
													break;
													
												case 6:
													//Buscar por fecha
													fechaBuscar = validacion.leerYValidarFecha();
													configuraciones = resguardo.obtenerConfiguraciones(fechaBuscar);
													//configuraciones = gestion.obtenerConfiguraciones(fechaBuscar);
													
													break;
													
											}
											
											//configuracionComunidadElegida = validacion.mostrarConfiguracionesYValidar(configuraciones);
											
											for(ConfiguracionImpl configuracion:configuraciones)
											{
												gestion.cargarRelacionesEnConfiguracion(configuracion);
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
														gestion.insertarVotacion(calificacion);
														System.out.println("Votación realizada con éxito.");
														gestion.cargarRelacionesEnConfiguracion(configuracionComunidadElegida);
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
									/*
									opcionMenuEditarCuenta = mostrarMenuEditarCuentaYValidarOpcion();
									
									while(opcionMenuEditarCuenta != 0)
									{
										switch(opcionMenuEditarCuenta)
										{
											case 1:
												//Borrar cuenta
											case 2:
												//Cambiar contraseña
										}
										
										opcionMenuEditarCuenta = mostrarMenuEditarCuentaYValidarOpcion();
									}*/
									break;
									
							}
							
							//Mostrar menu sesion y validar opcion
							opcionSesion = validacion.mostrarMenuSesionYValidarOpcion();
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
							
							confirmado = teclado.next().charAt(0);
							
							confirmado = Character.toUpperCase(confirmado);
							
						}while(confirmado != 'S' && confirmado != 'N');
						
						if(confirmado == 'S')
							//Registrar cuenta
							//System.out.println(contrasena);
							try 
							{
								if(gestion.insertarCuenta(new CuentaImpl(usuario, contrasena)))
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
		
		gestion.cerrarConexion();
		validacion.cerrarConexion();
	}
}
