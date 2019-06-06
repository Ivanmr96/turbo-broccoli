package tests;

import clases.basicas.MotorImpl;

public class MotorImplTest {

	public static void main(String[] args) 
	{
		MotorImpl porDefecto = new MotorImpl();
		MotorImpl conParametros = new MotorImpl(3, "30 TFSI 6 vel.", "Motor de inyección estratificada de combustible", 0, 'D', 6, 200, 116);
		MotorImpl deCopia = new MotorImpl(conParametros);
		
		System.out.println("conParametros.getTraccion(): " + conParametros.getTraccion());
		
		System.out.println("deCopia.getNumeroVelocidades(): " + deCopia.getNumeroVelocidades());
		
		System.out.println("porDefecto.getPotencia(): " + porDefecto.getPotencia());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setTraccion('T')");
		
		System.out.println("ANTES -> " + porDefecto.getTraccion());
		
		porDefecto.setTraccion('T');
		
		System.out.println("DESPUES -> " + porDefecto.getTraccion());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setNumeroVelocidades(5)");
		
		System.out.println("ANTES -> " + porDefecto.getNumeroVelocidades());
		
		porDefecto.setNumeroVelocidades(5);
		
		System.out.println("DESPUES -> " + porDefecto.getNumeroVelocidades());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setPotencia(95)");
		
		System.out.println("ANTES -> " + porDefecto.getPotencia());
		
		porDefecto.setPotencia(95);
		
		System.out.println("DESPUES -> " + porDefecto.getPotencia());
	}

}
