package interfaces;

import java.util.GregorianCalendar;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> ID: String, consultable
 * 						-> fecha: GregorianCalendar, consultable, modificable
 * 						-> calificacion: int, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public String getID();
 * public GregorianCalendar getFecha();
 * public int getCalificacion();
 * 
 * public void setFecha(GregorianCalendar fecha);
 * public void setCalificacion(int calificacion);
 */

public interface Votacion 
{
	public String getID();
	public GregorianCalendar getFecha();
	public int getCalificacion();
	
	public void setFecha(GregorianCalendar fecha);
	public void setCalificacion(int calificacion);
}
