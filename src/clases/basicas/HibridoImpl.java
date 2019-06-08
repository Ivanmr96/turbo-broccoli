package clases.basicas;

public class HibridoImpl extends MotorImpl
{
	private char tipoCarburante;
	private double consumoCarburante;
	
	public HibridoImpl()
	{
		super();
		this.tipoCarburante = ' ';
		this.consumoCarburante = 0.0;
	}
	
	public HibridoImpl(int ID, String nombre, String descripcion, double precio, char traccion, int numeroVelocidades, int potencia, double consumoCarburante, char tipoCarburante, int autonomia)
	{
		super(ID, nombre, descripcion, precio, traccion, numeroVelocidades, autonomia, potencia);
		this.consumoCarburante = consumoCarburante;
		this.tipoCarburante = tipoCarburante;
	}
	
	public HibridoImpl(HibridoImpl otro)
	{
		super(otro);
		this.consumoCarburante = otro.consumoCarburante;
		this.tipoCarburante = otro.tipoCarburante;
	}
	
	public char getTipoCarburante() { return this.tipoCarburante; }
	public double getConsumoCarburante() { return this.consumoCarburante; }
	
	public void setTipoCarburante(char tipoCarburante) { this.tipoCarburante = tipoCarburante; }
	public void setConsumoCarburante(double consumoCarburante) { this.consumoCarburante = consumoCarburante; }
}
