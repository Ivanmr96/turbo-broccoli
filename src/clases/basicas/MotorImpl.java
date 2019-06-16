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

//TODO Javadoc
public class MotorImpl extends PiezaImpl
{
	private char traccion;
	private int numeroVelocidades;
	private int autonomia;
	private int potencia;
	private String tipo;
	
	public MotorImpl()
	{
		super();
		this.traccion = ' ';
		this.numeroVelocidades = 0;
		this.autonomia = 0;
		this.potencia = 0;
		this.tipo = "";
	}
	
	public MotorImpl(int ID, String nombre, String descripcion, double precio, char traccion, int numeroVelocidades, int autonomia, int potencia, String tipo)
	{
		super(ID, nombre, descripcion, precio);
		this.traccion = traccion;
		this.numeroVelocidades = numeroVelocidades;
		this.autonomia = autonomia;
		this.potencia = potencia;
		this.tipo = tipo;
	}
	
	public MotorImpl(MotorImpl otro)
	{
		super(otro);
		this.traccion = otro.traccion;
		this.numeroVelocidades = otro.numeroVelocidades;
		this.autonomia = otro.autonomia;
		this.potencia = otro.potencia;
		this.tipo = otro.tipo;
	}
	
	public char getTraccion() { return this.traccion; }
	public int getNumeroVelocidades() { return this.numeroVelocidades; }
	public int getAutonomia() { return this.autonomia; }
	public int getPotencia() { return this.potencia; }
	public String getTipo() { return this.tipo; }
	 
	public void setTraccion(char traccion) { this.traccion = traccion; }
	public void setNumeroVelocidades(int numeroVelocidades) { this.numeroVelocidades = numeroVelocidades; }
	public void setAutonomia(int autonomia) { this.autonomia = autonomia; }
	public void setPotencia(int potencia) {  this.potencia = potencia; }
	public void setTipo(String tipo) { this.tipo = tipo; }
	
	@Override
	public String toString()
	{		
		return this.getID() + " - " + this.getNombre() + " - "  + this.potencia + " cv - " + this.getPrecio() + " €";
	}
	
	//TODO clone, hashcode, compareTo, equals
}
