package tests;

import java.util.GregorianCalendar;

import clases.basicas.VotacionImpl;

public class VotacionImplTest {

	public static void main(String[] args) 
	{
		VotacionImpl porDefecto = new VotacionImpl();
		VotacionImpl conParametros = new VotacionImpl("5b653596-80d8-42f0-9dca-ead2f4980172", new GregorianCalendar(), 8);
		VotacionImpl deCopia = new VotacionImpl(conParametros);
		
		System.out.println("conParametros.getID(): " + conParametros.getID());
		
		System.out.println("deCopia.getFecha().getTime(): " + deCopia.getFecha().getTime());
		
		System.out.println("porDefecto.getCalificacion(): " + porDefecto.getCalificacion());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setFecha(new GregorianCalendar(2017, 6, 6))");
		
		try
		{
			System.out.println("ANTES -> " + porDefecto.getFecha().getTime());
		}
		catch(NullPointerException e)
		{
			System.out.println("ANTES -> NULL");
		}
		
		porDefecto.setFecha(new GregorianCalendar(2017, 6, 7));
		
		System.out.println("DESPUES -> " + porDefecto.getFecha().getTime());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("deCopia.setCalificacion(3)");
		
		System.out.println("ANTES -> " + deCopia.getCalificacion());
		
		deCopia.setCalificacion(3);
		
		System.out.println("DESPUES -> " + deCopia.getCalificacion());
	}

}
