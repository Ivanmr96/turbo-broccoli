package tests;

import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import clases.basicas.ConfiguracionImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.VotacionImpl;
import clases.gestion.AObjeto;

public class GestionTest {

	public static void main(String[] args) 
	{
		String URLConexion = "jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;";
		
		AObjeto gestion = new AObjeto(URLConexion);
		
		gestion.abrirConexion();
		
		System.out.println("obtenerCoche(\"AUDI\", \"A1\"): " + gestion.obtenerCoche("AUDI", "A1"));
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("configuracion = obtenerConfiguracion(\"8722F525-3C36-4A79-B6CA-7DAB14BB23BF\");");
		
		ConfiguracionImpl configuracion = gestion.obtenerConfiguracion("8722F525-3C36-4A79-B6CA-7DAB14BB23BF");
		
		System.out.println("cargarRelacionesEnConfiguracion(configuracion);");
		
		gestion.cargarRelacionesEnConfiguracion(configuracion);
		
		System.out.println();
		
		System.out.println("configuracion.toString(): " + configuracion.toString());
		
		System.out.println();
		
		System.out.println("obtenerCuenta(configuracion): " + gestion.obtenerCuenta(configuracion).toString());
		
		System.out.println();
		
		System.out.println("obtenerCoche(configuracion): " + gestion.obtenerCoche(configuracion).toString());
		
		System.out.println();
		
		System.out.println("obtenerPiezasExtra(configuracion):");
		for(PiezaImpl pieza:gestion.obtenerPiezasExtra(configuracion))
		{
			System.out.println(pieza.toString());
		}
		
		System.out.println();
		
		System.out.println("obtenerVotaciones(configuracion):");
		for(VotacionImpl votacion:gestion.obtenerVotaciones(configuracion))
		{
			System.out.println(votacion.toString());
		}
		
		System.out.println();
		
		System.out.println("obtenerPiezaPintura(configuracion): " + gestion.obtenerPiezaPintura(configuracion).toString());
		
		System.out.println();
		
		System.out.println("obtenerPiezaLlantas(configuracion): " + gestion.obtenerPiezaLlantas(configuracion).toString());
		
		System.out.println();
		
		System.out.println("obtenerPiezaMotor(configuracion): " + gestion.obtenerPiezaMotor(configuracion).toString());
		
		System.out.println("Precio total: " + configuracion.obtenerPrecioTotal());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("gestion.obtenerCuenta(\"testuser\"): " + gestion.obtenerCuenta("testuser").toString());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("obtenerPieza(1): " + gestion.obtenerPieza(1).toString());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("obtenerPiezaPintura(1): " + gestion.obtenerPiezaPintura(1).toString());
		
		System.out.println("obtenerPiezaLlantas(2): " + gestion.obtenerPiezaLlantas(2).toString());
		
		System.out.println("obtenerPiezaMotor(3): " + gestion.obtenerPiezaMotor(3).toString());
		
		System.out.println();
		
		//configuracion.establecerPintura(gestion.obtenerPiezaPintura(4));
		
		//System.out.println(gestion.actualizarPinturaDeConfiguracion(configuracion));
		
		//System.out.println(gestion.eliminarPiezasExtraDeConfiguracion(configuracion));
		
		System.out.println(gestion.eliminarConfiguracion(configuracion));
	}

}
