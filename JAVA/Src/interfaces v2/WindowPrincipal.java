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
		private JTextField textField_3;
		private JTextField textField_4;
		private JTextField textField_5;

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
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
			
			textField_1 = new JTextField();
			textField_1.setBounds(291, 243, 63, 20);
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
			lblTempe.setBounds(153, 244, 100, 20);
			contentPane.add(lblTempe);
			
			JLabel lblNewLabel_2 = new JLabel("Humedad:");
			lblNewLabel_2.setForeground(new Color(220, 20, 60));
			lblNewLabel_2.setFont(new Font("Corbel", Font.PLAIN, 15));
			lblNewLabel_2.setBounds(169, 277, 100, 16);
			contentPane.add(lblNewLabel_2);
			
			JLabel lblNewLabel_3 = new JLabel("Estado: ");
			lblNewLabel_3.setForeground(new Color(220, 20, 60));
			lblNewLabel_3.setFont(new Font("Corbel", Font.PLAIN, 15));
			lblNewLabel_3.setBounds(187, 306, 56, 16);
			contentPane.add(lblNewLabel_3);
			
			txtHumedad = new JTextField();
			txtHumedad.setText("\r\n");
			txtHumedad.setBounds(291, 273, 63, 22);
			contentPane.add(txtHumedad);
			txtHumedad.setColumns(10);
			
			textField_2 = new JTextField();
			textField_2.setBounds(291, 302, 63, 22);
			contentPane.add(textField_2);
			textField_2.setColumns(10);
			
			textField_3 = new JTextField();
			textField_3.setBounds(417, 242, 63, 22);
			contentPane.add(textField_3);
			textField_3.setColumns(10);
			
			textField_4 = new JTextField();
			textField_4.setBounds(417, 273, 63, 22);
			contentPane.add(textField_4);
			textField_4.setColumns(10);
			
			textField_5 = new JTextField();
			textField_5.setBounds(417, 302, 63, 22);
			contentPane.add(textField_5);
			textField_5.setColumns(10);
			
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
	