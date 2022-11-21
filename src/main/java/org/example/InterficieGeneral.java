package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface InterficieGeneral {

	
	boolean crearObjecte(Connection conection,Statement st) throws SQLException;
	boolean updatejarObjecte(Connection conection,Statement st) throws SQLException;
	boolean deleteObjecte (Connection conection,Statement st) throws SQLException;
}
