package clases.basicas;

import interfaces.Llantas;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> pulgadas: int, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 * 
 */

/* INTERFAZ
 * 
 * public int getPulgadas();
 * 
 * public void setPulgadas(int pulgadas);
 */

/**
 * Implementación de unas {@link interfaces.Llantas} para el modelo de la aplicación.<br>
 * 
 * @author Iván Moreno <br> <a href="https://github.com/Ivanmr96/">Github</a>
 */
public class LlantasImpl extends PiezaImpl implements Llantas
{
	private int pulgadas;
	
	/**
	 * Constructor por defecto.
	 */
	public LlantasImpl()
	{
		super();
		this.pulgadas = 0;
	}
	
	/**
	 * Constructor con parámetros.
	 * 
	 * @param ID El identificador de las llantas.
	 * @param nombre El nombre de las llantas.
	 * @param descripcion La descripción de las llantas.
	 * @param precio El precio de las llantas. En euros.
	 * @param pulgadas Las pulgadas de tamaño de las llantas.
	 */
	public LlantasImpl(int ID, String nombre, String descripcion, double precio, int pulgadas)
	{
		super(ID, nombre, descripcion, precio);
		this.pulgadas = pulgadas;
	}
	
	/**
	 * Constructor de copia.
	 * 
	 * @param otras Otras llantas a partir de las cuales se quiere construir unas nuevas.
	 */
	public LlantasImpl(LlantasImpl otras)
	{
		 super(otras);
		 this.pulgadas = otras.pulgadas;
	}
	
	/**
	 * Recupera las pulgadas de las llantas.
	 * 
	 * @return Las pulgadas de las llantas.
	 */
	public int getPulgadas() { return this.pulgadas; }
	
	/**
	 * Establece las pulgadas de las llantas.
	 * 
	 * @param pulgadas Las pulgadas a establecer.
	 */
	public void setPulgadas(int pulgadas) { this.pulgadas = pulgadas; }
	
	/**
	 * Representación como cadena: "ID - nombre - pulgadas - precio €"
	 */
	@Override
	public String toString()
	{
		return this.getID() + " - " + this.getNombre() + " - " + this.pulgadas + "\"" + " - " + this.getPrecio() + " €";
	}
}
