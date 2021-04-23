
/**
 * Modulo Temperatura JavaRXTX
 * @author Rebeca Pérez Carrasco; Num.Exp: 21542824
 *
 */

package Java_Arduino.ArduinoRX;

import Java_Arduino.ArduinoRX.JavaRX;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.sql.*;


public class JavaRXTX extends javax.swing.JFrame {
    private PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    private Timestamp timestamp ;
    
    
    
    private SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {
                if (ino.isMessageAvailable()) {
                    jLabelOutput.setText("Resultado: " + ino.printMessage());
                    
                    //Se imprime el mensaje recibido en la consola
                    System.out.println(ino.printMessage());
                    
                    
                    String lectura = ino.printMessage();
                    String registros[] = lectura.split("#");
                   
                    // Se registra el Driver de MySQL
                    DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
                    
                    // Se obtiene una conexión con la base de datos. Hay que
                    // cambiar el usuario "root" y la clave "la_clave" por las
                    // adecuadas a la base de datos que estemos usando.
                    Connection conexion = DriverManager.getConnection (
                        "jdbc:mysql://localhost/lp_domotica","root", "");
                    
                    // Se crea un Statement, para realizar la consulta
                    Statement s = conexion.createStatement();
                    
                    // Se realiza la consulta. Los resultados se guardan en el 
                    // ResultSet rs
                    ResultSet rs = s.executeQuery ("select * from Sensores");
                    
                             
                    if ( orden == "0010"){ //Si pulsamos botón guardar en bbdd 
                    		
                    		 timestamp = new Timestamp(System.currentTimeMillis());
                    		 s.executeUpdate("INSERT INTO sensores (id, tipo,unidad,medida,descripcion) " + "VALUES ("+registros[0]+","+registros[1]+","+registros[2]+","+registros[3]+")");
                        	 s.executeUpdate("INSERT INTO medidas (id, tipo,valor,timestamp) " + "VALUES ("+registros[4]+","+registros[5]+","+registros[6]+","+timestamp+")");                          
                             
                        	// Se cierra la conexión con la base de datos.
                             conexion.close();
                        	
                                
                    }else{ // si estamos en modo automático                       	
                        	 timestamp = new Timestamp(System.currentTimeMillis());
                        	 s.executeUpdate("INSERT INTO medidas (id, tipo,valor,timestamp) " + "VALUES ("+registros[0]+","+registros[1]+","+registros[2]+","+timestamp+")");
                             
                        	// Se cierra la conexión con la base de datos.
                             conexion.close();                             
                    }
                }
             
            } catch (SerialPortException | ArduinoException ex ) {
                Logger.getLogger(JavaRX.class.getName()).log(Level.SEVERE, null, ex);
               
            }
        }
    };
    public JavaRXTX() {
        initComponents();
        try {
            ino.arduinoRXTX("COM3", 9600, listener);
        } catch (ArduinoException ex) {
            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        
        
        jLabelOutput = new javax.swing.JLabel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("E/A calentador");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("E/A ventilador");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabelOutput.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelOutput.setText("Resultado: ");
        
       
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelOutput)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelOutput)
                .addContainerGap(21, Short.MAX_VALUE))
        );      
        pack();      
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {  //Se hace el envío del String "2101" en modo manual
            ino.sendData("0000.2101"); //Temperatura
        } catch (ArduinoException | SerialPortException ex) {
            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Se hace el envío del String "3101" en modo manual
        try {
            ino.sendData("0000.3101"); //Ventilador
        } catch (ArduinoException | SerialPortException ex) {
            Logger.getLogger(JavaRXTX.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JavaRXTX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JavaRXTX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JavaRXTX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JavaRXTX.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JavaRXTX().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabelOutput;
    
    
    
   
    // End of variables declaration//GEN-END:variables
}