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
	 * Comentario: Cierra la conexión
	 * Prototipo: public void cerrarConexion()
	 * Entrada: No hay
	 * Precondiciones: La conexión debe estar abierta
	 * Salida: No ay
	 * Postcondiciones: Se cierra la conexión a la base de datos establecida, con las credenciales dadas y en el servidor determinado.
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
	 * Comentario: Busca un coche según su marca y modelo en la base de datos
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
	 * 					- Si, aunque el coche exista en la base de datos, este no tiene ninguna pieza valida, la lista estará vacía.
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
				
				if(tipo == null)		//Si el campo tipo se dejo a NULL, la instancia será de tipo PiezaImpl
					pieza = obtenerPieza(ID);
				else
				{
					switch(tipo)		//Si el campo tiene algun valor, sera del tipo indicado si coincide con algún subtipo, si es distinto, será de tipo PiezaImpl (default)
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
	 * Postcondiciones: El CocheImpl pasado por parámetro tendrá su lista de piezas validas cargadas si este coche (es decir, su marca y modelo) existe en la base de datos.
	 * 					- Si el coche existe pero no tiene ninguna pieza valida, la lista estará vacía
	 * 					- Si el coche no existe en la base de datos, no se cambiará el estado del coche pasado por parámetro.
	 * 					- Si el coche existe y tiene piezas validas, este tendrá su lista de piezas validas cargadas en él.
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
	 * Comentario: Elimina un coche de la base de datos, asi como las configuraciones(y sus votaciones) asociadas a él.
	 * Prototipo: public boolean eliminarCoche(CocheImpl coche)
	 * Entrada: El CocheImpl que se desea eliminar de la base de datos
	 * Precondiciones: No hay
	 * Salida: Un boolean indicando si la eliminacion tuvo exito
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. El coche ha sido borrado satisfactoriamente de la base de datos. Las configuraciones y las votaciones asociadas al cocha también se borran.
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
	 * Comentario: Actualiza la información sobre un coche en la base de datos
	 * Prototipo: public boolean actualizarCoche(CocheImpl coche)
	 * Entrada: El CocheImpl que se desea modificar
	 * Precondiciones: No hay
	 * Salida: Un boolean indicando si la actualización se realizo bien o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True. La información del coche se ha actualizado con exito en la base de datos.
	 * 					- False. No se ha podido actualizar el coche en la base de datos, quizás no exista un coche de esa marca y modelo en la base de datos
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
	
	//-------------------------------------------------------
	
	//ConfiguracionImpl
	
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
	
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(String marca);
	
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
	
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(double precioMinimo, double precioMaximo);
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(GregorianCalendar fecha);
	
	/* INTERFAZ
	 * Comentario: Obtiene el CocheImpl asociado a la ConfiguracionImpl dada, busca en la base de datos
	 * Prototipo: public CocheImpl obtenerCoche(ConfiguracionImpl configuracion)
	 * Entrada: Un objeto ConfiguracionImpl del que se desea obtener su CocheImpl
	 * Precondiciones: No hay
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
	 * Precodiciones: No hay
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
						+ "WHERE conf.ID = '" + configuracion.getID() + "'";
		try 
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
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
	 * Comentario: Obtiene la lista de las piezas de una configuracion. Busca en la base de datos
	 * Prototipo: public ArrayList<PiezaImpl> obtenerPiezas(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl de la que se desea obtener la lista de sus piezas
	 * Precondiciones: No hay
	 * Salida: Un ArrayList de PiezaImpl con la lista de las piezas la configuracion dada.
	 * Postcondiciones: Asociado al nombe devuelve un ArrayList<PiezaImpl>.
	 * 					- Si la configuracion existe en la base de datos, la lista tendrá las piezas de dicha configuracion, es posible que la lista esté vacía.
	 * 						Significa que la configuración no tiene ninguna pieza.
	 * 					- Si la configuración no existe en la base de datos, el ArrayList<PiezaImpl> será null.
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
				
				if(tipo == null)		//Si el campo tipo se dejo a NULL, la instancia será de tipo PiezaImpl
					pieza = obtenerPieza(ID);
				else
				{
					switch(tipo)		//Si el campo tiene algun valor, sera del tipo indicado si coincide con algún subtipo, si es distinto, será de tipo PiezaImpl (default)
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
	 * Comentario: Comprueba en la base de datos si una configuracion existe
	 * Prototipo: public boolean existeConfiguracion(ConfiguracionImpl configuracion)
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
	 * Precondiciones: No hay
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
	 * Comentario: Carga el coche de una configuración en ella, buscando en la base de datos.
	 * Prototipo: public void cargarCocheEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar el CocheImpl que tiene asociado en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por parámetro tiene el objeto CocheImpl cargado dentro de él.
	 * 					Si la configuración no existe en la base de datos, el CocheImpl será null.
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
	 * Precondiciones: No hay
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la CuentaImpl que tiene asociada en la base de datos.
	 * Postcondiciones: El Objeto ConfiguracionImpl pasado por parámetro tiene el objeto CuentaImpl cargado, esta cuenta es la cuenta
	 * 					que le corresponde a la configuracion en la base de datos.
	 * 					Si la configuración no existe en la base de datos, la CuentaImpl será null.
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
	 * Precondiciones: No hay
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la lista de piezas que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por parámetro tiene la lista de las piezas, esta lista de piezas son las piezas
	 * 					que le corresponden a la configuración en la base de datos.
	 * 					Si la configuración no existe en la base de datos, la lista de las piezas será null.
	 */
	public void cargarPiezasEnConfiguracion(ConfiguracionImpl configuracion)
	{
		ArrayList<PiezaImpl> piezas = obtenerPiezas(configuracion);
		
		configuracion.establecerPiezas(piezas);
	}
	
	/* INTERFAZ
	 * Comentario: Carga la lista con las votaciones de una configuración en dicha configuracion, buscando en la base de datos
	 * Prototipo: public void cargarVotacionesEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar la lista de votaciones que tiene asociada, buscando en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl pasado por parámetro tiene la lista de las votaciones que le pertenecen según la base de datos.
	 * 					Si la configuración no existe en la base de datos, la lista de las votaciones será null.
	 */
	public void cargarVotacionesEnConfiguracion(ConfiguracionImpl configuracion)
	{
		ArrayList<VotacionImpl> votaciones = obtenerVotaciones(configuracion);
		
		configuracion.establecerVotaciones(votaciones);
	}
	
	/* INTERFAZ
	 * Comentario: Carga todas las relaciones con otros objetos que tiene una configuracion en ella misma, es decir, carga el Coche que le pertenece,
	 * 				La Cuenta que le pertenece, las piezas y las votaciones.
	 * Prototipo: public void cargarRelacionesEnConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: No hay
	 * Entrada/Salida: Una ConfiguracionImpl a la que se le desea cargar todas sus relaciones con otros objetos, busca en la base de datos.
	 * Postcondiciones: El objeto ConfiguracionImpl tiene cargada todas sus relaciones con otros objetos, es decir:
	 * 						- Carga en el objeto ConfiguracionImpl el CocheImpl que le pertenece según la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl la CuentaImpl que le pertenece según la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl el ArrayList<PiezaImpl> que le pertenece según la base de datos.
	 * 						- Carga en el objeto ConfiguracionImpl el ArrayList<VotacionImpl> que le pertenece según la base de datos.
	 * 						- Si la configuración no existe en la base de datos, lógicamente no podrá obtener las relaciones de la base de datos, por lo tanto las relaciones
	 * 						quedarán con valores null.
	 */
	public void cargarRelacionesEnConfiguracion(ConfiguracionImpl configuracion)
	{
		cargarCocheEnConfiguracion(configuracion);
		cargarCuentaEnConfiguracion(configuracion);
		cargarPiezasEnConfiguracion(configuracion);
		cargarVotacionesEnConfiguracion(configuracion);
	}
	
	/* INTERFAZ
	 * Comentario: Inserta una nueva configuracion en la base de datos
	 * Prototipo: public boolean insertarConfiguracion(ConfiguracionImpl configuracion) throws SQLServerException
	 * Entrada: la ConfiguracionImpl que se desea insertar en la base de datos
	 * Precondiciones: el objeto ConfiguracionImpl debe tener asignado un CocheImpl y una CuentaImpl, debido a que no se puede insertar
	 * 					en la base de datos una configuracion que no tiene ninguna cuenta ni ningun coche asociado.
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
	
	public boolean eliminarConfiguracion(ConfiguracionImpl configuracion);
	
	public boolean actualizarConfiguracion(ConfiguracionImpl configuracion);
	
	//-------------------------------------------------------
	
	//CuentaImpl
	
	/* INTERFAZ
	 * Comentario: Obtiene una Cuenta a partir de su nombre de usuario buscando en la base de datos, la contraseña está cifrada con MD5.
	 * Prototipo: public CuentaImpl obtenerCuenta(String nombreUsuario)
	 * Entrada: Un String con el nombre de usuario de la cuenta a buscar
	 * Precondiciones: No hay
	 * Salida: Una CuentaImpl con la cuenta perteneciente a ese nombre de usuario.
	 * Postcondiciones: Asociado al nombre devuelve una CuentaImpl perteneciente al nombre de usuario dado, busca en la base de datos.
	 * 					Si el nombre de usuario no existe, devuelve una CuentaImpl null.
	 */
	public CuentaImpl obtenerCuenta(String nombreUsuario)
	{
		CuentaImpl cuenta = null;
		
		String consulta = "SELECT Contraseña FROM Cuentas "
						+ "WHERE NombreUsuario = '" + nombreUsuario + "'";
		try 
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
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
	
	public void cargarConfiguracionesEnCuenta(CuentaImpl cuenta)
	{
		ArrayList<ConfiguracionImpl> configuraciones = obtenerConfiguraciones(cuenta);
		
		cuenta.establecerConfiguraciones(configuraciones);
	}
	
	public void cargarVotacionesEnCuenta(CuentaImpl cuenta)
	{
		ArrayList<VotacionImpl> votaciones = obtenerVotaciones(cuenta);
		
		cuenta.establecerVotaciones(votaciones);
	}
	
	public void cargarRelacionesEnCuenta(CuentaImpl cuenta)
	{
		cargarConfiguracionesEnCuenta(cuenta);
		cargarVotacionesEnCuenta(cuenta);
	}
	
	/* INTERFAZ
	 * Comentario: Inserta una nueva cuenta en la base de datos
	 * Prototipo: public boolean insertarCuenta(CuentaImpl cuenta) throws SQLServerException
	 * Entrada: la CuentaImpl que se desea insertar en la base de datos
	 * Precondiciones: No hay
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
	 * Precondiciones: No hay
	 * Salida: la PiezaImpl con el ID dado, busca en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve una PiezaImpl perteneciente a la pieza con el ID dado buscando en la base de datos.
	 * 					- Este método no debe utilizarse para obtener piezas especializadas, es decir, motores, llantas o pinturas. 
	 * 						Este método no obtiene la información extra que tienen estas piezas especializadas, solo la parte común a todas las piezas.
	 * 						Para las piezas especializadas, usar los métodos obtenerPiezaPintura, obtenerPiezaLlantas y obtenerPiezaMotor...
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
	
	public void cargarCochesValidosEnPieza(PiezaImpl pieza)
	{
		ArrayList<CocheImpl> cochesValidos = obtenerCochesValidos(pieza);
		
		pieza.establecerCochesValidos(cochesValidos);
	}
	
	/* INTERFAZ
	 * Comentario: Inserta una nueva pieza en la base de datos
	 * Prototipo: public boolean insertarPieza(PiezaImpl pieza) throws SQLServerException
	 * Entrada: la PiezaImpl que se desea insertar en la base de datos
	 * Precondiciones: No hay
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
	
	/* INTERFAZ
	 * Comentario: Inserta un nuevo motor en la base de datos
	 * Prototipo: public boolean insertarPiezaMotor(MotorImpl motor) throws SQLServerException
	 * Entrada: el MotorImpl que se desea insertar en la base de datos
	 * Precondiciones: No hay
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
	 * Comentario: Obtiene la pieza de tipo pintura según un ID dado. Busca en la base de datos
	 * Prototipo: public PinturaImpl obtenerPiezaPintura(int ID)
	 * Entrada: Un int con la ID de la pieza tipo pintura que se desea obtener de la base de datos.
	 * Precondiciones: No hay
	 * Salida: Una PinturaImpl que pertenece a la pintura con la ID dada en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve devuelve una PinturaImpl.
	 * 					- Si la ID dada pertenece a una pintura existente en la base de datos, devuelve el objeto con la información.
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
	 * Comentario: Obtiene la pieza de tipo llantas según un ID dado. Busca en la base de datos
	 * Prototipo: public LlantasImpl obtenerPiezaLlantas(int ID)
	 * Entrada: Un int con la ID de la pieza tipo llantas que se desea obtener de la base de datos.
	 * Precondiciones: No hay
	 * Salida: Una LlantasImpl que pertenece a las llantas con la ID dada en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve devuelve un objeto LlantasImpl.
	 * 					- Si la ID dada pertenece a unas llantas existentes en la base de datos, devuelve el objeto con la información.
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
	 * Comentario: Obtiene la pieza de tipo motor según un ID dado. Busca en la base de datos
	 * Prototipo: public MotorImpl obtenerPiezaMotor(int ID)
	 * Entrada: Un int con la ID de la pieza tipo motor que se desea obtener de la base de datos.
	 * Precondiciones: No hay
	 * Salida: Una MotorImpl que pertenece al motor con la ID dada en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve devuelve un objeto MotorImpl.
	 * 					- Si la ID dada pertenece a un motor existente en la base de datos, devuelve el objeto con la información.
	 * 					- Si la ID dada no pertenece a un motor existente en la base de datos, devuelve el objeto null.
	 */
	public MotorImpl obtenerPiezaMotor(int ID)
	{
		{
			MotorImpl motor = null;
			
			String consulta = "SELECT Nombre, Descripcion, Precio, Traccion, NumeroVelocidades, Autonomia, Potencia, Tipo FROM Motores AS M "
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
		
		ArrayList<VotacionImpl> votaciones = aObj.obtenerVotaciones(conf);º
		
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
				System.out.println("Configuracion ya existente en la base de datos, no se insertará.");
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
				System.out.println("Cuenta ya exitente en la base de datos, no se insertará.");
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
			System.out.println("Pieza ya exitente en la base de datos, no se insertará.");
		}
		
		MotorImpl motor = new MotorImpl(10, "Motor", "Un motor", 2300, 'T', 6, 300, 160, "D");
		
		try 
		{
			System.out.println(aObj.insertarPiezaMotor(motor));
		} catch (SQLServerException e) 
		{
			if(e.getErrorCode() == 2627)
			{
				System.out.println("Pieza ya existente en la base de datos, no se insertará.");
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
	// Tengo que ver como se harán las actualizaciones, en el caso de la configuracion,
	// No hay nada que actualizar salvo las piezas, quizás sería buena idea
	// Ponerle métodos a la clase para añadir/quitar piezas. Y luego
	// Un método en la clase de gestión para actualizar solo las piezas, en lugar de todo.
	//
	// Otra solución sería que las funcionalidades de añadir/quitar piezas lo hagan directamente
	// A la base de datos en lugar de cambiarlo primero en el objeto y luego con ese objeto actualizar
	// La base de datos.

	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(CuentaImpl cuenta);

	public ArrayList<VotacionImpl> obtenerVotaciones(CuentaImpl cuenta);

	public boolean insertarCuenta(CuentaImpl cuenta);

	public boolean eliminarCuenta(CuentaImpl cuenta)
	// Debe de haber dos formas de borrar una cuenta:
	// Borrando tambien sus configuraciones y votaciones, o borrando solo la cuenta sin borrar lo demas.

	public boolean actualizarCuenta(CuentaImpl cuenta)
	// Es solo cambiar contraseña.

	public ArrayList<CocheImpl> obtenerCochesValidos(PiezaImpl pieza);

	public boolean insertarPieza(PiezaImpl pieza);

	public boolean eliminarPieza(PiezaImpl pieza)
	// Al borrar una pieza, se deben borrar las configuraciones que usan dicha pieza

	public boolean actualizarPieza(PiezaImpl pieza);
 */
