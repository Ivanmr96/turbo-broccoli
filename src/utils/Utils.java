package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.GregorianCalendar;

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
	
	public static void main(String[] args)
	{
		Utils utils = new Utils();
		System.out.println(utils.obtenerMD5("123"));
	}
}
