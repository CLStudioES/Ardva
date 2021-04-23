package arduinoRXTX;

import java.util.Vector;
public class GestorSensores {
			// Atributos
			private Vector<Sensor> sensores;
			GestorSensores() {
				this.sensores = new Vector<Sensor>();
				/* Inicializar el vector de sensores metiendo los
				sensores definidos en la configuración inicial */
				//Sensor t1 = new Sensor('2', '0', "ºC", "Temperatura");
				//Sensor h1 = new Sensor('3', '0', "%", "Humedad");
				
				
				//addSensor(t1);
				//addSensor(h1);
				
			}
			
			public void addSensor (Sensor sensor) {
				this.sensores.add(sensor);
			}
			
			public void actualizarValor (char tipo, char id, int valor) {
				// buscar en el vector sensores el que corresponda (tipo, id)
				boolean encontrado = false;
				int i = 0;
				while(!encontrado) {
					if ( sensores.get(i).getTipo() == tipo && sensores.get(i).getId() == id ) {
						encontrado = true;
						sensores.get(i).setValor(valor);
					}
				i++;
				}
			}
}