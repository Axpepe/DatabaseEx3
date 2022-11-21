package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Connexio {
	 
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection conexio = DriverManager.getConnection("jdbc:postgresql://localhost:5432/viatge","postgres","972325248");
		Statement statement = conexio.createStatement();
		
		Vol vol = new Vol();
		//vol.crearVol(conexio, statement);
		///vol.importar(null, statement);
		Estacio estacio = new Estacio();
		//estacio.crearEstacio(conexio, statement);
		//estacio.deleteEstacio(conexio, statement);
		Bitllet bitllet = new Bitllet();
		bitllet.importar(null, statement);
		bitllet.minusMaxPeople(null, statement, "2"); //FUNCIO PER RESTAR VALORS TOTAL
		//bitllet.updatejarObjecte(null, statement);
		bitllet.show(null, statement);
		
		
		
		bitllet.crearObjecte(null, statement);
		bitllet.deleteObjecte(null, statement);
		estacio.importar(conexio, statement);
		estacio.show(conexio, statement);
		estacio.updatejarEstacio(conexio, statement);
		estacio.show(conexio, statement);
		estacio.deleteEstacio(conexio, statement);
		}	
}
