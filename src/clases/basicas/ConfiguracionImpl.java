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
 * public ArrayList<PiezaImpl> obtenerPiezas();
 * public ArrayList<VotacionImpl> obtenerVotaciones();
 * 
 * public void establecerCoche(CocheImpl coche);
 * public void establecerCuenta(CuentaImpl cuenta);
 * public void establecerPiezas(ArrayList<PiezaImpl> piezas);
 * public void establecerVotaciones(ArrayList<VotacionImpl> votaciones);
 *
 * 
 */

public class ConfiguracionImpl 
{
	private GregorianCalendar fecha;
	
	private String ID;
	private CocheImpl coche;
	private CuentaImpl cuenta;
	private ArrayList<PiezaImpl> piezas;
	private ArrayList<VotacionImpl> votaciones;
	
	public ConfiguracionImpl()
	{
		this.ID = null;
		this.fecha = null;
		this.coche = null;
		this.piezas = null;
		this.votaciones = null;
	}
	
	public ConfiguracionImpl(String ID, GregorianCalendar fecha)
	{
		this.ID = ID;
		this.fecha = fecha;
	}
	
	public ConfiguracionImpl(ConfiguracionImpl otra)
	{
		this.ID = otra.ID;
		this.fecha = otra.fecha;
		this.coche = otra.coche;
		this.piezas = otra.piezas;
		this.votaciones = otra.votaciones;
	}
	
	public GregorianCalendar getFecha() { return this.fecha; }
	
	public String getID() { return this.ID; }
	
	public void setFecha(GregorianCalendar fecha) { this.fecha = fecha; }
	
	public CocheImpl obtenerCoche() { return this.coche; }
	public CuentaImpl obtenerCuenta() { return this.cuenta; }
	public ArrayList<PiezaImpl> obtenerPiezas() { return this.piezas; }
	public ArrayList<VotacionImpl> obtenerVotaciones() { return this.votaciones; }
	
	public void establecerCoche(CocheImpl coche) { this.coche = coche; }
	public void establecerCuenta(CuentaImpl cuenta) { this.cuenta = cuenta; }
	public void establecerPiezas(ArrayList<PiezaImpl> piezas) { this.piezas = piezas; }
	public void establecerVotaciones(ArrayList<VotacionImpl> votaciones ) { this.votaciones = votaciones; }
	
	@Override
	public String toString()
	{
		return this.ID + ", " + 
			   this.fecha.getTime() + ", " + 
			   coche.getMarca() + " " + 
			   coche.getModelo() + ", " + 
			   cuenta.getNombreUsuario();
	}
	
	//TODO Alguna funcion que pueda ser útil, como calcularPrecioTotal(), o calcularCalificacionMedia()
}
