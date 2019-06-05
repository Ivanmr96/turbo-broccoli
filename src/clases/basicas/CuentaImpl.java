package clases.basicas;

import java.util.ArrayList;

import interfaces.Cuenta;

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

/* FUNCIONALIDADES AÑADIDAS
 * 
 * public ArrayList<ConfiguracionImpl> obtenerConfiguraciones();
 * public ArrayList<VotacionImpl> obtenerVotaciones();
 */

public class CuentaImpl implements Cuenta
{
	private String nombreUsuario;
	private String contrasena;
	
	private ArrayList<ConfiguracionImpl> configuraciones;
	private ArrayList<VotacionImpl> votaciones;
	
	public CuentaImpl()
	{
		this.nombreUsuario = "";
		this.contrasena = "";
		
		this.configuraciones = null;
		this.votaciones = null;
	}
	
	public CuentaImpl(String nombreUsuario, String contrasena)
	{
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		
		this.configuraciones = null;
		this.votaciones = null;
	}
	
	public CuentaImpl(CuentaImpl otra)
	{
		this.nombreUsuario = otra.nombreUsuario;
		this.contrasena = otra.contrasena;
		this.configuraciones = otra.configuraciones;
		this.votaciones = otra.votaciones;
	}
	
	public String getNombreUsuario() { return this.nombreUsuario; }
	public String getContrasena() { return this.contrasena; }
	 
	public void setContrasena(String contrasena) { this.contrasena = contrasena; }
	
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones() { return this.configuraciones; }
	
	public ArrayList<VotacionImpl> obtenerVotaciones() { return this.votaciones; }  
}
