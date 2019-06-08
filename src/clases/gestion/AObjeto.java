package clases.gestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import clases.basicas.CocheImpl;
import clases.basicas.CombustionImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.CuentaImpl;
import clases.basicas.LlantasImpl;
import clases.basicas.MotorImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.PinturaImpl;
import clases.basicas.VotacionImpl;

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
	public ArrayList<PiezaImpl> obtenerPiezasValidas(CocheImpl coche);
	
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
	 * 					- False. El coche no se ha introducido correctamente en la base de datos. (Puede que ya exista)
	 */
	public boolean insertarCoche(CocheImpl coche);
	
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
	public boolean actualizarCoche(CocheImpl coche);
	
	//-------------------------------------------------------
	
	//ConfiguracionImpl
	
	public ConfiguracionImpl obtenerConfiguracion(String ID);
	
	public CocheImpl obtenerCoche(ConfiguracionImpl configuracion);
	public CuentaImpl obtenerCuenta(ConfiguracionImpl configuracion);
	public ArrayList<PiezaImpl> obtenerPiezas(ConfiguracionImpl configuracion);
	public ArrayList<VotacionImpl> obtenerVotaciones(ConfiguracionImpl configuracion);
	
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
	
	public CuentaImpl obtenerCuenta(String nombreUsuario);
	
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
	
	public PiezaImpl obtenerPieza(int ID);
	
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
	
	public PinturaImpl obtenerPiezaPintura(int ID);
	
	public LlantasImpl obtenerPiezaLlantas(int ID);
	
	public MotorImpl obtenerPiezaMotor(int ID);
	
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
		
		CocheImpl coche = aObj.obtenerCoche("Mercedes", "Clase A");
		
		System.out.println(coche.getPrecioBase());
	}
	
}
