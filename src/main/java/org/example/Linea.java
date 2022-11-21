package org.example;

public class Linea {
	
	int num_factura;
	String id_bitllet;
	double preu;
	
	public Linea() {}
	public Linea(int num_factura, String id_bitllet, double preu) {
		this.num_factura = num_factura; this.id_bitllet = id_bitllet; this.preu = preu;
	}
	
	
	public int getNum_factura() {
		return num_factura;
	}
	public void setNum_factura(int num_factura) {
		this.num_factura = num_factura;
	}
	public String getId_bitllet() {
		return id_bitllet;
	}
	public void setId_bitllet(String id_bitllet) {
		this.id_bitllet = id_bitllet;
	}
	public double getPreu() {
		return preu;
	}
	public void setPreu(double preu) {
		this.preu = preu;
	}
	
	
	
	

}
