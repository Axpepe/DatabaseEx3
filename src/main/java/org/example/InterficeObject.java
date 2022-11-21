package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public interface InterficeObject {
	
	ArrayList<Vol> volsTotal = new ArrayList<Vol>();
	Vol volCercar(String id); //VOL ID es String
	
	boolean crearVol(Connection conection,Statement st) throws SQLException;
	boolean updatejarVol(Connection conection,Statement st,Vol volObjecte);
	boolean deleteVol (Connection conection,Statement st,String idVol);

	Vol volCercar(String id, ArrayList<Vol> volsTotals);

	
	
}
