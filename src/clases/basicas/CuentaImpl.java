package clases.basicas;

import java.util.ArrayList;

import interfaces.Cuenta;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> nombreUsuario: String, consultable
 * 						-> contrasena: String, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public String getNombreUsuario();
 * public String getContrasena();
 * 
 * public void setContrasena(String contrasena);
 */

/* FUNCIONALIDADES AÑADIDAS
 * 
 * public ArrayList<ConfiguracionImpl> obtenerConfiguraciones();
 * public ArrayList<VotacionImpl> obtenerVotaciones();
 * 
 * public void establecerConfiguraciones(ArrayList<ConfiguracionImpl> configuraciones);
 * public void establecerVotaciones(ArrayList<VotacionImpl> votaciones);
 */

/**
 * Implementación de una {@link interfaces.Cuenta} para el modelo de la aplicación.<br>
 * Tiene las relaciones correspondientes con otras clases.<br> <br>
 * 
 * Tiene:<br>
 * - Una lista de {@link ConfiguracionImpl}.<br>
 * - Una lista de {@link VotacionImpl}.<br><br>
 * 
 * Para cargar las relaciones, ha de usarse la clase de gestión {@link clases.gestion.GestionCuenta}.
 * 
 * @author Iván Moreno <br> <a href="https://github.com/Ivanmr96/">Github</a>
 */
public class CuentaImpl implements Cuenta
{
	private String nombreUsuario;
	private String contrasena;
	
	private ArrayList<ConfiguracionImpl> configuraciones;
	private ArrayList<VotacionImpl> votaciones;
	
	/**
	 * Constructor por defecto.
	 */
	public CuentaImpl()
	{
		this.nombreUsuario = "";
		this.contrasena = "";
		
		this.configuraciones = null;
		this.votaciones = null;
	}
	
	/**
	 * Constructor con parámetros.
	 * 
	 * @param nombreUsuario El nombre de usuario de la cuenta.
	 * @param contrasena La contraseña de la cuenta.
	 */
	public CuentaImpl(String nombreUsuario, String contrasena)
	{
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		
		this.configuraciones = null;
		this.votaciones = null;
	}
	
	/**
	 * Constructor de copia.
	 * 
	 * @param otra Cuenta a partir de la cual se desea construir.
	 */
	public CuentaImpl(CuentaImpl otra)
	{
		this.nombreUsuario = otra.nombreUsuario;
		this.contrasena = otra.contrasena;
		this.configuraciones = otra.configuraciones;
		this.votaciones = otra.votaciones;
	}
	
	/**
	 * Recupera el nombre de usuario de la cuenta.
	 * 
	 * @return El nombre de usuario de la cuenta.
	 */
	public String getNombreUsuario() { return this.nombreUsuario; }
	
	/**
	 * Recupera la contraseña de la cuenta.
	 * 
	 * @return La contraseña de la cuenta.
	 */
	public String getContrasena() { return this.contrasena; }
	 
	/**
	 * Establece la contraseña de la cuenta.
	 * 
	 * @param contrasena La contraseña a establecer.
	 */
	public void setContrasena(String contrasena) { this.contrasena = contrasena; }
	
	/**
	 * Obtiene las configuraciones realizadas por la cuenta.
	 * 
	 * @return Las configuraciones realizadas por la cuenta.
	 */
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones() { return this.configuraciones; }
	
	/**
	 * Obtiene las votaciones realizadas por la cuenta.
	 * 
	 * @return Las votaciones realizadas por la cuenta.
	 */
	public ArrayList<VotacionImpl> obtenerVotaciones() { return this.votaciones; }  
	
	/**
	 * Establece las configuraciones realizadas por la cuenta.
	 * 
	 * @param configuraciones Las configuraciones a establecer.
	 */
	public void establecerConfiguraciones(ArrayList<ConfiguracionImpl> configuraciones) { this.configuraciones = configuraciones; }
	
	/**
	 * Establece las votaciones realizadas por la cuenta.
	 * 
	 * @param votaciones Las votaciones a establecer.
	 */
	public void establecerVotaciones(ArrayList<VotacionImpl> votaciones) { this.votaciones = votaciones; }
	
	/**
	 * Representación como cadena: nombreUsuario
	 */
	@Override
	public String toString()
	{
		return this.nombreUsuario;
	}
}
