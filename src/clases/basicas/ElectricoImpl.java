package clases.basicas;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> consumoElectrico: double, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public double getConsumoElectrico();
 * 
 * public void setConsumoElectrico(double consumoElectrico);
 */


public class ElectricoImpl extends MotorImpl
{
	private double consumoElectrico;
	
	public ElectricoImpl()
	{
		super();
		this.consumoElectrico = 0.0;
	}
	
	public ElectricoImpl(int ID, String nombre, String descripcion, double precio, char traccion, int numeroVelocidades, int potencia, double consumoElectrico)
	{
		super(ID, nombre, descripcion, precio, traccion, numeroVelocidades, potencia);
		this.consumoElectrico = consumoElectrico;
	}
	
	public ElectricoImpl(ElectricoImpl otro)
	{
		super(otro);
		this.consumoElectrico = otro.consumoElectrico;
	}
	
	public double getConsumoElectrico() { return this.consumoElectrico; }
	
	public void setConsumoElectrico(double consumoElectrico) { this.consumoElectrico = consumoElectrico; }
}
