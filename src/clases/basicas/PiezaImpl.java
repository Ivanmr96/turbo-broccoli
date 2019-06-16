package clases.basicas;

import java.util.ArrayList;
import interfaces.Pieza;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades b�sicas:
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

/* FUNCIONALIDADES A�ADIDAS
 * 
 * public ArrayList<CocheImpl> obtenerCochesValidos();
 * public void establecerCochesValidos(ArrayList<CocheImpl> cochesValidos);
 */

/**
 * Implementaci�n de una {@link interfaces.Pieza} para el modelo de la aplicaci�n.<br>
 * Tiene las relaciones correspondientes con otras clases del modelo.<br><br>
 * 
 * Tiene una lista de {@link CocheImpl}.<br><br>
 * 
 * Para cargar las relaciones con los coches, ha de usarse la clase de gesti�n {@link clases.gestion.GestionPieza}.
 * 
 * @author Iv�n Moreno <br> <a href="https://github.com/Ivanmr96/">Github</a>
 *
 */
public class PiezaImpl implements Pieza
{
	private int ID;
	private String nombre;
	private String descripcion;
	private double precio;
	
	private ArrayList<CocheImpl> cochesValidos;
	
	/**
	 * Constructor por defecto.
	 */
	public PiezaImpl()
	{
		this.ID = 0;
		this.nombre = "";
		this.descripcion = "";
		this.precio = 0.0;
	}
	
	/**
	 * Constructor con par�metros.
	 * 
	 * @param ID El identificador de la pieza.
	 * @param nombre El nombre de la pieza.
	 * @param descripcion La descripci�n de la pieza.
	 * @param precio El precio de la pieza. En euros.
	 */
	public PiezaImpl(int ID, String nombre, String descripcion, double precio)
	{
		this.ID = ID;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
	}
	
	/**
	 * Constructor de copia.
	 * 
	 * @param otra La pieza a partir de la cual se desea construir una copia.
	 */
	public PiezaImpl(PiezaImpl otra)
	{
		this.ID = otra.ID;
		this.nombre = otra.nombre;
		this.descripcion = otra.descripcion;
		this.precio = otra.precio;
	}
	
	/**
	 * Recupera el n�mero identificador de la pieza.
	 * 
	 * @return El n�mero identificador de la pieza.
	 */
	public int getID() { return this.ID; }
	
	/**
	 * Recupera el nombre de la pieza.
	 * 
	 * @return El nombre de la pieza.
	 */
	public String getNombre() { return this.nombre; }
	
	/**
	 * Recupera la descripci�n de la pieza.
	 * 
	 * @return La descripci�n de la pieza.
	 */
	public String getDescripcion() { return this.descripcion; }
	
	/**
	 * Recupera el precio de la pieza en euros.
	 * 
	 * @return El precio de la pieza expresado en euros.
	 */
	public double getPrecio() { return this.precio; }
	
	/**
	 * Establece el nombre de la pieza.
	 * 
	 * @param nombre El nombre a establecer.
	 */
	public void setNombre(String nombre) { this.nombre = nombre; }
	
	/**
	 * Establece la descripci�n de la pieza.
	 * 
	 * @param descripcion La descripci�n a establecer.
	 */
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	
	/**
	 * Establece el precio de la pieza en euros.
	 * 
	 * @param precio el precio (en euros) a establecer.
	 */
	public void setPrecio(double precio) { this.precio = precio; }
	
	/**
	 * Obtiene los coches v�lidos para esta pieza.
	 * 
	 * @return Los coches v�lidos para esta pieza.
	 */
	public ArrayList<CocheImpl> obtenerCochesValidos() { return this.cochesValidos; }
	
	/**
	 * Establece los coches v�lidos para esta pieza.
	 * 
	 * @param cochesValidos La lista de coches v�lidos a establecer.
	 */
	public void establecerCochesValidos(ArrayList<CocheImpl> cochesValidos) { this.cochesValidos = cochesValidos; }
	
	/**
	 * Representaci�n como cadena: "ID - nombre - precio �"
	 */
	@Override
	public String toString()
	{
		return this.ID + " - " + this.nombre + " - " + this.precio + " �";
	}
	
	//TODO clone, hashcode, compareTo, equals
}
