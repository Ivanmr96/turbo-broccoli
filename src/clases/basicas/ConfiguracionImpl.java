package clases.basicas;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import utils.Utils;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades b�sicas:
 * 						-> ID: String, consultable
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

/* FUNCIONALIDADES A�ADIDAS
 * 
 * public CocheImpl obtenerCoche();
 * public CuentaImpl obtenerCuenta();
 * public ArrayList<PiezaImpl> obtenerPiezas();
 * public ArrayList<VotacionImpl> obtenerVotaciones();
 * 
 * public MotorImpl obtenerMotor();
 * public LlantasImpl obtenerLlantas();
 * public PinturaImpl obtenerPintura();
 * 
 * public void establecerCoche(CocheImpl coche);
 * public void establecerCuenta(CuentaImpl cuenta);
 * public void establecerPiezas(ArrayList<PiezaImpl> piezas);
 * public void establecerVotaciones(ArrayList<VotacionImpl> votaciones);
 * 
 * public void establecerMotor(MotorImpl motor);
 * public void establecerPintura(PinturaImpl pintura);
 * public void establecerLlantas(LlantasImpl llantas);
 * 
 * public void anhadirPiezaExtra(PiezaImpl pieza);
 * public void eliminarPiezaExtra(PiezaImpl pieza);
 * 
 * public double precioTotal();
 * 
 */

