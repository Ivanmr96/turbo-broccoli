package interfaces;

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

public interface Pieza 
{
	public int getID();
	public String getNombre();
	public String getDescripcion();
	public double getPrecio();
	
	public void setNombre(String nombre);
	public void setDescripcion(String descripcion);
	public void setPrecio(double precio);
}
