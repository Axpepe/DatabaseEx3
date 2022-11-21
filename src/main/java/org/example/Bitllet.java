package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Bitllet implements InterficieGeneral {

	
	String id,id_vol, tipus_seient;
	double preu;
	int max_px;
	
	
	ArrayList<Bitllet> bitlletsTotal = new ArrayList<Bitllet>();
	
	public Bitllet() {
		
	}
	public Bitllet(String id, String id_vol,String tipus_seient, double preu, int max_px) {
		this.id = id; this.id_vol=id_vol; this.tipus_seient=tipus_seient; this.preu = preu; this.max_px = max_px;
	}
	
	
	public void importar(Connection conection, Statement st) throws SQLException {
		ResultSet r = st.executeQuery("select * from bitllet");
		while(r.next()) {
			String id = r.getString("id");
			String id_vol = r.getString("id_vol");
			//
			String tipus_seient = r.getString("tipus_seient");
			double preu = r.getDouble("preu");
			int max_px = r.getInt("max_px");
			
			Bitllet newBitllet = new Bitllet(id,id_vol,tipus_seient,preu,max_px);
			bitlletsTotal.add(newBitllet);
					
		}
	}
	public boolean crearObjecte(Connection conection, Statement st) throws SQLException {
		
		Scanner lector = new Scanner(System.in);
		ResultSet  r = st.executeQuery("select * from bitllet;");
		int n= 0; String id="1"; int intMax=0;
		while(r.next()) {
			
			int idC = Integer.parseInt(r.getString("id"));
			if(intMax < idC) {
				intMax = idC;
				id = String.valueOf(idC+1);	 
			}
			
		}
		
		System.out.println("ID :"  + id);
		System.out.println("*Vols visualitzar els vols? S/N");
		char option = lector.nextLine().toLowerCase().charAt(0);
		if(option == 's') {
			ResultSet rVols = st.executeQuery("select v.id,e.nom as origen,d.nom as desti,durada,dia,hora,max_ps,max_kg,hora_arribada from vol v inner join estacio e on v.id_origen = e.id inner join estacio d on v.id_desti=d.id;");
			while(rVols.next()) {
				
				System.out.println("["+rVols.getString("id")+"] - O:  "+
						rVols.getString("origen")+" - D: "+
						rVols.getString("desti")+" - Durada: "+
						rVols.getTime("durada")+" - Dia: "+rVols.getDate("dia")+" - Hora_Sortida :"+
						rVols.getTime("hora")+" - Hora_Arribada :" + rVols.getTime("hora_arribada")+" "
						);
			}
		
		}
		System.out.println("*Introdueix el id del vol");
		String id_vol = lector.nextLine();
	
		String[] seientNom = {"Normal","Luxe"};
		System.out.println("*Selecciona tipus de seient\n 1."+seientNom[0] +"\n2."+seientNom[1]);
		int opcioS  = lector.nextInt(); lector.nextLine();
		System.out.println("*Introdueix el preu per el seient de classe" +seientNom[opcioS-1]);
		double preu = lector.nextDouble(); lector.nextLine();
		System.out.println("*Introdueix màxim de persones");
		int max_persones = lector.nextInt(); lector.nextLine();
		
		Bitllet newBitllet = new Bitllet(id,id_vol,seientNom[opcioS-1],preu,max_persones);
		String sql = "INSERT INTO BITLLET VALUES('"+id+"',"+
		"'"+id_vol+"',"+
		"'"+seientNom[opcioS-1]+"',"+
		"'"+preu+"',"+
		"'"+max_persones+"');";
				
		boolean creat = true;
		try {
			st.executeUpdate(sql);
			bitlletsTotal.add(newBitllet);
		}
		catch(Exception e) {
			System.out.println(e);
			creat = false;
		}
		
		return creat;
	}

	public boolean updatejarObjecte(Connection conection, Statement st) {
		Scanner lector = new Scanner(System.in); boolean done = true;
		try {
			System.out.println("Introdueix ID del Bitllet");
			String id = lector.nextLine();
			int n=0;
			
			while(bitlletsTotal.size()>n && !(id.equals(bitlletsTotal.get(n).getId()))) {
				n++;
			}
			if(n < bitlletsTotal.size()) {
				System.out.println("*Selecciona item que vols canviar:\n\ta.Preu\n\tb.Màxim persones");
				char opcio = lector.nextLine().trim().toLowerCase().charAt(0);
						if(opcio == 'a') {
							System.out.println("*Introdueix el nou valor per preu    PREU ANTIC:"+ bitlletsTotal.get(n).getPreu());
							double preuNou = lector.nextDouble(); lector.nextLine();
							String sql ="update bitllet set preu='"+preuNou+"' where id='"+id+"';";
							bitlletsTotal.get(n).setPreu(preuNou);
							st.executeUpdate(sql);
						}
						else if(opcio=='b') {
							System.out.println("*Introdueix el nou valor per el Màxim   MÀXIM ANTIC:"+ bitlletsTotal.get(n).getMax_px());
							int maximNou = lector.nextInt(); lector.nextLine();
							String sql ="update bitllet set max_px='"+maximNou+"' where id='"+id+"';";
							bitlletsTotal.get(n).setMax_px(maximNou);
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
	public void show(Connection conection, Statement st) throws SQLException {
		ResultSet r = st.executeQuery("select * from bitllet");
		while(r.next()) {
			System.out.println("["+r.getString("id")+"] - "+r.getString("id_vol")+"- "+
					r.getString("tipus_seient")+" - "+r.getDouble("preu")+" - "+r.getInt("max_px"));
		
		}
	}

	public double getMaxKg(Connection con, Statement st, String id) throws SQLException {
		ResultSet  r = st.executeQuery("select a.id,a.max_ps,a.max_kg from vol a inner join bitllet b on a.id=b.id_vol where b.id=\'" + id + "\'");
		r.next();
		double maxKg= r.getDouble("max_kg")/r.getDouble("max_ps");
		return maxKg;
	}
	public void minusMaxPeople(Connection conection, Statement st,String id) {
		//cada bitllet que es compra es resta -1 al maxim de persones
		int n=0;
		
		while(bitlletsTotal.size()>n && !(id.equals(bitlletsTotal.get(n).getId()))) {
			n++;
		}
		if(n < bitlletsTotal.size() && bitlletsTotal.get(n).getMax_px() > 0) {
			int numero_persones = bitlletsTotal.get(n).getMax_px();
			try {
				bitlletsTotal.get(n).setMax_px(numero_persones-1);
				st.execute("update bitllet set max_px='"+bitlletsTotal.get(n).getMax_px()+"' where id='"+id+"';");
			}
			catch(Exception e) {
				System.out.println("Valor no canviat");
			}
		}
		
		
	}
	public void setBitlletsTotal(ArrayList<Bitllet> bitlletsTotal) {
		this.bitlletsTotal = bitlletsTotal;
	}
	public boolean deleteObjecte(Connection conection, Statement st) {
		boolean done = true;
		Scanner lector = new Scanner(System.in);
		

		try {
			int n=0;
			System.out.println("*Introduiex la id per esborrar: ");
			String id = lector.nextLine().trim();
			
			while(bitlletsTotal.size()>n && !(id.equals(bitlletsTotal.get(n).getId()))) {
				n++;
			}
			System.out.println("ID: "+ id );
			bitlletsTotal.remove(n);
			st.executeUpdate("delete from bitllet where id='"+id+"';");
		}
		catch(Exception e) {
			done = false; System.out.println("Error");
			System.out.print(e);
		}
		return done;
	}
	public boolean cerca(String id) {
		int n=0;
		while(bitlletsTotal.size()<n && !(id.equals(bitlletsTotal.get(n).getId()))) {
			n++;
		}
		if(n<bitlletsTotal.size()) {
			return true;
		}
		return false;
	}

	public int cercaInt(String id) {
		int n=0;
		while(bitlletsTotal.size()>n && !(id.equals(bitlletsTotal.get(n).getId()))) {
			n++;
		}
		return n;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId_vol() {
		return id_vol;
	}
	public void setId_vol(String id_vol) {
		this.id_vol = id_vol;
	}
	public String getTipus_seient() {
		return tipus_seient;
	}
	public void setTipus_seient(String tipus_seient) {
		this.tipus_seient = tipus_seient;
	}
	public double getPreu() {
		return preu;
	}
	public void setPreu(double preu) {
		this.preu = preu;
	}
	public int getMax_px() {
		return max_px;
	}
	public void setMax_px(int max_px) {
		this.max_px = max_px;
	}
	public ArrayList<Bitllet> getBitlletsTotal() {
		return bitlletsTotal;
	}

}
