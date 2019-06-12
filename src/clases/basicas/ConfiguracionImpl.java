package clases.basicas;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import utils.Utils;

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
	
	private MotorImpl motor;
	private LlantasImpl llantas;
	private PinturaImpl pintura;
	
	//TODO El motor, las llantas y la pintura deben ser un atributo de la clase.
	
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
	public MotorImpl obtenerMotor() { return this.motor; }
	public LlantasImpl obtenerLlantas() { return this.llantas; }
	public PinturaImpl obtenerPintura() { return pintura; }
	
	public void establecerCoche(CocheImpl coche) { this.coche = coche; }
	public void establecerCuenta(CuentaImpl cuenta) { this.cuenta = cuenta; }
	public void establecerPiezas(ArrayList<PiezaImpl> piezas) { this.piezas = piezas; }
	public void establecerVotaciones(ArrayList<VotacionImpl> votaciones ) { this.votaciones = votaciones; }
	public void establecerMotor(MotorImpl motor) { this.motor = motor; }
	public void establecerLlantas(LlantasImpl llantas) { this.llantas = llantas; }
	public void establecerPintura(PinturaImpl pintura) { this.pintura = pintura; }
	
	public void anhadirPiezaExtra(PiezaImpl pieza)
	{
		int IDPieza = pieza.getID();
		boolean repetida = false;
		
		for(int i = 0 ; i < piezas.size() && repetida == false; i++)
		{
			if(piezas.get(i).getID() == IDPieza)
				repetida = true;
		}
		
		if(!repetida)
			this.piezas.add(pieza);
	}
	
	public void eliminarPiezaExtra(PiezaImpl pieza)
	{
		int IDPieza = pieza.getID();
		
		int index = -1;
		
		
		for(int i = 0 ; i < piezas.size() && index == -1 ; i++)
		{
			if(piezas.get(i).getID() == IDPieza)
				index = i;
		}
		
		if(index > -1)
			this.piezas.remove(index);
	}
	
	
	public double obtenerPrecioTotal()
	{
		double precioTotal = obtenerCoche().getPrecioBase();
		
		if(motor != null)
		{
			precioTotal += this.motor.getPrecio();
		}
		if(llantas != null)
		{
			precioTotal += this.llantas.getPrecio();
		}
		if(pintura != null)
		{
			precioTotal += this.pintura.getPrecio();
		}
		
		for(PiezaImpl pieza:obtenerPiezas())
		{
			precioTotal += pieza.getPrecio();
		}
		
		return precioTotal;
	}
	
	@Override
	public String toString()
	{
		Utils utils = new Utils();
		
		return utils.formatearFecha(fecha) + " - " + 
			   coche.getMarca() + " " + 
			   coche.getModelo() + " - " + 
			   cuenta.getNombreUsuario() + " - " +
			   this.obtenerPrecioTotal() + " €";
	}
	
	//TODO Alguna funcion que pueda ser útil, como calcularPrecioTotal(), o calcularCalificacionMedia()
}
