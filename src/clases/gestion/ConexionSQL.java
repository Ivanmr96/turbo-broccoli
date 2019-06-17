package clases.gestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión con la base de datos.
 * 
 * @author Iván Moreno <br> <a href="https://github.com/Ivanmr96/">Github</a>
 */
public class ConexionSQL 
{
	private String URLConexion;
	private Connection conexion;
	
	/**
	 * Constructor con parámetro.
	 * 
	 * @param URLConexion La URL de la conexión con la base de datos.
	 */
	public ConexionSQL(String URLConexion)
	{
		this.URLConexion = URLConexion;
	}
	
	/* INTERFAZ
	 * Comentario: Abre una nueva conexion a la base de datos establecida en el constructor.
	 * Prototipo: public void abrirConexion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: No hay
	 * Postcondiciones: Se abre una nueva conexion a la base de datos establecida, con las credenciales dadas y en el servidor determinado.
	 */
	/**
	 * Abre la conexión con la base de datos.
	 */
	public void abrirConexion()
	{
		try
		{
			conexion = DriverManager.getConnection(URLConexion);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/* INTERFAZ
	 * Comentario: Cierra la conexión con la base de datos
	 * Prototipo: public void cerrarConexion()
	 * Entrada: No hay
	 * Precondiciones: La conexión debe estar abierta
	 * Salida: No hay
	 * Postcondiciones: Se cierra la conexión a la base de datos establecida, con las credenciales dadas y en el servidor determinado.
	 */
	/**
	 * Cierra la conexión con la base de datos.
	 */
	public void cerrarConexion()
	{
		try 
		{
			conexion.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Obtiene la conexión con la base de datos.
	 * 
	 * @return la conexión con la base de datos.
	 */
	public Connection getConexion() { return this.conexion; }
	
	/**
	 * Establece la conexión con la base de datos.
	 * @param conexion La conexión a establecer.
	 */
	public void setConexion(Connection conexion) { this.conexion = conexion; }
	
}
