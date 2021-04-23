package ventanas;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import jssc.SerialPortException;


import javax.swing.JButton;
import javax.swing.JLabel;


import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Toolkit;


import arduinoRXTX.Sensor;
import baseDeDatos.ConnectionPool;

public class VentanaPrincipal extends JFrame {
	private PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    private SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if (ino.isMessageAvailable()) {
                                    
                    //Se imprime el mensaje recibido en la consola
                    System.out.println(ino.printMessage());
                    
                    String lectura = ino.printMessage();
                    //String lectura ="200925#300931#210925#310918#50094376#51093782#52093935#90190#90290";
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
                    	
                    	ConnectionPool conn = new ConnectionPool();
                		conn.MySQLConnection();
                		conn.insertDataSensores(id,tipo,unidad,valor,descripcion);
                    	
                    	//float valor = Float.parseFloat(registros[i].substring(4));
                    	System.out.println(i+" : "+valor);
                     	
                    }                  
                }
            } catch (SerialPortException | ArduinoException ex) {
                //Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            	System.out.println(ex);
            }
        }
    };
    
    
  
   
	private JPanel contentPane;
	private JTextField textField;
	private static JTextField textField_1;
	private JTextField txtHumedad;
	private JTextField textField_2;

	
	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		
		try {
            ino.arduinoRXTX("COM4", 9600, listener);
        } catch (ArduinoException ex) {
            //Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAutoRequestFocus(false);
		setTitle("Sistema Domotico\r\n");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\UEM\\Desktop\\casa.jpg"));
		setBounds(100, 100, 882, 552);
		contentPane = new JPanel();
		contentPane.setForeground(Color.ORANGE);
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAutomatico = new JButton("Automatico");
		btnAutomatico.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		btnAutomatico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
		            ino.sendData("0111");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            //Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
					System.out.println(ex);
		        }
			}
		});
		btnAutomatico.setBounds(467, 94, 121, 39);
		contentPane.add(btnAutomatico);
		
		JButton btnManual = new JButton("Manual");
		btnManual.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		btnManual.setForeground(Color.BLACK);
		btnManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GestionManual frame = new GestionManual();
					frame.setVisible(true); 
		            ino.sendData("0000")  ;
		            
		        }
				catch (ArduinoException | SerialPortException ex) {
		           // Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		            System.out.println(ex);
		        }
			}
		});
		btnManual.setBounds(254, 94, 100, 39);
		contentPane.add(btnManual);
		
		JButton btnTemperatura = new JButton("Consumos");
		btnTemperatura.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
		/*btnTemperatura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					Consumos frame = new Consumos();
					frame.setVisible(true);
		            ino.sendData("2001");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});*/
		btnTemperatura.setBounds(361, 353, 144, 38);
		contentPane.add(btnTemperatura);
		
		JLabel lblModoDeFuncionamiento = new JLabel("Modo de Funcionamiento");
		lblModoDeFuncionamiento.setForeground(new Color(220, 20, 60));
		lblModoDeFuncionamiento.setFont(new Font("Corbel", Font.PLAIN, 24));
		lblModoDeFuncionamiento.setBounds(280, 41, 272, 40);
		contentPane.add(lblModoDeFuncionamiento);
		
		JLabel lblSensores = new JLabel("Informaci\u00F3n Sensores");
		lblSensores.setForeground(new Color(220, 20, 60));
		lblSensores.setFont(new Font("Corbel", Font.PLAIN, 24));
		lblSensores.setBounds(292, 145, 228, 52);
		contentPane.add(lblSensores);
		
		textField_1 = new JTextField();
		textField_1.setBounds(344, 197, 114, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		//textField_1.setText(tmp);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\UEM\\Desktop\\Sin t\u00EDtulo-1.jpg"));
		lblNewLabel.setBounds(29, 373, 272, 96);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\UEM\\Desktop\\casa.jpg"));
		lblNewLabel_1.setBounds(715, 25, 90, 96);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblTempe = new JLabel("Temperatura: ");
		lblTempe.setForeground(new Color(220, 20, 60));
		lblTempe.setFont(new Font("Corbel", Font.PLAIN, 15));
		lblTempe.setBounds(232, 198, 100, 20);
		contentPane.add(lblTempe);
		
		JLabel lblNewLabel_2 = new JLabel("Humedad:");
		lblNewLabel_2.setForeground(new Color(220, 20, 60));
		lblNewLabel_2.setFont(new Font("Corbel", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(254, 235, 100, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Estado: ");
		lblNewLabel_3.setForeground(new Color(220, 20, 60));
		lblNewLabel_3.setFont(new Font("Corbel", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(264, 274, 56, 16);
		contentPane.add(lblNewLabel_3);
		
		txtHumedad = new JTextField();
		txtHumedad.setText("\r\n");
		txtHumedad.setBounds(344, 231, 116, 22);
		contentPane.add(txtHumedad);
		txtHumedad.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(344, 270, 114, 22);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnGuardarDatos = new JButton("Guardar datos");
		btnGuardarDatos.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		btnGuardarDatos.setBounds(564, 236, 127, 39);
		contentPane.add(btnGuardarDatos);
		
		
	}

		
	}