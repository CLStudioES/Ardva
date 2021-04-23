

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
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.JTabbedPane;
import java.awt.Window.Type;



	public class WindowManual extends JFrame {
		
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
		private JTextField textField_2;
		private JTextField textField_3;

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
						WindowManual frame = new WindowManual();
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
		public WindowManual() {
			setType(Type.UTILITY);
			setAutoRequestFocus(false);
			setTitle("Sistema Domotico");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 882, 552);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(255, 255, 255));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JButton btnRele1On = new JButton("Rele1 - ON");
			btnRele1On.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
			            ino.sendData("1011");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
					
				}
			});
			btnRele1On.setBounds(24, 159, 100, 23);
			contentPane.add(btnRele1On);
			
			JButton btnRele1Off = new JButton("Rele1 - OFF");
			btnRele1Off.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
			            ino.sendData("1010");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnRele1Off.setBounds(151, 159, 100, 23);
			contentPane.add(btnRele1Off);
			
			JButton btnRele2On = new JButton("Rele2 - ON");
			btnRele2On.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
			            ino.sendData("1021");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnRele2On.setBounds(24, 209, 100, 23);
			contentPane.add(btnRele2On);
			
			JButton btnRele2Off = new JButton("Rele2 - OFF");
			btnRele2Off.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
			            ino.sendData("1020");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnRele2Off.setBounds(151, 209, 100, 23);
			contentPane.add(btnRele2Off);
			
			JButton btnRele3On = new JButton("Rele3 - ON");
			btnRele3On.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
			            ino.sendData("1031");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnRele3On.setBounds(24, 257, 100, 23);
			contentPane.add(btnRele3On);
			
			JButton btnRele3Off = new JButton("Rele3 - OFF");
			btnRele3Off.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
			            ino.sendData("1030");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnRele3Off.setBounds(151, 257, 100, 23);
			contentPane.add(btnRele3Off);
			
			JButton btnRele4On = new JButton("Rele4 - ON");
			btnRele4On.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
			            ino.sendData("1041");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnRele4On.setBounds(24, 306, 100, 23);
			contentPane.add(btnRele4On);
			
			JButton btnRele4Off = new JButton("Rele4 - OFF");
			btnRele4Off.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
			            ino.sendData("1040");
			        }
					catch (ArduinoException | SerialPortException ex) {
			            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
			        }
				}
			});
			btnRele4Off.setBounds(151, 306, 100, 23);
			contentPane.add(btnRele4Off);
			
			JLabel lblModoDeFuncionamiento = new JLabel("Modo Manual");
			lblModoDeFuncionamiento.setForeground(new Color(220, 20, 60));
			lblModoDeFuncionamiento.setFont(new Font("Corbel", Font.PLAIN, 24));
			lblModoDeFuncionamiento.setBounds(308, 13, 208, 46);
			contentPane.add(lblModoDeFuncionamiento);
			
			JLabel lblActivardesactivarRels = new JLabel("Activar/Desactivar Rel\u00E9s");
			lblActivardesactivarRels.setForeground(new Color(220, 20, 60));
			lblActivardesactivarRels.setFont(new Font("Corbel", Font.PLAIN, 24));
			lblActivardesactivarRels.setBounds(24, 109, 272, 23);
			contentPane.add(lblActivardesactivarRels);
			
			textField_1 = new JTextField();
			textField_1.setBounds(115, 76, 83, 20);
			contentPane.add(textField_1);
			textField_1.setColumns(10);
			
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon("C:\\Users\\UEM\\Desktop\\Sin t\u00EDtulo-1.jpg"));
			lblNewLabel.setBounds(42, 380, 272, 96);
			contentPane.add(lblNewLabel);
			
			JButton btnNewButton = new JButton("");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					WindowPrincipal frame = new WindowPrincipal();
					frame.setVisible(true);
					

					
				}
			});
				
				
				
			btnNewButton.setIcon(new ImageIcon("C:\\Users\\UEM\\Desktop\\exit.jpg"));
			btnNewButton.setBounds(649, 380, 150,80);
			contentPane.add(btnNewButton);
			
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\UEM\\Desktop\\casa.jpg"));
			lblNewLabel_1.setBounds(762, 13, 90, 96);
			contentPane.add(lblNewLabel_1);
			
			JLabel lblTemperatura = new JLabel("Temperatura:");
			lblTemperatura.setForeground(new Color(220, 20, 60));
			lblTemperatura.setFont(new Font("Corbel", Font.PLAIN, 15));
			lblTemperatura.setBounds(24, 78, 89, 18);
			contentPane.add(lblTemperatura);
			
			JLabel lblNewLabel_2 = new JLabel("Humedad:");
			lblNewLabel_2.setForeground(new Color(220, 20, 60));
			lblNewLabel_2.setFont(new Font("Corbel", Font.PLAIN, 15));
			lblNewLabel_2.setBounds(240, 78, 65, 18);
			contentPane.add(lblNewLabel_2);
			
			textField_2 = new JTextField();
			textField_2.setBounds(318, 75, 89, 22);
			contentPane.add(textField_2);
			textField_2.setColumns(10);
			
			JLabel lblLuz = new JLabel("Luz:");
			lblLuz.setForeground(new Color(220, 20, 60));
			lblLuz.setFont(new Font("Corbel", Font.PLAIN, 15));
			lblLuz.setBounds(443, 79, 38, 16);
			contentPane.add(lblLuz);
			
			textField_3 = new JTextField();
			textField_3.setBounds(493, 75, 116, 22);
			contentPane.add(textField_3);
			textField_3.setColumns(10);
			
			JButton btnConsumos = new JButton("Consumos");
			btnConsumos.setForeground(new Color(0, 0, 0));
			btnConsumos.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
			btnConsumos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					Consumos frame = new Consumos();
					frame.setVisible(true);
				}
			});
			btnConsumos.setBounds(426, 159, 150, 46);
			contentPane.add(btnConsumos);
			
			JButton btnActivarAlarma = new JButton("Activar Alarma");
			btnActivarAlarma.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
			btnActivarAlarma.setBounds(426, 272, 150, 46);
			contentPane.add(btnActivarAlarma);
			
			
		}
	}