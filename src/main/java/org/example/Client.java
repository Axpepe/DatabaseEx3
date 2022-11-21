package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Client implements InterficieGeneral {

	protected String dni,nom,cognom,mail,telefon;
	protected LocalDate data_naixement;
	protected ArrayList<Client> clientsTotal = new ArrayList<Client>();
	
	
	public Client() {}
	public Client(String dni,String nom,String cognom,String mail,String telefon,LocalDate data_n) {
		this.dni = dni; this.nom = nom; this.cognom = cognom; this.mail = mail; this.telefon =telefon;
		this.data_naixement = data_n;
	}

	public void importar(Connection conection, Statement st) throws SQLException {
		ResultSet r = st.executeQuery("select * from client");
		while(r.next()) {
			String dni = r.getString("dni");
			String nom = r.getString("nom");
			//
			String cognom = r.getString("cognom");
			String mail = r.getString("mail");
			String telefon = r.getString("telefon");
			LocalDate data_n = r.getDate("data_naixement").toLocalDate();
			
			Client newClient = new Client(dni,nom,cognom,mail,telefon,data_n);
			clientsTotal.add(newClient);
					
		}
	}
	@Override
	public boolean crearObjecte(Connection conection, Statement st) throws SQLException {
		Scanner lector = new Scanner(System.in);
		System.out.println("*Introduir dni");
		String dni = lector.nextLine();
		if(dni.length() > 9) {
			dni = dni.substring(0,8);
		}
		System.out.println("*Introduir el seu nom");
		String nom = lector.nextLine();
		System.out.println("*Introduir el seu cognom");
		String cognom = lector.nextLine();
		System.out.println("*Introduir el seu mail");
		String mail = lector.nextLine();
		System.out.println("*Introduir el seu telefon");
		String telefon = lector.nextLine();
		LocalDate data_n;
		try {
			int day,month,year;
			System.out.println("*Introduir la seva data de naixement DD/MM/YYYY");
			String dia = lector.nextLine();
			String dia_split []= dia.split("/");
			
			day =Integer.parseInt(dia_split[0]);
			month = Integer.parseInt(dia_split[1]);
			year = Integer.parseInt(dia_split[2]);
			
			data_n = LocalDate.of(year, month, day);
			
		}
		catch(Exception e) {
			data_n = LocalDate.now(); //si dona error el dia de naixement es igual a aquest any
		}
		boolean creat = true;
		try {
			Client newClient = new Client(dni,nom,cognom,mail,telefon,data_n);
			String sql = "INSERT INTO CLIENT VALUES('"+dni+"',"+
					"'"+nom+"',"+
			"'"+cognom+"',"+
			"'"+mail+"',"+
			"'"+telefon+"',"+
			"'"+data_n+"');";
			st.executeUpdate(sql);
			clientsTotal.add(newClient);
			
		}
		catch(Exception e) {
			creat = false;
		}
		return creat;	
		
	}
	public void show(Connection conection, Statement st) throws SQLException {
		ResultSet r = st.executeQuery("select * from client");
		while(r.next()) {
			System.out.println("["+r.getString("dni")+"] - "+r.getString("nom")+"- "+
					r.getString("cognom")+" - "+r.getString("mail")+" - "+r.getString("telefon")+" - "+r.getDate("data_naixement")+" - ");
		
		}
	}
	@Override
	public boolean updatejarObjecte(Connection conection, Statement st) throws SQLException {
		Scanner lector = new Scanner(System.in); boolean done = true;
		try {
			System.out.println("Introdueix DNI del Client");
			String id = lector.nextLine();
			int n=0;
			
			while(clientsTotal.size()>n && !(id.equals(clientsTotal.get(n).getDni()))) {
				n++;
			}
			if(n < clientsTotal.size()) {
				System.out.println("*Selecciona item que vols canviar:\n\ta.Mail\n\tb.Telefon");
				char opcio = lector.nextLine().trim().toLowerCase().charAt(0);
						if(opcio == 'a') {
							System.out.println("*Introdueix el nou valor per MAIL    MAIL ANTIC:"+ clientsTotal.get(n).getMail());
							String clientNou = lector.nextLine();
							String sql ="update client set mail='"+clientNou+"' where dni='"+id+"';";
							clientsTotal.get(n).setMail(id);
							st.executeUpdate(sql);
						}
						else if(opcio=='b') {
							System.out.println("*Introdueix el nou valor per el TELEFON   MÀXIM ANTIC:"+ clientsTotal.get(n).getTelefon());
							String telefon = lector.nextLine();
							String sql ="update client set telefon='"+telefon+"' where dni='"+id+"';";
							clientsTotal.get(n).setTelefon(telefon);
							st.executeUpdate(sql);
							
							
						}
						else {
							System.out.println("Seleccio desitjada conclosa.");
						}
			}	
		}
		catch(Exception e) {
			 done = false;
		}
		return done;
	}

	@Override
	public boolean deleteObjecte(Connection conection, Statement st) throws SQLException {
		boolean done = true;
		Scanner lector = new Scanner(System.in);
		

		try {
			int n=0;
			System.out.println("*Introduiex el dni per esborrar: ");
			String id = lector.nextLine().trim();
			
			while(clientsTotal.size()>n && !(id.equals(clientsTotal.get(n).getDni()))) {
				n++;
			}
			System.out.println("ID: "+ id );
			clientsTotal.remove(n);
			st.executeUpdate("delete from client where dni='"+id+"';");
		}
		catch(Exception e) {
			done = false; System.out.println("Error");
			System.out.print(e);
		}
		return done;
	}
public Client cercar(String id) {
		
		int n=0;
		while(n<clientsTotal.size() && !(id.equals(clientsTotal.get(n).getDni()))) {
			n++;
		}
		if(n<clientsTotal.size()) {
			return clientsTotal.get(n);
		}
		return null;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCognom() {
		return cognom;
	}
	public void setCognom(String cognom) {
		this.cognom = cognom;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public LocalDate getData_naixement() {
		return data_naixement;
	}
	public void setData_naixement(LocalDate data_naixement) {
		this.data_naixement = data_naixement;
	}

}
