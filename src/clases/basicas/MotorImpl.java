package clases.basicas;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
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
 * Implementación de un {@link interfaces.Motor} para el modelo de la aplicación.<br>
 * 
 * @author Iván Moreno <br> <a href="https://github.com/Ivanmr96/">Github</a>
 */
public class MotorImpl extends PiezaImpl
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
	 * Constructor con parámetros.
	 * 
	 * @param ID El identificador del motor.
	 * @param nombre El nombre del motor.
	 * @param descripcion La descripción del motor.
	 * @param precio El precio del motor.
	 * @param traccion La tracción.
	 * @param numeroVelocidades El número de velocidades.
	 * @param autonomia La autonomía en kilómetros.
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
	 * Recupera la tracción.
	 * 
	 * @return La tracción
	 */
	public char getTraccion() { return this.traccion; }
	
	/**
	 * Recupera el número de velocidades.
	 * 
	 * @return El número de velocidades.
	 */
	public int getNumeroVelocidades() { return this.numeroVelocidades; }
	
	/**
	 * Recupera la autonomía (en kilómetros).
	 * 
	 * @return La autonomía (en kilómetros).
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
	 * Establece la tracción.
	 * 
	 * @param traccion La tracción a establecer.
	 */
	public void setTraccion(char traccion) { this.traccion = traccion; }
	
	/**
	 * Establecer el número de velocidades.
	 * 
	 * @param numeroVelocidades el número de velocidades a establecer.
	 */
	public void setNumeroVelocidades(int numeroVelocidades) { this.numeroVelocidades = numeroVelocidades; }
	
	/**
	 * Establece la autonomía.
	 * 
	 * @param autonomia La autonomía a establecer.
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
	 * Representación como cadena. "ID - nombre - potencia cv - precio €"
	 */
	@Override
	public String toString()
	{		
		return this.getID() + " - " + this.getNombre() + " - "  + this.potencia + " cv - " + this.getPrecio() + " €";
	}
	
	//TODO clone, hashcode, compareTo, equals
}
