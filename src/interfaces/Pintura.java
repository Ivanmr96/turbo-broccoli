package interfaces;

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

public interface Pintura 
{
	public String getColor();
	public String getAcabado();
	
	public void setColor(String color);
	public void setAcabado(String acabado);
}
