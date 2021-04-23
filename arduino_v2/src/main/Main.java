package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import baseDeDatos.ConnectionPool;
import ventanas.VentanaPrincipal;
import arduinoRXTX.JavaRXTX;

public class Main {

	public static void main(String[] args)
	{
		/*
		ConnectionPool conn = new ConnectionPool();
		conn.MySQLConnection();
		conn.insertDataSensores(2,0,"C",55,"prueba1");
		*/
		
		//new JavaRXTX();
		VentanaPrincipal frame = new VentanaPrincipal();
		frame.setVisible(true);
	}
	
	
}
