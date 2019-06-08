package clases.basicas;

import java.util.GregorianCalendar;

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

public class VotacionImpl 
{
	private String ID;
	private GregorianCalendar fecha;
	private int calificacion;
	
	private ConfiguracionImpl configuracion;
	private CuentaImpl cuenta;
	
	public VotacionImpl()
	{
		this.ID = "";
		this.fecha = null;
		this.calificacion = 0;
		
		this.configuracion = null;
		this.cuenta = null;
	}
	
	public VotacionImpl(String ID, GregorianCalendar fecha, int calificacion)
	{
		this.ID = ID;
		this.fecha = fecha;
		this.calificacion = calificacion;
		
		this.configuracion = null;
		this.cuenta = null;
	}
	
	public VotacionImpl(VotacionImpl otra)
	{
		this.ID = otra.ID;
		this.fecha = otra.fecha;
		this.calificacion = otra.calificacion;
		
		this.configuracion = otra.configuracion;
		this.cuenta = otra.cuenta;
	}
	
	public String getID() { return this.ID; }
	public GregorianCalendar getFecha() { return this.fecha; }
	public int getCalificacion() { return this.calificacion; }
	
	public void setFecha(GregorianCalendar fecha) { this.fecha = fecha; }
	public void setCalificacion(int calificacion) { this.calificacion = calificacion; }
	
	public ConfiguracionImpl obtenerConfiguracion() { return this.configuracion; }
	public CuentaImpl obtenerCuenta() { return this.cuenta; }
	
	public void establecerConfiguracion(ConfiguracionImpl configuracion) { this.configuracion = configuracion; }
	public void establecerCuenta(CuentaImpl cuenta) { this.cuenta = cuenta; }
}
