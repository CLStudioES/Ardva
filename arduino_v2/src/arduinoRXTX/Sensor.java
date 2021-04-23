package arduinoRXTX;
//Clase para modelizar un sensor cualquira
public class Sensor {
	//Atributos
	private char tipo;
	private char id;
	private int valor;
	private String unidad; // Por ejemplo ºC
	private String medida; // Por ejemplo temperatura
	private String descripcion; // Por ejemplo Salón
	//Constructor 1
		public Sensor(char tipo, char id, String unidad, String medida, String descripcion) {
			this.tipo = tipo;
			this.id = id;
			this.unidad = unidad;
			this.medida = medida;
			this.descripcion = descripcion;
			this.valor = 0;
		}
	//Constructor 2
	public Sensor(char tipo, char id, String unidad, String medida, String descripcion, int valor) {
		this.tipo = tipo;
		this.id = id;
		this.unidad = unidad;
		this.medida = medida;
		this.descripcion = descripcion;
		this.valor = valor;
	}
	//Métodos
	public char getTipo() {
		return tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	public char getId() {
		return id;
	}
	public void setId(char id) {
		this.id = id;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public String getMedida() {
		return medida;
	}
	public void setMedida(String medida) {
		this.medida = medida;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}