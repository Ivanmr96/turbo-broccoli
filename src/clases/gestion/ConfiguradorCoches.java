package clases.gestion;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
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
		Utils utils = new Utils();
		Scanner teclado = new Scanner(System.in);
		Validaciones validacion = new Validaciones();
		int opcionMenuPrincipal, opcionSesion, opcionSubMenuConfiguracionElegida, opcionMenuConfiguracionesComunidad, opcionMenuConfiguracionComunidadElegida;
		CocheImpl opcionCoche;
		CuentaImpl cuentaSesion;
		char confirmado;
		String usuario, contrasena;
		ConfiguracionImpl configuracionNueva, opcionConfiguracionPropia, configuracionComunidadElegida;
		VotacionImpl calificacion;
		ArrayList<ConfiguracionImpl> configuraciones;
		AObjeto gestion = new AObjeto("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		
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
										opcionCoche = validacion.mostrarCochesDisponiblesYValidarOpcionCocheElegido();
										
										configuracionNueva = new ConfiguracionImpl(utils.toString(), new GregorianCalendar());		//Instancia de nueva configuracion
										configuracionNueva.establecerCoche(opcionCoche);											//Establecer la relacion del coche elegido con la nueva configuracion creada.
										
										
										if(opcionCoche != null)
										{
											//Modulo Editar configuracion
										}
									break;
									
								case 2: 
									//Ver configuraciones propias
										//Mostrar configuraciones propias y validar opcion de configuracion elegida
										opcionConfiguracionPropia = validacion.mostrarConfiguracionesPropiasYValidarOpcionElegida(cuentaSesion);
									
										while(opcionConfiguracionPropia != null)
										{
											//Mostrar estadisticas
											
											//Mostrar submenuConfiguracionElegida y validar opcion elegida
											opcionSubMenuConfiguracionElegida = validacion.mostrarSubMenuConfiguracionElegidaYValidarOpcion();
											
											while(opcionSubMenuConfiguracionElegida != 0)
											{
												//Modulo editar configuracion
											}
										}
										
									break;
									
								case 3: 
									//Ver configuraciones de la comunidad
										//Mostrar menuConfiguracionesComunidad y validar opcion elegida
										opcionMenuConfiguracionesComunidad = validacion.mostrarMenuConfiguracionesComunidadYValidarOpcion();
										
										while (opcionMenuConfiguracionesComunidad != 0)
										{
											switch(opcionMenuConfiguracionesComunidad)
											{
												case 1:
													//Mostrar todas las configuraciones de la comunidad
													configuracionComunidadElegida = validacion.mostrarConfiguracionesYValidar(configuraciones);
													
													break;
												case 2:
													//Buscar por marca
													
													configuraciones = gestion.obtenerConfiguraciones();
													
													break;
												case 3:
													//Buscar por marca y modelo
													break;
												case 4:
													//Buscar por usuario
													break;
												case 5:
													//Buscar por rango de precio
													break;
												case 6:
													//Buscar por fecha
													break;
											}
											
											if(configuracionComunidadElegida != null)
											{
												//Mostrar estadisticas
												
												//Mostrar menu de configuracion de la comunidad elegida
												opcionMenuConfiguracionComunidadElegida = validacion.mostarMenuConfiguracionComunidadElegida();
												
												if(opcionMenuConfiguracionComunidadElegida != 0)
												{
													//Puntuar la configuracion
													calificacion = validacion.validarCalificacion();
													
													//Insertar configuracion
													
												}
											}
										}
									break;
									
								case 4: 
									//Editar cuenta
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
					break;
			}
			
			//Mostrar menu principal y validar opcion
			opcionMenuPrincipal = validacion.mostrarMenuPrincipalYValidarOpcion();
		}
	}
}
