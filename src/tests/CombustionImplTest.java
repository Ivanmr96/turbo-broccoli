package tests;

import clases.basicas.CombustionImpl;

public class CombustionImplTest {

	public static void main(String[] args) 
	{
		CombustionImpl porDefecto = new CombustionImpl();
		CombustionImpl conParametros = new CombustionImpl(3, "30 TFSI 6 vel.", "Motor de inyección estratificada de combustible", 0, 'D', 6, 220, 116, 'G', 5.8 );
	}

}
