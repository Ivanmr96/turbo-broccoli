package clases.basicas;

import java.util.GregorianCalendar;

import interfaces.Votacion;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades b�sicas:
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

/* FUNCIONALIDADES A�ADIDAS
 * 
 * public ConfiguracionImpl obtenerConfiguracion();
 * public CuentaImpl obtenerCuenta();
 * 
 * public void establecerConfiguracion(ConfiguracionImpl configuracion);
 * public void establecerCuenta(CuentaImpl cuenta);
 */

/**
 * Implementaci�n de una {@link interfaces.Votacion} para el modelo de la aplicaci�n.<br>
 * Tiene las relaciones correspondientes con otras clases del modelo.<br><br>
 * 
 * Tiene:<br>
 * - Una {@link ConfiguracionImpl}<br>
 * - Una {@link CuentaImpl}<br><br>
 * 
 * Para cargar las relaciones con las configuraciones y las cuentas, ha de usarse la clase de gesti�n {@link clases.gestion.GestionVotacion}.
 * 
 * @author Iv�n Moreno <br> <a href="https://github.com/Ivanmr96/">Github</a>
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
	 * Constructor con par�metros.
	 * 
	 * @param ID El identificador de la votaci�n.
	 * @param fecha La fecha en la que se realiz� la votaci�n.
	 * @param calificacion La calificaci�n dada en la votaci�n.
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
	 * @param otra Votaci�n a partir de la cual se desea construir una copia.
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
	 * Recupera el identificador de la votaci�n.
	 * 
	 * @return El identificador de la votaci�n.
	 */
	public String getID() { return this.ID; }
	
	/**
	 * Recupera la fecha en la que se realiz� la votaci�n.
	 * 
	 * @return La fecha en la que se realiz� la votaci�n.
	 */
	public GregorianCalendar getFecha() { return this.fecha; }
	
	/**
	 * Recupera la calificaci�n dada en la votaci�n.
	 * 
	 * @return La calificaci�n dada en la votaci�n.
	 */
	public int getCalificacion() { return this.calificacion; }
	
	/**
	 * Establece la fecha en la que se realiz� la configuraci�n.
	 * 
	 * @param fecha La fecha en la que se realiz� la configuraci�n.
	 */
	public void setFecha(GregorianCalendar fecha) { this.fecha = fecha; }
	
	/**
	 * Establece la calificaci�n dada en la votaci�n.
	 * 
	 * @param calificacion La calificaci�n dada en la votaci�n.
	 */
	public void setCalificacion(int calificacion) { this.calificacion = calificacion; }
	
	/**
	 * Obtiene la configuraci�n a la que se realiz� esta votaci�n.
	 * 
	 * @return La configuraci�n a la que se realiz� esta votaci�n.
	 */
	public ConfiguracionImpl obtenerConfiguracion() { return this.configuracion; }
	
	/**
	 * Obtiene la cuenta que realiz� esta votaci�n.
	 * 
	 * @return La cuenta que realiz� esta votaci�n.
	 */
	public CuentaImpl obtenerCuenta() { return this.cuenta; }
	
	/**
	 * Establece la configuraci�n a la que se realiza la votaci�n.
	 * 
	 * @param configuracion La a configuraci�n a la que se realiza la votaci�n a establecer.
	 */
	public void establecerConfiguracion(ConfiguracionImpl configuracion) { this.configuracion = configuracion; }
	
	/**
	 * Establece la cuenta que realiza la votaci�n.
	 * 
	 * @param cuenta La cuenta que realiza la votaci�n a establecer.
	 */
	public void establecerCuenta(CuentaImpl cuenta) { this.cuenta = cuenta; }
}
