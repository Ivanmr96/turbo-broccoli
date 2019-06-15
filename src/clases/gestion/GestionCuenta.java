package clases.gestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.VotacionImpl;

public class GestionCuenta 
{
	private Connection conexion;
	
	public GestionCuenta(Connection conexion)
	{
		this.conexion = conexion;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene una Cuenta a partir de su nombre de usuario buscando en la base de datos, la contraseña está cifrada con MD5.
	 * Prototipo: public CuentaImpl obtenerCuenta(String nombreUsuario)
	 * Entrada: Un String con el nombre de usuario de la cuenta a buscar
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Una CuentaImpl con la cuenta perteneciente a ese nombre de usuario.
	 * Postcondiciones: Asociado al nombre devuelve una CuentaImpl perteneciente al nombre de usuario dado, busca en la base de datos.
	 * 					Si el nombre de usuario no existe, devuelve una CuentaImpl null.
	 */
	public CuentaImpl obtenerCuenta(String nombreUsuario)
	{
		CuentaImpl cuenta = null;
		
		String consulta = "SELECT Contraseña FROM Cuentas "
						+ "WHERE NombreUsuario = ?";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, nombreUsuario);
			
			ResultSet resultado = statement.executeQuery();
			
			if(resultado.next())		//Salta a la primera y unica fila
			{
				String contrasena = resultado.getString("Contraseña");
				
				cuenta = new CuentaImpl(nombreUsuario, contrasena);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return cuenta;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene la CuentaImpl de una Configuracion. Busca en la base de datos.
	 * Prototipo: public CuentaImpl obtenerCuenta(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl de la que se desea obtener la CuentaImpl que la hizo
	 * Precodiciones: La conexion tiene que estar abierta
	 * Salida: La CuentaImpl que pertenece (en la base de datos) a la Configuracion dada
	 * Postcondiciones: Asociado al nombre devuelve una CuentaImpl
	 * 					- El objeto tendrá la información con la cuenta correspondiente a la configuracion si dicha configuracion existe en la base de datos.
	 * 					- El objeto será null si la configuración no existe en la base de datos.
	 */
	public CuentaImpl obtenerCuenta(ConfiguracionImpl configuracion)
	{
		CuentaImpl cuenta = null;
		
		String consulta = "SELECT NombreUsuario, Contraseña FROM Configuraciones AS conf "
						+ "INNER JOIN Cuentas AS cu ON cu.NombreUsuario = conf.Usuario "
						+ "WHERE conf.ID = ?";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, configuracion.getID());
			
			ResultSet resultado = statement.executeQuery();
			
			if(resultado.next())		//Salta a la primera y unica fila si la configuracion existe
			{
				String nombreUsuario = resultado.getString("NombreUsuario");
				String contrasena = resultado.getString("Contraseña");
				
				cuenta = new CuentaImpl(nombreUsuario, contrasena);
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return cuenta;
	}
	
	
	/* INTERFAZ
	 * Comentario: Carga la lista con las configuraciones de una cuenta en dicha cuenta, buscando en la base de datos
	 * Prototipo: public void cargarConfiguracionesEnCuenta(CuentaImpl cuenta)
	 * Entrada: Una CuentaImpl en la que se desea cargar sus configuraciones
	 * Precondiciones: La conexion debe estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una CuentaImpl a la que se le desea cargar la lista de configuraciones que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto CuentaImpl pasado por parámetro tiene la lista de las configuraciones que le pertenecen según la base de datos.
	 * 					Si la cuenta no existe en la base de datos, la lista de las votaciones será null.
	 */
	public void cargarConfiguracionesEnCuenta(CuentaImpl cuenta)
	{
		GestionConfiguracion gestionConfiguracion = new GestionConfiguracion(conexion);
		
		ArrayList<ConfiguracionImpl> configuraciones = gestionConfiguracion.obtenerConfiguraciones(cuenta);
		
		cuenta.establecerConfiguraciones(configuraciones);
	}
	
	/* INTERFAZ
	 * Comentario: Carga la lista con las votacines realizadas por una cuenta en dicha cuenta, buscando en la base de datos
	 * Prototipo: public void cargarVotacionesEnCuenta(CuentaImpl cuenta)
	 * Entrada: Una CuentaImpl en la que se desea cargar sus votaciones realizadas.
	 * Precondiciones: La conexion debe estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una CuentaImpl a la que se le desea cargar la lista de votaciones que ha realizado, buscando en la base de datos.
	 * Postcondiciones: El objeto CuentaImpl pasado por parámetro tiene la lista de las votaciones que le pertenecen según la base de datos.
	 * 					Si la cuenta no existe en la base de datos, la lista de las votaciones será null.
	 */
	public void cargarVotacionesEnCuenta(CuentaImpl cuenta)
	{
		GestionVotacion gestionVotacion = new GestionVotacion(conexion);
		
		ArrayList<VotacionImpl> votaciones = gestionVotacion.obtenerVotaciones(cuenta);
		
		cuenta.establecerVotaciones(votaciones);
	}
	
	/* INTERFAZ
	 * Comentario: Carga todas las relaciones con otros objetos que tiene una cuenta en ella misma, es decir, carga las configuraciones que le pertenece y las votaciones.
	 * Prototipo: public void cargarRelacionesEnCuenta(CuentaImpl cuenta)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una CuentaImpl a la que se le desea cargar todas sus relaciones con otros objetos, busca en la base de datos.
	 * Postcondiciones: El objeto CuentaImpl tiene cargada todas sus relaciones con otros objetos, es decir:
	 * 						- Carga en el objeto CuentaImpl el ArrayList<ConfiguracionImpl> que le pertenece según la base de datos.
	 * 						- Carga en el objeto CuentaImpl el ArrayList<VotacionImpl> que le pertenece según la base de datos.
	 * 						- Si la configuración no existe en la base de datos, las relaciones quedarán con valores null.
	 */
	public void cargarRelacionesEnCuenta(CuentaImpl cuenta)
	{
		cargarConfiguracionesEnCuenta(cuenta);
		cargarVotacionesEnCuenta(cuenta);
	}
	
	/* INTERFAZ
	 * Comentario: Inserta una nueva cuenta en la base de datos
	 * Prototipo: public boolean insertarCuenta(CuentaImpl cuenta) throws SQLServerException
	 * Entrada: la CuentaImpl que se desea insertar en la base de datos
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un boolean indicando si se introdujo la cuenta satisfactoriamente o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. Por lo tanto la cuenta ha sido introducido correctamente en la base de datos
	 * 					- False. La cuenta no se ha introducido correctamente en la base de datos.
	 * 					- Lanza SQLServerException si se intenta introducir una cuenta que ya existe en la base de datos.
	 */
	public boolean insertarCuenta(CuentaImpl cuenta) throws SQLServerException
	{
		boolean insertado = false;
		
		String insert = "INSERT INTO Cuentas "
						+ "VALUES (?, ?);";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(insert);
			
			statement.setString(1, cuenta.getNombreUsuario());
			statement.setString(2, cuenta.getContrasena());
			
			int filasAfectadas = statement.executeUpdate();
			
			if(filasAfectadas > 0)
				insertado = true;
		} 
		catch (SQLServerException e)
		{
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		return insertado;
	}
	
	/* INTERFAZ
	 * Comentario: Actualiza una cuenta de la base de datos.
	 * Prototipo: public boolean actualizarCuenta(CuentaImpl cuenta)
	 * Entrada: la CuentaImpl que se desea actualizar en la de datos.
	 * Precondiciones: La conexion con la base de datos tiene que estar abierta.
	 * Salida: Un boolean indicando si se actualizó la cuenta satisfactoriamente o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. Por lo tanto la cuenta ha sido actualizada correctamente en la base de datos
	 * 					- False. La cuenta no se ha actualizado correctamente en la base de datos.
	 */
	public boolean actualizarCuenta(CuentaImpl cuenta)
	{
		boolean actualizada = false;
		
		String update = "UPDATE Cuentas "
					  + "SET Contraseña = ? "
					  + "WHERE NombreUsuario = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(update);
			
			statement.setString(1, cuenta.getContrasena());
			statement.setString(2, cuenta.getNombreUsuario());
			
			int filasAfectadas = statement.executeUpdate();
			
			if(filasAfectadas > 0)
				actualizada = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return actualizada;
	}
	
	/* INTERFAZ
	 * Comentario: Borra una cuenta de la base de datos
	 * Prototipo: public boolean eliminarCuenta(CuentaImpl cuenta)
	 * Entrada: la CuentaImpl que se desea eliminar de la de datos
	 * Precondiciones: La conexion con la base de datos tiene que estar abierta
	 * Salida: Un boolean indicando si se elimino la cuenta satisfactoriamente o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. Por lo tanto la cuenta ha sido eliminada correctamente de la base de datos
	 * 					- False. La cuenta no se ha eliminado correctamente de la base de datos.
	 */
	public boolean eliminarCuenta(CuentaImpl cuenta)
	{
		boolean borrada = false;
		
		String delete = "DELETE FROM Cuentas "
					  + "WHERE NombreUsuario = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(delete);
			
			statement.setString(1, cuenta.getNombreUsuario());
			
			int filasAfectadas = statement.executeUpdate();
			
			if(filasAfectadas > 0)
				borrada = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return borrada;
	}
	
	/* INTERFAZ
	 * Comentario: Comprueba en la base de datos si un usuario existe
	 * Prototipo: public boolean existeUsuario(String usuario)
	 * Entrada: Un String con el usuario del que se desea comprobar su existencia en la base de datos.
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un boolean indicando si el usuario existe en la base de datos o no.
	 * Postcondiciones: Asociado al nombre devuelve true si el usuario existe en la base de datos o false de lo contrario.
	 */
	public boolean existeUsuario(String usuario)
	{
		boolean existe = false;
		
		String consulta = "SELECT NombreUsuario FROM Cuentas WHERE NombreUsuario = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, usuario);
			
			ResultSet resultado = statement.executeQuery();
			
			if(resultado.next())
				existe = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
		return existe;
	}
}
