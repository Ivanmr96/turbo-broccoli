package interfaces;

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
public interface Coche 
{
	public String getMarca();
	public String getModelo();
	public double getPrecioBase();
	  
	public void setMarca(String marca);
	public void setModelo(String modelo);
	public void setPrecioBase(double precioBase);
}
