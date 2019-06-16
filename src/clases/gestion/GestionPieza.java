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
import clases.basicas.LlantasImpl;
import clases.basicas.MotorImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.PinturaImpl;

//TODO Javadoc
public class GestionPieza 
{
	private Connection conexion;
	
	public GestionPieza(Connection conexion)
	{
		this.conexion = conexion;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene una pieza dado su ID, busca en la base de datos
	 * Prototipo: public PiezaImpl obtenerPieza(int ID)
	 * Entrada: Un int con la ID de la pieza a buscar
	 * Precondiciones: La conexion tiene que estar abierta
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
						+ "WHERE ID = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setInt(1, ID);
			
			ResultSet resultado = statement.executeQuery();
			
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
	 * Comentario: Obtiene la pieza de tipo llantas según un ID dado. Busca en la base de datos
	 * Prototipo: public LlantasImpl obtenerPiezaLlantas(int ID)
	 * Entrada: Un int con la ID de la pieza tipo llantas que se desea obtener de la base de datos.
	 * Precondiciones: La conexion tiene que estar abierta.
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
						+ "WHERE pz.ID = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setInt(1, ID);
			
			ResultSet resultado = statement.executeQuery();
			
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
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Salida: Una MotorImpl que pertenece al motor con la ID dada en la base de datos.
	 * Postcondiciones: Asociado al nombre devuelve devuelve un objeto MotorImpl.
	 * 					- Si la ID dada pertenece a un motor existente en la base de datos, devuelve el objeto con la información.
	 * 					- Si la ID dada no pertenece a un motor existente en la base de datos, devuelve el objeto null.
	 */
	public MotorImpl obtenerPiezaMotor(int ID)
	{
		MotorImpl motor = null;
		
		String consulta = "SELECT Nombre, Descripcion, Precio, Traccion, NumeroVelocidades, Autonomia, Potencia, M.Tipo FROM Motores AS M "
						+ "INNER JOIN Piezas AS Pz ON pz.ID = M.IDPieza "
						+ "WHERE pz.ID = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setInt(1, ID);
			
			ResultSet resultado = statement.executeQuery();
			
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
	 * Comentario: Obtiene la pieza de tipo pintura según un ID dado. Busca en la base de datos
	 * Prototipo: public PinturaImpl obtenerPiezaPintura(int ID)
	 * Entrada: Un int con la ID de la pieza tipo pintura que se desea obtener de la base de datos.
	 * Precondiciones: La conexion tiene que estar abierta.
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
						+ "WHERE pz.ID = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setInt(1, ID);
			
			ResultSet resultado = statement.executeQuery();
			
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
						+ "WHERE confi.ID = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, configuracion.getID());
			
			ResultSet resultado = statement.executeQuery();
			
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
						+ "WHERE confi.ID = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, configuracion.getID());
			
			ResultSet resultado = statement.executeQuery();
			
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
						+ "WHERE confi.ID = ?";
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, configuracion.getID());
			
			ResultSet resultado = statement.executeQuery();
			
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
	 * Comentario: Obtiene la lista de las piezas extra de una configuracion. Busca en la base de datos
	 * Prototipo: public ArrayList<PiezaImpl> obtenerPiezasExtra(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl de la que se desea obtener la lista de sus piezas extra
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList de PiezaImpl con la lista de las piezas extra la configuracion dada.
	 * Postcondiciones: Asociado al nombe devuelve un ArrayList<PiezaImpl>.
	 * 					- Si la configuracion existe en la base de datos, la lista tendrá las piezas extra de dicha configuracion, es posible que la lista esté vacía.
	 * 						Significa que la configuración no tiene ninguna pieza extra.
	 * 					- Si la configuración no existe en la base de datos, el ArrayList<PiezaImpl> será null.
	 */
	public ArrayList<PiezaImpl> obtenerPiezasExtra(ConfiguracionImpl configuracion)
	{
		ArrayList<PiezaImpl> piezasExtra = null;
		GestionPieza gestionPieza = new GestionPieza(conexion);
		
		String consulta = "SELECT pz.ID, Nombre, Descripcion, Precio, Tipo FROM Configuraciones AS conf " 
				+ "INNER JOIN PiezasConfiguracionCoche AS PzConf ON PzConf.IDConfiguracion = conf.ID "
				+ "INNER JOIN Piezas AS pz ON pz.ID = PzConf.IDPieza "
				+ "WHERE conf.ID = ? ";
				//+ "AND Tipo NOT IN ('motor', 'llantas', 'pintura') OR Tipo IS NULL";
		
		PiezaImpl pieza;
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, configuracion.getID());
			
			ResultSet resultado = statement.executeQuery();
			
			piezasExtra = new ArrayList<PiezaImpl>();
			
			while(resultado.next())
			{
				int ID = resultado.getInt("ID");
				
				pieza = gestionPieza.obtenerPieza(ID);
				
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
	 * Comentario: Obtiene la lista de las piezas de una configuracion. Busca en la base de datos
	 * Prototipo: public ArrayList<PiezaImpl> obtenerPiezas(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl de la que se desea obtener la lista de sus piezas
	 * Precondiciones: La conexion tiene que estar abierta
	 * Salida: Un ArrayList de PiezaImpl con la lista de las piezas la configuracion dada.
	 * Postcondiciones: Asociado al nombe devuelve un ArrayList<PiezaImpl>.
	 * 					- Si la configuracion existe en la base de datos, la lista tendrá las piezas de dicha configuracion, es posible que la lista esté vacía.
	 * 						Significa que la configuración no tiene ninguna pieza.
	 * 					- Si la configuración no existe en la base de datos, el ArrayList<PiezaImpl> será null.
	 */
	public ArrayList<PiezaImpl> obtenerPiezas(ConfiguracionImpl configuracion)
	{
		ArrayList<PiezaImpl> piezas = null;
		GestionPieza gestionPieza = new GestionPieza(conexion);
		
		String consulta = "SELECT pz.ID, Nombre, Descripcion, Precio, Tipo FROM Configuraciones AS conf " 
						+ "INNER JOIN PiezasConfiguracionCoche AS PzConf ON PzConf.IDConfiguracion = conf.ID "
						+ "INNER JOIN Piezas AS pz ON pz.ID = PzConf.IDPieza "
						+ "WHERE conf.ID = ?";
		
		PiezaImpl pieza;
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, configuracion.getID());
			
			ResultSet resultado = statement.executeQuery();
			
			piezas = new ArrayList<PiezaImpl>();
			
			while(resultado.next())
			{
				String tipo = resultado.getString("Tipo");
				int ID = resultado.getInt("ID");
				
				if(tipo == null)		//Si el campo tipo se dejo a NULL, la instancia será de tipo PiezaImpl
					pieza = gestionPieza.obtenerPieza(ID);
				else
				{
					switch(tipo)		//Si el campo tiene algun valor, sera del tipo indicado si coincide con algún subtipo, si es distinto, será de tipo PiezaImpl (default)
					{
						case "motor":
							pieza = gestionPieza.obtenerPiezaMotor(ID);
							break;
						case "pintura":
							pieza = gestionPieza.obtenerPiezaPintura(ID);
							break;
						case "llantas":
							pieza = gestionPieza.obtenerPiezaLlantas(ID);
							break;
						default:
							pieza = gestionPieza.obtenerPieza(ID);
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
	 * Comentario: Busca las piezas validas de un coche en la base de datos
	 * Prototipo: public ArrayList<PiezaImpl> obtenerPiezasValidas(CocheImpl coche
	 * Entrada: Un objeto CocheImpl con el coche del que se desean obtener sus piezas validas
	 * Precondiciones: La conexion con la base de datos debe estar abierta.
	 * Salida: Un ArrayList de PiezaImpl con las piezas validas del coche
	 * Postcondciones: Asociado al nombre devuelve un ArrayList de PiezaImpl con las piezas validas del coche.
	 * 					- Si, aunque el coche exista en la base de datos, este no tiene ninguna pieza valida, la lista estará vacía.
	 * 					- Si el coche no existe en la base de datos, devuelve null
	 * 					- Si el coche existe y tiene piezas validas, devuelve una lista con las piezas validas.
	 */
	/**
	 * Busca las piezas validas de un coche en la base de datos. <br>
	 * <b>Precondiciones: </b>La conexion con la base de datos debe estar abierta.
	 * @param coche El coche del que se desean obtener sus piezas válidas.
	 * @return Las piezas válidas del coche. <br>
	 * - Si, aunque el coche exista en la base de datos, este no tienen ninguna pieza válida, la lista estará vacía. <br>
	 * - Si el coche no existe en la base de datos, devuelve null. <br>
	 * - Si el coche existe y tiene piezas válidas, devuelve una lista con las piezas válidas.
	 */
	public ArrayList<PiezaImpl> obtenerPiezasValidas(CocheImpl coche)
	{
		ArrayList<PiezaImpl> piezasValidas = null;
		
		String consulta = "SELECT ID, Nombre, Descripcion, Precio, Tipo FROM Piezas AS pz " 
				+ "INNER JOIN PiezasCoches AS pzco ON pzco.IDPieza = pz.ID "
				+ "WHERE pzco.MarcaCoche = ? AND pzco.ModeloCoche = ?";
		
		PiezaImpl pieza;
		
		try
		{
			PreparedStatement statement = conexion.prepareStatement(consulta);
			
			statement.setString(1, coche.getMarca());
			statement.setString(2, coche.getModelo());
			
			ResultSet resultado = statement.executeQuery();
			
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
		
		String insert = "INSERT INTO Piezas "
						+ "(ID, Nombre, Descripcion, Precio) "
						+ "VALUES (?, ?, ?, ?);";
		try 
		{
			PreparedStatement statement = conexion.prepareStatement(insert);
			
			statement.setInt(1, pieza.getID());
			statement.setString(2, pieza.getNombre());
			statement.setString(3, pieza.getDescripcion());
			statement.setDouble(4, pieza.getPrecio());
			
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
		
		String insertPieza = "INSERT INTO Piezas "
							+ "VALUES (?, ?, ?, ?, ?);";
		
		String insertMotor = "INSERT INTO Motores "
							+ "(IDPieza, Traccion, NumeroVelocidades, Autonomia, Potencia, Tipo) "
							+ "VALUES (?, ?, ?, ?, ?, ?);";
		try 
		{
			Statement statement = conexion.createStatement();
			PreparedStatement statementPieza = conexion.prepareStatement(insertPieza);
			PreparedStatement statementMotor = conexion.prepareStatement(insertMotor);
			
			statementPieza.setInt(1, ID);
			statementPieza.setString(2, motor.getNombre());
			statementPieza.setString(3, motor.getDescripcion());
			statementPieza.setDouble(4, motor.getPrecio());
			statementPieza.setString(5, "motor");
			
			statementMotor.setInt(1, ID);
			statementMotor.setString(2, Character.toString(motor.getTraccion()));
			statementMotor.setInt(3, motor.getNumeroVelocidades());
			statementMotor.setInt(4, motor.getAutonomia());
			statementMotor.setInt(5, motor.getPotencia());
			statementMotor.setString(6, motor.getTipo());
			
			statement.execute("BEGIN TRAN");
			
			int filasAfectadas = statementPieza.executeUpdate();
			
			filasAfectadas += statementMotor.executeUpdate();
			
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
	
	/* INTERFAZ
	 * Comentario: Carga en un objeto PiezaImpl la lista de coches validos.
	 * Prototipo: public void cargarCochesValidosEnPieza(PiezaImpl pieza)
	 * Entrada: No hay
	 * Precondiciones: La conexion tiene que estar abierta.
	 * Entrada/Salida: Una PiezaImpl a la que se le desea cargar la lista de coches validos buscando en la base de datos.
	 * Salida: No hay
	 * Postcondiciones: El Objeto PiezaImpl de la lista de parámetros tiene cargada la lista de coches validos según la base de datos.
	 */
	public void cargarCochesValidosEnPieza(PiezaImpl pieza)
	{
		GestionCoche gestionCoche = new GestionCoche(conexion);
		
		ArrayList<CocheImpl> cochesValidos = gestionCoche.obtenerCochesValidos(pieza);
		
		pieza.establecerCochesValidos(cochesValidos);
	}
	
	//TODO public boolean actualizarPieza(PiezaImpl pieza);
	
	//TODO public boolean eliminarPieza(PiezaImpl pieza);
}
