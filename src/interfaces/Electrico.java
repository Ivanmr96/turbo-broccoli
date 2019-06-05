package interfaces;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> consumoElectrico: double, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public double getConsumoElectrico();
 * 
 * public void setConsumoElectrico(double consumoElectrico);
 */

public interface Electrico 
{
	public double getConsumoElectrico();
	
	public void setConsumoElectrico(double consumoElectrico);
}
