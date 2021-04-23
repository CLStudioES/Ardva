import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import jssc.SerialPortException;

import javax.swing.ImageIcon;
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



public class WindowManual extends JFrame {
	
	/*
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
	*/
	
	private JPanel contentPane;
	private JTextField textField;
	private static JTextField textField_1;
	private static JTextField textField_2;
	private static JTextField textField_3;
	private static JTextField textField_4;
	private static JTextField textField_5;
	private static JTextField textField_6;
	public void actualizarTemperatura(String temperatura){
		textField_1.setText(temperatura);
	}
	public void actualizarHumedad(String humedad){
		textField_2.setText(humedad);
	}
	
	public void actualizarLuz(String luz){
		textField_3.setText(luz);
	}
	public void actualizarTemperatura2(String temperatura2){
		textField_4.setText(temperatura2);
	}
	public void actualizarHumedad2(String humedad2){
		textField_5.setText(humedad2);
	}
	public void actualizarLuz2(String luz2){
		textField_6.setText(luz2);
	}
	
	/**
	 * Launch the application.
	 */
	/*
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
*/
	/**
	 * Create the frame.
	 */
	public WindowManual(PanamaHitek_Arduino ino) {
		
		setAlwaysOnTop(true);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 882, 552);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnManual = new JButton("Refrescar");
		btnManual.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		btnManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("0000");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnManual.setBounds(619, 78, 90, 46);
		contentPane.add(btnManual);
		
		JButton btnRele1On = new JButton("Luz 1- ON");
		btnRele1On.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9011");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
				
			}
		});
		btnRele1On.setBounds(10, 196, 100, 23);
		contentPane.add(btnRele1On);
		
		JButton btnRele1Off = new JButton("Luz 1- OFF");
		btnRele1Off.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9010");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnRele1Off.setBounds(130, 196, 100, 23);
		contentPane.add(btnRele1Off);
		
		JButton btnRele2On = new JButton("Luz 2- ON");
		btnRele2On.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9021");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnRele2On.setBounds(10, 230, 100, 23);
		contentPane.add(btnRele2On);
		
		JButton btnRele2Off = new JButton("Luz 2- OFF");
		btnRele2Off.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9020");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnRele2Off.setBounds(130, 230, 100, 23);
		contentPane.add(btnRele2Off);
		
		JButton btnRele3On = new JButton("HeatherON");
		btnRele3On.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9031");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnRele3On.setBounds(10, 264, 100, 23);
		contentPane.add(btnRele3On);
		
		JButton btnRele3Off = new JButton("HeatherOFF");
		btnRele3Off.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9030");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnRele3Off.setBounds(130, 264, 100, 23);
		contentPane.add(btnRele3Off);
		
		JButton btnRele4On = new JButton("Fan - ON");
		btnRele4On.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9041");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnRele4On.setBounds(10, 298, 100, 23);
		contentPane.add(btnRele4On);
		
		JButton btnRele4Off = new JButton("Fan - OFF");
		btnRele4Off.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9040");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnRele4Off.setBounds(130, 298, 100, 23);
		contentPane.add(btnRele4Off);
		
		JLabel lblModoDeFuncionamiento = new JLabel("Modo Manual");
		lblModoDeFuncionamiento.setForeground(new Color(220, 20, 60));
		lblModoDeFuncionamiento.setFont(new Font("Corbel", Font.PLAIN, 24));
		lblModoDeFuncionamiento.setBounds(287, 0, 208, 46);
		contentPane.add(lblModoDeFuncionamiento);
		
		JLabel lblActivardesactivarRels = new JLabel("Activar/Desactivar Rel\u00E9s");
		lblActivardesactivarRels.setForeground(new Color(220, 20, 60));
		lblActivardesactivarRels.setFont(new Font("Corbel", Font.PLAIN, 24));
		lblActivardesactivarRels.setBounds(24, 163, 272, 23);
		contentPane.add(lblActivardesactivarRels);
		
		textField_1 = new JTextField();
		textField_1.setBounds(107, 77, 67, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\UEM\\Desktop\\Sin t\u00EDtulo-1.jpg"));
		lblNewLabel.setBounds(12, 409, 272, 96);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				//esto esta para que se cierre la ventanta cuando salgas

				
			}
		});
			
			
			
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\UEM\\Desktop\\exit.jpg"));
		btnNewButton.setBounds(569, 354, 150,80);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\UEM\\Desktop\\casa.jpg"));
		lblNewLabel_1.setBounds(762, 13, 90, 96);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblTemperatura = new JLabel("Temperatura:");
		lblTemperatura.setForeground(new Color(220, 20, 60));
		lblTemperatura.setFont(new Font("Corbel", Font.PLAIN, 15));
		lblTemperatura.setBounds(97, 46, 89, 18);
		contentPane.add(lblTemperatura);
		
		JLabel lblNewLabel_2 = new JLabel("Humedad:");
		lblNewLabel_2.setForeground(new Color(220, 20, 60));
		lblNewLabel_2.setFont(new Font("Corbel", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(308, 46, 65, 18);
		contentPane.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(308, 76, 73, 22);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblLuz = new JLabel("Luz:");
		lblLuz.setForeground(new Color(220, 20, 60));
		lblLuz.setFont(new Font("Corbel", Font.PLAIN, 15));
		lblLuz.setBounds(513, 47, 38, 16);
		contentPane.add(lblLuz);
		
		textField_3 = new JTextField();
		textField_3.setBounds(493, 75, 83, 22);
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
		
		
		btnConsumos.setBounds(645, 226, 150, 46);
		contentPane.add(btnConsumos);
		
		JButton btnActivarAlarma = new JButton("Activar Alarma");
		btnActivarAlarma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("0021");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnActivarAlarma.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		btnActivarAlarma.setBounds(426, 196, 150, 46);
		contentPane.add(btnActivarAlarma);
		
		textField_4 = new JTextField();
		textField_4.setBounds(107, 127, 65, 23);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(308, 127, 73, 22);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(493, 127, 83, 22);
		contentPane.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblDormitorio = new JLabel("Dormitorio");
		lblDormitorio.setForeground(new Color(220, 20, 60));
		lblDormitorio.setFont(new Font("Corbel", Font.PLAIN, 15));
		lblDormitorio.setBounds(12, 79, 83, 16);
		contentPane.add(lblDormitorio);
		
		JLabel lblSaln = new JLabel("Sal\u00F3n");
		lblSaln.setFont(new Font("Corbel", Font.PLAIN, 15));
		lblSaln.setForeground(new Color(220, 20, 60));
		lblSaln.setBounds(39, 131, 56, 16);
		contentPane.add(lblSaln);
		
		
		
		JButton btnDesactivarAlarma = new JButton("Desactivar Alarma");
		btnDesactivarAlarma.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		btnDesactivarAlarma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("0020");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnDesactivarAlarma.setBounds(426, 264, 150, 46);
		contentPane.add(btnDesactivarAlarma);
		
		JButton btnNewButton_1 = new JButton("Heather2ON");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9051");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
				
			}
		});
		btnNewButton_1.setBounds(10, 332, 100, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Heather2OFF");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9050");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
				
			}
		});
		btnNewButton_2.setBounds(130, 332, 100, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Fan 2-On");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9061");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
				
			}
		});
		btnNewButton_3.setBounds(10, 366, 100, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Fan 2-OFF");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("9060");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
				
			}
		});
		btnNewButton_4.setBounds(130, 366, 100, 23);
		contentPane.add(btnNewButton_4);
		
		
	}
}