package clases.gestion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.VotacionImpl;
import utils.Utils;

public class GestionConfiguracion 
{
	private Connection conexion;
	
	public GestionConfiguracion(Connection conexion)
	{
		this.conexion = conexion;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene una configuracion de la base de datos dado su ID
	 * Prototipo: public ConfiguracionImpl obtenerConfiguracion(String ID)
	 * Entrada: Un String con el ID de la configuración que se desea obtener
	 * Precondiciones: El String debe tener un formato de UUID, es decir:
	 * 					32 digitos hexadecimales divididos en cinco grupos separados por guiones divididos de la forma 8-4-4-4-12, por ejemplo: 550e8400-e29b-41d4-a716-446655440000
	 * Salida: Un objeto ConfiguracionImpl con la configuracion del ID dado
	 * Postcondiciones: Asociado al nombre devuelve un objeto ConfiguracionImpl.
	 * 					- Si el ID es de una configuración existente en la base de datos, el objeto tendrá la información correspondiente a la configuración con su ID en la base de datos.
	 * 					- Si el ID no existe en la base de datos, será null.
	 */
	/**
	 * Obtiene una configuracion de la base de datos dado su ID.<br>
	 * <b>Precondiciones:</b> La conexión con la base de datos debe estar abierta.<br>
	 * El ID debe ser un string con formato UUID, es decir:<br>
	 * - 32 dígitos hexadecimales divididos en cinco grupos separados por guiones divididos de la forma 8-4-4-4-12. <br>
	 * Por ejemplo: 550e8400-e29b-41d4-a716-446655440000
	 * 
	 * @param ID El ID de la configuración.
	 * @return La configuración obtenida de la base de datos.<br>
	 * - Si el ID es de una configuración existente en la base de datos, el objeto tendrá la información correspondiente a la configuración con su ID en la base de datos.<br>
	 * - SI el ID no existe en la base de datos, será null.
	 * @see java.util.UUID
	 */
	public ConfiguracionImpl obtenerConfiguracion(String ID)
	{
		ConfiguracionImpl configuracion = null;
		
		Utils utils = new Utils();
		
		String consulta = "SELECT Fecha FROM Configuraciones "
						+ "WHERE ID = ?";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, ID);
			
			ResultSet resultado = statement.executeQuery();
			
			if(resultado.next())	//Salta a la primera y unica fila si la ID existe
			{
				String fechaStr = resultado.getString("Fecha");
				
				GregorianCalendar fecha = utils.dateTimeToGregorianCalendar(fechaStr);
				
				configuracion = new ConfiguracionImpl(ID, fecha);
			}
			
		}
		catch (SQLException e) 
		{
			System.out.println(e.getErrorCode());
			e.printStackTrace();
		}
		
