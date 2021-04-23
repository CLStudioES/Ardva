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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Window.Type;



	public class Consumos extends JFrame {
		 {
		}
		
		private static PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
		
	

		private JPanel contentPane;
		private JTextField textField;
		private JTable table;

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			try {
	            ino.arduinoRXTX("COM3", 9600, null);
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
		public Consumos() {
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
			
			JLabel lblModoDeFuncionamiento = new JLabel("Consumos");
			lblModoDeFuncionamiento.setForeground(new Color(220, 20, 60));
			lblModoDeFuncionamiento.setFont(new Font("Corbel", Font.PLAIN, 24));
			lblModoDeFuncionamiento.setBounds(308, 13, 122, 46);
			contentPane.add(lblModoDeFuncionamiento);
			
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
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(37, 57, 524, 264);
			contentPane.add(scrollPane);
			
			table = new JTable();
			table.setEnabled(false);
			table.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
				},
				new String[] {
					"Registro", "ID", "Tipo", "Valor", "TimeStamp"
				}
			));
			scrollPane.setViewportView(table);
			
			
		}
	}
	