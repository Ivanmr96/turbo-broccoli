package clases.basicas;

import interfaces.Motor;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades b�sicas:
 * 						-> traccion: char, consultable, modificable
 * 						-> numeroVelocidades: int, consultable, modificable
 * 						-> potencia: int, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public char getTraccion();
 * public int getNumeroVelocidades();
 * public int getPotencia();
 * 
 * public void setTraccion(char traccion);
 * public void setNumeroVelocidades(int numeroVelocidades);
 * public void setPotencia(int potencia);
 */

/**
 * Implementaci�n de un {@link interfaces.Motor} para el modelo de la aplicaci�n.<br>
 * 
 * @author Iv�n Moreno <br> <a href="https://github.com/Ivanmr96/">Github</a>
 */
public class MotorImpl extends PiezaImpl implements Motor
{
	private char traccion;
	private int numeroVelocidades;
	private int autonomia;
	private int potencia;
	private String tipo;
	
	/**
	 * Constructor por defecto.
	 */
	public MotorImpl()
	{
		super();
		this.traccion = ' ';
		this.numeroVelocidades = 0;
		this.autonomia = 0;
		this.potencia = 0;
		this.tipo = "";
	}
	
	/**
	 * Constructor con par�metros.
	 * 
	 * @param ID El identificador del motor.
	 * @param nombre El nombre del motor.
	 * @param descripcion La descripci�n del motor.
	 * @param precio El precio del motor.
	 * @param traccion La tracci�n.
	 * @param numeroVelocidades El n�mero de velocidades.
	 * @param autonomia La autonom�a en kil�metros.
	 * @param potencia La potencia del motor.
	 * @param tipo El tipo del motor.
	 */
	public MotorImpl(int ID, String nombre, String descripcion, double precio, char traccion, int numeroVelocidades, int autonomia, int potencia, String tipo)
	{
		super(ID, nombre, descripcion, precio);
		this.traccion = traccion;
		this.numeroVelocidades = numeroVelocidades;
		this.autonomia = autonomia;
		this.potencia = potencia;
		this.tipo = tipo;
	}
	
	/**
	 * Constructor de copia.
	 * 
	 * @param otro Otro motor a partir del cual se desea construir.
	 */
	public MotorImpl(MotorImpl otro)
	{
		super(otro);
		this.traccion = otro.traccion;
		this.numeroVelocidades = otro.numeroVelocidades;
		this.autonomia = otro.autonomia;
		this.potencia = otro.potencia;
		this.tipo = otro.tipo;
	}
	
	/**
	 * Recupera la tracci�n.
	 * 
	 * @return La tracci�n
	 */
	public char getTraccion() { return this.traccion; }
	
	/**
	 * Recupera el n�mero de velocidades.
	 * 
	 * @return El n�mero de velocidades.
	 */
	public int getNumeroVelocidades() { return this.numeroVelocidades; }
	
	/**
	 * Recupera la autonom�a (en kil�metros).
	 * 
	 * @return La autonom�a (en kil�metros).
	 */
	public int getAutonomia() { return this.autonomia; }
	
	/**
	 * Recupera la potencia en CV.
	 * @return La potencia en CV.
	 */
	public int getPotencia() { return this.potencia; }
	
	/**
	 * Recupera el tipo de motor.
	 * 
	 * @return El tipo de motor.
	 */
	public String getTipo() { return this.tipo; }
	 
	/**
	 * Establece la tracci�n.
	 * 
	 * @param traccion La tracci�n a establecer.
	 */
	public void setTraccion(char traccion) { this.traccion = traccion; }
	
	/**
	 * Establecer el n�mero de velocidades.
	 * 
	 * @param numeroVelocidades el n�mero de velocidades a establecer.
	 */
	public void setNumeroVelocidades(int numeroVelocidades) { this.numeroVelocidades = numeroVelocidades; }
	
	/**
	 * Establece la autonom�a.
	 * 
	 * @param autonomia La autonom�a a establecer.
	 */
	public void setAutonomia(int autonomia) { this.autonomia = autonomia; }
	
	/**
	 * Establece la potencia del motor en CV.
	 * 
	 * @param potencia La potencia del motor en CV.
	 */
	public void setPotencia(int potencia) {  this.potencia = potencia; }
	
	/**
	 * Establece el tipo de motor.
	 * 
	 * @param tipo El tipo de motor.
	 */
	public void setTipo(String tipo) { this.tipo = tipo; }
	
	/**
	 * Representaci�n como cadena. "ID - nombre - potencia cv - precio �"
	 */
	@Override
	public String toString()
	{		
		return this.getID() + " - " + this.getNombre() + " - "  + this.potencia + " cv - " + this.getPrecio() + " �";
	}
}
