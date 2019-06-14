package clases.gestion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import clases.basicas.CocheImpl;
import clases.basicas.CombustionImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.LlantasImpl;
import clases.basicas.MotorImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.PinturaImpl;
import clases.basicas.VotacionImpl;
import utils.Utils;

public class AObjeto 
{
	/*private String URLConexion = "jdbc:sqlserver://localhost;"
								  + "database=Coches;"
								  + "user=usuarioCoches;"
								  + "password=123;";*/
	
	private String URLConexion;
	private Connection conexion;
	
	public AObjeto(String URLConexion)
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
	 * Comentario: Cierra la conexi�n con la base de datos
	 * Prototipo: public void cerrarConexion()
	 * Entrada: No hay
	 * Precondiciones: La conexi�n debe estar abierta
	 * Salida: No hay
	 * Postcondiciones: Se cierra la conexi�n a la base de datos establecida, con las credenciales dadas y en el servidor determinado.
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
	
	//CocheImpl
	
	/* INTERFAZ
	 * Comentario: Busca un coche seg�n su marca y modelo en la base de datos
	 * Prototipo: public CocheImpl obtenerCoche(String marca, String modelo)
	 * Entrada: Un String con la marca del coche y otro String con el modelo.
	 * Precondiciones: No hay
	 * Salida: Un CocheImpl con el coche encontrado en la bbdd
	 * Postcondiciones: Asociado al nombre devuelve un objeto CocheImpl, si el coche con la marca y modelo determinados existe en la base de datos, devuelve un CocheImpl
	 * 					con el coche determinado, si no existe en la base de datos, devuelve null.
	 */
	public CocheImpl obtenerCoche(String marca, String modelo)
	{
		CocheImpl coche = null;
		String consulta = "SELECT PrecioBase FROM Coches "
						+ "WHERE Marca = '" + marca + "' AND Modelo = '" + modelo + "'";
		try 
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
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
	 * Comentario: Busca todas las marcas de coches en la base de datos
	 * Prototipo: public ArrayList<String> obtenerMarcas()
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList<String> con las marcas de coches de la base de datos
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<String> con todas las marcas de coches de la base de datos, si no hay ninguna, la lista estar� vac�a.
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
	 * Precondiciones: La conexion debe estar abierta
	 * Salida: Un ArrayList<String> con los nombres de los modelos de la marca dada.
	 * Postcondicions: Asociado al nombre devuelve un ArrayList<String> con los nombres de los modelos de la marca dada, si la marca no existe, la lista estar� vac�a.
	 */
	public ArrayList<String> obtenerModelos(String marca)
	{
		ArrayList<String> modelos = new ArrayList<String>();
		
		String consulta = "SELECT Modelo FROM Coches WHERE Marca = '" + marca + "'";
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
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
	 * Comentario: Busca las piezas validas de un coche en la base de datos
	 * Prototipo: public ArrayList<PiezaImpl> obtenerPiezasValidas(CocheImpl coche
	 * Entrada: Un objeto CocheImpl con el coche del que se desean obtener sus piezas validas
	 * Precondiciones: No hay
	 * Salida: Un ArrayList de PiezaImpl con las piezas validas del coche
	 * Postcondciones: Asociado al nombre devuelve un ArrayList de PiezaImpl con las piezas validas del coche.
	 * 					- Si, aunque el coche exista en la base de datos, este no tiene ninguna pieza valida, la lista estar� vac�a.
	 * 					- Si el coche no existe en la base de datos, devuelve null
	 * 					- Si el coche existe y tiene piezas validas, devuelve una lista con las piezas validas.
	 */
	public ArrayList<PiezaImpl> obtenerPiezasValidas(CocheImpl coche)
	{
		ArrayList<PiezaImpl> piezasValidas = null;
		
		String consulta = "SELECT ID, Nombre, Descripcion, Precio, Tipo FROM Piezas AS pz " 
				+ "INNER JOIN PiezasCoches AS pzco ON pzco.IDPieza = pz.ID "
				+ "WHERE pzco.MarcaCoche = '" + coche.getMarca() + "' AND pzco.ModeloCoche = '" + coche.getModelo() + "'";
		
		PiezaImpl pieza;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			piezasValidas = new ArrayList<PiezaImpl>();
			
			while(resultado.next())
			{
				String tipo = resultado.getString("Tipo");
				int ID = resultado.getInt("ID");
				
				if(tipo == null)		//Si el campo tipo se dejo a NULL, la instancia ser� de tipo PiezaImpl
					pieza = obtenerPieza(ID);
				else
				{
					switch(tipo)		//Si el campo tiene algun valor, sera del tipo indicado si coincide con alg�n subtipo, si es distinto, ser� de tipo PiezaImpl (default)
					{
						case "motor":
							pieza = obtenerPiezaMotor(ID);
							break;
						case "pintura":
							pieza = obtenerPiezaPintura(ID);
							break;
						case "llantas":
							pieza = obtenerPiezaLlantas(ID);
							break;
						default:
							pieza = obtenerPieza(ID);
							break;
					}
				}
				
				piezasValidas.add(pieza);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return piezasValidas;
	}
	
	/* INTERFAZ
	 * Comentario: Carga las piezas validas en un objeto CocheImpl
	 * Prototipo: public void cargarPiezasValidasEnCoche(CocheImpl coche)
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: No hay
	 * Entrada/salida: Un CocheImpl al que se le desea carga su lista de piezas validas
	 * Postcondiciones: El CocheImpl pasado por par�metro tendr� su lista de piezas validas cargadas si este coche (es decir, su marca y modelo) existe en la base de datos.
	 * 					- Si el coche existe pero no tiene ninguna pieza valida, la lista estar� vac�a
	 * 					- Si el coche no existe en la base de datos, no se cambiar� el estado del coche pasado por par�metro.
	 * 					- Si el coche existe y tiene piezas validas, este tendr� su lista de piezas validas cargadas en �l.
	 */
	public void cargarPiezasValidasEnCoche(CocheImpl coche)
	{
		ArrayList<PiezaImpl> piezasValidas = obtenerPiezasValidas(coche);
		
		coche.establecerPiezasValidas(piezasValidas);
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
	 * 					- Lanza SQLServerException si se intenta introducir un coche(modelo y marca) que ya existen en la base de datos.
	 */
	public boolean insertarCoche(CocheImpl coche) throws SQLServerException
	{
		boolean insertado = false;
		
		String marca = coche.getMarca();
		String modelo = coche.getModelo();
		double precioBase = coche.getPrecioBase();
		
		String insert = "INSERT INTO Coches "
						+ "VALUES ('" + marca + "', '" + modelo + "', " + precioBase + ");";
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
		
		
		return insertado;
	}
	
	/* INTERFAZ
	 * Comentario: Elimina un coche de la base de datos, asi como las configuraciones(y sus votaciones) asociadas a �l.
	 * Prototipo: public boolean eliminarCoche(CocheImpl coche)
	 * Entrada: El CocheImpl que se desea eliminar de la base de datos
	 * Precondiciones: No hay
	 * Salida: Un boolean indicando si la eliminacion tuvo exito
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. El coche ha sido borrado satisfactoriamente de la base de datos. Las configuraciones y las votaciones asociadas al cocha tambi�n se borran.
	 * 					- False. El coche no se ha podido borrar de la base de datos (Puede que el coche(marca y modelo) no exista en la base de datos)
	 */
	public boolean eliminarCoche(CocheImpl coche)
	{
		boolean borrado = false;
		
		String marca = coche.getMarca();
		String modelo = coche.getModelo();
		
		String delete = "DELETE FROM Coches "
					  + "WHERE Marca = '" + marca + "' AND Modelo = '" + modelo + "'";
		
		try
		{
			Statement statement = conexion.createStatement();
			
			int filasAfectadas = statement.executeUpdate(delete);
			
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
	 * Comentario: Actualiza la informaci�n sobre un coche en la base de datos
	 * Prototipo: public boolean actualizarCoche(CocheImpl coche)
	 * Entrada: El CocheImpl que se desea modificar
	 * Precondiciones: No hay
	 * Salida: Un boolean indicando si la actualizaci�n se realizo bien o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. La informaci�n del coche se ha actualizado con exito en la base de datos.
	 * 					- False. No se ha podido actualizar el coche en la base de datos, quiz�s no exista un coche de esa marca y modelo en la base de datos
	 */
	public boolean actualizarCoche(CocheImpl coche)
	{
		boolean actualizado = false;
		
		String marca = coche.getMarca();
		String modelo = coche.getModelo();
		double precioBase = coche.getPrecioBase();
		
		String insert = "UPDATE Coches "
						+ "SET PrecioBase = " + precioBase + " "
						+ "WHERE Marca = '" + marca + "' AND Modelo = '" + modelo + "'";
		try 
		{
			Statement statement = conexion.createStatement();
			
			int filasAfectadas = statement.executeUpdate(insert);
			
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
	 * Comentario: Obtiene una configuracion de la base de datos dado su ID
	 * Prototipo: public ConfiguracionImpl obtenerConfiguracion(String ID)
	 * Entrada: Un String con el ID de la configuraci�n que se desea obtener
	 * Precondiciones: El String debe tener un formato de UUID, es decir:
	 * 					32 digitos hexadecimales divididos en cinco grupos separados por guiones divididos de la forma 8-4-4-4-12, por ejemplo: 550e8400-e29b-41d4-a716-446655440000
	 * Salida: Un objeto ConfiguracionImpl con la configuracion del ID dado
	 * Postcondiciones: Asociado al nombre devuelve un objeto ConfiguracionImpl.
	 * 					- Si el ID es de una configuraci�n existente en la base de datos, el objeto tendr� la informaci�n correspondiente a la configuraci�n con su ID en la base de datos.
	 * 					- Si el ID no existe en la base de datos, ser� null.
	 */
	public ConfiguracionImpl obtenerConfiguracion(String ID)
	{
		ConfiguracionImpl configuracion = null;
		
		Utils utils = new Utils();
		
		String consulta = "SELECT Fecha FROM Configuraciones "
						+ "WHERE ID = '" + ID + "'";
		try 
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
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
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList<ConfiguracionImpl> con todas las configuraciones de la base de datos
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<ConfiguracionImpl> con todas las configuraciones de la base de datos.
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
	 * Comentario: Obtiene las configuraciones de una marca de coche.
	 * Prototipo: public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(String marca)
	 * Entrada: Un String con la marca de coche de la que se desea obtener todas las configuraciones realizadas
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList<ConfiguracionImpl> con todas las configuraciones de una marca en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<ConfiguracionImpl> con todas las configuraciones de una marca en la base de datos. Si no hay
	 * 					ninguna configuracion para ning�n coche de la marca dada, o la marca no existe, la lista estar� vac�a.
	 */
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(String marca)
	{
		ArrayList<ConfiguracionImpl> configuraciones = new ArrayList<ConfiguracionImpl>();
		Utils utils = new Utils();
		ConfiguracionImpl configuracion;
		String ID;
		GregorianCalendar fecha;
		
		String consulta = "SELECT ID, Fecha FROM Configuraciones "
						+ "WHERE MarcaCoche = '" + marca + "'";
		
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
	 * 					ninguna configuracion para ning�n coche de la marca dada, o la marca no existe, la lista estar� vac�a.
	 */
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(CocheImpl coche)
	{
		ArrayList<ConfiguracionImpl> configuraciones = new ArrayList<ConfiguracionImpl>();
		Utils utils = new Utils();
		ConfiguracionImpl configuracion;
		String ID;
		GregorianCalendar fecha;
		
		String consulta = "SELECT ID, Fecha FROM Configuraciones "
						+ "WHERE MarcaCoche = '" + coche.getMarca() + "' AND ModeloCoche = '" + coche.getModelo() + "'";
		
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
	
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(double precioMinimo, double precioMaximo); //TODO funcionalidad para calcular el precio de una configuracion
	
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(GregorianCalendar fecha);
	
	/* INTERFAZ
	 * Comentario: Obtiene el CocheImpl asociado a la ConfiguracionImpl dada, busca en la base de datos
	 * Prototipo: public CocheImpl obtenerCoche(ConfiguracionImpl configuracion)
	 * Entrada: Un objeto ConfiguracionImpl del que se desea obtener su CocheImpl
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un CocheImpl con el coche perteneciente(en la base de datos) a la configuracion dada
	 * Postcondiciones: Asociado al nombre devuelve un CocheImpl.
	 * 					- El objeto devuelto tendr� la informaci�n correspondiente al Coche que pertenece a la configuraci�n dada si dicha configuraci�n existe en la base de datos
	 * 					- El objeto devuelto ser� null si la configuraci�n no existe en la base de datos.
	 */
	public CocheImpl obtenerCoche(ConfiguracionImpl configuracion)
	{
		CocheImpl coche = null;
		
		String consulta = "SELECT MarcaCoche, ModeloCoche, PrecioBase FROM Configuraciones AS conf "
						+ "INNER JOIN Coches AS co ON co.Marca = conf.MarcaCoche AND co.Modelo = conf.ModeloCoche "
						+ "WHERE conf.ID = '" + configuracion.getID() + "'";
		try 
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
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
	 * Comentario: Obtiene la CuentaImpl de una Configuracion. Busca en la base de datos.
	 * Prototipo: public CuentaImpl obtenerCuenta(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl de la que se desea obtener la CuentaImpl que la hizo
	 * Precodiciones: La conexion tiene que estar abierta
	 * Salida: La CuentaImpl que pertenece (en la base de datos) a la Configuracion dada
	 * Postcondiciones: Asociado al nombre devuelve una CuentaImpl
	 * 					- El objeto tendr� la informaci�n con la cuenta correspondiente a la configuracion si dicha configuracion existe en la base de datos.
	 * 					- El objeto ser� null si la configuraci�n no existe en la base de datos.
	 */
	public CuentaImpl obtenerCuenta(ConfiguracionImpl configuracion)
	{
		CuentaImpl cuenta = null;
		
		String consulta = "SELECT NombreUsuario, Contrase�a FROM Configuraciones AS conf "
						+ "INNER JOIN Cuentas AS cu ON cu.NombreUsuario = conf.Usuario "
						+ "WHERE conf.ID = '" + configuracion.getID() + "'";
		try 
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			if(resultado.next())		//Salta a la primera y unica fila si la configuracion existe
			{
				String nombreUsuario = resultado.getString("NombreUsuario");
				String contrasena = resultado.getString("Contrase�a");
				
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
	 * Comentario: Obtiene la lista de las piezas de una configuracion. Busca en la base de datos
	 * Prototipo: public ArrayList<PiezaImpl> obtenerPiezas(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl de la que se desea obtener la lista de sus piezas
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList de PiezaImpl con la lista de las piezas la configuracion dada.
	 * Postcondiciones: Asociado al nombe devuelve un ArrayList<PiezaImpl>.
	 * 					- Si la configuracion existe en la base de datos, la lista tendr� las piezas de dicha configuracion, es posible que la lista est� vac�a.
	 * 						Significa que la configuraci�n no tiene ninguna pieza.
	 * 					- Si la configuraci�n no existe en la base de datos, el ArrayList<PiezaImpl> ser� null.
	 */
	public ArrayList<PiezaImpl> obtenerPiezas(ConfiguracionImpl configuracion)
	{
		ArrayList<PiezaImpl> piezas = null;
		
		String consulta = "SELECT pz.ID, Nombre, Descripcion, Precio, Tipo FROM Configuraciones AS conf " 
						+ "INNER JOIN PiezasConfiguracionCoche AS PzConf ON PzConf.IDConfiguracion = conf.ID "
						+ "INNER JOIN Piezas AS pz ON pz.ID = PzConf.IDPieza "
						+ "WHERE conf.ID = '" + configuracion.getID() + "'";
		
		PiezaImpl pieza;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			piezas = new ArrayList<PiezaImpl>();
			
			while(resultado.next())
			{
				String tipo = resultado.getString("Tipo");
				int ID = resultado.getInt("ID");
				
				if(tipo == null)		//Si el campo tipo se dejo a NULL, la instancia ser� de tipo PiezaImpl
					pieza = obtenerPieza(ID);
				else
				{
					switch(tipo)		//Si el campo tiene algun valor, sera del tipo indicado si coincide con alg�n subtipo, si es distinto, ser� de tipo PiezaImpl (default)
					{
						case "motor":
							pieza = obtenerPiezaMotor(ID);
							break;
						case "pintura":
							pieza = obtenerPiezaPintura(ID);
							break;
						case "llantas":
							pieza = obtenerPiezaLlantas(ID);
							break;
						default:
							pieza = obtenerPieza(ID);
							break;
					}
				}
				
				piezas.add(pieza);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return piezas;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene la lista de las piezas extra de una configuracion. Busca en la base de datos
	 * Prototipo: public ArrayList<PiezaImpl> obtenerPiezasExtra(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl de la que se desea obtener la lista de sus piezas extra
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList de PiezaImpl con la lista de las piezas extra la configuracion dada.
	 * Postcondiciones: Asociado al nombe devuelve un ArrayList<PiezaImpl>.
	 * 					- Si la configuracion existe en la base de datos, la lista tendr� las piezas extra de dicha configuracion, es posible que la lista est� vac�a.
	 * 						Significa que la configuraci�n no tiene ninguna pieza extra.
	 * 					- Si la configuraci�n no existe en la base de datos, el ArrayList<PiezaImpl> ser� null.
	 */
	public ArrayList<PiezaImpl> obtenerPiezasExtra(ConfiguracionImpl configuracion)
	{
		ArrayList<PiezaImpl> piezasExtra = null;
		
		String consulta = "SELECT pz.ID, Nombre, Descripcion, Precio, Tipo FROM Configuraciones AS conf " 
						+ "INNER JOIN PiezasConfiguracionCoche AS PzConf ON PzConf.IDConfiguracion = conf.ID "
						+ "INNER JOIN Piezas AS pz ON pz.ID = PzConf.IDPieza "
						+ "WHERE conf.ID = '" + configuracion.getID() + "' "
						+ "AND Tipo NOT IN ('motor', 'llantas', 'pintura')"; //OR Tipo IS NULL";
		
		PiezaImpl pieza;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			piezasExtra = new ArrayList<PiezaImpl>();
			
			while(resultado.next())
			{
				int ID = resultado.getInt("ID");
				
				pieza = obtenerPieza(ID);
				
				piezasExtra.add(pieza);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return piezasExtra;
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
		
		String consulta = "SELECT NombreUsuario FROM Cuentas WHERE NombreUsuario = '" + usuario + "'";
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			if(resultado.next())
				existe = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
		return existe;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene las votaciones de una configuracion. Busca en la base de datos
	 * Prototipo: public ArrayList<VotacionImpl> obtenerVotaciones(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl de la que se desean obtener sus votaciones.
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList de VotacionImpl con la lista de votaciones de la configuracion dada.
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<VotacionImpl>.
	 * 					- Si la configuraci�n existe en la base de datos, devuelve una lista con las votaciones realizadas a dicha configuracion.
	 * 						La lista puede estar vac�a, esto significa que la configuraci�n no tiene ninguna votaci�n.
	 * 					- Si la configuraci�n no existe en la base de datos, el ArrayList<VotacionImpl> estar� vacio.
	 */
	public ArrayList<VotacionImpl> obtenerVotaciones(ConfiguracionImpl configuracion)
	{
		ArrayList<VotacionImpl> votaciones = null;
		
		String consulta = "SELECT ID, Calificacion, Fecha FROM Votaciones " 
						+ "WHERE IDConfiguracion = '" + configuracion.getID() + "'";		//TODO Como hacer que devuelva NULL si la configuracion no existe en la bbdd
		
		Utils utils = new Utils();
		
		VotacionImpl votacion;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
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
	 * Comentario: Carga el coche de una configuraci�n en ella, buscando en la base de datos.
	 * Prototipo: public void cargarCocheEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar el CocheImpl que tiene asociado en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por par�metro tiene el objeto CocheImpl cargado dentro de �l.
	 * 					Si la configuraci�n no existe en la base de datos, el CocheImpl ser� null.
	 */
	public void cargarCocheEnConfiguracion(ConfiguracionImpl configuracion)
	{
		CocheImpl coche = obtenerCoche(configuracion);
		
		configuracion.establecerCoche(coche);
	}
	
	/* INTERFAZ
	 * Comentario: Carga la CuentaImpl en una ConfiguracionImpl, buscando en la base de datos.
	 * Prototipo: public void cargarCuentaEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la CuentaImpl que tiene asociada en la base de datos.
	 * Postcondiciones: El Objeto ConfiguracionImpl pasado por par�metro tiene el objeto CuentaImpl cargado, esta cuenta es la cuenta
	 * 					que le corresponde a la configuracion en la base de datos.
	 * 					Si la configuraci�n no existe en la base de datos, la CuentaImpl ser� null.
	 */
	public void cargarCuentaEnConfiguracion(ConfiguracionImpl configuracion)
	{
		CuentaImpl cuenta = obtenerCuenta(configuracion);
		
		configuracion.establecerCuenta(cuenta);
	}
	
	/* INTERFAZ
	 * Comentario: Carga las piezas de una configuracion en dicha configuracion, buscando en la base de datos.
	 * Prototipo: public void cargarPiezasEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la lista de piezas que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por par�metro tiene la lista de las piezas, esta lista de piezas son las piezas
	 * 					que le corresponden a la configuraci�n en la base de datos.
	 * 					Si la configuraci�n no existe en la base de datos, la lista de las piezas ser� null.
	 */
	public void cargarPiezasEnConfiguracion(ConfiguracionImpl configuracion)
	{
		ArrayList<PiezaImpl> piezas = obtenerPiezas(configuracion);
		
		configuracion.establecerPiezas(piezas);
	}
	
	/* INTERFAZ
	 * Comentario: Carga las piezas extra de una configuracion en dicha configuracion, buscando en la base de datos.
	 * Prototipo: public void cargarPiezasExtraEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la lista de piezas extra que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por par�metro tiene la lista de las piezas extra, esta lista de piezas son las piezas extra
	 * 					que le corresponden a la configuraci�n en la base de datos.
	 * 					Si la configuraci�n no existe en la base de datos, la lista de las piezas ser� null.
	 */
	public void cargarPiezasExtraEnConfiguracion(ConfiguracionImpl configuracion)
	{
		ArrayList<PiezaImpl> piezasExtra = obtenerPiezasExtra(configuracion);
		
		configuracion.establecerPiezas(piezasExtra);
	}
	
	/* INTERFAZ
	 * Comentario: Carga la lista con las votaciones de una configuraci�n en dicha configuracion, buscando en la base de datos
	 * Prototipo: public void cargarVotacionesEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la lista de votaciones que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por par�metro tiene la lista de las votaciones que le pertenecen seg�n la base de datos.
	 * 					Si la configuraci�n no existe en la base de datos, la lista de las votaciones ser� null.
	 */
	public void cargarVotacionesEnConfiguracion(ConfiguracionImpl configuracion)
	{
		ArrayList<VotacionImpl> votaciones = obtenerVotaciones(configuracion);
		
		configuracion.establecerVotaciones(votaciones);
	}
	
	/* INTERFAZ
	 * Comentario: Carga el motor de una configuraci�n en dicha configuracion, buscando en la base de datos
	 * Prototipo: public void cargarMotorEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar el motor que tiene asociado, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por par�metro tiene el motor que le pertenece seg�n la base de datos.
	 * 					Si la configuraci�n no existe en la base de datos, el motor ser� null.
	 */
	public void cargarMotorEnConfiguracion(ConfiguracionImpl configuracion)
	{
		configuracion.establecerMotor(obtenerPiezaMotor(configuracion));
	}
	
	/* INTERFAZ
	 * Comentario: Carga las llantas de una configuraci�n en dicha configuracion, buscando en la base de datos
	 * Prototipo: public void cargarLlantasEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar las llantas que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por par�metro tiene las llantas que le pertenece seg�n la base de datos.
	 * 					Si la configuraci�n no existe en la base de datos, las llantas ser� null.
	 */
	public void cargarLlantasEnConfiguracion(ConfiguracionImpl configuracion)
	{
		configuracion.establecerLlantas(obtenerPiezaLlantas(configuracion));
	}
	
	/* INTERFAZ
	 * Comentario: Carga la pintura de una configuraci�n en dicha configuracion, buscando en la base de datos
	 * Prototipo: public void cargarPinturaEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la pintura que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por par�metro tiene la pintura que le pertenece seg�n la base de datos.
	 * 					Si la configuraci�n no existe en la base de datos, la pintura ser� null.
	 */
	public void cargarPinturaEnConfiguracion(ConfiguracionImpl configuracion)
	{
		configuracion.establecerPintura(obtenerPiezaPintura(configuracion));
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
	 * 						- Carga en el objeto ConfiguracionImpl el CocheImpl que le pertenece seg�n la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl la CuentaImpl que le pertenece seg�n la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl el ArrayList<PiezaImpl> que le pertenece seg�n la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl el ArrayList<VotacionImpl> que le pertenece seg�n la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl el MotorImpl que le pertenece seg�n la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl las LlantasImpl que le pertenece seg�n la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl la Pintura que le pertenece seg�n la base de datos.
	 * 						- Si la configuraci�n no existe en la base de datos, las relaciones quedar�n con valores null.
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
		
		//cargarCocheEnConfiguracion(configuracion);
		//cargarCuentaEnConfiguracion(configuracion);
		
		boolean insertado = false;
		
		String ID = configuracion.getID();
		String fecha = utils.GregorianCalendarToDateTime(configuracion.getFecha());
		
		String usuario = configuracion.obtenerCuenta().getNombreUsuario();
		
		String marcaCoche = configuracion.obtenerCoche().getMarca();
		String modeloCoche = configuracion.obtenerCoche().getModelo();
		
		String insert = "INSERT INTO Configuraciones "
						+ "(ID, Usuario, Fecha, MarcaCoche, ModeloCoche) "
						+ "VALUES ('" + ID + "', '" + usuario + "', '" + fecha + "', '" + marcaCoche + "', '" + modeloCoche + "');";
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
		
		
		return insertado;
	}
	
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
	
	public boolean eliminarPiezasExtraDeConfiguracion(ConfiguracionImpl configuracion)
	{
		boolean eliminado = false;
		
		String borrarPiezasExtra = "DELETE FROM PiezasConfiguracionCoche "
				 + "WHERE IDConfiguracion = '" + configuracion.getID() + "'";
		
		try 
		{
			Statement statement = conexion.createStatement();
			
			statement.executeUpdate(borrarPiezasExtra);
			eliminado = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return eliminado;
	}
	
	/* INTERFAZ
	 * Comentario: Actualiza en la base de datos el motor que pertenece a la configuracion
	 * 			   Mira en el objeto ConfiguracionImpl su MotorImpl para guardar en la base de datos cu�l es su motor.
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
		
		String IDMotor;
		
		if(configuracion.obtenerMotor() != null)
			IDMotor = String.valueOf(configuracion.obtenerMotor().getID());
		else
			IDMotor = "NULL";
		
		String updateConfiguracion = "UPDATE Configuraciones "
								   + "SET IDMotor = " + IDMotor;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			filasAfectadas = statement.executeUpdate(updateConfiguracion);
			
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
	 * 			   Mira en el objeto ConfiguracionImpl su LlantasImpl para guardar en la base de datos cu�les son sus llantas.
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
		String IDLlantas;
		
		if(configuracion.obtenerLlantas() != null)
			IDLlantas = String.valueOf(configuracion.obtenerLlantas().getID());
		else
			IDLlantas= "NULL";
		
		String updateConfiguracion = "UPDATE Configuraciones "
								   + "SET IDLlantas = " + IDLlantas;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			filasAfectadas = statement.executeUpdate(updateConfiguracion);
			
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
	 * 			   Mira en el objeto ConfiguracionImpl su PinturaImp, para guardar en la base de datos cu�l es su pintura.
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
		String IDPintura;
		
		if(configuracion.obtenerPintura() != null)
			IDPintura = String.valueOf(configuracion.obtenerPintura().getID());
		else
			IDPintura= "NULL";
		
		String updateConfiguracion = "UPDATE Configuraciones "
								   + "SET IDPintura = " + IDPintura;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			filasAfectadas = statement.executeUpdate(updateConfiguracion);
			
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
	 * 					- True si se actualizo correctamente la configuracion en la base de datos seg�n el objeto ConfiguracionImpl del par�emtro.
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
	 * Comentario: Obtiene una Cuenta a partir de su nombre de usuario buscando en la base de datos, la contrase�a est� cifrada con MD5.
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
		
		String consulta = "SELECT Contrase�a FROM Cuentas "
						+ "WHERE NombreUsuario = '" + nombreUsuario + "'";
		try 
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			if(resultado.next())		//Salta a la primera y unica fila
			{
				String contrasena = resultado.getString("Contrase�a");
				
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
						+ "WHERE Usuario = '" + cuenta.getNombreUsuario() + "'";
		try 
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
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
						+ "WHERE Usuario = '" + cuenta.getNombreUsuario() + "'";
		try 
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
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
	 * Comentario: Carga la lista con las configuraciones de una cuenta en dicha cuenta, buscando en la base de datos
	 * Prototipo: public void cargarConfiguracionesEnCuenta(CuentaImpl cuenta)
	 * Entrada: Una CuentaImpl en la que se desea cargar sus configuraciones
	 * Precondiciones: La conexion debe estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una CuentaImpl a la que se le desea cargar la lista de configuraciones que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto CuentaImpl pasado por par�metro tiene la lista de las configuraciones que le pertenecen seg�n la base de datos.
	 * 					Si la cuenta no existe en la base de datos, la lista de las votaciones ser� null.
	 */
	public void cargarConfiguracionesEnCuenta(CuentaImpl cuenta)
	{
		ArrayList<ConfiguracionImpl> configuraciones = obtenerConfiguraciones(cuenta);
		
		cuenta.establecerConfiguraciones(configuraciones);
	}
	
	/* INTERFAZ
	 * Comentario: Carga la lista con las votacines realizadas por una cuenta en dicha cuenta, buscando en la base de datos
	 * Prototipo: public void cargarVotacionesEnCuenta(CuentaImpl cuenta)
	 * Entrada: Una CuentaImpl en la que se desea cargar sus votaciones realizadas.
	 * Precondiciones: La conexion debe estar abierta
	 * Salida: No hay
	 * Entrada/Salida: Una CuentaImpl a la que se le desea cargar la lista de votaciones que ha realizado, buscando en la base de datos.
	 * Postcondiciones: El objeto CuentaImpl pasado por par�metro tiene la lista de las votaciones que le pertenecen seg�n la base de datos.
	 * 					Si la cuenta no existe en la base de datos, la lista de las votaciones ser� null.
	 */
	public void cargarVotacionesEnCuenta(CuentaImpl cuenta)
	{
		ArrayList<VotacionImpl> votaciones = obtenerVotaciones(cuenta);
		
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
	 * 						- Carga en el objeto CuentaImpl el ArrayList<ConfiguracionImpl> que le pertenece seg�n la base de datos.
	 * 						- Carga en el objeto CuentaImpl el ArrayList<VotacionImpl> que le pertenece seg�n la base de datos.
	 * 						- Si la configuraci�n no existe en la base de datos, las relaciones quedar�n con valores null.
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
		
		String nombreUsuario = cuenta.getNombreUsuario();
		String contrasena = cuenta.getContrasena();
		
		String insert = "INSERT INTO Cuentas "
						+ "VALUES ('" + nombreUsuario + "', '" + contrasena + "');";
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
		
		
		return insertado;
	}
	
	public boolean eliminarCuenta(CuentaImpl cuenta);
	
	public boolean actualizarCuenta(CuentaImpl cuenta);
	
	//-------------------------------------------------------
	
	//PiezaImpl
	
	/* INTERFAZ
	 * Comentario: Obtiene una pieza dado su ID, busca en la base de datos
	 * Prototipo: public PiezaImpl obtenerPieza(int ID)
	 * Entrada: Un int con la ID de la pieza a buscar
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: la PiezaImpl con el ID dado, busca en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve una PiezaImpl perteneciente a la pieza con el ID dado buscando en la base de datos.
	 * 					- Este m�todo no debe utilizarse para obtener piezas especializadas, es decir, motores, llantas o pinturas. 
	 * 						Este m�todo no obtiene la informaci�n extra que tienen estas piezas especializadas, solo la parte com�n a todas las piezas.
	 * 						Para las piezas especializadas, usar los m�todos obtenerPiezaPintura, obtenerPiezaLlantas y obtenerPiezaMotor...
	 * 					- Si el ID no es de ninguna pieza existente en la base de datos, devuelve null.
	 */
	public PiezaImpl obtenerPieza(int ID)
	{
		PiezaImpl pieza = null;
		
		String consulta = "SELECT Nombre, Descripcion, Precio FROM Piezas "
						+ "WHERE ID = " + ID;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			if(resultado.next())
			{
				String nombre = resultado.getString("Nombre");
				String descripcion = resultado.getString("Descripcion");
				double precio = resultado.getDouble("Precio");
				
				pieza = new PiezaImpl(ID, nombre, descripcion,precio);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return pieza;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene una lista de los coches validos para una pieza dada
	 * Prototipo: public ArrayList<CocheImpl> obtenerCochesValidos(PiezaImpl pieza)
	 * Entrada: Una PiezaImpl de la que se desea conocer los coches validos para esa pieza.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Un ArrayList<CocheImpl> con los coches v�lidos para la pieza determinada.
	 * Postcondiciones: Asociado al nombre devuelve un ArrayList<CocheImpl> con los coche v�lidos para la pieza determinada.
	 */
	public ArrayList<CocheImpl> obtenerCochesValidos(PiezaImpl pieza)
	{
		ArrayList<CocheImpl> cochesValidos = null;
		CocheImpl coche = null;
		
		String consulta = "SELECT Marca, Modelo, PrecioBase FROM Coches AS c "
						+ "INNER JOIN PiezasCoches  AS pz ON pz.MarcaCoche = c.Marca AND pz.ModeloCoche = c.Modelo "
						+ "WHERE pz.IDPieza = " + pieza.getID();
		try 
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
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
	 * Comentario: Carga en un objeto PiezaImpl la lista de coches validos.
	 * Prototipo: public void cargarCochesValidosEnPieza(PiezaImpl pieza)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Entrada/Salida: Una PiezaImpl a la que se le desea cargar la lista de coches validos buscando en la base de datos.
	 * Salida: No hay
	 * Postcondiciones: El Objeto PiezaImpl de la lista de par�metros tiene cargada la lista de coches validos seg�n la base de datos.
	 */
	public void cargarCochesValidosEnPieza(PiezaImpl pieza)
	{
		ArrayList<CocheImpl> cochesValidos = obtenerCochesValidos(pieza);
		
		pieza.establecerCochesValidos(cochesValidos);
	}
	
	/* INTERFAZ
	 * Comentario: Inserta una nueva pieza en la base de datos
	 * Prototipo: public boolean insertarPieza(PiezaImpl pieza) throws SQLServerException
	 * Entrada: la PiezaImpl que se desea insertar en la base de datos
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Un boolean indicando si se introdujo la cuenta satisfactoriamente o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. Por lo tanto la pieza ha sido introducida correctamente en la base de datos
	 * 					- False. La pieza no se ha introducido correctamente en la base de datos.
	 * 					- Lanza SQLServerException si se intenta introducir una pieza que ya existe en la base de datos.
	 */
	public boolean insertarPieza(PiezaImpl pieza) throws SQLServerException
	{
		boolean insertado = false;
		
		int ID = pieza.getID();
		String nombre = pieza.getNombre();
		String descripcion = pieza.getDescripcion();
		double precio = pieza.getPrecio();
		
		String insert = "INSERT INTO Piezas "
						+ "(ID, Nombre, Descripcion, Precio) "
						+ "VALUES (" + ID + ", '" + nombre + "', '" + descripcion + "', " + precio + ");";
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
		
		
		return insertado;
	}
	
	public boolean insertarPiezaEnConfiguracion(PiezaImpl pieza, ConfiguracionImpl configuracion) throws SQLServerException
	{
		boolean insertado = false;
		
		String insert = "INSERT INTO PiezasConfiguracionCoche "
					  + "VALUES (" + pieza.getID() + ", '" + configuracion.getID() + "');";
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
		
		
		return insertado;
	}
	
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
	 * Comentario: Inserta un nuevo motor en la base de datos
	 * Prototipo: public boolean insertarPiezaMotor(MotorImpl motor) throws SQLServerException
	 * Entrada: el MotorImpl que se desea insertar en la base de datos
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Un boolean indicando si se introdujo la cuenta satisfactoriamente o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. Por lo tanto el motor ha sido introducido correctamente en la base de datos
	 * 					- False. El motor no se ha introducido correctamente en la base de datos.
	 * 					- Lanza SQLServerException si se intenta introducir un motor que ya existe en la base de datos.
	 */
	public boolean insertarPiezaMotor(MotorImpl motor) throws SQLServerException
	{
		
		boolean insertado = false;
		
		int ID = motor.getID();
		String nombre = motor.getNombre();
		String descripcion = motor.getDescripcion();
		double precio = motor.getPrecio();
		
		char traccion = motor.getTraccion();
		int numeroVelocidades = motor.getNumeroVelocidades();
		int autonomia = motor.getAutonomia();
		int potencia = motor.getPotencia();
		String tipo = motor.getTipo();
		
		String insertPieza = "INSERT INTO Piezas "
							+ "VALUES (" + ID + ", '" + nombre + "', '" + descripcion + "', " + precio + ", 'motor');";
		
		String insertMotor = "INSERT INTO Motores "
							+ "(IDPieza, Traccion, NumeroVelocidades, Autonomia, Potencia, Tipo) "
							+ "VALUES (" + ID + ", '" + traccion + "', " + numeroVelocidades + ", " + autonomia + ", " + potencia + ", '" + tipo + "');";
		try 
		{
			Statement statement = conexion.createStatement();
			
			statement.execute("BEGIN TRAN");
			
			int filasAfectadas = statement.executeUpdate(insertPieza);
			
			filasAfectadas += statement.executeUpdate(insertMotor);
			
			System.out.println("QUE?");
			
			System.out.println(filasAfectadas);
			
			if(filasAfectadas == 2)
			{
				insertado = true;
				statement.execute("COMMIT");
			}
		} 
		catch (SQLServerException e)
		{
			Statement statement;
			try {
				statement = conexion.createStatement();
				
				statement.execute("ROLLBACK");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		}
		catch (SQLException e) 
		{
			Statement statement;
			try {
				statement = conexion.createStatement();
				
				statement.execute("ROLLBACK");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		
		return insertado;
	}
	
	public boolean eliminarPieza(PiezaImpl pieza);
	public boolean actualizarPieza(PiezaImpl pieza);
	
	//-------------------------------------------------------
	
	/* INTERFAZ
	 * Comentario: Obtiene la pieza de tipo pintura seg�n un ID dado. Busca en la base de datos
	 * Prototipo: public PinturaImpl obtenerPiezaPintura(int ID)
	 * Entrada: Un int con la ID de la pieza tipo pintura que se desea obtener de la base de datos.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Una PinturaImpl que pertenece a la pintura con la ID dada en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve devuelve una PinturaImpl.
	 * 					- Si la ID dada pertenece a una pintura existente en la base de datos, devuelve el objeto con la informaci�n.
	 * 					- Si la ID dada no pertenece a una pintura existente en la base de datos, devuelve el objeto null.
	 */
	public PinturaImpl obtenerPiezaPintura(int ID)
	{
		PinturaImpl pintura = null;
		
		String consulta = "SELECT Nombre, Descripcion, Precio, Color, Acabado FROM Pinturas AS P "
						+ "INNER JOIN Piezas AS Pz ON pz.ID = P.IDPieza "
						+ "WHERE pz.ID = " + ID;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			if(resultado.next())
			{
				String nombre = resultado.getString("Nombre");
				String descripcion = resultado.getString("Descripcion");
				double precio = resultado.getDouble("Precio");
				String color = resultado.getString("Color");
				String acabado = resultado.getString("Acabado");
				
				pintura = new PinturaImpl(ID, nombre, descripcion,precio, color, acabado);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return pintura;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene la pieza de tipo llantas seg�n un ID dado. Busca en la base de datos
	 * Prototipo: public LlantasImpl obtenerPiezaLlantas(int ID)
	 * Entrada: Un int con la ID de la pieza tipo llantas que se desea obtener de la base de datos.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Una LlantasImpl que pertenece a las llantas con la ID dada en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve devuelve un objeto LlantasImpl.
	 * 					- Si la ID dada pertenece a unas llantas existentes en la base de datos, devuelve el objeto con la informaci�n.
	 * 					- Si la ID dada no pertenece a unas llantas existentes en la base de datos, devuelve el objeto null.
	 */
	public LlantasImpl obtenerPiezaLlantas(int ID)
	{
		LlantasImpl llantas = null;
		
		String consulta = "SELECT Nombre, Descripcion, Precio, Pulgadas FROM Llantas AS Ll "
						+ "INNER JOIN Piezas AS Pz ON pz.ID = Ll.IDPieza "
						+ "WHERE pz.ID = " + ID;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			if(resultado.next())
			{
				String nombre = resultado.getString("Nombre");
				String descripcion = resultado.getString("Descripcion");
				double precio = resultado.getDouble("Precio");
				int pulgadas = resultado.getInt("Pulgadas");
				
				llantas = new LlantasImpl(ID, nombre, descripcion, precio, pulgadas);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return llantas;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene la pieza de tipo motor seg�n un ID dado. Busca en la base de datos
	 * Prototipo: public MotorImpl obtenerPiezaMotor(int ID)
	 * Entrada: Un int con la ID de la pieza tipo motor que se desea obtener de la base de datos.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Una MotorImpl que pertenece al motor con la ID dada en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve devuelve un objeto MotorImpl.
	 * 					- Si la ID dada pertenece a un motor existente en la base de datos, devuelve el objeto con la informaci�n.
	 * 					- Si la ID dada no pertenece a un motor existente en la base de datos, devuelve el objeto null.
	 */
	public MotorImpl obtenerPiezaMotor(int ID)
	{
		MotorImpl motor = null;
		
		String consulta = "SELECT Nombre, Descripcion, Precio, Traccion, NumeroVelocidades, Autonomia, Potencia, M.Tipo FROM Motores AS M "
						+ "INNER JOIN Piezas AS Pz ON pz.ID = M.IDPieza "
						+ "WHERE pz.ID = " + ID;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			if(resultado.next())
			{
				String nombre = resultado.getString("Nombre");
				String descripcion = resultado.getString("Descripcion");
				double precio = resultado.getDouble("Precio");
				char traccion = resultado.getString("Traccion").charAt(0);
				int numeroVelocidades = resultado.getInt("NumeroVelocidades");
				int autonomia = resultado.getInt("Autonomia");
				int potencia = resultado.getInt("Potencia");
				String tipo = resultado.getString("Tipo");
				
				motor = new MotorImpl(ID, nombre, descripcion, precio, traccion, numeroVelocidades, autonomia, potencia, tipo);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return motor;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene el motor de una configuracion dada. Busca en la base de datos
	 * Prototipo: public MotorImpl obtenerPiezaMotor(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracioImpl con la configuracion de la que se desea obtener su motor.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Una MotorImpl que pertenece, en la base de datos, a la configuracion determinada.
	 * Postcondiciones: Asociado al nombre devuelve el MotorImpl de la configuracion dada, busca en la base de datos.
	 */
	public MotorImpl obtenerPiezaMotor(ConfiguracionImpl configuracion)
	{
		MotorImpl motor = null;
		
		String consulta = "SELECT pz.ID, Nombre, Descripcion, Precio, Traccion, NumeroVelocidades, Autonomia, Potencia, mot.Tipo FROM Configuraciones AS confi "
						+ "INNER JOIN Piezas AS pz ON pz.ID = confi.IDMotor "
						+ "INNER JOIN Motores AS mot ON mot.IDPieza = pz.ID "
						+ "WHERE confi.ID = '" + configuracion.getID() + "'";
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			if(resultado.next())
			{
				int ID = resultado.getInt("ID");
				String nombre = resultado.getString("Nombre");
				String descripcion = resultado.getString("Descripcion");
				double precio = resultado.getDouble("Precio");
				char traccion = resultado.getString("Traccion").charAt(0);
				int numeroVelocidades = resultado.getInt("NumeroVelocidades");
				int autonomia = resultado.getInt("Autonomia");
				int potencia = resultado.getInt("Potencia");
				String tipo = resultado.getString("Tipo");
				
				motor = new MotorImpl(ID, nombre, descripcion, precio, traccion, numeroVelocidades, autonomia, potencia, tipo);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return motor;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene las llantas de una configuracion dada. Busca en la base de datos
	 * Prototipo: public LlantasImpl obtenerPiezaLlantas(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracioImpl con la configuracion de la que se desea obtener sus llantas.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Unas LlantasImpl que pertenece, en la base de datos, a la configuracion determinada.
	 * Postcondiciones: Asociado al nombre devuelve las LlantasImpl de la configuracion dada, busca en la base de datos.
	 */
	public LlantasImpl obtenerPiezaLlantas(ConfiguracionImpl configuracion)
	{
		LlantasImpl llantas = null;
		
		String consulta = "SELECT pz.ID, Nombre, Descripcion, Precio, Pulgadas FROM Configuraciones AS confi "
						+ "INNER JOIN Piezas AS pz ON pz.ID = confi.IDLlantas "
						+ "INNER JOIN Llantas AS pnt ON pnt.IDPieza = pz.ID "
						+ "WHERE confi.ID = '" + configuracion.getID() + "'";
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			if(resultado.next())
			{
				int ID = resultado.getInt("ID");
				String nombre = resultado.getString("Nombre");
				String descripcion = resultado.getString("Descripcion");
				double precio = resultado.getDouble("Precio");
				int pulgadas = resultado.getInt("Pulgadas");
				
				llantas = new LlantasImpl(ID, nombre, descripcion, precio, pulgadas);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return llantas;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene la pintura de una configuracion dada. Busca en la base de datos
	 * Prototipo: public PinturaImpl obtenerPiezaPintura(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracioImpl con la configuracion de la que se desea obtener su pintura.
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Unas PinturaImpl que pertenece, en la base de datos, a la configuracion determinada.
	 * Postcondiciones: Asociado al nombre devuelve la PinturaImpl de la configuracion dada, busca en la base de datos.
	 */
	public PinturaImpl obtenerPiezaPintura(ConfiguracionImpl configuracion)
	{
		PinturaImpl pintura = null;
		
		String consulta = "SELECT pz.ID, Nombre, Descripcion, Precio, Color, Acabado FROM Configuraciones AS confi " 
						+ "INNER JOIN Piezas AS pz ON pz.ID = confi.IDPintura " 
						+ "INNER JOIN Pinturas AS pnt ON pnt.IDPieza = pz.ID "
						+ "WHERE confi.ID = '" + configuracion.getID() + "'";
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			if(resultado.next())
			{
				int ID = resultado.getInt("ID");
				String nombre = resultado.getString("Nombre");
				String descripcion = resultado.getString("Descripcion");
				double precio = resultado.getDouble("Precio");
				String color = resultado.getString("Color");
				String acabado = resultado.getString("Acabado");
				
				pintura = new PinturaImpl(ID, nombre, descripcion, precio, color, acabado);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return pintura;
	}
	
	public boolean insertarVotacion(VotacionImpl votacion);
	
	//public boolean insertarPiezaPintura(PinturaImpl pieza);
	//public boolean insertarPiezaLlantas(LlantasImpl pieza);
	//public boolean insertarPiezaMotor(MotorImpl pieza);
	
	public static void main(String[] args)
	{
		AObjeto aObj = new AObjeto("jdbc:sqlserver://localhost;"
								  + "database=Coches;"
								  + "user=usuarioCoches;"
								  + "password=123;");
		
		aObj.abrirConexion();
		
		/*CocheImpl coche = aObj.obtenerCoche("AUDI", "A1");
		
		System.out.println(coche.getPrecioBase());
		
		System.out.println();
		
		ArrayList<PiezaImpl> piezasValidas = null; //aObj.obtenerPiezasValidas(coche);
		
		//piezasValidas.forEach((n) -> System.out.println(n.getClass().getTypeName()));
		
		System.out.println();
		
		aObj.cargarPiezasValidasEnCoche(coche);
		
		piezasValidas = coche.obtenerPiezasValidas();
		
		for(PiezaImpl pieza:piezasValidas)
		{
			System.out.println(pieza.getNombre());
		}
		
		try 
		{
			aObj.insertarCoche(new CocheImpl("Toyota", "Prius", 21200));
		} catch (SQLServerException e) 
		{
			if(e.getErrorCode() == 2627)
			{
				System.out.println("Este coche ya existe en la base de datos.");
			}
		}
		
		coche.setPrecioBase(300);
		
		System.out.println(aObj.actualizarCoche(new CocheImpl("Citroen", "C3", 9500)));
		
		System.out.println();
		
		ConfiguracionImpl conf = aObj.obtenerConfiguracion("FB619D46-359D-46A0-B68D-6CCBE62714DC");
		
		System.out.println(conf.getFecha().getTime()); */
		
		
		
		
		/*
		
		ConfiguracionImpl conf = aObj.obtenerConfiguracion("2B4B87BD-E79C-440F-A429-8D15F2F476FE");
		
		System.out.println(conf.getFecha().getTime());
		
		System.out.println();
		
		CocheImpl coche = aObj.obtenerCoche(conf);
		CuentaImpl cuenta = aObj.obtenerCuenta(conf);
		
		System.out.println(coche.getPrecioBase());
		System.out.println(cuenta.getContrasena());
		
		ArrayList<PiezaImpl> piezas = aObj.obtenerPiezas(conf);
		
		for(PiezaImpl pz:piezas)
		{
			System.out.println(pz.getClass().getTypeName());
		}
		
		Utils utils = new Utils();
		
		GregorianCalendar fecha = utils.dateTimeToGregorianCalendar("2019-06-08 21:32:22.990");
		
		System.out.println(fecha.getTime());
		
		System.out.println();
		
		ArrayList<VotacionImpl> votaciones = aObj.obtenerVotaciones(conf);
		
		for(VotacionImpl votacion:votaciones)
		{
			System.out.println(votacion.getCalificacion());
		}
		
		aObj.cargarRelacionesEnConfiguracion(conf);
		
		System.out.println(conf.obtenerCoche().getMarca());
		System.out.println(conf.obtenerCuenta().getNombreUsuario());

		piezas = conf.obtenerPiezas();
		
		for(PiezaImpl pieza:piezas) { System.out.println(pieza.getNombre()); }
		
		votaciones = conf.obtenerVotaciones();
		
		for(VotacionImpl votacion:votaciones)
		{
			System.out.println(votacion.getFecha().getTime());
		}
		
		Scanner teclado = new Scanner(System.in);
		
		System.out.print("Introduce nombre usuario: ");
		String nombreUsuario = teclado.nextLine();
		
		CuentaImpl cuentaa = aObj.obtenerCuenta(nombreUsuario);
		
		System.out.print("Introduce contrasena: ");
		String contrasena = utils.obtenerMD5(teclado.nextLine());
		
		if(contrasena.equals(cuentaa.getContrasena()))
		{
			System.out.println("Inicio de sesion correcto.");
		}
		else
		{
			System.out.println("contrasena de la cuenta: " + cuentaa.getContrasena());
			System.out.println("contrasena introducida: " + contrasena);
			System.out.println("Contrasena incorrecta.");
		}*/
		
		/*
		
		ConfiguracionImpl conf = aObj.obtenerConfiguracion("2B4B87BD-E79C-440F-A429-8D15F2F476FE");
		
		ArrayList<VotacionImpl> votaciones = aObj.obtenerVotaciones(conf);�
		
		System.out.println(votaciones.size());
		
		for(VotacionImpl votacion:votaciones)
		{
			System.out.println(votacion.getID());
		}
		
		System.out.println();
		
		for(ConfiguracionImpl config:aObj.obtenerConfiguraciones(aObj.obtenerCuenta("testuser")))
		{
			System.out.println(config.getID());
		}
		
		System.out.println();
		
		for(VotacionImpl votacion:aObj.obtenerVotaciones(aObj.obtenerCuenta("testuser")))
		{
			System.out.println(votacion.getFecha().getTime());
		}
		
		System.out.println();
		
		for(CocheImpl coche:aObj.obtenerCochesValidos(aObj.obtenerPieza(1)))
		{
			System.out.println(coche.getMarca());
		}
		
		CocheImpl coche2 = aObj.obtenerCoche("AUDI", "A1");
		
		System.out.println(coche2.getPrecioBase());
		
		Utils utils = new Utils();
		
		ConfiguracionImpl confi = new ConfiguracionImpl("137D8ABC-FA8C-458D-891A-25B76CDD96BD", utils.dateTimeToGregorianCalendar("2019-06-08 21:06:20.280"));
		
		confi.establecerCoche(aObj.obtenerCoche("Toyota", "Prius"));
		
		confi.establecerCuenta(aObj.obtenerCuenta("testuser"));
		
		try 
		{
			aObj.insertarConfiguracion(confi);
		} catch (SQLServerException e) 
		{
			if(e.getErrorCode() == 2627)
			{
				System.out.println("Configuracion ya existente en la base de datos, no se insertar�.");
			}
			else
				e.printStackTrace();
		}
		
		ArrayList<ConfiguracionImpl> confis = aObj.obtenerConfiguraciones(aObj.obtenerCuenta("testuser"));
		
		for(ConfiguracionImpl configuracioncita:confis)
		{
			System.out.print(aObj.obtenerCoche(configuracioncita).getModelo() + ": ");
			System.out.println(aObj.obtenerCoche(configuracioncita).getPrecioBase());
		}
		
		try 
		{
			aObj.insertarCuenta(new CuentaImpl("Ivansito", utils.obtenerMD5("321")));
		} 
		catch (SQLServerException e) 
		{
			if(e.getErrorCode() == 2627)
			{
				System.out.println("Cuenta ya exitente en la base de datos, no se insertar�.");
			}
			else
				e.printStackTrace();
		}
		
		CuentaImpl cuentecita = aObj.obtenerCuenta("Ivansito");
		
		System.out.println(cuentecita.getContrasena());
		
		//System.out.println(conf.getFecha().getTime());
		
		PiezaImpl pz = new LlantasImpl(6, "llantas", "de algo", 17, 13);
		
		try {
			aObj.insertarPieza(pz);
		} catch (SQLServerException e) {
			System.out.println("Pieza ya exitente en la base de datos, no se insertar�.");
		}
		
		MotorImpl motor = new MotorImpl(10, "Motor", "Un motor", 2300, 'T', 6, 300, 160, "D");
		
		try 
		{
			System.out.println(aObj.insertarPiezaMotor(motor));
		} catch (SQLServerException e) 
		{
			if(e.getErrorCode() == 2627)
			{
				System.out.println("Pieza ya existente en la base de datos, no se insertar�.");
			}
			else
				e.printStackTrace();
		}
		
		System.out.println(aObj.eliminarCoche(aObj.obtenerCoche("AUDI", "A1"))); */
		
		ConfiguracionImpl configuracion = aObj.obtenerConfiguracion("C59F23B1-BB9A-4C0B-B8C0-0854D291157F");
		
		System.out.println(aObj.existeConfiguracion(configuracion));
		
		System.out.println(aObj.existeUsuario("testuser"));
	}
	
}

/* TODO >>
 * Por implementar:
	public boolean insertarConfiguracion(ConfiguracionImpl configuracion);
	// Tengo que ver si al insertar una configuracion, inserto tambien sus votaciones y sus piezas.

	public boolean eliminarConfiguracion(ConfiguracionImpl configuracion);
	// Posible procedimiento almacenado (O borrado en cascada)

	public boolean actualizarConfiguracion(ConfiguracionImpl configuracion);
	// Tengo que ver como se har�n las actualizaciones, en el caso de la configuracion,
	// No hay nada que actualizar salvo las piezas, quiz�s ser�a buena idea
	// Ponerle m�todos a la clase para a�adir/quitar piezas. Y luego
	// Un m�todo en la clase de gesti�n para actualizar solo las piezas, en lugar de todo.
	//
	// Otra soluci�n ser�a que las funcionalidades de a�adir/quitar piezas lo hagan directamente
	// A la base de datos en lugar de cambiarlo primero en el objeto y luego con ese objeto actualizar
	// La base de datos.

	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(CuentaImpl cuenta);

	public ArrayList<VotacionImpl> obtenerVotaciones(CuentaImpl cuenta);

	public boolean insertarCuenta(CuentaImpl cuenta);

	public boolean eliminarCuenta(CuentaImpl cuenta)
	// Debe de haber dos formas de borrar una cuenta:
	// Borrando tambien sus configuraciones y votaciones, o borrando solo la cuenta sin borrar lo demas.

	public boolean actualizarCuenta(CuentaImpl cuenta)
	// Es solo cambiar contrase�a.

	public ArrayList<CocheImpl> obtenerCochesValidos(PiezaImpl pieza);

	public boolean insertarPieza(PiezaImpl pieza);

	public boolean eliminarPieza(PiezaImpl pieza)
	// Al borrar una pieza, se deben borrar las configuraciones que usan dicha pieza

	public boolean actualizarPieza(PiezaImpl pieza);
 */
