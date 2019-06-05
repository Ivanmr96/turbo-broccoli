package clases.basicas;

public class HibridoImpl extends MotorImpl
{
	private double consumoElectrico;
	private char tipoCarburante;
	private double consumoCarburante;
	
	public HibridoImpl()
	{
		super();
		this.consumoElectrico = 0.0;
		this.tipoCarburante = ' ';
		this.consumoCarburante = 0.0;
	}
	
	public HibridoImpl(int ID, String nombre, String descripcion, double precio, char traccion, int numeroVelocidades, int potencia, double consumoCarburante, char tipoCarburante, double consumoElectrico)
	{
		super(ID, nombre, descripcion, precio, traccion, numeroVelocidades, potencia);
		this.consumoCarburante = consumoCarburante;
		this.tipoCarburante = tipoCarburante;
		this.consumoElectrico = consumoElectrico;
	}
	
	public HibridoImpl(HibridoImpl otro)
	{
		super(otro);
		this.consumoCarburante = otro.consumoCarburante;
		this.consumoElectrico = otro.consumoElectrico;
		this.tipoCarburante = otro.tipoCarburante;
	}
	
	public double getConsumoElectrico() { return this.consumoElectrico; }
	
	public void setConsumoElectrico(double consumoElectrico) { this.consumoElectrico = consumoElectrico; }
	
	public char getTipoCarburante() { return this.tipoCarburante; }
	public double getConsumoCarburante() { return this.consumoCarburante; }
	
	public void setTipoCarburante(char tipoCarburante) { this.tipoCarburante = tipoCarburante; }
	public void setConsumoCarburante(double consumoCarburante) { this.consumoCarburante = consumoCarburante; }
}
