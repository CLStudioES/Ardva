import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;


/**
 * Ejemplo sobre recepción de datos desde Java hasta Arduino
 *
 * @author Antony García González
 */

public class JavaRX {

    //Se crea una instancia de la librería PanamaHitek_Arduino
    private static PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    private static final SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if (ino.isMessageAvailable()) {
                	String lectura = ino.printMessage();
                	String registros[] = lectura.split("#");
                	
                	for (int i=1; i<registros.length; i++){
                		//registros[i].charAt(0);		//Accede al primer caracter del string [i] del vector registros
                		float valor = Float.parseFloat(registros[i].substring(4));
                		System.out.println(i + " : " + valor);
                	}
                	
                    //Se imprime el mensaje recibido en la consola
                    System.out.println(lectura);
                }
            } catch (SerialPortException | ArduinoException ex) {
                Logger.getLogger(JavaRX.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    };

    public static void main(String[] args) {
        try {
            ino.arduinoRX("COM3", 9600, listener);
        } catch (ArduinoException | SerialPortException ex) {
            Logger.getLogger(JavaRX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
