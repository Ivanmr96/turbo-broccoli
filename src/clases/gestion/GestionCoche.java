package clases.gestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.PiezaImpl;

public class GestionCoche 
{
	private Connection conexion;
	
	public GestionCoche(Connection conexion)
	{
		this.conexion = conexion;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene el CocheImpl asociado a la ConfiguracionImpl dada, busca en la base de datos
	 * Prototipo: public CocheImpl obtenerCoche(ConfiguracionImpl configuracion)
	 * Entrada: Un objeto ConfiguracionImpl del que se desea obtener su CocheImpl
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un CocheImpl con el coche perteneciente(en la base de datos) a la configuracion dada
	 * Postcondiciones: Asociado al nombre devuelve un CocheImpl.
	 * 					- El objeto devuelto tendrá la información correspondiente al Coche que pertenece a la configuración dada si dicha configuración existe en la base de datos
	 * 					- El objeto devuelto será null si la configuración no existe en la base de datos.
	 */
	public CocheImpl obtenerCoche(ConfiguracionImpl configuracion)
	{
		CocheImpl coche = null;
		
		String consulta = "SELECT MarcaCoche, ModeloCoche, PrecioBase FROM Configuraciones AS conf "
						+ "INNER JOIN Coches AS co ON co.Marca = conf.MarcaCoche AND co.Modelo = conf.ModeloCoche "
						+ "WHERE conf.ID = ?";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, configuracion.getID());
			
			ResultSet resultado = statement.executeQuery();
			
			if(resultado.next())		//Salta a la primera y unica fila si la configuracion existe
			{
			
				String marca = resultado.getString("MarcaCoche");
				String modelo = resultado.getString("ModeloCoche");
				double precioBase = resultado.getDouble("PrecioBase");
				
				coche = new CocheImpl(marca, modelo, precioBase);
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return coche;
	}
	
	/* INTERFAZ
	 * Comentario: Busca un coche según su marca y modelo en la base de datos
	 * Prototipo: public CocheImpl obtenerCoche(String marca, String modelo)
	 * Entrada: Un String con la marca del coche y otro String con el modelo.
	 * Precondiciones: La conexion con la base de datos tiene que estar abierta
	 * Salida: Un CocheImpl con el coche encontrado en la base de datos
	 * Postcondiciones: Asociado al nombre devuelve un objeto CocheImpl, si el coche con la marca y modelo determinados existe en la base de datos, devuelve un CocheImpl
	 * 					con el coche determinado, si no existe en la base de datos, devuelve null.
	 */
	/**
	 * Busca un coche según su marca y modelo en la base de datos.<br>
	 * <b>Precondiciones:</b> La conexion con la base de datos tiene que estar abierta
	 * 
	 * @param marca La marca del coche a buscar.
	 * @param modelo El modelo del coche a buscar.
	 * @return El coche encontrado en la base de datos, será null si el modelo no existe.
	 */
	public CocheImpl obtenerCoche(String marca, String modelo)
	{
		CocheImpl coche = null;
		
		String consulta = "SELECT PrecioBase FROM Coches "
						+ "WHERE Marca = ? AND Modelo = ?";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, marca);
			statement.setString(2, modelo);
			ResultSet resultado = statement.executeQuery();
			
			if(resultado.next())		//Salta a la primera y unica fila
			{
				double precioBase = resultado.getDouble("PrecioBase");
				
				coche = new CocheImpl(marca, modelo, precioBase);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return coche;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene todos los coches de la base de datos
	 * Prototipo: public ArrayList<CocheImpl> obtenerCoches()
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList<CocheImpl> con todos los coches de la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<CocheImpl> con todos los coches de la base de datos.
	 */
	public ArrayList<CocheImpl> obtenerCoches()
	{
		ArrayList<CocheImpl> coches = new ArrayList<CocheImpl>();
		CocheImpl coche;
		String marca, modelo;
		double precioBase;
		
		String consulta = "SELECT Marca, Modelo, PrecioBase FROM Coches";
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			while(resultado.next())
			{
				marca = resultado.getString("Marca");
				modelo = resultado.getString("Modelo");
				precioBase = resultado.getDouble("PrecioBase");
				
				coche = new CocheImpl(marca, modelo, precioBase);
				
				coches.add(coche);
			}
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		
		return coches;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene una lista de los coches validos para una pieza dada
	 * Prototipo: public ArrayList<CocheImpl> obtenerCochesValidos(PiezaImpl pieza)
	 * Entrada: Una PiezaImpl de la que se desea conocer los coches validos para esa pieza.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Un ArrayList<CocheImpl> con los coches válidos para la pieza determinada.
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<CocheImpl> con los coche válidos para la pieza determinada.
	 */
	public ArrayList<CocheImpl> obtenerCochesValidos(PiezaImpl pieza)
	{
		ArrayList<CocheImpl> cochesValidos = null;
		CocheImpl coche = null;
		
		String consulta = "SELECT Marca, Modelo, PrecioBase FROM Coches AS c "
						+ "INNER JOIN PiezasCoches  AS pz ON pz.MarcaCoche = c.Marca AND pz.ModeloCoche = c.Modelo "
						+ "WHERE pz.IDPieza = ?";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setInt(1, pieza.getID());
			
			ResultSet resultado = statement.executeQuery();
			
			cochesValidos = new ArrayList<CocheImpl>();
			
			while(resultado.next())
			{
				String marca = resultado.getString("Marca");
				String modelo = resultado.getString("Modelo");
				double precioBase = resultado.getDouble("PrecioBase");
				
				coche = new CocheImpl(marca, modelo, precioBase);
				
				cochesValidos.add(coche);
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return cochesValidos;
	}
	
	/* INTERFAZ
	 * Comentario: Busca todas las marcas de coches en la base de datos
	 * Prototipo: public ArrayList<String> obtenerMarcas()
	 * Entrada: No hay
	 * Precondiciones: La conexion con la base de datos tiene que estar abierta
	 * Salida: Un ArrayList<String> con las marcas de coches de la base de datos
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<String> con todas las marcas de coches de la base de datos, si no hay ninguna, la lista estará vacía.
	 */
	/**
	 * Busca todas las marcas de coches en la base de datos. <br>
	 * <b>Precondiciones:</b> La conexion con la base de datos tiene que estar abierta
	 * 
	 * @return Todas las marcas del coches de la base de datos, si no hay ninguna, la lista estará vacía.
	 */
	public ArrayList<String> obtenerMarcas()
	{
		ArrayList<String> marcas = new ArrayList<String>();
		
		String consulta = "SELECT DISTINCT Marca FROM Coches";
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			while(resultado.next())
			{
				marcas.add(resultado.getString("Marca"));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return marcas;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene una lista de los modelos de coches de una marca dada
	 * Prototipo: public ArrayList<String> obtenerModelos(String marca)
	 * Entrada: Un String con la marca de la que se desea obtener sus modelos de coches
	 * Precondiciones: La conexion con la base de datos debe estar abierta.
	 * Salida: Un ArrayList<String> con los nombres de los modelos de la marca dada.
	 * Postcondicions: Asociado al nombre devuelve un ArrayList<String> con los nombres de los modelos de la marca dada, si la marca no existe o no tiene modelos, la lista estará vacía.
	 */
	/**
	 * Obtiene una lista de los modelos de coches de una marca dada<br>
	 * <b>Precondiciones:</b> La conexion con la base de datos debe estar abierta.
	 * 
	 * @param marca La marca de los modelos a buscar en la base de datos.
	 * @return Los modelos de la marca determinada, si la marca no existe o no tiene modelos, la lista estará vacía.
	 */
	public ArrayList<String> obtenerModelos(String marca)
	{
		ArrayList<String> modelos = new ArrayList<String>();
		
		String consulta = "SELECT Modelo FROM Coches WHERE Marca = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, marca);
			
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next())
			{
				modelos.add(resultado.getString("Modelo"));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return modelos;
	}
	
	/* INTERFAZ
	 * Comentario: Inserta un nuevo coche en la base de datos
	 * Prototipo: public boolean insertarCoche(CocheImpl coche)
	 * Entrada: el CocheImpl que se desea insertar en la base de datos
	 * Precondiciones: No hay
	 * Salida: Un boolean indicando si se introdujo el coche satisfactoriamente o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. Por lo tanto el coche ha sido introducido correctamente en la base de datos
	 * 					- False. El coche no se ha introducido correctamente en la base de datos.
	 * 					- Lanza SQLServerException si se intenta introducir un coche(modelo y marca) que ya existe en la base de datos.
	 */
	/**
	 * Inserta un nuevo coche en la base de datos.
	 * @param coche Coche que se insertará en la base de datos.
	 * @return Devuelve true si el coche ha sido introducido correctamente en la base de datos, false de lo contrario.
	 * @throws SQLServerException Si se intenta introducir un coche con modelo y marca que ya existe en la base de datos.
	 */
	public boolean insertarCoche(CocheImpl coche) throws SQLServerException
	{
		boolean insertado = false;
		
		String insert = "INSERT INTO Coches "
						+ "VALUES (?, ?, ?);";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(insert);
			
			statement.setString(1, coche.getMarca());
			statement.setString(2, coche.getModelo());
			statement.setDouble(3, coche.getPrecioBase());
			
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
	 * Comentario: Elimina un coche de la base de datos, asi como las configuraciones(y sus votaciones) asociadas a él.
	 * Prototipo: public boolean eliminarCoche(CocheImpl coche)
	 * Entrada: El CocheImpl que se desea eliminar de la base de datos
	 * Precondiciones: No hay
	 * Salida: Un boolean indicando si la eliminacion tuvo exito
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. El coche ha sido borrado satisfactoriamente de la base de datos. Las configuraciones y las votaciones asociadas al cocha también se borran.
	 * 					- False. El coche no se ha podido borrar de la base de datos (Puede que el coche(marca y modelo) no exista en la base de datos)
	 */
	/**
	 * Elimina un coche de la base de datos, asi como las configuraciones(y sus votaciones) asociadas a él.
	 * @param coche El coche que se desea eliminar de la base de datos.
	 * @return True si el coche ha sido borrado correctamente de la base de datos, las configuraciones y las votaciones asociadas al coche se borran también.<br>
	 * 		   False si el coche no se ha podido borrar de la base de datos.
	 */
	public boolean eliminarCoche(CocheImpl coche)
	{
		boolean borrado = false;
		
		String delete = "DELETE FROM Coches "
					  + "WHERE Marca = ? AND Modelo = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(delete);
			
			statement.setString(1, coche.getMarca());
			statement.setString(2, coche.getModelo());
			
			int filasAfectadas = statement.executeUpdate();
			
			if (filasAfectadas > 0)
				borrado = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return borrado;
	}
	
	/* INTERFAZ
	 * Comentario: Actualiza la información sobre un coche en la base de datos
	 * Prototipo: public boolean actualizarCoche(CocheImpl coche)
	 * Entrada: El CocheImpl que se desea modificar
	 * Precondiciones: La conexion con la base de datos debe estar abierta.
	 * Salida: Un boolean indicando si la actualización se realizo bien o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. La información del coche se ha actualizado con exito en la base de datos.
	 * 					- False. No se ha podido actualizar el coche en la base de datos, quizás no exista un coche de esa marca y modelo en la base de datos
	 */
	/**
	 * Actualiza la información sobre un coche en la base de datos.<br>
	 * Toma la información del {@link CocheImpl CocheImpl} de la entrada (es decir, su precio base) para actualizarlo en la base de datos.<br>
	 * <b>Precondiciones:</b> La conexion con la base de datos debe estar abierta.
	 * @param coche El coche que se desea modificar.
	 * @return True si la información del coche se ha actualizado con éxtio en la base de datos.<br>
	 * 		   False si no se ha podido actualizar el coche en la base de datos, quizás no exista un coche con esa marca y modelo en la base de datos.
	 */
	public boolean actualizarCoche(CocheImpl coche)
	{
		boolean actualizado = false;
		
		String insert = "UPDATE Coches "
						+ "SET PrecioBase = ? "
						+ "WHERE Marca = ? AND Modelo = ?";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(insert);
			
			statement.setDouble(1, coche.getPrecioBase());
			statement.setString(2, coche.getMarca());
			statement.setString(3, coche.getModelo());
			
			int filasAfectadas = statement.executeUpdate();
			
			if(filasAfectadas > 0)
				actualizado = true;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return actualizado;
	}
	
	/* INTERFAZ
	 * Comentario: Carga las piezas validas en un objeto CocheImpl
	 * Prototipo: public void cargarPiezasValidasEnCoche(CocheImpl coche)
	 * Entrada: No hay
	 * Precondiciones: La conexion con la base de datos debe estar abierta.
	 * Salida: No hay
	 * Entrada/salida: Un CocheImpl al que se le desea cargar su lista de piezas validas.
	 * Postcondiciones: El CocheImpl pasado por parámetro tendrá su lista de piezas validas cargadas si este coche (es decir, su marca y modelo) existe en la base de datos.
	 * 					- Si el coche existe pero no tiene ninguna pieza valida, la lista estará vacía
	 * 					- Si el coche no existe en la base de datos, no se cambiará el estado del coche pasado por parámetro.
	 * 					- Si el coche existe y tiene piezas validas, este tendrá su lista de piezas validas cargadas en él.
	 */
	/**
	 * Carga las piezas válidas en un objeto {@link CocheImpl CocheImpl}.<br>
	 * <b>Precondiciones:</b> La conexion con la base de datos debe estar abierta.
	 * @param coche Coche al que se le desea cargar su lsita de piezas válidas.
	 */
	public void cargarPiezasValidasEnCoche(CocheImpl coche)
	{
		GestionPieza gestionPieza = new GestionPieza(conexion);
		
		ArrayList<PiezaImpl> piezasValidas = gestionPieza.obtenerPiezasValidas(coche);
		
		coche.establecerPiezasValidas(piezasValidas);
	}
}
