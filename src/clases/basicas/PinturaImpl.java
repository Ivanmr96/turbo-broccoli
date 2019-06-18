package clases.basicas;

import interfaces.Pintura;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> color: String, consultable, modificable
 * 						-> acabado: String, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 * 
 */

/* INTERFAZ
 * 
 * public String getColor();
 * public String getAcabado();
 * 
 * public void setColor(String color);
 * public void setAcabado(String acabado);
 */

/**
 * Implementación de una {@link interfaces.Pintura} para el modelo de la aplicación.<br>
 * 
 * @author Iván Moreno <br> <a href="https://github.com/Ivanmr96/">Github</a>
 *
 */
public class PinturaImpl extends PiezaImpl implements Pintura
{
	private String color;
	private String acabado;
	
	/**
	 * Constructor por defecto.
	 */
	public PinturaImpl()
	{
		super();
		this.color = "";
		this.acabado = "";
	}
	
	/**
	 * Constructor con parámetros.
	 * 
	 * @param ID El identificador de la pintura.
	 * @param nombre El nombre de la pintura.
	 * @param descripcion La descripción de la pintura.
	 * @param precio El precio de la pintura.
	 * @param color El color de la pintura.
	 * @param acabado El acabado de la pintura.
	 */
	public PinturaImpl(int ID, String nombre, String descripcion, double precio, String color, String acabado)
	{
		super(ID, nombre, descripcion, precio);
		this.color = color;
		this.acabado = acabado;
	}
	
	/**
	 * Constructor de copia.
	 * 
	 * @param otra La pintura a partir de la cual se desea construir una copia.
	 */
	public PinturaImpl(PinturaImpl otra)
	{
		super(otra);
		this.color = otra.color;
		this.acabado = otra.acabado;
	}
	
	/**
	 * Recupera el color de la pintura.
	 * 
	 * @return El color de la pintura.
	 */
	public String getColor() { return this.color; }
	
	/**
	 * Recupera el acabado de la pintura.
	 * 
	 * @return El acabado de la pintura.
	 */
	public String getAcabado() { return this.acabado; }
	
	/**
	 * Establece el color de la pintura.
	 * 
	 * @param color El color a establecer.
	 */
	public void setColor(String color) { this.color = color; }
	
	/**
	 * Establece el acabado de la pintura.
	 * 
	 * @param acabado El acabado a establecer.
	 */
	public void setAcabado(String acabado) { this.acabado = acabado; }
	
	/**
	 * Representación como cadena: "ID - nombre - color - acabado - precio €"
	 */
	@Override
	public String toString()
	{
		return this.getID() + " - " + this.getNombre() + " - " + this.getColor() + " " + this.getAcabado() + " - " + this.getPrecio() + " €";
	}
}
