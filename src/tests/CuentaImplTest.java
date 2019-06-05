package tests;

import clases.basicas.CuentaImpl;

public class CuentaImplTest {

	public static void main(String[] args) 
	{
		CuentaImpl porDefecto = new CuentaImpl();
		CuentaImpl conParametros = new CuentaImpl("test", "a4046dd703ca71e0b38fafcd4f15e094");
		CuentaImpl deCopia = new CuentaImpl(conParametros);
		
		System.out.println("conParametros.getNombreUsuario(): " + conParametros.getNombreUsuario());
		System.out.println("deCopia.getContrasena(): " + deCopia.getContrasena());
		
		System.out.println("-------------------------------------------------------");
		
		System.out.println("porDefecto.setContrasena(\"108ea74f4f4b7e9cbf66ae06d9707a05\")");
		
		System.out.println("ANTES -> " + porDefecto.getContrasena());
		
		porDefecto.setContrasena("108ea74f4f4b7e9cbf66ae06d9707a05");
		
		System.out.println("DESPUES -> " + porDefecto.getContrasena());
	}

}
