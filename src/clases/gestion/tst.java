package clases.gestion;

import java.util.ArrayList;

import clases.basicas.CocheImpl;
import clases.basicas.PiezaImpl;

public class tst {

	public static void main(String[] args) 
	{
		AObjeto gestion = new AObjeto("jdbc:sqlserver://localhost;"
				  + "database=Coches;"
				  + "user=usuarioCoches;"
				  + "password=123;");
		
		gestion.abrirConexion();
		
		CocheImpl coche = gestion.obtenerCoche("AUDI", "A1");
		
		gestion.cargarPiezasValidasEnCoche(coche);
		
		ArrayList<PiezaImpl> piezasValidas = coche.obtenerPiezasValidas();
		
		System.out.println(coche.getPrecioBase());
		
		System.out.println();
		
		for(PiezaImpl piezaValida:piezasValidas)
		{
			System.out.println(piezaValida.getNombre());
		}
		
		System.out.println();
		
		for(PiezaImpl piezaExtraValida:coche.obtenerPiezasExtraValidas())
		{
			System.out.println(piezaExtraValida.getNombre());
		}
		
		System.out.println();
		
		for(PiezaImpl pinturaValida:coche.obtenerPinturasValidas()) System.out.println(pinturaValida.getNombre());
		
		System.out.println();
		
		for(PiezaImpl llantasValida:coche.obtenerLlantasValidas()) System.out.println(llantasValida.getNombre());
				
		System.out.println();
				
		for(PiezaImpl motorValido:coche.obtenerMotoresValidos()) System.out.println(motorValido.getNombre());
	}

}
