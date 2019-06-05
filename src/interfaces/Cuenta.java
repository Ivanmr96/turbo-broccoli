package interfaces;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> nombreUsuario: String, consultable
 * 						-> contrasena: String, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 */

/* INTERFAZ
 * 
 * public String getNombreUsuario();
 * public String getContrasena();
 * 
 * public void setContrasena(String contrasena);
 */
public interface Cuenta 
{
	public String getNombreUsuario();
	public String getContrasena();
	 
	public void setContrasena(String contrasena);
}
