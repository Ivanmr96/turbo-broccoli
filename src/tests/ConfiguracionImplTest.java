package tests;

import java.util.GregorianCalendar;

import clases.basicas.ConfiguracionImpl;

public class ConfiguracionImplTest {

	public static void main(String[] args) 
	{
		ConfiguracionImpl porDefecto = new ConfiguracionImpl();
		ConfiguracionImpl conParametros = new ConfiguracionImpl("EF49FC93-2EEF-4F9A-BBBD-B4CF232D17A9", new GregorianCalendar());
		ConfiguracionImpl deCopia = new ConfiguracionImpl(conParametros);
		
		System.out.println("conParametros.getID(): " + conParametros.getID());
		
		System.out.println("conParametros.getFecha().getTime(): " + conParametros.getFecha().getTime());

		System.out.println("deCopia.getFecha().getTime(): " + deCopia.getFecha().getTime());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setFecha(new GregorianCalendar(2010, 5, 3))");
		
		try
		{
			System.out.println("ANTES -> " + porDefecto.getFecha().getTime());
		}
		catch(NullPointerException e)
		{
			System.out.println("ANTES -> NULL");
		}
		
		porDefecto.setFecha(new GregorianCalendar(2010, 5, 3));
		
		System.out.println("DESPUES -> " + porDefecto.getFecha().getTime());
	}

}
