package org.example;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Vol implements InterficeObject {
    String id_vol;
	
	String id_origen;
	String id_desti;
	
	

	Duration durada;
	LocalDateTime dia_hora_sortida;
	LocalDateTime dia_hora_arribada;
	
	int max_p;
	double max_kl;
	
	
	
	protected ArrayList<Vol> volsTotal = new ArrayList<Vol>();
	public Vol() {}
	public Vol(String id_vol,String id_origen, String id_desti,Duration durada, LocalDateTime h_a,LocalDateTime h_s,int max_p, double max_kl) {
	this.id_vol = id_vol; this.id_origen = id_origen; this.id_desti = id_desti;  this.durada = durada; 
	this.dia_hora_arribada = h_a; this.dia_hora_sortida = h_s;
	this.max_p = max_p; this.max_kl = max_kl;
	}
	
	static Connexio con = new Connexio();
	
	public boolean crearVol(Connection con, Statement str) throws SQLException {
		
		Scanner lector = new Scanner(System.in);
		
		int day,month,year;
		int hours,minutes,second;
		
		int n = 0; String id = "1"; int intMax=0;
		ResultSet  r = str.executeQuery("select * from vol;");
		while(r.next()) {
			int idC = Integer.parseInt(r.getString("id"));
			if(intMax < idC) {
				intMax = idC;
				id = String.valueOf(idC+1);	 
			}
             }
		
				
		
		System.out.print("ID   :"+id);
		System.out.println("*Introduir id del origen: ");
		String id_origen = lector.nextLine().trim().toLowerCase();
		System.out.println("*Introduit id del destí:");
		String id_desti = lector.nextLine().trim().toLowerCase();
		//data del vol
		System.out.println("*Introduit data del vol DD/MM/YEAR");
		String dia = lector.nextLine();
		String dia_split []= dia.split("/");
		
		day =Integer.parseInt(dia_split[0]);
		month = Integer.parseInt(dia_split[1]);
		year = Integer.parseInt(dia_split[2]);
		
		System.out.println("*Introduit hora d'arribada HH:MIN:");
		String dt_arribada = lector.nextLine();
		String data_arribada_split [] = dt_arribada.split(":");
		
		hours = Integer.parseInt(data_arribada_split[0]);
		minutes = Integer.parseInt(data_arribada_split[1]);
		
		
		LocalDateTime data_hora_arribada = LocalDateTime.of(year, month, day, hours, minutes);
		
			
		System.out.println("*Introduit hora de sortida HH:MIN:");
		String dt_sortida = lector.nextLine();
		String data_sotida_split [] = dt_sortida.split(":");
		
		hours = Integer.parseInt(data_sotida_split[0]);
		minutes = Integer.parseInt(data_sotida_split[1]);
		
		LocalDateTime data_hora_sortida = LocalDateTime.of(year, month, day, hours, minutes);
		Duration durada = Duration.between(data_hora_sortida,data_hora_arribada);
		
		long tempsMinutsTotal = durada.toMinutes();
		int horesDurada = 0,minutsDurada = 0;
		while(!(tempsMinutsTotal==0)) {
			if(tempsMinutsTotal >= 60) {
				horesDurada +=1;
				tempsMinutsTotal -= 60;
			}
			if(tempsMinutsTotal <= 0) {
				tempsMinutsTotal = 0;
			}
			else if(tempsMinutsTotal <= 59 ) {
				minutsDurada+=1;
				tempsMinutsTotal-=1;
			}
		}
		System.out.println("*Introdueix el máxim de persones:");
		int mx_persones = lector.nextInt(); lector.nextLine();
		
		System.out.println("*Introdueix el máxim de kl: ");
		double mx_kl = lector.nextDouble(); lector.nextLine();
		
		String sql = "INSERT INTO VOL VALUES ('"+id+"',"+
		"'"+id_origen+"',"+
		"'"+id_desti+"',"+
		
		"'"+horesDurada+":"+minutsDurada+"',"+
			"'"+data_hora_sortida.getDayOfMonth()+"-"+data_hora_sortida.getMonthValue()+"-"+data_hora_sortida.getYear()+"',"+
			"'"+data_hora_sortida.getHour()+":"+data_hora_sortida.getMinute()+"',"+
					"'"+mx_persones+"',"+
					"'"+mx_kl+"',"+
					"'"+data_hora_arribada.getHour()+":"+data_hora_arribada.getMinute()+"');";
		
		Vol newVol = new Vol(id,id_origen,id_desti,durada,data_hora_arribada,data_hora_sortida, mx_persones, mx_kl);
	
		
		boolean creat = true;
		try {
			str.executeUpdate(sql);
			volsTotal.add(newVol);
		}
			
		catch(Exception e) {
			System.out.println(e);
			 creat = false;
		}
		return creat;
	}
	public void importar(Connection conexio, Statement st) throws SQLException {
		ResultSet  r = st.executeQuery("select *  from vol");
		while(r.next()) {
			String id = r.getString("id");
			String id_origen = r.getString("id_origen");
			String id_desti = r.getString("id_desti");
			//
			int max_persones = r.getInt("max_ps");
			double max_kilos = r.getDouble("max_kg");
			//
			//Date dia = r.getDate("dia");
			//
			//Time hora_arribada = r.getTime("hora_arribada");
			//Time hora_sortida = r.getTime("hora");
			//Time durada = r.getTime("durada");
			//CONVERSIO
			LocalTime hora_arribada_converted = r.getTime("hora_arribada").toLocalTime();
			LocalTime hora_sortida_converted = r.getTime("hora").toLocalTime();
			LocalDate dia_vol = r.getDate("dia").toLocalDate();
			//
			LocalDateTime dia_hora_arribada = LocalDateTime.of(dia_vol, hora_arribada_converted);
			LocalDateTime dia_hora_sortida = LocalDateTime.of(dia_vol, hora_sortida_converted);
			//
			Duration duradaConverted = Duration.between(dia_hora_sortida, dia_hora_arribada);
			//
			Vol newVol = new Vol(id,id_origen,id_desti,duradaConverted,dia_hora_arribada, dia_hora_sortida,max_persones,max_kilos);
			volsTotal.add(newVol);
		}
	}
	@Override
	public Vol volCercar(String id) {
		
		int n=0;
		while(volsTotal.size()<n && !(id.equals(volsTotal.get(n).getId_vol()))) {
			n++;
		}
		return volsTotal.get(n);
	}
	/*
	 * ELS OBJECTES VOL NECESITEN PASSAR PARAMETRES ID DEL VOL PER EL MAIN
	 */
	
	@Override
	public boolean deleteVol(Connection conection, Statement st, String idVol) {
		boolean done = true;
		try {
			int n=0;
			while(volsTotal.size()>n && !(idVol.equals(volsTotal.get(n).getId_vol()))) {
				n++;
			}
			volsTotal.remove(n);
			st.executeUpdate("delete from vol where id='"+idVol+"';");
		}
		catch(Exception e) {
			System.out.println(e);
			done = false;
		}
		// statement.executeUpdate("delete from persona where dni='"+dniEsborrar+"';");
		return done;
	}
	
	public void show(Connection conection, Statement st) throws SQLException {
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



//////GETTERS AND SETTERS
	
	
	public String getId_vol() {
		return id_vol;
	}

	public void setId_vol(String id_vol) {
		this.id_vol = id_vol;
	}

	public String getId_origen() {
		return id_origen;
	}

	public void setId_origen(String id_origen) {
		this.id_origen = id_origen;
	}

	public String getId_desti() {
		return id_desti;
	}

	public void setId_desti(String id_desti) {
		this.id_desti = id_desti;
	}

	public Duration getDurada() {
		return durada;
	}

	public void setDurada(Duration durada) {
		this.durada = durada;
	}

	public LocalDateTime getDia_hora_sortida() {
		return dia_hora_sortida;
	}

	public void setDia_hora_sortida(LocalDateTime dia_hora_sortida) {
		this.dia_hora_sortida = dia_hora_sortida;
	}

	public LocalDateTime getDia_hora_arribada() {
		return dia_hora_arribada;
	}

	public void setDia_hora_arribada(LocalDateTime dia_hora_arribada) {
		this.dia_hora_arribada = dia_hora_arribada;
	}

	public int getMax_p() {
		return max_p;
	}

	public void setMax_p(int max_p) {
		this.max_p = max_p;
	}

	public double getMax_kl() {
		return max_kl;
	}

	public void setMax_kl(double max_kl) {
		this.max_kl = max_kl;
	}
	
	@Override
	public boolean updatejarVol(Connection conection, Statement st, Vol volObjecte) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Vol volCercar(String id, ArrayList<Vol> volsTotals) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
