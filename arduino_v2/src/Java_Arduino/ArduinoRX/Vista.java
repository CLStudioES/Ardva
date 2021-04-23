package Java_Arduino.ArduinoRX;

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




public class Vista extends JFrame {
	
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
					Vista frame = new Vista();
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
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAutomatico = new JButton("Automatico");
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
		btnAutomatico.setBounds(109, 36, 121, 23);
		contentPane.add(btnAutomatico);
		
		JButton btnManual = new JButton("Manual");
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
		btnManual.setBounds(10, 36, 89, 23);
		contentPane.add(btnManual);
		
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
		btnRele1On.setBounds(10, 95, 100, 23);
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
		btnRele1Off.setBounds(130, 95, 100, 23);
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
		btnRele2On.setBounds(10, 125, 100, 23);
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
		btnRele2Off.setBounds(130, 125, 100, 23);
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
		btnRele3On.setBounds(10, 155, 100, 23);
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
		btnRele3Off.setBounds(130, 155, 100, 23);
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
		btnRele4On.setBounds(10, 184, 100, 23);
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
		btnRele4Off.setBounds(130, 184, 100, 23);
		contentPane.add(btnRele4Off);
		
		JButton btnTemperatura = new JButton("Temperatura");
		btnTemperatura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("2001");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnTemperatura.setBounds(10, 243, 115, 23);
		contentPane.add(btnTemperatura);
		
		JButton btnHumedad = new JButton("Humedad");
		btnHumedad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ino.sendData("3001");
		        }
				catch (ArduinoException | SerialPortException ex) {
		            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnHumedad.setBounds(10, 277, 115, 23);
		contentPane.add(btnHumedad);
		
		JLabel lblModoDeFuncionamiento = new JLabel("Modo de Funcionamiento");
		lblModoDeFuncionamiento.setBounds(10, 11, 199, 14);
		contentPane.add(lblModoDeFuncionamiento);
		
		JLabel lblActivardesactivarRels = new JLabel("Activar/Desactivar Rel\u00E9s");
		lblActivardesactivarRels.setBounds(10, 70, 165, 14);
		contentPane.add(lblActivardesactivarRels);
		
		JLabel lblSensores = new JLabel("Sensores");
		lblSensores.setBounds(10, 218, 46, 14);
		contentPane.add(lblSensores);
		
		textField_1 = new JTextField();
		textField_1.setBounds(158, 244, 225, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		
	}
}
