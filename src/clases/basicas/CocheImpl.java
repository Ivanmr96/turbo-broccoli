package clases.basicas;

import java.util.ArrayList;

import interfaces.Coche;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades b�sicas: 
 * 						-> marca: String, consultable, modificable
 * 						-> modelo: String, consultable, modificable
 * 						-> precioBase: double, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public String getMarca();
 * public String getModelo();
 * public double getPrecioBase();
 * 
 * public void setMarca(String marca);
 * public void setModelo(String modelo);
 * public void setPrecioBase(double precioBase);
 */

/* FUNCIONALIDADES A�ADIDAS
 * 
 * public ArrayList<PiezaImpl> obtenerPiezasValidas();
 * 
 * public void establecerPiezasValidas(ArrayList<PiezaImpl> piezasValidas);
 * 
 * public ArrayList<MotorImpl> obtenerMotoresValidos()
 * 
 * public ArrayList<PinturaImpl> obtenerPinturasValidas()
 * 
 * public ArrayList<LlantaImpl> obtenerLlantasValidas()
 */

/**
 * Implementaci�n de un {@link Coche} para el modelo de la aplicaci�n.<br>
 * Tiene las relaciones correspondientes con otras clases.<br> <br>
 * 
 * Tiene una lista de {@link PiezaImpl}.<br> <br>
 * 
 * Para cargar la relaci�n con las piezas, ha de usarse la clase de gesti�n {@link clases.gestion.GestionCoche}.
 * 
 * @author Iv�n Moreno <br> <a href="https://github.com/Ivanmr96/">Github</a>
 */
public class CocheImpl implements Coche
{
	private String marca;
	private String modelo;
	private double precioBase;
	
	private ArrayList<PiezaImpl> piezasValidas;
	
	/**
	 * Constructor por defecto.
	 */
	public CocheImpl()
	{
		this.marca = "";
		this.modelo = "";
		this.precioBase = 0.0;
		this.piezasValidas = null;
	}
	
	/**
	 * Constructor con par�metros.
	 * 
	 * @param marca La marca del coche.
	 * @param modelo El modelo del coche.
	 * @param precioBase El precio b�sico del coche.
	 */
	public CocheImpl(String marca, String modelo, double precioBase)
	{
		this.marca = marca;
		this.modelo = modelo;
		this.precioBase = precioBase;
		this.piezasValidas = null;
	}
	
	/**
	 * Constructor de copia.
	 * 
	 * @param otro Coche que se quiere copiar.
	 */
	public CocheImpl(CocheImpl otro)
	{
		this.marca = otro.marca;
		this.modelo = otro.modelo;
		this.precioBase = otro.precioBase;
		this.piezasValidas = otro.piezasValidas;
	}
	
	/**
	 * Recupera la marca del coche.
	 * 
	 * @return La marca del coche.
	 */
	public String getMarca() { return this.marca; }
	
	/**
	 * Recupera el modelo del coche.
	 * 
	 * @return El modelo del coche.
	 */
	public String getModelo() { return this.modelo; }
	
	/**
	 * Recupera el precio b�sico del coche.
	 * 
	 * @return El precio base del coche.
	 */
	public double getPrecioBase() { return this.precioBase; }
	
	/**
	 * Establece la marca del coche
	 * @param marca La marca a establecer al coche.
	 */
	public void setMarca(String marca) { this.marca = marca; }
	
	/**
	 * Establece el modelo del coche
	 * @param modelo El modelo a establecer al coche.
	 */
	public void setModelo(String modelo) { this.modelo = modelo; }
	
	/**
	 * Establece el precio base al coche.
	 * 
	 * @param precioBase El precio base a establecer al coche.
	 */
	public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }
	
	/**
	 * Recupera las piezas v�lidas para el coche.
	 * 
	 * @return Las piezas v�lidas para el coche.
	 */
	public ArrayList<PiezaImpl> obtenerPiezasValidas() { return this.piezasValidas; }
	
	/**
	 * Recupera las piezas extra v�lidas para el coche.
	 * 
	 * @return Las piezas extra v�lidas para el coche.
	 */
	public ArrayList<PiezaImpl> obtenerPiezasExtraValidas() 
	{
		ArrayList<PiezaImpl> piezasExtraValidas = new ArrayList<PiezaImpl>();
		
		for(PiezaImpl pieza:piezasValidas)
		{
			if(!(pieza instanceof MotorImpl) && 
			   !(pieza instanceof LlantasImpl) && 
			   !(pieza instanceof PinturaImpl))
			{	
				piezasExtraValidas.add(pieza);
			}
		}
		
		return piezasExtraValidas;
	}
	
	/**
	 * Recupera los motores v�lidos para el coche.
	 * 
	 * @return Los motores v�lidos para el coche.
	 */
	public ArrayList<MotorImpl> obtenerMotoresValidos()
	{
		ArrayList<MotorImpl> motoresValidos = new ArrayList<MotorImpl>();
		
		for(PiezaImpl pieza:piezasValidas) 
		{
			if(pieza instanceof MotorImpl) 
				motoresValidos.add((MotorImpl)pieza);
		}

		
		return motoresValidos;
	}
	
	/**
	 * Recupera las llantas v�lidas para el coche.
	 * 
	 * @return Las llantas v�lidas para el coche.
	 */
	public ArrayList<LlantasImpl> obtenerLlantasValidas()
	{
		ArrayList<LlantasImpl> llantasValidas = new ArrayList<LlantasImpl>();
		
		for(PiezaImpl pieza:piezasValidas) 
		{
			if(pieza instanceof LlantasImpl) 
				llantasValidas.add((LlantasImpl)pieza);
		}

		
		return llantasValidas;
	}
	
	/**
	 * Recupera las pinturas v�lidas para el coche.
	 * 
	 * @return Las pinturas v�lidas para el coche.
	 */
	public ArrayList<PinturaImpl> obtenerPinturasValidas()
	{
		ArrayList<PinturaImpl> pinturasValidas = new ArrayList<PinturaImpl>();
		
		for(PiezaImpl pieza:piezasValidas) 
		{
			if(pieza instanceof PinturaImpl) 
				pinturasValidas.add((PinturaImpl)pieza);
		}

		
		return pinturasValidas;
	}
	
	/**
	 * Establece las piezas v�lidas para el coche.
	 * 
	 * @param piezasValidas Las piezas v�lidas a establecer para el coche.
	 */
	public void establecerPiezasValidas(ArrayList<PiezaImpl> piezasValidas) { this.piezasValidas = piezasValidas; }
	
	/**
	 * Representacion como cadena: "marca modelo - precioBase".
	 */
	@Override
	public String toString()
	{
		return marca + " " + modelo + " - " + precioBase;
	}
	
	//TODO clone, hashcode, compareTo, equals
}
