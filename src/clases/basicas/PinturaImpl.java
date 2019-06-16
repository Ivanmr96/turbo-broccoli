package clases.basicas;

/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades básicas:
 * 						-> color: String, consultable, modificable
 * 						-> acabado: String, consultable, modificable
 * 
 * Propiedades derivadas: No hay
 * Propiedades compartidas: No hay
 * 
 */

/* INTERFAZ
 * 
 * public String getColor();
 * public String getAcabado();
 * 
 * public void setColor(String color);
 * public void setAcabado(String acabado);
 */

//TODO Javadoc
public class PinturaImpl extends PiezaImpl
{
	private String color;
	private String acabado;
	
	public PinturaImpl()
	{
		super();
		this.color = "";
		this.acabado = "";
	}
	
	public PinturaImpl(int ID, String nombre, String descripcion, double precio, String color, String acabado)
	{
		super(ID, nombre, descripcion, precio);
		this.color = color;
		this.acabado = acabado;
	}
	
	public PinturaImpl(PinturaImpl otra)
	{
		super(otra);
		this.color = otra.color;
		this.acabado = otra.acabado;
	}
	
	public String getColor() { return this.color; }
	public String getAcabado() { return this.acabado; }
	
	public void setColor(String color) { this.color = color; }
	public void setAcabado(String acabado) { this.acabado = acabado; }
	
	@Override
	public String toString()
	{
		return this.getID() + " - " + this.getNombre() + " - " + this.getColor() + " " + this.getAcabado() + " - " + this.getPrecio() + " €";
	}
	
	//TODO clone, hashcode, compareTo, equals
}
