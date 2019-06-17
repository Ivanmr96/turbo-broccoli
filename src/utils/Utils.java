package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import clases.basicas.CocheImpl;
import clases.basicas.ConfiguracionImpl;
import clases.basicas.LlantasImpl;
import clases.basicas.MotorImpl;
import clases.basicas.PiezaImpl;
import clases.basicas.PinturaImpl;

//TODO Javadoc
public class Utils 
{
	/* INTERFAZ
	 * Comentario: Obtiene un objeto GregorianCalendar a partir de un String con un tipo datetime de SQL
	 * Prototipo: public GregorianCalendar dateTimeToGregorianCalendar(String datetime)
	 * Entrada: un String con un datetime de SQL
	 * Precondiciones: El String debe tener un formato de datetime de SQL. (ejemplo: 2019-06-08 21:06:20.280) (ver: https://docs.microsoft.com/es-es/sql/t-sql/data-types/datetime-transact-sql?view=sql-server-2017)
	 * Salida: Un GregorianCalendar que representa la fecha del datetime introducido
	 * Postcondiciones: Asociado al nombre devuelve un GregorianCalendar que representa la fecha del string con formato datetime de SQL introducido por parametro.
	 */
	public GregorianCalendar dateTimeToGregorianCalendar(String datetime)
	{
		GregorianCalendar fecha;
		
		String[] campos = datetime.split(" ");
		
		String[] camposFecha = campos[0].split("-");
		
		String[] camposHora = campos[1].split(":");
		
		int anho = Integer.parseInt(camposFecha[0]);
		int mes = Integer.parseInt(camposFecha[1]);
		int dia = Integer.parseInt(camposFecha[2]);
		
		int hora = Integer.parseInt(camposHora[0]);
		int minutos = Integer.parseInt(camposHora[1]);
		int segundos = (int)Double.parseDouble(camposHora[2]);
		
		fecha = new GregorianCalendar(anho, mes, dia, hora, minutos, segundos);
		
		return fecha;
	}
	
	/* INTERFAZ
	 * Comentario: Convierte la fecha de un objeto GregorianCalendar en un string con el formato DateTime de SQL.
	 * Prototipo: public String GregorianCalendarToDateTime(GregorianCalendar fecha)
	 * Entrada: Un objeto GregorianCalendar con la fecha que se desea convertir a un string con el formate DateTime.
	 * Precondiciones: No hay
	 * Salida: Un String con la fecha del objeto GregorianCalendar dado en el formato DateTime de SQL.
	 * Postcondiciones: Asociado al nombre devuelve un String cojn la fecha del objeto GregorianCalendar dado, en el formato DateTime de SQL.
	 */
	public String GregorianCalendarToDateTime(GregorianCalendar fecha)
	{
		String dateTime;
		
		int anho = fecha.get(GregorianCalendar.YEAR);
		int mes = fecha.get(GregorianCalendar.MONTH);
		int dia = fecha.get(GregorianCalendar.DAY_OF_MONTH);
		
		int hora = fecha.get(GregorianCalendar.HOUR_OF_DAY);
		int minutos = fecha.get(GregorianCalendar.MINUTE);
		int segundos = fecha.get(GregorianCalendar.SECOND);
		
		dateTime = anho + "-" + dia + "-" + (mes+1) + " " + hora + ":" + minutos + ":" + segundos;
		
		return dateTime;
	}
	
	/* INTERFAZ
	 * Comentario: Obtiene el codigo hash MD5 de un String
	 * Prototipo: public String obtenerMD5(String str)
	 * Entrada: Un String para obtener su MD5
	 * Precondiciones: No hay
	 * Salida: Un String con el codigo hash MD5 del String de entrada.
	 * Postcondiciones: Asociado al nombre devuelve un String con el codigo hash MD5 del String de entrada.
	 */
	public String obtenerMD5(String str) 
	{
		String md5 = null;
		
		try 
		{ 
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestBytes = md.digest(str.getBytes());
			BigInteger numero = new BigInteger(1, digestBytes);
			md5 = numero.toString(16);
			 
			while (md5.length() < 32)
			{
				md5 = "0" + md5;	
			}
		}
		catch (NoSuchAlgorithmException e) 
		{
			throw new RuntimeException(e);
		}
		
		return md5;
	}
	
