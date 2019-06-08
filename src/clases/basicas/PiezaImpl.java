package clases.basicas;

import java.util.ArrayList;
import interfaces.Pieza;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> ID: int, consultable
 * 						-> nombre: String, consultable, modificable
 * 						-> descripcion: String, consultable, modificable
 * 						-> precio: double, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * 
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public int getID();
 * public String getNombre();
 * public String getDescripcion();
 * public double getPrecio();
 * 
 * public void setNombre(String nombre);
 * public void setDescripcion(String descripcion);
 * public void setPrecio(double precio);
 */

/* FUNCIONALIDADES AÑADIDAS
 * 
 * public ArrayList<CocheImpl> obtenerCochesValidos();
 * public void establecerCochesValidos(ArrayList<CocheImpl> cochesValidos);
 */

public class PiezaImpl implements Pieza
{
	private int ID;
	private String nombre;
	private String descripcion;
	private double precio;
	
	private ArrayList<CocheImpl> cochesValidos;
	
	public PiezaImpl()
	{
		this.ID = 0;
		this.nombre = "";
		this.descripcion = "";
		this.precio = 0.0;
	}
	
	public PiezaImpl(int ID, String nombre, String descripcion, double precio)
	{
		this.ID = ID;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
	}
	
	public PiezaImpl(PiezaImpl otra)
	{
		this.ID = otra.ID;
		this.nombre = otra.nombre;
		this.descripcion = otra.descripcion;
		this.precio = otra.precio;
	}
	
	public int getID() { return this.ID; }
	public String getNombre() { return this.nombre; }
	public String getDescripcion() { return this.descripcion; }
	public double getPrecio() { return this.precio; }
	
	public void setNombre(String nombre) { this.nombre = nombre; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	public void setPrecio(double precio) { this.precio = precio; }
	
	public ArrayList<CocheImpl> obtenerCochesValidos() { return this.cochesValidos; }
	
	public void establecerCochesValidos(ArrayList<CocheImpl> cochesValidos) { this.cochesValidos = cochesValidos; }
}
