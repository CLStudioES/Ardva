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
		
		private static SerialPortEventListener listener = new SerialPortEventListener() {
	        @Override
	        public void serialEvent(SerialPortEvent spe) {
	            try {
	                if (ino.isMessageAvailable()) {
	                	textField_1.setText(ino.printMessage());
	                }
	            }
	            catch (SerialPortException | ArduinoException ex) {
	                Logger.getLogger(JavaRX.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    };

		private JPanel contentPane;
		private JTextField textField;
		private static JTextField textField_1;
		private JTextField txtHumedad;
		private JTextField textField_2;

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			try {
	            ino.arduinoRXTX("COM3", 9600, listener);
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
		public WindowPrincipal() {
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
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
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
						WindowManual frame = new WindowManual();
						frame.setVisible(true); 
			            ino.sendData("0000")  ;
			            
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnManual.setBounds(254, 94, 100, 39);
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
	