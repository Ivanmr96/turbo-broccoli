package clases.gestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.VotacionImpl;
import utils.Utils;

public class GestionVotacion 
{
	private Connection conexion;
	
	public GestionVotacion(Connection conexion)
	{
		this.conexion = conexion;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene las votaciones de una configuracion. Busca en la base de datos
	 * Prototipo: public ArrayList<VotacionImpl> obtenerVotaciones(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl de la que se desean obtener sus votaciones.
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList de VotacionImpl con la lista de votaciones de la configuracion dada.
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<VotacionImpl>.
	 * 					- Si la configuración existe en la base de datos, devuelve una lista con las votaciones realizadas a dicha configuracion.
	 * 						La lista puede estar vacía, esto significa que la configuración no tiene ninguna votación.
	 * 					- Si la configuración no existe en la base de datos, el ArrayList<VotacionImpl> estará vacio.
	 */
	public ArrayList<VotacionImpl> obtenerVotaciones(ConfiguracionImpl configuracion)
	{
		ArrayList<VotacionImpl> votaciones = null;
		
		String consulta = "SELECT ID, Calificacion, Fecha FROM Votaciones " 
						+ "WHERE IDConfiguracion = ?";		//TODO Como hacer que devuelva NULL si la configuracion no existe en la bbdd
		
		Utils utils = new Utils();
		
		VotacionImpl votacion;
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, configuracion.getID());
			
			ResultSet resultado = statement.executeQuery();
			
			votaciones = new ArrayList<VotacionImpl>();
			
			while(resultado.next())
			{
				String ID = resultado.getString("ID");
				GregorianCalendar fecha = utils.dateTimeToGregorianCalendar(resultado.getString("Fecha"));
				int calificacion = resultado.getInt("Calificacion");
				
				votacion = new VotacionImpl(ID, fecha, calificacion);
				
				votacion.establecerConfiguracion(configuracion);	//Aprovecha la situacion y establece la relacion con su configuracion determinada.
				
				votaciones.add(votacion);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return votaciones;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene todas las votaciones realizadas por una cuenta.
	 * Prototipo: public ArrayList<VotacionImpl> obtenerVotaciones(CuentaImpl cuenta)
	 * Entrada: Una CuentaImpl de la que se desea obtener todas sus votaciones realizadas.
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList<VotacionImpl> con todas los votaciones realizadas por la cuenta dada.
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<VotacionImpl> con todas las votaciones realizadas por la cuenta buscando en la base de datos.
	 */
	public ArrayList<VotacionImpl> obtenerVotaciones(CuentaImpl cuenta)
	{
		ArrayList<VotacionImpl> votaciones = null;
		VotacionImpl votacion = null;
		Utils utils = new Utils();
		
		String consulta = "SELECT ID, Fecha, Calificacion FROM Votaciones "
						+ "WHERE Usuario = ?";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, cuenta.getNombreUsuario());
			
			ResultSet resultado = statement.executeQuery();
			
			votaciones = new ArrayList<VotacionImpl>();
			
			while(resultado.next())
			{
				String ID = resultado.getString("ID");
				GregorianCalendar fecha = utils.dateTimeToGregorianCalendar(resultado.getString("Fecha"));
				int calificacion = resultado.getInt("Calificacion");
				
				votacion = new VotacionImpl(ID, fecha, calificacion);
				
				votacion.establecerCuenta(cuenta);
				
				votaciones.add(votacion);
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return votaciones;
	}
	
	/* INTERFAZ
	 * Comentario: Inserta una nueva votación en la base de datos,
	 * Prototipo: public boolean insertarVotacion(VotacionImpl votacion) throws SQLServerException
	 * Entrada: La VotacionImpl que se desea insertar en la base de datos.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Un boolean indicando si se introdujo la votación satisfactoriamente o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. Por lo tanto la votacion ha sido introducido correctamente en la base de datos
	 * 					- False. La votación no se ha introducido correctamente en la base de datos.
	 * 					- Lanza SQLServerException si se intenta introducir una votación(ID) que ya existe en la base de datos.
	 * 					- Lanza SQLServerException si se intenta introducir una votacion cuyo usuario es el mismo que el usuario de la configuración a la que se realiza la votación.
	 */
	public boolean insertarVotacion(VotacionImpl votacion) throws SQLServerException
	{
		boolean insertada = false;
		Utils utils = new Utils();
		
		String insert = "INSERT INTO Votaciones "
						+ "VALUES (?, ?, ?, ?, ?);";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(insert);
			
			statement.setString(1, votacion.getID());
			statement.setInt(2, votacion.getCalificacion());
			statement.setString(3, utils.GregorianCalendarToDateTime(votacion.getFecha()));
			statement.setString(4, votacion.obtenerConfiguracion().getID());
			statement.setString(5, votacion.obtenerCuenta().getNombreUsuario());
			
			int filasAfectadas = statement.executeUpdate();
			
			if(filasAfectadas > 0)
				insertada = true;
		} 
		catch (SQLServerException e)
		{
			throw e;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		return insertada;
	}
}