	/* INTERFAZ
	 * Comentario: Analiza una cadena para determinar si es un número o no.
	 * Prototipo: public boolean esNumero(String caracteres)
	 * Entrada: Un String con la cadena a analizar.
	 * Precondiciones: No hay.
	 * Salida: Un boolean indicando si la cadena es un número o no.
	 * Postcondiciones: Asociado al nombre devuelve:
	 * 					- True si la cadena dada es un número.
	 * 					- False si la cadena dada no es un número.
	 */
	public boolean esNumero(String caracteres)
	{
		boolean esNumero;
		
		try
		{
			Integer.parseInt(caracteres);
			
			esNumero = true;
		}
		catch(NumberFormatException e)
		{
			esNumero = false;
		}
		
		return esNumero;
	}
	
	/* INTERFAZ
	 * Comentario: Formatea una fecha dada (GregorianCalendar) con el formato "dia/mes/año hora:minutos:segundos"
	 * Prototipo: public String formatearFecha(GregorianCalendar fecha)
	 * Entrada: Un objeto GregorianCalendar con la fecha que se desea formatear
	 * Precondiciones: No hay
	 * Salida: Un String con la fecha dada formateada.
	 * Postcondiciones: Asociado al nombre devuelve un String con la fecha formatea con el formato "dia/mes/año hora:minutos:segundos"
	 */
	public String formatearFecha(GregorianCalendar fecha)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		return sdf.format(fecha.getTime());
	}
	
	/* INTERFAZ
	 * Comeantario: Imprime en pantalla de forma detallada una configuración, muestra:
	 * 				- ID
	 * 				- Fecha
	 * 				- Modelo
	 * 				- Precio total
	 * 				- Piezas (motor, llantas, pintura y piezas extra si tiene)
	 * 				- Calificación media
	 * Prototipo: public void mostrarConfiguracion(ConfiguracionImpl configuracion)
	 * Entrada: Una ConfiguracionImpl la cual se desea imprimir en pantalla de forma detallada.
	 * Precondiciones: La ConfiguracionImpl debe tener sus relaciones cargadas, es decir, al menos el coche no puede ser null (las otras relaciones no son obligatorias)
	 * Salida: No hay
	 * Postcondiciones: No hay. Solo imprime en pantalla la configuración de forma detallada, mostrando:
	 * 				- ID
	 * 				- Fecha
	 * 				- Modelo
	 * 				- Precio total
	 * 				- Piezas (motor, llantas, pintura y piezas extra si tiene)
	 * 				- Calificación media 				
	 */
	public void mostrarConfiguracion(ConfiguracionImpl configuracion)
	{
		CocheImpl coche = configuracion.obtenerCoche();
		MotorImpl motor = configuracion.obtenerMotor();
		LlantasImpl llantas = configuracion.obtenerLlantas();
		PinturaImpl pintura = configuracion.obtenerPintura();
		double calificacionMedia = configuracion.calificacionMedia();
		ArrayList<PiezaImpl> piezasExtra = configuracion.obtenerPiezas();
		
		System.out.println("--------------------------------------------------------------");
		
		System.out.println("ID: " + configuracion.getID());
		
		System.out.println("Fecha: " + formatearFecha(configuracion.getFecha()));
		
		System.out.println();
		
		System.out.println("Modelo: " + coche.getMarca() + " " + coche.getModelo());
		
		System.out.println();
		
		System.out.println("Precio total: " + configuracion.obtenerPrecioTotal());
		
		System.out.println();
		
		if(motor != null)
			System.out.println("Motor: " + motor.getNombre() + " - " + motor.getPotencia() + "cv - " + motor.getPrecio() + "€");
		else
			System.out.println("Motor: Aun no tiene!");
		
		if(llantas != null)
			System.out.println("Llantas: " + llantas.getNombre() + " - "  + llantas.getPulgadas() + "\" " + llantas.getPrecio() + "€");
		else
			System.out.println("Llantas: Aun no tiene!");
		
		if(pintura != null)
			System.out.println("Pintura: " + pintura.getNombre() + " - " + pintura.getAcabado() + " - " + pintura.getPrecio() + "€");
		else
			System.out.println("Pintura: Aun no tiene!");
		
		System.out.println();
		
		if(piezasExtra.size() > 0)
		{
			System.out.println("Piezas extra:");
			for(PiezaImpl piezaExtra:piezasExtra)
			{
				System.out.println(" -> " + piezaExtra.getNombre() + " - " + piezaExtra.getPrecio() + "€");
			}
		}
		else
			System.out.println("Piezas extra: No tiene ninguna pieza extra!");
		
		System.out.println();
		
		if(calificacionMedia >= 0)
			System.out.println("Calificacion media: " + configuracion.calificacionMedia() + " puntos");
		else
			System.out.println("Calificacion media: Aun no hay ninguna votacion!");
		
		System.out.println("--------------------------------------------------------------");
	}
}
