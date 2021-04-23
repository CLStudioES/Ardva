package arduinoRXTX;



import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import java.util.logging.Level;
import java.util.logging.Logger;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;



public class JavaRXTX  {
    private PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    private SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if (ino.isMessageAvailable()) {
                                    
                    //Se imprime el mensaje recibido en la consola
                    System.out.println(ino.printMessage());
                    
                    //String lectura = ino.printMessage();
                    String lectura ="200925#300931#210925#310918#50094376#51093782#52093935#90190#90290";
                    System.out.println(lectura);
                    String registros[] = lectura.split("#");
                    
                    for (int i=0; i<registros.length;i++){
                    	//registros[i].charAt(0);
                    	
                    	//Sensor(char tipo, char id, String unidad, String medida)
                    	// Sensor tmp = new Sensor('2', '0', "ºC", "Temperatura");
                    	char tipo = registros[i].charAt(0);
                    	String descripcion="";
                    	if ( registros[i].charAt(1) == '0'){
                    		descripcion = "Salon";
                    	}
                    	if ( registros[i].charAt(1) == '1' ){
                    		descripcion = "Dormitorio";
                    	}
                    	char id = registros[i].charAt(2);
                    	
                    	String medida;
                    	String unidad;
                    	if ( tipo == '2'){
                    		medida = "Temperatura";
                    		unidad = "ºC";
                    	}else if( tipo == '3'){
                    		medida = "Humedad";
                    		unidad = "ºC";
                    	}else if ( tipo == '5'){
                    		medida = "LUZ";
                    		unidad = "W";
                    	}else{//El 9??
                    		medida = "??";
                    		unidad = "??";
                    	}
                    	
                    
                    	System.out.println(i+" : "+tipo+","+id+","+unidad+","+medida+","+descripcion);
                    	
                    	
                    	int valor= Integer.parseInt(registros[i].substring(4));
                    	Sensor tmp = new Sensor (tipo,id,unidad,medida,descripcion,valor);
                    	//float valor = Float.parseFloat(registros[i].substring(4));
                    	System.out.println(i+" : "+valor);
                    	
                    	
                    }
                    
                    
                    
                }
            } catch (SerialPortException | ArduinoException ex) {
                Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    public JavaRXTX() {
        
        try {
            ino.arduinoRXTX("COM4", 9600, listener);
        } catch (ArduinoException ex) {
            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
}