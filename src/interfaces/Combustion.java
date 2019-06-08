package interfaces;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> tipoCarburante: char, consultable, modificable
 * 						-> consumoCarburante: double, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public char getTipoCarburante();
 * public double getConsumoCarburante();
 * 
 * public void setTipoCarburante(char tipoCarburante);
 * public void setConsumoCarburante(double consumoCarburante);
 */

public interface Combustion 
{
	public char getTipoCarburante();
	public double getConsumoCarburante();
	
	public void setTipoCarburante(char tipoCarburante);
	public void setConsumoCarburante(double consumoCarburante);
}
