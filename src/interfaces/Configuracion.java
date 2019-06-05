package interfaces;

import java.util.GregorianCalendar;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades b�sicas:
 * 						-> fecha: GregorianCalendar, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 * 
 */

/* INTERFAZ
 * 
 * public GregorianCalendar getFecha();
 * 
 * public void setFecha(GregorianCalendar fecha);
 * 
 */

public interface Configuracion 
{
	public GregorianCalendar getFecha();
	
	public void setFecha(GregorianCalendar fecha);
}
