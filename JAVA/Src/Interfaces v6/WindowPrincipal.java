	import java.awt.BorderLayout;
	import java.awt.EventQueue;

	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.border.EmptyBorder;

	import com.panamahitek.ArduinoException;
	import com.panamahitek.PanamaHitek_Arduino;

	import jssc.SerialPortException;

	import javax.swing.JButton;
	import javax.swing.JLabel;
	import java.awt.event.ActionListener;
	import java.util.logging.Level;
	import java.util.logging.Logger;
	import java.awt.event.ActionEvent;

	import jssc.SerialPortEvent;
	import jssc.SerialPortEventListener;
	import jssc.SerialPortException;
	import javax.swing.JTextField;
	import java.awt.Label;
	import javax.swing.JTextArea;
	import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JToggleButton;
import java.awt.Toolkit;
import java.awt.Window.Type;



	public class WindowPrincipal extends JFrame {
		
		private static PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
		private static WindowManual frameManual;
		
		private JPanel contentPane;
		private JTextField textField;
		private static JTextField txtTemperatura;
		private static JTextField txtHumedad;
		private static JTextField txtLuz;
		private static JTextField txtTemperatura2;
		private static JTextField txtHumedad2;
		private static JTextField txtLuz2;
		
		
		private SerialPortEventListener listener = new SerialPortEventListener() {
	        @Override
	        public void serialEvent(SerialPortEvent spe) {
	            try {
	                if (ino.isMessageAvailable()) {
	                	//textField_1.setText(ino.printMessage()); 
	                
	                	//Se imprime el mensaje recibido en la consola
	                    //System.out.println(ino.printMessage());
	                    
	                    //String lectura = ino.printMessage(); //linea a descomentar
	                    String lectura ="200925#300931#210925#310918#50094376#51093782#52093935#90190#90290#";
	                    System.out.println(lectura);
	                    String registros[] = lectura.split("#");
	                    
	                    for (int i=0; i<registros.length;i++){
	                    	//registros[i].charAt(0);
	                    	
	                    	
	                    	String valor= registros[i].substring(4);
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
	                    	
	                    	
	                   //temperatura 	
	                    	if ( tipo == '2'){
	                    		medida = "Temperatura";
	                    		unidad = "ºC";
	                    			if(descripcion== "Salon" ){
	                    					txtTemperatura.setText(valor+unidad);
	                    		                    		
	                    					if(frameManual != null)
	                    						frameManual.actualizarTemperatura(valor+unidad);}
	                    			
	                    				else{
	                    					txtTemperatura2.setText(valor+unidad);
	                    					
	                    					if(frameManual != null)
	                    						frameManual.actualizarTemperatura2(valor+unidad);
	                    					
	                    				}
	                    					
	                    		}
	                    		
	                  //humedad  	
	                    else if( tipo == '3'){
	                    		medida = "Humedad";
	                    		unidad = "%";
	                    		
	                    		
	                    			if(descripcion== "Salon"){
	                    					txtHumedad.setText(valor+unidad);
	                    		
	                    					if(frameManual != null)
	                    						frameManual.actualizarHumedad(valor+unidad);
	                    		
	                    			}else{
	                    				txtHumedad2.setText(valor+unidad);
	                    				
	                    				if(frameManual != null)
                    						frameManual.actualizarHumedad2(valor+unidad);
	                    			}
	                    		
	                    		}

	                    //luz
	                    
	                    else if ( tipo == '5'){
	                    		medida = "LUZ";
	                    		unidad = "W";
	                    		
	                    		if(descripcion== "Salon"){
	                    			txtLuz.setText(valor+unidad);
		                    		
		                    		if(frameManual != null)
		                    			frameManual.actualizarLuz(valor+unidad);
	                    		}else{
	                    			txtLuz2.setText(valor+unidad);
		                    		
		                    		if(frameManual != null)
		                    			frameManual.actualizarLuz2(valor+unidad);
	                    		}
	                    		
	                    		
	                    		
	                    }
	                    		
	                    		
	                    
	                    		
	                    		
	                    		
	                    		
	                    	else  {//dfEl 9??
	                    		medida = "??";
	                    		unidad = "??";
	                    			} 
	                    	
	                    	
	                    	
	                    	
	                
	                    }
	                }
	            }
	            
	            catch (SerialPortException | ArduinoException ex) {
	                Logger.getLogger(JavaRX.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    };

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			WindowPrincipal w = new WindowPrincipal();
			
			try {
	            w.ino.arduinoRXTX("COM3", 9600, w.listener);
	        } 
			catch (ArduinoException ex) {
	            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
	        }
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						WindowPrincipal frame = new WindowPrincipal();
						frame.setVisible(true);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		/**
		 * Create the frame.
		 */
		public WindowPrincipal() 
		{
			frameManual = null;
			
			setAlwaysOnTop(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("Sistema Domotico\r\n");
			setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\UEM\\Desktop\\casa.jpg"));
			setBounds(100, 100, 882, 552);
			contentPane = new JPanel();
			contentPane.setForeground(Color.ORANGE);
			contentPane.setBackground(new Color(255, 255, 255));
			contentPane.setBorder(null);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JButton btnAutomatico = new JButton("Automatico");
			btnAutomatico.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
			btnAutomatico.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
			            ino.sendData("0001");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnAutomatico.setBounds(432, 77, 121, 39);
			contentPane.add(btnAutomatico);
			
			JButton btnManual = new JButton("Manual");
			btnManual.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
			btnManual.setForeground(Color.BLACK);
			btnManual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
						frameManual = new WindowManual(ino);
						frameManual.setVisible(true); 
						
							try {
					            ino.sendData("0000");
					        }
							catch (ArduinoException | SerialPortException ex) {
					            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
					        }
						}
				
			});
			btnManual.setBounds(185, 77, 100, 39);
			contentPane.add(btnManual);
			 
			JButton btnTemperatura = new JButton("Consumos");
			btnTemperatura.setFont(new Font("Berlin Sans FB", Font.PLAIN, 16));
			btnTemperatura.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						
						Consumos frame = new Consumos();
						frame.setVisible(true);
			            ino.sendData("2001");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnTemperatura.setBounds(376, 413, 144, 38);
			contentPane.add(btnTemperatura);
			
			JLabel lblModoDeFuncionamiento = new JLabel("Modo de Funcionamiento");
			lblModoDeFuncionamiento.setForeground(new Color(220, 20, 60));
			lblModoDeFuncionamiento.setFont(new Font("Corbel", Font.PLAIN, 24));
			lblModoDeFuncionamiento.setBounds(239, 25, 272, 40);
			contentPane.add(lblModoDeFuncionamiento);
			
			JLabel lblSensores = new JLabel("Informaci\u00F3n Sensores");
			lblSensores.setForeground(new Color(220, 20, 60));
			lblSensores.setFont(new Font("Corbel", Font.PLAIN, 24));
			lblSensores.setBounds(264, 146, 228, 52);
			contentPane.add(lblSensores);
			
			txtTemperatura = new JTextField();
			txtTemperatura.setBounds(291, 243, 63, 20);
			contentPane.add(txtTemperatura);
			txtTemperatura.setColumns(10);
			
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
			lblTempe.setBounds(153, 244, 100, 20);
			contentPane.add(lblTempe);
			
			JLabel lblNewLabel_2 = new JLabel("Humedad:");
			lblNewLabel_2.setForeground(new Color(220, 20, 60));
			lblNewLabel_2.setFont(new Font("Corbel", Font.PLAIN, 15));
			lblNewLabel_2.setBounds(169, 277, 100, 16);
			contentPane.add(lblNewLabel_2);
			
			JLabel lblNewLabel_3 = new JLabel("Consumo: ");
			lblNewLabel_3.setForeground(new Color(220, 20, 60));
			lblNewLabel_3.setFont(new Font("Corbel", Font.PLAIN, 15));
			lblNewLabel_3.setBounds(169, 306, 74, 16);
			contentPane.add(lblNewLabel_3);
			
			txtHumedad = new JTextField();
			txtHumedad.setText("\r\n");
			txtHumedad.setBounds(291, 273, 63, 22);
			contentPane.add(txtHumedad);
			txtHumedad.setColumns(10);
			
			txtLuz = new JTextField();
			txtLuz.setBounds(291, 302, 63, 22);
			contentPane.add(txtLuz);
			txtLuz.setColumns(10);
			
			txtTemperatura2 = new JTextField();
			txtTemperatura2.setBounds(417, 242, 63, 22);
			contentPane.add(txtTemperatura2);
			txtTemperatura2.setColumns(10);
			
			txtHumedad2 = new JTextField();
			txtHumedad2.setBounds(417, 273, 63, 22);
			contentPane.add(txtHumedad2);
			txtHumedad2.setColumns(10);
			
			txtLuz2 = new JTextField();
			txtLuz2.setBounds(417, 302, 63, 22);
			contentPane.add(txtLuz2);
			txtLuz2.setColumns(10);
			
			JLabel lblSalon = new JLabel("Sal\u00F3n");
			lblSalon.setFont(new Font("Corbel", Font.PLAIN, 18));
			lblSalon.setForeground(new Color(220, 20, 60));
			lblSalon.setBounds(432, 211, 56, 16);
			contentPane.add(lblSalon);
			
			JLabel lblNewLabel_4 = new JLabel("Dormitorio\r\n");
			lblNewLabel_4.setFont(new Font("Corbel", Font.PLAIN, 18));
			lblNewLabel_4.setForeground(new Color(220, 20, 60));
			lblNewLabel_4.setBounds(274, 211, 90, 16);
			contentPane.add(lblNewLabel_4);
			
			JButton btnNewButton = new JButton("Activar Alarma");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
			            ino.sendData("0021");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnNewButton.setBounds(666, 241, 139, 39);
			contentPane.add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("Desactivar Alarma");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
			            ino.sendData("0020");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnNewButton_1.setBounds(666, 301, 139, 39);
			contentPane.add(btnNewButton_1);
			
			
		}
		}
	