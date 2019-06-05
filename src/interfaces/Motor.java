package interfaces;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> traccion: char, consultable, modificable
 * 						-> numeroVelocidades: int, consultable, modificable
 * 						-> potencia: int, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public char getTraccion();
 * public int getNumeroVelocidades();
 * public int getPotencia();
 * 
 * public void setTraccion(char traccion);
 * public void setNumeroVelocidades(int numeroVelocidades);
 * public void setPotencia(int potencia);
 */

public interface Motor 
{
	public char getTraccion();
	public int getNumeroVelocidades();
	public int getPotencia();
	 
	public void setTraccion(char traccion);
	public void setNumeroVelocidades(int numeroVelocidades);
	public void setPotencia(int potencia);
}
