package clases.gestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import clases.basicas.CocheImpl;
import clases.basicas.PiezaImpl;

public class AObjeto 
{
	private String URLConexion = "jdbc:sqlserver://localhost;"
								  + "database=Coches;"
								  + "user=usuarioCoches;"
								  + "password=123;";
	
	public CocheImpl obtenerCoche(String marca, String modelo)
	{
		CocheImpl coche = null;
		ResultSet resultSet = null;
		
		String consulta = "SELECT Marca, Modelo, PrecioBase FROM Coches "
							+ "WHERE Marca = '" + marca +"' AND Modelo = '" + modelo +"'";
		
		try(Connection conexion = DriverManager.getConnection(URLConexion);
			Statement statement = conexion.createStatement();)
		{
			resultSet = statement.executeQuery(consulta);
			
			resultSet.next(); //Para entrar en la primera y unica fila
			
			coche = new CocheImpl(marca, modelo, resultSet.getDouble(3));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return coche;
	}
	
	//TODO Instanciar las piezas con el tipo adecuado para cada caso.
	public ArrayList<PiezaImpl> obtenerPiezasValidas(CocheImpl coche)
	{
		ArrayList<PiezaImpl> piezasValidas = new ArrayList<PiezaImpl>();
		
		ResultSet resultSet = null;
		
		String consulta = "SELECT ID, Nombre, Descripcion, Precio FROM Piezas AS P " + 
						"INNER JOIN PiezasCoches AS PC ON PC.IDPieza = P.ID " + 
						"WHERE PC.MarcaCoche = '" + coche.getMarca() + "' AND PC.ModeloCoche = '" + coche.getModelo() +"'";
		
		try(Connection conexion = DriverManager.getConnection(URLConexion);
			Statement statement = conexion.createStatement();)
		{
			resultSet = statement.executeQuery(consulta);
			
			int ID;
			String nombre;
			String descripcion;
			double precio;
			
			while(resultSet.next()) //Para entrar en la primera y unica fila
			{
			
				ID = resultSet.getInt(1);
				nombre = resultSet.getString(2);
				descripcion = resultSet.getString(3);
				precio = resultSet.getDouble(4);
				
				//PiezaImpl pieza = new PiezaImpl(ID, nombre, descripcion, precio);
				
				piezasValidas.add(new PiezaImpl(ID, nombre, descripcion, precio));
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
		return piezasValidas;
	}
	
	public static void main(String[] args)
	{
		AObjeto aObj = new AObjeto();
		
		CocheImpl coche = aObj.obtenerCoche("AUDI", "A1");
		
		System.out.println(coche.getPrecioBase());
		
		System.out.println();
		
		ArrayList<PiezaImpl> piezas = aObj.obtenerPiezasValidas(coche);
		
		for(PiezaImpl pieza:piezas)
		{
			System.out.println(pieza.getNombre());
		}
	}
}
