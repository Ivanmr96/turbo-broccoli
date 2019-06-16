package clases.basicas;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> pulgadas: int, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 * 
 */

/* INTERFAZ
 * 
 * public int getPulgadas();
 * 
 * public void setPulgadas(int pulgadas);
 */

//TODO Javadoc
public class LlantasImpl extends PiezaImpl
{
	private int pulgadas;
	
	public LlantasImpl()
	{
		super();
		this.pulgadas = 0;
	}
	
	public LlantasImpl(int ID, String nombre, String descripcion, double precio, int pulgadas)
	{
		super(ID, nombre, descripcion, precio);
		this.pulgadas = pulgadas;
	}
	
	public LlantasImpl(LlantasImpl otras)
	{
		 super(otras);
		 this.pulgadas = otras.pulgadas;
	}
	
	public int getPulgadas() { return this.pulgadas; }
	
	public void setPulgadas(int pulgadas) { this.pulgadas = pulgadas; }
	
	@Override
	public String toString()
	{
		return this.getID() + " - " + this.getNombre() + " - " + this.pulgadas + "\"" + " - " + this.getPrecio() + " €";
	}
	
	//TODO clone, hashcode, compareTo, equals
}
