package tests;

import clases.basicas.MotorImpl;

public class MotorImplTest {

	public static void main(String[] args) 
	{
		MotorImpl porDefecto = new MotorImpl();
		MotorImpl conParametros = new MotorImpl(3, "30 TFSI 6 vel.", "Motor de inyecci�n estratificada de combustible", 0, 'D', 6, 200, 116, "G");
		MotorImpl deCopia = new MotorImpl(conParametros);
		
		System.out.println("conParametros.getTraccion(): " + conParametros.getTraccion());
		
		System.out.println("deCopia.getNumeroVelocidades(): " + deCopia.getNumeroVelocidades());
		
		System.out.println("conParametros.getAutonomia(): " + conParametros.getAutonomia());
		
		System.out.println("porDefecto.getPotencia(): " + porDefecto.getPotencia());
		
		System.out.println("deCopia.getTipo(): " + deCopia.getTipo());
		
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
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("conParametros.setAutonomia(750)");
		
		System.out.println("ANTES -> " + conParametros.getAutonomia());
		
		conParametros.setAutonomia(750);
		
		System.out.println("DESPUES -> " + conParametros.getAutonomia());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("conParametros.setTipo(\"E\")");
		
		System.out.println("ANTES -> " + conParametros.getTipo());
		
		conParametros.setTipo("E");
		
		System.out.println("DESPUES -> " + conParametros.getTipo());
	}

}
