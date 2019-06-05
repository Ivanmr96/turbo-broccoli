package clases.basicas;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> fecha: GregorianCalendar, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 * 
 */

/* INTERFAZ
 * 
 * public GregorianCalendar getFecha();
 * 
 * public void setFecha(GregorianCalendar fecha);
 * 
 */

/* FUNCIONALIDADES AÑADIDAS
 * 
 * public CocheImpl obtenerCoche();
 * public CuentaImpl obtenerCuenta();
 * 
 */

public class ConfiguracionImpl 
{
	private GregorianCalendar fecha;
	
	private CocheImpl coche;
	private CuentaImpl cuenta;
	private ArrayList<PiezaImpl> piezas;
	private ArrayList<VotacionImpl> votaciones;
	
	public ConfiguracionImpl()
	{
		this.fecha = null;
		this.coche = null;
		this.piezas = null;
		this.votaciones = null;
	}
	
	public ConfiguracionImpl(GregorianCalendar fecha)
	{
		this.fecha = fecha;
	}
	
	public ConfiguracionImpl(ConfiguracionImpl otra)
	{
		this.fecha = otra.fecha;
		this.coche = otra.coche;
		this.piezas = otra.piezas;
		this.votaciones = otra.votaciones;
	}
	
	public GregorianCalendar getFecha() { return this.fecha; }
	
	public void setFecha(GregorianCalendar fecha) { this.fecha = fecha; }
}
