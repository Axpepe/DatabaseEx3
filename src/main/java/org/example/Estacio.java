package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


public class Estacio {
	
	
	String id;
	String nom;
	
	ArrayList<Estacio> estacionsTotal = new ArrayList<Estacio>();
	
	public Estacio() {}
	public Estacio(String id, String nom) {
		this.id = id; this.nom = nom;
	}
	public void importar(Connection con, Statement str) throws SQLException {
		int i = 0;
		ResultSet r = str.executeQuery("select * from estacio");
		try {
			while(r.next()) {
				
					String id = r.getString("id");
					String nom =  r.getString("nom");
					
					
					Estacio estacio = new  Estacio(id, nom);
					estacionsTotal.add(estacio);
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void show(Connection con, Statement str) throws SQLException {
		ResultSet r = str.executeQuery("select * from estacio");
		while(r.next()) {
			System.out.println(r.getString("id")+"-"+r.getString("nom"));
		}
		///
		/*int n=0;
		while(estacionsTotal.size() > n) {
			System.out.println(estacionsTotal.get(n).getID() + " - " + estacionsTotal.get(n).getNom());
			n++;
		}*/
	}
	public boolean crearEstacio(Connection con, Statement str) throws SQLException {
		
		Scanner lector = new Scanner(System.in);
		ResultSet  r = str.executeQuery("select * from estacio;");
		int n= 0; String id=""; int intMax=0;
		while(r.next()) {
			int idC = Integer.parseInt(r.getString("id"));
			if(intMax < idC) {
				intMax = idC;
				id = String.valueOf(idC+1);	 
			}
		}
		
		
		System.out.println("*ID creat " + id);
		System.out.println("*Introduir nom per l'estacio");
		String nom = lector.nextLine().trim();
		
		String sql = "INSERT INTO estacio VALUES('"+id+"','"+nom+"');";
		
		Estacio nouEstacio = new Estacio(id,nom); boolean creat = true;
		try {
			str.executeUpdate(sql);
			estacionsTotal.add(nouEstacio);
		}
		catch(Exception e) {
			creat = false;
		}
		return creat;
	}
	public boolean updatejarEstacio(Connection con,Statement str) {
		Scanner lector = new Scanner(System.in);
		System.out.println("*Introduir id de l'Estacio");
		String id = lector.nextLine().trim();
		boolean updated = true; int n=0;
		try {
			
			while(estacionsTotal.size()>n && !(id.equals(estacionsTotal.get(n).getID()))) {
				n++;
			}
			System.out.println(n);
			System.out.println("Escriu el nou nom per " + estacionsTotal.get(n).getNom());
			String nom = lector.nextLine();
			
			estacionsTotal.get(n).setNom(nom);
			str.executeUpdate("update estacio set nom='"+nom+"' where id='"+id+"';");
			
			
		}
		catch(Exception e) {
			System.out.println("Error");
			System.out.println(e);
			updated = false;
		}
		
		
		
		return updated;
	}
	public boolean deleteEstacio(Connection con,Statement str) {
		boolean done = true;
		Scanner lector = new Scanner(System.in);
		

		try {
			int n=0;
			System.out.println("*Introduiex la id per esborrar: ");
			String id = lector.nextLine().trim();
			
			while(estacionsTotal.size()>n && !(id.equals(estacionsTotal.get(n).getID()))) {
				n++;
			}
			System.out.println("ID: "+ id );
			estacionsTotal.remove(n);
			str.executeUpdate("delete from estacio where id='"+id+"';");
		}
		catch(Exception e) {
			done = false; System.out.println("Error");
			System.out.print(e);
		}
		return done;
	}
	
	
	public String getID() {
		return this.id;
	}
	public String getNom() {
		return this.nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

}
