package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Factura {
	int num_factura;
	String dni;
	LocalDate data;
	double total;
	ArrayList<Linea> linea;
	
	public Factura() {}

	public Factura(int num_factura,String dni, LocalDate data, double total, ArrayList<Linea> linea) {
		this.num_factura = num_factura; this.dni = dni; this.data = data; this.total = total; this.linea = linea;

	}
	public Factura(int num_factura,String dni, LocalDate data, double total, ArrayList<Linea> linea,Connection conection, Statement st) {
		this.num_factura = num_factura; this.dni = dni; this.data = data; this.total = total; this.linea = linea;
		try {
			System.out.println("Num factura: " + num_factura);
			System.out.println("Dni comprador: " + dni + "\nData de transacció: " + data + "\n------------------------------------");
			String sql = "INSERT INTO factura VALUES('"+num_factura+"',"+
					"'"+dni+"',"+
					"'"+total+"',"+
					"'"+data+"');";
			st.executeUpdate(sql);
			System.out.println("Bitllet  -  preu");
			for(int i = 0; i<linea.size();i++) {
				System.out.println(linea.get(i).id_bitllet + "        -  " + linea.get(i).preu);
				sql = "INSERT INTO nlinea VALUES('"+num_factura+"',"+
						"'"+linea.get(i).id_bitllet+"',"+
						"'"+linea.get(i).preu+"');";
				st.executeUpdate(sql);
			}
			System.out.println("------------------------------------\nTOTAL: " + total);

		}
		catch(Exception e) {
			System.out.println(e);
		}

	}
	public int getIdFactura(Connection con, Statement st) throws SQLException {
		ResultSet  r = st.executeQuery("select * from factura;");
		int n= 0; int id=1; int intMax=0;
		while(r.next()) {

			int idC = Integer.parseInt(r.getString("num_factura"));
			if(intMax < idC) {
				intMax = idC;
				id = idC+1;
			}

		}
		return id;
	}
	public Factura(int num_factura, String dni,LocalDate data, double total) {
		this.num_factura = num_factura; this.dni = dni; this.data = data; this.total = total;
	}
	
	public void show(Connection connection,Statement str, String dni) throws SQLException {
		ArrayList<Factura> facturas = new ArrayList<>();
		ArrayList<Linea> lineas = new ArrayList<>();
		ArrayList<Linea> lineasReal	= new ArrayList<>();
		ResultSet r = str.executeQuery("select * from factura");
		while(r.next()) {
			facturas.add(new Factura(r.getInt("num_factura"), r.getString("dni"),r.getDate("data_compra").toLocalDate(),r.getDouble("total"), lineas));
		}
		for(int i = 0; i<facturas.size();i++){
			lineasReal.clear();
			r = str.executeQuery("select * from nlinea where num_factura=" + facturas.get(i).num_factura);
			while (r.next()) {
				lineasReal.add(new Linea(r.getInt("num_factura"),r.getString("id_bitllet"),r.getDouble("preu")));
			}
			facturas.get(i).setLinea(new ArrayList<>(lineasReal));
		}
		for(int i = 0; i<facturas.size();i++) {
			System.out.println("Num factura: " + facturas.get(i).num_factura);
			System.out.println("Dni comprador: " + facturas.get(i).dni + "\nData de transacció: " + facturas.get(i).data + "\n------------------------------------");
			System.out.println("Bitllet  -  preu");
			for(int x = 0; x<facturas.get(i).linea.size();x++) {
				System.out.println(facturas.get(i).linea.get(x).id_bitllet + "        -  " + facturas.get(i).linea.get(x).preu);
			}
			System.out.println("------------------------------------\nTOTAL: " + facturas.get(i).total);
		}

	}
	
	
	public int getNum_factura() {
		return num_factura;
	}
	public void setNum_factura(int num_factura) {
		this.num_factura = num_factura;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public ArrayList<Linea> getLinea() {
		return linea;
	}
	public void setLinea(ArrayList<Linea> linea) {
		this.linea = linea;
	}
	
	

}
