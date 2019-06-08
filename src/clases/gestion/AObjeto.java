package clases.gestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.sound.midi.Soundbank;

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
			
			resultado.next();		//Salta a la primera y unica fila
			
			double precioBase = resultado.getDouble("PrecioBase");
			
			coche = new CocheImpl(marca, modelo, precioBase);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return coche;
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
	public boolean eliminarCoche(CocheImpl coche);
	
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
			
			resultado.next();		//Salta a la primera y unica fila
			
			String fechaStr = resultado.getString("Fecha");
			
			GregorianCalendar fecha = utils.dateTimeToGregorianCalendar(fechaStr);
			
			configuracion = new ConfiguracionImpl(ID, fecha);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return configuracion;
	}
	
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
			
			resultado.next();		//Salta a la primera y unica fila
			
			String marca = resultado.getString("MarcaCoche");
			String modelo = resultado.getString("ModeloCoche");
			double precioBase = resultado.getDouble("PrecioBase");
			
			coche = new CocheImpl(marca, modelo, precioBase);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return coche;
	}
	
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
			
			resultado.next();		//Salta a la primera y unica fila
			
			String nombreUsuario = resultado.getString("NombreUsuario");
			String contrasena = resultado.getString("Contraseña");
			
			cuenta = new CuentaImpl(nombreUsuario, contrasena);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return cuenta;
	}
	
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
	
	public ArrayList<VotacionImpl> obtenerVotaciones(ConfiguracionImpl configuracion)
	{
		ArrayList<VotacionImpl> votaciones = null;
		
		String consulta = "SELECT ID, Calificacion, Fecha FROM Votaciones " 
						+ "WHERE IDConfiguracion = '" + configuracion.getID() + "'";
		
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
	
	public void cargarCocheEnConfiguracion(ConfiguracionImpl configuracion)
	{
		CocheImpl coche = obtenerCoche(configuracion);
		
		configuracion.establecerCoche(coche);
	}
	
	public void cargarCuentaEnConfiguracion(ConfiguracionImpl configuracion)
	{
		CuentaImpl cuenta = obtenerCuenta(configuracion);
		
		configuracion.establecerCuenta(cuenta);
	}
	
	public void cargarPiezasEnConfiguracion(ConfiguracionImpl configuracion)
	{
		ArrayList<PiezaImpl> piezas = obtenerPiezas(configuracion);
		
		configuracion.establecerPiezas(piezas);
	}
	
	public void cargarVotacionesEnConfiguracion(ConfiguracionImpl configuracion)
	{
		ArrayList<VotacionImpl> votaciones = obtenerVotaciones(configuracion);
		
		configuracion.establecerVotaciones(votaciones);
	}
	
	public void cargarRelacionesEnConfiguracion(ConfiguracionImpl configuracion)
	{
		cargarCocheEnConfiguracion(configuracion);
		cargarCuentaEnConfiguracion(configuracion);
		cargarPiezasEnConfiguracion(configuracion);
		cargarVotacionesEnConfiguracion(configuracion);
	}
	
	public boolean insertarConfiguracion(ConfiguracionImpl configuracion);
	public boolean eliminarConfiguracion(ConfiguracionImpl configuracion);
	public boolean actualizarConfiguracion(ConfiguracionImpl configuracion);
	
	//-------------------------------------------------------
	
	//CuentaImpl
	
	public CuentaImpl obtenerCuenta(String nombreUsuario)
	{
		CuentaImpl cuenta = null;
		
		String consulta = "SELECT Contraseña FROM Cuentas "
						+ "WHERE NombreUsuario = '" + nombreUsuario + "'";
		try 
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			resultado.next();		//Salta a la primera y unica fila
			
			String contrasena = resultado.getString("Contraseña");
			
			cuenta = new CuentaImpl(nombreUsuario, contrasena);
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return cuenta;
	}
	
	public ArrayList<ConfiguracionImpl> obtenerConfiguraciones(CuentaImpl cuenta);
	public ArrayList<VotacionImpl> obtenerVotaciones(CuentaImpl cuenta);
	
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
	
	public boolean insertarCuenta(CuentaImpl cuenta);
	public boolean eliminarCuenta(CuentaImpl cuenta);
	public boolean actualizarCuenta(CuentaImpl cuenta);
	
	//-------------------------------------------------------
	
	//PiezaImpl
	
	public PiezaImpl obtenerPieza(int ID)
	{
		PiezaImpl pieza = null;
		
		String consulta = "SELECT Nombre, Descripcion, Precio FROM Piezas "
						+ "WHERE ID = " + ID;
		
		try
		{
			Statement statement = conexion.createStatement();
			
			ResultSet resultado = statement.executeQuery(consulta);
			
			resultado.next();
			
			String nombre = resultado.getString("Nombre");
			String descripcion = resultado.getString("Descripcion");
			double precio = resultado.getDouble("Precio");
			
			pieza = new PiezaImpl(ID, nombre, descripcion,precio);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return pieza;
	}
	
	public ArrayList<CocheImpl> obtenerCochesValidos(PiezaImpl pieza);
	
	public void cargarCochesValidosEnPieza(PiezaImpl pieza)
	{
		ArrayList<CocheImpl> cochesValidos = obtenerCochesValidos(pieza);
		
		pieza.establecerCochesValidos(cochesValidos);
	}
	
	public boolean insertarPieza(PiezaImpl pieza);
	public boolean eliminarPieza(PiezaImpl pieza);
	public boolean actualizarPieza(PiezaImpl pieza);
	
	//-------------------------------------------------------
	
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
			
			resultado.next();
			
			String nombre = resultado.getString("Nombre");
			String descripcion = resultado.getString("Descripcion");
			double precio = resultado.getDouble("Precio");
			String color = resultado.getString("Color");
			String acabado = resultado.getString("Acabado");
			
			pintura = new PinturaImpl(ID, nombre, descripcion,precio, color, acabado);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return pintura;
	}
	
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
			
			resultado.next();
			
			String nombre = resultado.getString("Nombre");
			String descripcion = resultado.getString("Descripcion");
			double precio = resultado.getDouble("Precio");
			int pulgadas = resultado.getInt("Pulgadas");
			
			llantas = new LlantasImpl(ID, nombre, descripcion, precio, pulgadas);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return llantas;
	}
	
	public MotorImpl obtenerPiezaMotor(int ID)
	{
		{
			MotorImpl motor = null;
			
			String consulta = "SELECT Nombre, Descripcion, Precio, Traccion, NumeroVelocidades, Autonomia, Potencia FROM Motores AS M "
							+ "INNER JOIN Piezas AS Pz ON pz.ID = M.IDPieza "
							+ "WHERE pz.ID = " + ID;
			
			try
			{
				Statement statement = conexion.createStatement();
				
				ResultSet resultado = statement.executeQuery(consulta);
				
				resultado.next();
				
				String nombre = resultado.getString("Nombre");
				String descripcion = resultado.getString("Descripcion");
				double precio = resultado.getDouble("Precio");
				char traccion = resultado.getString("Traccion").charAt(0);
				int numeroVelocidades = resultado.getInt("NumeroVelocidades");
				int autonomia = resultado.getInt("Autonomia");
				int potencia = resultado.getInt("Potencia");
				
				motor = new MotorImpl(ID, nombre, descripcion, precio, traccion, numeroVelocidades, autonomia, potencia);
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
			return motor;
		}
	}
	
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
		}
	}
	
}
