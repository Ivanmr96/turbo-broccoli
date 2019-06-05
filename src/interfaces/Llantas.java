package interfaces;

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

public interface Llantas 
{
	public int getPulgadas();
	
	public void setPulgadas(int pulgadas);
}
