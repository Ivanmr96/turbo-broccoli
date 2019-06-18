package clases.basicas;

import java.util.GregorianCalendar;

import interfaces.Votacion;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> ID: String, consultable
 * 						-> fecha: GregorianCalendar, consultable, modificable
 * 						-> calificacion: int, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public String getID();
 * public GregorianCalendar getFecha();
 * public int getCalificacion();
 * 
 * public void setFecha(GregorianCalendar fecha);
 * public void setCalificacion(int calificacion);
 */

/* FUNCIONALIDADES AÑADIDAS
 * 
 * public ConfiguracionImpl obtenerConfiguracion();
 * public CuentaImpl obtenerCuenta();
 * 
 * public void establecerConfiguracion(ConfiguracionImpl configuracion);
 * public void establecerCuenta(CuentaImpl cuenta);
 */

/**
 * Implementación de una {@link interfaces.Votacion} para el modelo de la aplicación.<br>
 * Tiene las relaciones correspondientes con otras clases del modelo.<br><br>
 * 
 * Tiene:<br>
 * - Una {@link ConfiguracionImpl}<br>
 * - Una {@link CuentaImpl}<br><br>
 * 
 * Para cargar las relaciones con las configuraciones y las cuentas, ha de usarse la clase de gestión {@link clases.gestion.GestionVotacion}.
 * 
 * @author Iván Moreno <br> <a href="https://github.com/Ivanmr96/">Github</a>
 *
 */
public class VotacionImpl implements Votacion
{
	private String ID;
	private GregorianCalendar fecha;
	private int calificacion;
	
	private ConfiguracionImpl configuracion;
	private CuentaImpl cuenta;
	
	/**
	 * Constructor por defecto.
	 */
	public VotacionImpl()
	{
		this.ID = "";
		this.fecha = null;
		this.calificacion = 0;
		
		this.configuracion = null;
		this.cuenta = null;
	}
	
	/**
	 * Constructor con parámetros.
	 * 
	 * @param ID El identificador de la votación.
	 * @param fecha La fecha en la que se realizó la votación.
	 * @param calificacion La calificación dada en la votación.
	 */
	public VotacionImpl(String ID, GregorianCalendar fecha, int calificacion)
	{
		this.ID = ID;
		this.fecha = fecha;
		this.calificacion = calificacion;
		
		this.configuracion = null;
		this.cuenta = null;
	}
	
	/**
	 * Constructor de copia.
	 * 
	 * @param otra Votación a partir de la cual se desea construir una copia.
	 */
	public VotacionImpl(VotacionImpl otra)
	{
		this.ID = otra.ID;
		this.fecha = otra.fecha;
		this.calificacion = otra.calificacion;
		
		this.configuracion = otra.configuracion;
		this.cuenta = otra.cuenta;
	}
	
	/**
	 * Recupera el identificador de la votación.
	 * 
	 * @return El identificador de la votación.
	 */
	public String getID() { return this.ID; }
	
	/**
	 * Recupera la fecha en la que se realizó la votación.
	 * 
	 * @return La fecha en la que se realizó la votación.
	 */
	public GregorianCalendar getFecha() { return this.fecha; }
	
	/**
	 * Recupera la calificación dada en la votación.
	 * 
	 * @return La calificación dada en la votación.
	 */
	public int getCalificacion() { return this.calificacion; }
	
	/**
	 * Establece la fecha en la que se realizó la configuración.
	 * 
	 * @param fecha La fecha en la que se realizó la configuración.
	 */
	public void setFecha(GregorianCalendar fecha) { this.fecha = fecha; }
	
	/**
	 * Establece la calificación dada en la votación.
	 * 
	 * @param calificacion La calificación dada en la votación.
	 */
	public void setCalificacion(int calificacion) { this.calificacion = calificacion; }
	
	/**
	 * Obtiene la configuración a la que se realizó esta votación.
	 * 
	 * @return La configuración a la que se realizó esta votación.
	 */
	public ConfiguracionImpl obtenerConfiguracion() { return this.configuracion; }
	
	/**
	 * Obtiene la cuenta que realizó esta votación.
	 * 
	 * @return La cuenta que realizó esta votación.
	 */
	public CuentaImpl obtenerCuenta() { return this.cuenta; }
	
	/**
	 * Establece la configuración a la que se realiza la votación.
	 * 
	 * @param configuracion La a configuración a la que se realiza la votación a establecer.
	 */
	public void establecerConfiguracion(ConfiguracionImpl configuracion) { this.configuracion = configuracion; }
	
	/**
	 * Establece la cuenta que realiza la votación.
	 * 
	 * @param cuenta La cuenta que realiza la votación a establecer.
	 */
	public void establecerCuenta(CuentaImpl cuenta) { this.cuenta = cuenta; }
}