		return configuracion;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene todas las configuraciones de la base de datos
	 * Prototipo: public ArrayList<ConfiguracionImpl> obtenerConfiguraciones()
	 * Entrada: No hay
	 * Precondiciones: La conexión con la base de datos debe que estar abierta
	 * Salida: Un ArrayList<ConfiguracionImpl> con todas las configuraciones de la base de datos
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<ConfiguracionImpl> con todas las configuraciones de la base de datos.
	 */
	/**
	 * Obtiene todas las configuraciones de la base de datos.<br>
	 * <b>Precondiciones:</b> La conexión con la base de datos debe estar abierta.
	 * 
	 * @return La lista con todas las configuraciones de la base de datos.
	 */
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones()
	{
		ArrayList<ConfiguracionImpl> configuraciones = new ArrayList<ConfiguracionImpl>();
		Utils utils = new Utils();
		ConfiguracionImpl configuracion;
		String ID;
		GregorianCalendar fecha;
		
		String consulta = "SELECT ID, Fecha FROM Configuraciones";
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			while(resultado.next())
			{
				ID = resultado.getString("ID");
				fecha = utils.dateTimeToGregorianCalendar(resultado.getString("Fecha"));
				
				configuracion = new ConfiguracionImpl(ID, fecha);
				
				configuraciones.add(configuracion);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return configuraciones;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene las configuraciones de un coche en la base de datos
	 * Prototipo: public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(CocheImpl coche)
	 * Entrada: Un CocheImpl con el coche del que se desea obtener todas las configuraciones realizadas para ese coche.
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList<ConfiguracionImpl> con todas las configuraciones de un coche en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<ConfiguracionImpl> con todas las configuraciones de una marca en la base de datos. Si no hay
	 * 					ninguna configuracion para ningún coche de la marca dada, o la marca no existe, la lista estará vacía.
	 */
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(CocheImpl coche)
	{
		ArrayList<ConfiguracionImpl> configuraciones = new ArrayList<ConfiguracionImpl>();
		Utils utils = new Utils();
		ConfiguracionImpl configuracion;
		String ID;
		GregorianCalendar fecha;
		
		String consulta = "SELECT ID, Fecha FROM Configuraciones "
						+ "WHERE MarcaCoche = ? AND ModeloCoche = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, coche.getMarca());
			statement.setString(2, coche.getModelo());
			
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next())
			{
				ID = resultado.getString("ID");
				fecha = utils.dateTimeToGregorianCalendar(resultado.getString("Fecha"));
				configuracion = new ConfiguracionImpl(ID, fecha);
				
				configuraciones.add(configuracion);
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return configuraciones;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene todas las configuraciones realizadas por una cuenta.
	 * Prototipo: public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(CuentaImpl cuenta)
	 * Entrada: Una CuentaImpl de la que se desea obtener todas sus configuraciones realizadas.
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList<ConfiguracionImpl> con todas los configuraciones de la cuenta dada.
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<ConfiguracionImpl> con todas las configuraciones de una cuenta buscando en la base de datos.
	 */
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(CuentaImpl cuenta)
	{
		ArrayList<ConfiguracionImpl> configuraciones = null;
		ConfiguracionImpl configuracion = null;
		Utils utils = new Utils();
		
		String consulta = "SELECT ID, Fecha FROM Configuraciones "
						+ "WHERE Usuario = ?";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
					
			statement.setString(1, cuenta.getNombreUsuario());
			
			ResultSet resultado = statement.executeQuery();
			
			configuraciones = new ArrayList<ConfiguracionImpl>();
			
			while(resultado.next())
			{
				String ID = resultado.getString("ID");
				GregorianCalendar fecha = utils.dateTimeToGregorianCalendar(resultado.getString("Fecha"));
				
				configuracion = new ConfiguracionImpl(ID, fecha);
				
				configuracion.establecerCuenta(cuenta);
				
				configuraciones.add(configuracion);
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return configuraciones;
	}
	
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(double precioMinimo, double precioMaximo) //TODO funcionalidad para calcular el precio de una configuracion
	{
		ArrayList<ConfiguracionImpl> configuraciones = new ArrayList<ConfiguracionImpl>();
		
		double precioTotal;

		for(ConfiguracionImpl configuracion:obtenerConfiguraciones())
		{
			cargarRelacionesEnConfiguracion(configuracion);
			precioTotal = configuracion.obtenerPrecioTotal();
			
			if(precioTotal >= precioMinimo && precioTotal <= precioMaximo)
			{
				configuraciones.add(configuracion);
			}
		}
		
		return configuraciones;
	}
	
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(GregorianCalendar fecha)
	{
		ArrayList<ConfiguracionImpl> configuraciones = new ArrayList<ConfiguracionImpl>();
		
		return configuraciones;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene las configuraciones de una marca de coche.
	 * Prototipo: public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(String marca)
	 * Entrada: Un String con la marca de coche de la que se desea obtener todas las configuraciones realizadas
	 * Precondiciones: La conexion con la base de datos debe estar abierta.
	 * Salida: Un ArrayList<ConfiguracionImpl> con todas las configuraciones de una marca en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<ConfiguracionImpl> con todas las configuraciones de una marca en la base de datos. Si no hay
	 * 					ninguna configuracion para ningún coche de la marca dada, o la marca no existe, la lista estará vacía.
	 */
	/**
	 * Obtiene las configuraciones de una marca de coche.<br>
	 * <b>Precondicione:</b> La conexion con la base de datos debe estar abierta.
	 * @param marca La marca de coche de la que se desea obtener todas las configuraciones realizadas.
	 * @return Una lista con todas las configuraciones de una marca en la base de datos.<br>
	 * Si no hay ningun configuracion para ningñun coche de la marca dada, o bien la marca no existe, la lista estará vacía.
	 */
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(String marca)
	{
		ArrayList<ConfiguracionImpl> configuraciones = new ArrayList<ConfiguracionImpl>();
		Utils utils = new Utils();
		ConfiguracionImpl configuracion;
		String ID;
		GregorianCalendar fecha;
		
		String consulta = "SELECT ID, Fecha FROM Configuraciones "
						+ "WHERE MarcaCoche = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, marca);
			
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next())
			{
				ID = resultado.getString("ID");
				fecha = utils.dateTimeToGregorianCalendar(resultado.getString("Fecha"));
				configuracion = new ConfiguracionImpl(ID, fecha);
				
				configuraciones.add(configuracion);
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return configuraciones;
	}
	
	/* INTERFAZ
	 * Comentario: Inserta una nueva configuracion en la base de datos
	 * Prototipo: public boolean insertarConfiguracion(ConfiguracionImpl configuracion) throws SQLServerException
	 * Entrada: la ConfiguracionImpl que se desea insertar en la base de datos
	 * Precondiciones:  - el objeto ConfiguracionImpl debe tener asignado un CocheImpl y una CuentaImpl, debido a que no se puede insertar
	 * 					  en la base de datos una configuracion que no tiene ninguna cuenta ni ningun coche asociado.
	 * 					- La conexion tiene que estar abierta
	 * Salida: Un boolean indicando si se introdujo la configuracion satisfactoriamente o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. Por lo tanto la configuracion ha sido introducido correctamente en la base de datos
	 * 					- False. La configuracion no se ha introducido correctamente en la base de datos.
	 * 					- Lanza SQLServerException si se intenta introducir una configuracion que ya existe en la base de datos.
	 */
	public boolean insertarConfiguracion(ConfiguracionImpl configuracion) throws SQLServerException
	{
		Utils utils = new Utils();
		
		boolean insertado = false;
		
		String marcaCoche = configuracion.obtenerCoche().getMarca();
		String modeloCoche = configuracion.obtenerCoche().getModelo();
		
		String insert = "INSERT INTO Configuraciones "
						+ "(ID, Usuario, Fecha, MarcaCoche, ModeloCoche) "
						+ "VALUES (?, ?, ?, ?, ?)";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(insert);
			
			statement.setString(1, configuracion.getID());
			statement.setString(2, configuracion.obtenerCuenta().getNombreUsuario());
			statement.setString(3, utils.GregorianCalendarToDateTime(configuracion.getFecha()));
			statement.setString(4, marcaCoche);
			statement.setString(5, modeloCoche);
			
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
	 * Comentario: Inserta una pieza en una configuración en la base de datos.
	 * Prototipo: public boolean insertarPiezaEnConfiguracion(PiezaImpl pieza, ConfiguracionImpl configuracion) throws SQLServerException
	 * Entrada: la PiezaImpl que se desea insertar en la configuración dentro de la base de datos.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Un boolean indicando si se introdujo la pieza satisfactoriamente o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. Por lo tanto la pieza ha sido asociada correctamente a la configuración en la base de datos
	 * 					- False. La pieza no se ha asociado correctamente a la configuración en la base de datos.
	 * 					- Lanza SQLServerException si se intenta introducir una pieza que ya está asociada a la configuración en la base de datos.
	 */
	public boolean insertarPiezaEnConfiguracion(PiezaImpl pieza, ConfiguracionImpl configuracion) throws SQLServerException
	{
		boolean insertado = false;
		
		String insert = "INSERT INTO PiezasConfiguracionCoche "
					  + "VALUES (?, ?);";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(insert);
			
			statement.setInt(1, pieza.getID());
			statement.setString(2, configuracion.getID());
			
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
	 * Comentario: Inserta una lista de piezas en una configuración en la base de datos.
	 * Prototipo: public boolean insertarPiezasEnConfiguracion(ArrayList<PiezaImpl> piezas, ConfiguracionImpl configuracion) throws SQLServerException
	 * Entrada: La lista de PiezaImpl que se desean insertar en la configuración dentro de la base de datos.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Un boolean indicando si se introdujeron las piezas satisfactoriamente o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. Por lo tanto las piezas han sido asociadas correctamente a la configuración en la base de datos
	 * 					- False. Las piezas no se han asociado correctamente a la configuración en la base de datos.
	 * 					- Lanza SQLServerException si se intenta introducir una pieza que ya está asociada a la configuración en la base de datos.
	 */
	//TODO PreparedStatement aqui
	public boolean insertarPiezasEnConfiguracion(ArrayList<PiezaImpl> piezas, ConfiguracionImpl configuracion) throws SQLServerException
	{
		boolean insertado = false;
		
		String configuracionID = configuracion.getID();
		
		if(piezas.size() > 0)
		{
			String insert = "INSERT INTO PiezasConfiguracionCoche ";
			
			if(!piezas.isEmpty())
				insert += "\n VALUES ";
			
			for(int i = 0 ; i < piezas.size()-1 ; i++)
			{
				insert += "\n (" + piezas.get(i).getID() + ", '" + configuracionID + "'),";
			}
			
			if(!piezas.isEmpty())
			{
				insert += "\n (" + piezas.get(piezas.size()-1).getID() + ", '" + configuracionID + "')";
			}
			
			try 
			{
				Statement statement = conexion.createStatement();
				
				int filasAfectadas = statement.executeUpdate(insert);
				
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
		}
		
		return insertado;
	}
	
	/* INTERFAZ
	 * Comentario: Actualiza en la base de datos una configuracion.
	 * 			   obtiene las relaciones que tiene el objeto ConfiguracionImpl y las guarda en la base de datos.
	 * 			   Actualiza:
	 * 					-> El motor
	 * 					-> Las llantas
	 * 					-> La pinutra
	 * 					-> las piezas extra
	 * Prototipo: public boolean actualizarConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: Un objeto ConfiguracionImpl que se desea actualizar en la base de datos.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Un booleano indicando si se actualizo acorde al objeto ConfiguracionImpl determinado o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True si se actualizo correctamente la configuracion en la base de datos según el objeto ConfiguracionImpl del paráemtro.
	 * 					- False si no se actualizo.
	 */
	public boolean actualizarConfiguracion(ConfiguracionImpl configuracion)
	{
		boolean actualizado = false;
		

		//Update a los ID de motor, llantas y pintura
		try
		{
			Statement statement = conexion.createStatement();
			
			statement.execute("BEGIN TRAN");
			
			actualizarMotorDeConfiguracion(configuracion);
			actualizarPinturaDeConfiguracion(configuracion);
			actualizarLlantasDeConfiguracion(configuracion);
			
			eliminarPiezasExtraDeConfiguracion(configuracion);
			
			insertarPiezasEnConfiguracion(configuracion.obtenerPiezas(), configuracion);
			
			statement.execute("COMMIT");
			actualizado = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
			Statement statement;
			try 
			{
				statement = conexion.createStatement();
				statement.execute("ROLLBACK");
				actualizado = false;
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			
		}
		
		return actualizado;
	}
	
	/* INTERFAZ
	 * Comentario: Actualiza en la base de datos el motor que pertenece a la configuracion
	 * 			   Mira en el objeto ConfiguracionImpl su MotorImpl para guardar en la base de datos cuál es su motor.
	 * Prototipo: public boolean actualizarMotorDeConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl a la que se le desea cambiar su motor en la base de datos
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un boolean indicando si se actualizo correctamente la configuracion o no
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True en caso de que el motor de la configuracion se haya cambiado en la base de datos.
	 * 					- False si no se ha podido actualizar la configuracion.
	 */
	public boolean actualizarMotorDeConfiguracion(ConfiguracionImpl configuracion)
	{
		boolean actualizado = false;
		int filasAfectadas;
		
		String updateConfiguracion = "UPDATE Configuraciones "
								   + "SET IDMotor = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(updateConfiguracion);
			
			if(configuracion.obtenerMotor() != null)
				statement.setInt(1, configuracion.obtenerMotor().getID());
			else
				statement.setNull(1, Types.INTEGER);
			
			filasAfectadas = statement.executeUpdate();
			
			if(filasAfectadas > 0)
				actualizado = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	/* INTERFAZ
	 * Comentario: Actualiza en la base de datos las llantas que pertenecen a la configuracion
	 * 			   Mira en el objeto ConfiguracionImpl su LlantasImpl para guardar en la base de datos cuáles son sus llantas.
	 * Prototipo: public boolean actualizarLlantasDeConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl a la que se le desea cambiar sus llantas en la base de datos
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un boolean indicando si se actualizo correctamente la configuracion o no
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True en caso de que las llantas de la configuracion se haya cambiado en la base de datos.
	 * 					- False si no se ha podido actualizar la configuracion.
	 */
	public boolean actualizarLlantasDeConfiguracion(ConfiguracionImpl configuracion)
	{
		boolean actualizado = false;
		int filasAfectadas;
		
		String updateConfiguracion = "UPDATE Configuraciones "
								   + "SET IDLlantas = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(updateConfiguracion);
			
			if(configuracion.obtenerLlantas() != null)
				statement.setInt(1, configuracion.obtenerLlantas().getID());
			else
				statement.setNull(1, Types.INTEGER);
			
			filasAfectadas = statement.executeUpdate();
			
			if(filasAfectadas > 0)
				actualizado = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return actualizado;
	}
	
	/* INTERFAZ
	 * Comentario: Actualiza en la base de datos la pintura que pertenece a la configuracion
	 * 			   Mira en el objeto ConfiguracionImpl su PinturaImp, para guardar en la base de datos cuál es su pintura.
	 * Prototipo: public boolean actualizarPinturaDeConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl a la que se le desea cambiar su pintura en la base de datos
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un boolean indicando si se actualizo correctamente la configuracion o no
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True en caso de que la pintura de la configuracion se haya cambiado en la base de datos.
	 * 					- False si no se ha podido actualizar la configuracion.
	 */
	public boolean actualizarPinturaDeConfiguracion(ConfiguracionImpl configuracion)
	{
		boolean actualizado = false;
		int filasAfectadas;
		
		String updateConfiguracion = "UPDATE Configuraciones "
								   + "SET IDPintura = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(updateConfiguracion);
			
			if(configuracion.obtenerPintura() != null) 
				statement.setInt(1, configuracion.obtenerPintura().getID());
			else 
				statement.setNull(1, Types.INTEGER);
			
			filasAfectadas = statement.executeUpdate();
			
			if(filasAfectadas > 0)
				actualizado = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	/* INTERFAZ
	 * Comentario: Elimina una configuración de la base de datos, asi como las votaciones asociadas a ella.
	 * Prototipo: public boolean eliminarConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: La ConfiguracionImpl que se desea eliminar de la base de datos.
	 * Precondiciones: La conexión con la base de dato debe estar abierta.
	 * Salida: Un boolean indicando si la eliminacion tuvo exito
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. La configuración ha sido borrado satisfactoriamente de la base de datos. las votaciones asociadas a la configuración también se borran.
	 * 					- False. La configuración no se ha podido borrar de la base de datos (Puede que la configuración(su ID), no exista en la base de datos).
	 */
	public boolean eliminarConfiguracion(ConfiguracionImpl configuracion)
	{
		boolean eliminada = false;
		
		String procedimiento = "EXECUTE BorrarConfiguracion @IDConfiguracion = '" + configuracion.getID() +"'";
		
		try 
		{
			Statement statement = conexion.createStatement();
			
			statement.execute(procedimiento);
			eliminada = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return eliminada;
	}
	
	/* INTERFAZ
	 * Comentario: Elimina las piezas extra de una configuración en la base de datos.
	 * Prototipo: public boolean eliminarPiezasExtraDeConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: La ConfiguracionImpl de la cual se desea borrar sus piezas extra.
	 * Precondiciones: La conexión con la base de dato debe estar abierta.
	 * Salida: Un boolean indicando si la eliminacion tuvo exito
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. Las piezas extra de la configuración han sido borradas satisfactoriamente de la base de datos.
	 * 					- False. Las piezas no se han podido borrar de la base de datos. (Puede que la configuración no exista en la base de datos).
	 */
	public boolean eliminarPiezasExtraDeConfiguracion(ConfiguracionImpl configuracion)
	{
		boolean eliminado = false;
		
		String borrarPiezasExtra = "DELETE FROM PiezasConfiguracionCoche "
				 + "WHERE IDConfiguracion = ?";
		
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(borrarPiezasExtra);
			
			statement.setString(1, configuracion.getID());
			
			statement.executeUpdate();
			eliminado = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return eliminado;
	}
	
	/* =EJW=EWV=IM=·N=FOIEFNO"·NR)NQ=! N=!N =!·FIN !^FIN ÊFNOÑNKJAA IL! RU!^SACN ÑKCH U!O"NE^)NDAJVBIGHB^/T H"()ENDWUSPBFY"BHRQ^DBSYVEB(H!B"U!)"HR!)"RB)!"RH )NSCÑACS)SOC=!=!DN=)!R=!N"R */
	
	/* INTERFAZ
	 * Comentario: Carga el coche de una configuración en ella, buscando en la base de datos.
	 * Prototipo: public void cargarCocheEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar el CocheImpl que tiene asociado en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por parámetro tiene el objeto CocheImpl cargado dentro de él.
	 * 					Si la configuración no existe en la base de datos, el CocheImpl será null.
	 */
	public void cargarCocheEnConfiguracion(ConfiguracionImpl configuracion)
	{
		GestionCoche gestionCoche = new GestionCoche(conexion);
		
		CocheImpl coche = gestionCoche.obtenerCoche(configuracion);
		
		configuracion.establecerCoche(coche);
	}
	
	/* INTERFAZ
	 * Comentario: Carga la CuentaImpl en una ConfiguracionImpl, buscando en la base de datos.
	 * Prototipo: public void cargarCuentaEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la CuentaImpl que tiene asociada en la base de datos.
	 * Postcondiciones: El Objeto ConfiguracionImpl pasado por parámetro tiene el objeto CuentaImpl cargado, esta cuenta es la cuenta
	 * 					que le corresponde a la configuracion en la base de datos.
	 * 					Si la configuración no existe en la base de datos, la CuentaImpl será null.
	 */
	public void cargarCuentaEnConfiguracion(ConfiguracionImpl configuracion)
	{
		GestionCuenta gestionCuenta = new GestionCuenta(conexion);
		
		CuentaImpl cuenta = gestionCuenta.obtenerCuenta(configuracion);
		
		configuracion.establecerCuenta(cuenta);
	}
	
	/* INTERFAZ
	 * Comentario: Carga las llantas de una configuración en dicha configuracion, buscando en la base de datos
	 * Prototipo: public void cargarLlantasEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar las llantas que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por parámetro tiene las llantas que le pertenece según la base de datos.
	 * 					Si la configuración no existe en la base de datos, las llantas será null.
	 */
	public void cargarLlantasEnConfiguracion(ConfiguracionImpl configuracion)
	{
		GestionPieza gestionPieza = new GestionPieza(conexion);
		
		configuracion.establecerLlantas(gestionPieza.obtenerPiezaLlantas(configuracion));
	}
	
	/* INTERFAZ
	 * Comentario: Carga la pintura de una configuración en dicha configuracion, buscando en la base de datos
	 * Prototipo: public void cargarPinturaEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la pintura que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por parámetro tiene la pintura que le pertenece según la base de datos.
	 * 					Si la configuración no existe en la base de datos, la pintura será null.
	 */
	public void cargarPinturaEnConfiguracion(ConfiguracionImpl configuracion)
	{
		GestionPieza gestionPieza = new GestionPieza(conexion);
		
		configuracion.establecerPintura(gestionPieza.obtenerPiezaPintura(configuracion));
	}
	
	/* INTERFAZ
	 * Comentario: Carga el motor de una configuración en dicha configuracion, buscando en la base de datos
	 * Prototipo: public void cargarMotorEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar el motor que tiene asociado, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por parámetro tiene el motor que le pertenece según la base de datos.
	 * 					Si la configuración no existe en la base de datos, el motor será null.
	 */
	public void cargarMotorEnConfiguracion(ConfiguracionImpl configuracion)
	{
		GestionPieza gestionPieza = new GestionPieza(conexion);
		
		configuracion.establecerMotor(gestionPieza.obtenerPiezaMotor(configuracion));
	}
	
	/* INTERFAZ
	 * Comentario: Carga las piezas de una configuracion en dicha configuracion, buscando en la base de datos.
	 * Prototipo: public void cargarPiezasEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la lista de piezas que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por parámetro tiene la lista de las piezas, esta lista de piezas son las piezas
	 * 					que le corresponden a la configuración en la base de datos.
	 * 					Si la configuración no existe en la base de datos, la lista de las piezas será null.
	 */
	public void cargarPiezasEnConfiguracion(ConfiguracionImpl configuracion)
	{
		GestionPieza gestionPieza = new GestionPieza(conexion);
		
		ArrayList<PiezaImpl> piezas = gestionPieza.obtenerPiezas(configuracion);
		
		configuracion.establecerPiezas(piezas);
	}
	
	/* INTERFAZ
	 * Comentario: Carga las piezas extra de una configuracion en dicha configuracion, buscando en la base de datos.
	 * Prototipo: public void cargarPiezasExtraEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la lista de piezas extra que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por parámetro tiene la lista de las piezas extra, esta lista de piezas son las piezas extra
	 * 					que le corresponden a la configuración en la base de datos.
	 * 					Si la configuración no existe en la base de datos, la lista de las piezas será null.
	 */
	public void cargarPiezasExtraEnConfiguracion(ConfiguracionImpl configuracion)
	{
		GestionPieza gestionPieza = new GestionPieza(conexion);
		
		ArrayList<PiezaImpl> piezasExtra = gestionPieza.obtenerPiezasExtra(configuracion);
		
		configuracion.establecerPiezas(piezasExtra);
	}
	
	/* INTERFAZ
	 * Comentario: Carga la lista con las votaciones de una configuración en dicha configuracion, buscando en la base de datos
	 * Prototipo: public void cargarVotacionesEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la lista de votaciones que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por parámetro tiene la lista de las votaciones que le pertenecen según la base de datos.
	 * 					Si la configuración no existe en la base de datos, la lista de las votaciones será null.
	 */
	public void cargarVotacionesEnConfiguracion(ConfiguracionImpl configuracion)
	{
		GestionVotacion gestionVotacion = new GestionVotacion(conexion);
		
		ArrayList<VotacionImpl> votaciones = gestionVotacion.obtenerVotaciones(configuracion);
		
		configuracion.establecerVotaciones(votaciones);
	}
	
	/* INTERFAZ
	 * Comentario: Carga todas las relaciones con otros objetos que tiene una configuracion en ella misma, es decir, carga el Coche que le pertenece,
	 * 				La Cuenta que le pertenece, las piezas y las votaciones.
	 * Prototipo: public void cargarRelacionesEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar todas sus relaciones con otros objetos, busca en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl tiene cargada todas sus relaciones con otros objetos, es decir:
	 * 						- Carga en el objeto ConfiguracionImpl el CocheImpl que le pertenece según la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl la CuentaImpl que le pertenece según la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl el ArrayList<PiezaImpl> que le pertenece según la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl el ArrayList<VotacionImpl> que le pertenece según la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl el MotorImpl que le pertenece según la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl las LlantasImpl que le pertenece según la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl la Pintura que le pertenece según la base de datos.
	 * 						- Si la configuración no existe en la base de datos, las relaciones quedarán con valores null.
	 */
	public void cargarRelacionesEnConfiguracion(ConfiguracionImpl configuracion)
	{
		cargarCocheEnConfiguracion(configuracion);
		cargarCuentaEnConfiguracion(configuracion);
		cargarPiezasExtraEnConfiguracion(configuracion);
		cargarVotacionesEnConfiguracion(configuracion);
		cargarMotorEnConfiguracion(configuracion);
		cargarLlantasEnConfiguracion(configuracion);
		cargarPinturaEnConfiguracion(configuracion);
	}
	
	/* INTERFAZ
	 * Comentario: Comprueba en la base de datos si una configuracion existe
	 * Prototipo: public boolean existeConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl de la que se desea comprobar su existencia en la base de datos.
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un boolean indicando si la configuracion existe en la base de datos o no.
	 * Postcondiciones: Asociado al nombre devuelve true si la configuracion existe en la base de datos o false de lo contrario.
	 */
	public boolean existeConfiguracion(ConfiguracionImpl configuracion)
	{
		boolean existe = false;
		
		String execute = "SELECT dbo.ExisteConfiguracion('" + configuracion.getID() + "') AS Existe";
		
		try
		{	
			CallableStatement stmnt = conexion.prepareCall(execute);
			
			ResultSet resultado = stmnt.executeQuery();
			
			if(resultado.next())
			{
				existe = resultado.getBoolean("Existe");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return existe;
	}
}
