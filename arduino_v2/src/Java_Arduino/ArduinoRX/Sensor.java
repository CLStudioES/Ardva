package Java_Arduino.ArduinoRX;
//Clase para modelizar un sensor cualquira
public class Sensor {
	//Atributos
	private char tipo;
	private char id;
	private float valor;
	private String unidad; // Por ejemplo ºC
	private String medida; // Por ejemplo temperatura
	//Constructor
	Sensor(char tipo, char id, String unidad, String medida) {
		this.tipo = tipo;
		this.id = id;
		this.unidad = unidad;
		this.medida = medida;
		this.valor = 0;
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
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
}