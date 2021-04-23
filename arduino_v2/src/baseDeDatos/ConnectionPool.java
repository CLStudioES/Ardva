package baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
 
public class ConnectionPool {
 
    private static Connection Conexion;
 
    public void MySQLConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Conexion = DriverManager.getConnection ("jdbc:mysql://localhost/lp_domotica","root", "");
            //Conexion = DriverManager.getConnection("jdbc:mysql://localhost/" + db_name, user, pass);
            System.out.println("Se ha iniciado la conexión con el servidor de forma exitosa");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public void closeConnection() {
        try {
            Conexion.close();
            System.out.println("Se ha finalizado la conexión con el servidor");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
   
 
 
    public void insertDataSensores(int id,int tipo,String unidad,int medida,String descripcion) {
        try {
            String Query = "INSERT INTO sensores (id, tipo,unidad,medida,descripcion) VALUES("
                    + "\"" + id + "\", "
                    + "\"" + tipo + "\", "
                    + "\"" + unidad + "\", "
                    + "\"" + medida + "\", "
                    + "\"" + descripcion + "\")";
         
            Statement st = Conexion.createStatement();
          
            st.executeUpdate(Query);
            JOptionPane.showMessageDialog(null, "Datos almacenados de forma exitosa");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos");
        }
    }
    
    public void insertDataMedidas(int id,int tipo,int valor) {
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            String Query = "INSERT INTO medidas (id, tipo,valor,timestamp) VALUES("
                    + "\"" + id + "\", "
                    + "\"" + tipo + "\", "
                    + "\"" + valor + "\", "
                    + "\"" + timestamp + "\")";
         
            Statement st = Conexion.createStatement();
          
            st.executeUpdate(Query);
            JOptionPane.showMessageDialog(null, "Datos almacenados de forma exitosa");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos");
        }
    }
    
 
    public void getValuesSensores() {
        try {
            String Query = "SELECT * FROM sensores" ;
            Statement st = Conexion.createStatement();
            java.sql.ResultSet resultSet;
            resultSet = st.executeQuery(Query);
 
            while (resultSet.next()) {
            	 System.out.println (resultSet.getInt ("Registro")  + " " + resultSet.getString (2)+ 
           			  " " + resultSet.getString(3) + " " + resultSet.getString(4)  + " " + resultSet.getString(5)+
           			  " " + resultSet.getString(6));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la adquisición de datos");
        }
    }
}
	