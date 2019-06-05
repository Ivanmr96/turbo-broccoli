package clases.basicas;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> tipoCarburante: char, consultable, modificable
 * 						-> consumoCarburante: double, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public char getTipoCarburante();
 * public double getConsumoCarburante();
 * 
 * public void setTipoCarburante(char tipoCarburante);
 * public void setConsumoCarburante(double consumoCarburante);
 */

public class CombustionImpl extends MotorImpl
{
	private char tipoCarburante;
	private double consumoCarburante;
	
	public CombustionImpl()
	{
		super();
		this.tipoCarburante = ' ';
		this.consumoCarburante = 0.0;
	}
	
	public CombustionImpl(int ID, String nombre, String descripcion, double precio, char traccion, int numeroVelocidades, int potencia, char tipoCarburante, double consumoCarburante)
	{
		super(ID, nombre, descripcion, precio, traccion, numeroVelocidades, potencia);
		this.tipoCarburante = tipoCarburante;
		this.consumoCarburante = consumoCarburante;
	}
	
	public CombustionImpl(CombustionImpl otro)
	{
		super(otro);
		this.tipoCarburante = otro.tipoCarburante;
		this.consumoCarburante = otro.consumoCarburante;
	}
	
	public char getTipoCarburante() { return this.tipoCarburante; }
	public double getConsumoCarburante() { return this.consumoCarburante; }
	
	public void setTipoCarburante(char tipoCarburante) { this.tipoCarburante = tipoCarburante; }
	public void setConsumoCarburante(double consumoCarburante) { this.consumoCarburante = consumoCarburante; }
}
