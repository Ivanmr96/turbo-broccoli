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
 * public void establecerPiezasValidas(ArrayList<PiezaImpl> piezasValidas),
 * 
 */

/**
 * 
 * @author moren
 *
 */
public class CocheImpl implements Coche
{
	private String marca;
	private String modelo;
	private double precioBase;
	
	private ArrayList<PiezaImpl> piezasValidas;
	
	public CocheImpl()
	{
		this.marca = "";
		this.modelo = "";
		this.precioBase = 0.0;
		this.piezasValidas = null;
	}
	
	public CocheImpl(String marca, String modelo, double precioBase)
	{
		this.marca = marca;
		this.modelo = modelo;
		this.precioBase = precioBase;
		this.piezasValidas = null;
	}
	
	public CocheImpl(CocheImpl otro)
	{
		this.marca = otro.marca;
		this.modelo = otro.modelo;
		this.precioBase = otro.precioBase;
		this.piezasValidas = otro.piezasValidas;
	}
	
	public String getMarca() { return this.marca; }
	public String getModelo() { return this.modelo; }
	public double getPrecioBase() { return this.precioBase; }
	  
	public void setMarca(String marca) { this.marca = marca; }
	public void setModelo(String modelo) { this.modelo = modelo; }
	public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }
	
	public ArrayList<PiezaImpl> obtenerPiezasValidas() { return this.piezasValidas; }
	
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
	
	public void establecerPiezasValidas(ArrayList<PiezaImpl> piezasValidas) { this.piezasValidas = piezasValidas; }
	
	@Override
	public String toString()
	{
		return marca + " " + modelo + " - " + precioBase;
	}
}
