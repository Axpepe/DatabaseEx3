package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws SQLException {
		Vol inventariVols = new Vol(); 
		Bitllet inventariBitllets = new Bitllet();
		Estacio inventariEstacions = new Estacio(); 
		Client inventariClients = new Client();
		Factura fact = new Factura();
		char choose2 = ' ';
		///USUARIS
		String admPassword = "1234"; String admName = "adm";
		String userPassword="1234";  String userName ="user";
		boolean connectat = false;
		boolean connectatUser = false;
		Connection conexio = DriverManager.getConnection("jdbc:postgresql://localhost:5432/vol","postgres","BH09880988");
		Statement statement = conexio.createStatement();
		//IMPORTACIO de dades
		inventariVols.importar(conexio, statement); inventariBitllets.importar(null, statement);
		inventariEstacions.importar(conexio, statement); inventariClients.importar(conexio, statement);
		String nomUsuari =" "; String contrasenyaUsuari=" ";
		boolean open = true; char  userOption = ' '; String nomAdministrador = " ",contrasenyaAdministrador = " ";
		char optionC =' '; String dni="";
		
		Scanner lector = new Scanner(System.in);
		
		
		do {
			System.out.println("--Ménu principal--"
					+ "  --Selecciona el teu estat--:\n\ta.Usuari\n\tb.Administrador");
			System.out.println("useroption: " +userOption);
				userOption = lector.nextLine().toLowerCase().charAt(0);
				if(userOption == 'a') { //SI ES USUARI
						System.out.println("a.Accedir com Usuari B.Crear Client C.Sortir");
						optionC=lector.nextLine().toLowerCase().charAt(0);
						if(optionC == 'a') {//accedir com usuari
							if(!connectatUser) {
								if (dni.equals("")) {
									System.out.println("*Introdueix el seu dni");
									dni = lector.nextLine();
									nomUsuari = dni;
								}
							}
							if(inventariClients.clientsTotal.contains(inventariClients.cercar(dni)) || connectatUser) {
								connectatUser = true;
								System.out.println("--Seleccioni a quin apartat vol inserir--:\n\ta.Updatejar\n\tb.Esborrar\n\tc.Mirar Vols\n\td.Mirar Bitllets\n\te.Comprar Bitllet\n\th.Informacio\n\ti.Factures usuari");
								String opcioApartat = lector.nextLine().toLowerCase();
								switch(opcioApartat) {
									case"a"://UPDATEJAR
										inventariClients.updatejarObjecte(conexio, statement);
									break;
									case"b"://ESBORRAR
										inventariClients.deleteObjecte(conexio, statement);
										optionC = ' '; userOption=' ';
										break;
									case"c"://MIRAR VOLS
										inventariVols.show(conexio, statement);
										break;
									case"d"://MIRAR BITLLETS
										inventariBitllets.show(conexio, statement);
										break;
									case"e"://COMPRAR VOLS
										boolean finished = false;
										ArrayList<Linea> lineas = new ArrayList<>();
										int idFactura = fact.getIdFactura(conexio,statement);
										double total = 0;
										do{
											inventariBitllets.show(conexio,statement);
											System.out.println("Quin bitllet vols comprar?");
											String choose = lector.nextLine();
											if (inventariBitllets.cerca(choose)) {
												double pes = inventariBitllets.getMaxKg(conexio,statement,choose);
												inventariBitllets.minusMaxPeople(conexio,statement,choose);
												System.out.println("Quant pes d'equipatje portaràs?(Max: " + pes + ")");
												double pesPersona = lector.nextDouble();lector.nextLine();
												if(pesPersona<=pes) {
													double preu = inventariBitllets.getBitlletsTotal().get(inventariBitllets.cercaInt(choose)).preu;
													lineas.add(new Linea(idFactura, choose, preu));
													total += preu;
													System.out.println("Vols continuar comprant?(S/N)");
													choose2 = lector.nextLine().toLowerCase().charAt(0);
												}
												else {
													System.out.println("Pes maxim sobrepasat");
												}
											if(choose2=='n') {
												finished=true;
											}
											}
											else System.out.println("Bitllet no trobat");
										}while(!finished);
										Factura factura = new Factura(idFactura,dni, LocalDate.now(),total,lineas,conexio,statement);
										break;
									case"h"://informacio
										Client clientInfo = inventariClients.cercar(dni);
										System.out.println(clientInfo.getDni() +": "+ clientInfo.getNom()+"-"+clientInfo.getCognom()+"-"+clientInfo.getMail()+"-"+clientInfo.getTelefon()+"-"+clientInfo.getData_naixement());
										break;
									case"i":
										fact.show(conexio,statement,dni);
										break;
								}
							}
							else {
								System.out.println("**Client amb dni: "+ dni +" no existeix");
								optionC=' ';
							}
							
						}
						else if(optionC == 'b') { //crear client
							System.out.println("Segur que vols crear un nou client? S/N");
							optionC = lector.nextLine().toUpperCase().charAt(0);
							if(optionC == 'S') {
								inventariClients.crearObjecte(conexio, statement);
								optionC=' ';
							}
							else {
								optionC =' ';
							}
						}
						else if(optionC == 'c') {
							userOption = ' ';
						}
					
					
					
				}
				else if((userOption == 'b')) { //SI ES ADMINISTRADOR
					if(!connectat) {
						System.out.println("Introdueixi el seu nick com Administrador");
						nomAdministrador = lector.nextLine().toLowerCase();
					}
					if(nomAdministrador.equalsIgnoreCase(admName) || connectat) {
						if (!connectat) {
							System.out.println("Introdueixi la seva contrasenya com Administrador");
							contrasenyaAdministrador = lector.nextLine().toLowerCase();
						}
						if(contrasenyaAdministrador.equalsIgnoreCase(admPassword) || connectat) {
							connectat= true;
							System.out.println("--Seleccioni a quin apartat vol inserir:--\n\ta.Vol\n\tb.Estacio\n\tc.Bitllet\n\td.Sortir");
							String opcioApartat = lector.nextLine().toLowerCase();
							switch(opcioApartat) {
							case"a": //Vol
								System.out.println("--APARTAT:    VOL--\n\t1.Crear\n\t2.Updatejar\n\t3.Esborrar\n\t4.Show");
								String op = lector.nextLine();
								switch(op) {
									case"1"://CREAR
										if(!(inventariVols.crearVol(conexio, statement))) {
											System.out.println("--Hi ha hagut un problema amb aquesta funció--");
										}
										break;
									case"2"://UPDATEJAR
										System.out.println("--No es pot editar informació sobre els vols--");
										break;
									case"3"://ESBORRAR
										System.out.println("*Insereix la IP del vol que vol esborrar");
										String id = lector.nextLine();
										if(!(inventariVols.deleteVol(conexio, statement, op))) {
											System.out.println("--Hi ha hagut un problema amb aquesta funció--");
										}
										break;
									case"4"://SHOW
										inventariVols.show(conexio, statement);
										break;
										
								}
								break;
							case"b": //estacio
								System.out.println("--APARTAT:    ESTACIO--\n\t1.Crear\n\t2.Updatejar\n\t3.Esborrar\n\t4.Show");
								String op1 = lector.nextLine();
								switch(op1) {
									case"1"://CREAR
										if(!(inventariEstacions.crearEstacio(conexio, statement))) {
											System.out.println("--Hi ha hagut un problema amb aquesta funció--");
										}
										break;
									case"2"://UPDATEJAR
										if(!(inventariEstacions.updatejarEstacio(conexio, statement))) {
											System.out.println("--Hi ha hagut un problema amb aquesta funció--");
										}
										break;
									case"3"://ESBORRAR
										if(!(inventariEstacions.deleteEstacio(conexio, statement))) {
											System.out.println("--Hi ha hagut un problema amb aquesta funció--");
										}
										break;
									case"4"://SHOW
										inventariEstacions.show(conexio, statement);
										break;
										
								}
								break;
							case"c": //bitllet
								System.out.println("--APARTAT:    BITLLET--\n\t1.Crear\n\t2.Updatejar\n\t3.Esborrar\n\t4.Show");
								String op2 = lector.nextLine();
								switch(op2) {
									case"1"://CREAR
										
										if(!(inventariBitllets.crearObjecte(conexio, statement))) {
											System.out.println("--Hi ha hagut un problema amb aquesta funció--");
										}
										break;
									case"2"://UPDATEJAR
										if(!(inventariBitllets.deleteObjecte(conexio, statement))) {
											System.out.println("--Hi ha hagut un problema amb aquesta funció--");
										}
										break;
									case"3"://ESBORRAR
										if(!(inventariBitllets.deleteObjecte(conexio, statement))) {
											System.out.println("--Hi ha hagut un problema amb aquesta funció--");
										}
										break;
									case"4"://SHOW
										inventariBitllets.show(conexio, statement);
										break;
										
								}
								break;
								case "d":
									break;
							}
						}
					}
					else System.out.println("Nom incorrecte");
				}

		}while(open);
	}
}
