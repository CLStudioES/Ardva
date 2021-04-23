//Con este código rellenaremos la tabla de datos de la BBDD
//En algunos comentarios explica qué hay que cambiar en el código para aplicarlo a nuestro proyecto

private void setFilas(){
	
	try{
		String sql="Select Registro, ID, Tipo, Valor, TimeStamp from Medidas";
				PreparedStatement us = con.conexion().prepareStatement(sql);//Para inicializar la consulta
				ResultSet res = us.executeQuery();//Para ejecutar la consulta
				
				Object datos[]=new Object[5];//Numero de columnas de la consulta
				
		while (res.next()){
			for(int i=0; i<5; i++){
				datos[i]=res.getObject(i+1);
			}
			modeloTabla.addRow(datos); //Cambiar el nombre de modelo.Tabla por el nombre bueno
		}
		
		res.close():
	}catch (SQLExcepction ex){
		Logger.getLogger(JTable1.class.getName()).log(Level.SEVERE, null, ex);//Cambiar el nombre JTable1 si se ha llamado de alguna otra manera en neustro código
	}
}