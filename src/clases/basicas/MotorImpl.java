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

public class MotorImpl extends PiezaImpl
{
	private char traccion;
	private int numeroVelocidades;
	private int potencia;
	
	public MotorImpl()
	{
		super();
		this.traccion = ' ';
		this.numeroVelocidades = 0;
		this.potencia = 0;
	}
	
	public MotorImpl(int ID, String nombre, String descripcion, double precio, char traccion, int numeroVelocidades, int potencia)
	{
		super(ID, nombre, descripcion, precio);
		this.traccion = traccion;
		this.numeroVelocidades = numeroVelocidades;
		this.potencia = potencia;
	}
	
	public MotorImpl(MotorImpl otro)
	{
		super(otro);
		this.traccion = otro.traccion;
		this.numeroVelocidades = otro.numeroVelocidades;
		this.potencia = otro.potencia;
	}
	
	public char getTraccion() { return this.traccion; }
	public int getNumeroVelocidades() { return this.numeroVelocidades; }
	public int getPotencia() { return this.potencia; }
	 
	public void setTraccion(char traccion) { this.traccion = traccion; }
	public void setNumeroVelocidades(int numeroVelocidades) { this.numeroVelocidades = numeroVelocidades; }
	public void setPotencia(int potencia) {  this.potencia = potencia; }
}
