package clases.basicas;

import java.util.ArrayList;

import interfaces.Coche;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas: 
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

/* FUNCIONALIDADES AÑADIDAS
 * 
 * public ArrayList<PiezaImpl> obtenerPiezasValidas();
 * 
 * public void establecerPiezasValidas(ArrayList<PiezaImpl> piezasValidas),
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
	
	public void establecerPiezasValidas(ArrayList<PiezaImpl> piezasValidas) { this.piezasValidas = piezasValidas; }
}