/**
 * Implementacion de una {@link interfaces.Configuracion} para el modelo de la aplicaci�n.<br>
 * Tiene las relaciones correspondientes con otras clases.<br> <br>
 * 
 * Tiene:<br>
 * - Un {@link CocheImpl}<br>
 * - Una {@link CuentaImpl}<br>
 * - Una lista de {@link PiezaImpl}<br>
 * - Un {@link MotorImpl}<br>
 * - Unas {@link LlantasImpl}<br>
 * - Una {@link PinturaImpl}<br>
 * - Una lista de {@link VotacionImpl} <br><br>
 * 
 * Para cargar las relaciones, ha de usarse la clase de gesti�n {@link clases.gestion.GestionConfiguracion}.
 * 
 * @author Iv�n Moreno <br> <a href="https://github.com/Ivanmr96/">Github</a>
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
	
	/**
	 * Constructor por defecto.
	 */
	public ConfiguracionImpl()
	{
		this.ID = null;
		this.fecha = null;
		this.coche = null;
		this.piezas = null;
		this.votaciones = null;
	}
	
	/**
	 * Constructor con par�metros.
	 * 
	 * @param ID El identificador de la configuraci�n.
	 * @param fecha La fecha en la que se realiz� la configuraci�n.
	 */
	public ConfiguracionImpl(String ID, GregorianCalendar fecha)
	{
		this.ID = ID;
		this.fecha = fecha;
	}
	
	/**
	 * Constructor de copia.
	 * @param otra La configuraci�n de la cual se desea construir una copia.
	 */
	public ConfiguracionImpl(ConfiguracionImpl otra)
	{
		this.ID = otra.ID;
		this.fecha = otra.fecha;
		this.coche = otra.coche;
		this.piezas = otra.piezas;
		this.votaciones = otra.votaciones;
	}
	
	/**
	 * Recupera la fecha en la que se realiz� la configuraci�n.
	 * 
	 * @return La fecha en la que se realizo� la configuraci�n.
	 */
	public GregorianCalendar getFecha() { return this.fecha; }
	
	/**
	 * Recupera el ID de la configuraci�n.
	 * 
	 * @return El ID de la configuraci�n.
	 */
	public String getID() { return this.ID; }
	
	/**
	 * Establece la fecha en la que se realiz� la configuraci�n.
	 * 
	 * @param fecha La fecha que se desea establecer como fecha en la que se realiz� la configuraci�n.
	 */
	public void setFecha(GregorianCalendar fecha) { this.fecha = fecha; }
	
	/**
	 * Recupera el coche asociado a la configuraci�n.
	 * 
	 * @return El coche asociado a esta configuraci�n.
	 */
	public CocheImpl obtenerCoche() { return this.coche; }
	
	/**
	 * Recupera la cuenta asociada a la configuraci�n.
	 * 
	 * @return La cuenta asociada a esta configuraci�n.
	 */
	public CuentaImpl obtenerCuenta() { return this.cuenta; }
	
	/**
	 * Recupera la lista de piezas asociadas a la configuraci�n.
	 * 
	 * @return Lista de piezas asociadas a esta configuraci�n.
	 */
	public ArrayList<PiezaImpl> obtenerPiezas() { return this.piezas; }
	
	/**
	 * Recupera las votaciones asociadas a la configuraci�n.
	 * 
	 * @return Lista de votaciones asociadas a esta configuraci�n.
	 */
	public ArrayList<VotacionImpl> obtenerVotaciones() { return this.votaciones; }
	
	/**
	 * Recupera el motor asociado a la configuraci�n.
	 * 
	 * @return El motor asociado a esta configuraci�n.
	 */
	public MotorImpl obtenerMotor() { return this.motor; }
	
	/**
	 * Recupera las llantas asociadas a la configuraci�n.
	 * 
	 * @return Las llantas asociadas a esta configuraci�n.
	 */
	public LlantasImpl obtenerLlantas() { return this.llantas; }
	
	/**
	 * Recupera la pintura asociada a la configuraci�n.
	 * 
	 * @return La pintura asociada a esta configuraci�n.
	 */
	public PinturaImpl obtenerPintura() { return pintura; }
	
	/**
	 * Calcula la calificaci�n media de la configuraci�n.<br>
	 * Realiza la media aritm�tica con la calificaci�n de cada votaci�n asociada a esta configuraci�n.<br>
	 * 
	 * @return La calificaci�n media de esta configuraci�n.
	 */
	public double calificacionMedia()
	{
		double totalCalificaciones = 0;
		double calificacionMedia = 0;
		
		for(VotacionImpl votacion:votaciones)
		{
			totalCalificaciones += votacion.getCalificacion();
		}
		
		calificacionMedia = (votaciones.size() > 0) ? totalCalificaciones / votaciones.size() : -1;
		
		return calificacionMedia;
	}
	
	/**
	 * Establece el coche de configuraci�n.
	 * 
	 * @param coche El coche a establecer.
	 */
	public void establecerCoche(CocheImpl coche) { this.coche = coche; }
	
	/**
	 * Establece la cuenta de configuraci�n.
	 * 
	 * @param cuenta La cuenta a establecer.
	 */
	public void establecerCuenta(CuentaImpl cuenta) { this.cuenta = cuenta; }
	
	/**
	 * Establece las piezas deconfiguraci�n.
	 * 
	 * @param piezas Las piezas a establecer.
	 */
	public void establecerPiezas(ArrayList<PiezaImpl> piezas) { this.piezas = piezas; }
	
	/**
	 * Establece las votaciones de configuraci�n.
	 * 
	 * @param votaciones Las votaciones a establecer.
	 */
	public void establecerVotaciones(ArrayList<VotacionImpl> votaciones ) { this.votaciones = votaciones; }
	
	/**
	 * Establece el motor de configuraci�n.
	 * 
	 * @param motor El motor a establecer.
	 */
	public void establecerMotor(MotorImpl motor) { this.motor = motor; }
	
	/**
	 * Establece las llantas de esta configuraci�n.
	 * 
	 * @param llantas Las llantas a establecer.
	 */
	public void establecerLlantas(LlantasImpl llantas) { this.llantas = llantas; }
	
	/**
	 * Establece la pintura de esta configuraci�n.
	 * 
	 * @param pintura La pintura a establecer.
	 */
	public void establecerPintura(PinturaImpl pintura) { this.pintura = pintura; }
	
	/**
	 * A�ade una pieza extra a la lista de piezas de esta configuraci�n.<br>
	 * Si la pieza ya existe en la lista, no se a�adir�.
	 * 
	 * @param pieza La pieza a a�adir.
	 */
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
	
	/**
	 * Elimina una piza extra de la lista de piezas de esta configuraci�n.<br>
	 * @param pieza La pieza a eliminar.
	 */
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
	
	/**
	 * Calcula el precio total de la configuraci�n, sumando el precio b�sico del coche y el precio de las piezas (inclyuendo motor, pintura y llantas).
	 * 
	 * @return El preci total de la configuracion.
	 */
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
	
	/**
	 * Representaci�n como cadena: "fecha formateada - marca modelo - usuario - precio total".
	 */
	@Override
	public String toString()
	{
		Utils utils = new Utils();
		
		return utils.formatearFecha(fecha) + " - " + 
			   coche.getMarca() + " " + 
			   coche.getModelo() + " - " + 
			   cuenta.getNombreUsuario() + " - " +
			   this.obtenerPrecioTotal() + " �";
	}
	
}
