package Java_Arduino.ArduinoRX;

/*
 * PruebaMySQL.java
 *
 * Programa de prueba para conexión a una base de datos de MySQL.
 * Presupone que el servidor de base de datos está arrancado, disponible,
 * en el puerto por defecto.
 * El usuario y password de conexión con la base de datos debe cambiarse.
 * En la base de datos se supone que hay una base de datos llamada prueba y que
 * tiene una tabla persona con tres campos, de esta manera:
 * mysql> create database prueba;
 * mysql> use prueba;
 * mysql> create table persona (id smallint auto_increment, nombre varchar(60), 
 *      nacimiento date, primary key(id)); 
 */



import java.sql.*;

/**
 * Clase de prueba de conexión con una base de datos MySQL
 */
public class PruebaMySQL {
    
    /** 
     * Crea una instancia de la clase MySQL y realiza todo el código 
     * de conexión, consulta y muestra de resultados.
     */
    public PruebaMySQL() 
    {
        // Se mete todo en un try por los posibles errores de MySQL
        try
        {
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
            
            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            while (rs.next())
            {
               // System.out.println (rs.getInt ("Id") + " " + rs.getString (2)+ 
               //     " " + rs.getDate(3));
            	  System.out.println (rs.getInt ("Registro")  + " " + rs.getString (2)+ 
            			  " " + rs.getString(3) + " " + rs.getString(4)  + " " + rs.getString(5)+
            			  " " + rs.getString(6));
            }
            
            
         // insert the data
            s.executeUpdate("INSERT INTO sensores (id, tipo,unidad,medida,descripcion) " + "VALUES (2,1,'C',30,'rebeca')");
            
         
            // Se cierra la conexión con la base de datos.
            conexion.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Método principal, instancia una clase PruebaMySQL
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        new PruebaMySQL();
    }
    
}