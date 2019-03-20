package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Facture {


	



	public static void main(String[] args) {
		try {
			readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void readFile() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader( System.getProperty("user.dir") + "\\bin\\main\\commandes.txt"));
			
			ArrayList<String> data = new ArrayList<String>();
			String currentLine;
			
			while ((currentLine = br.readLine()) != null) {
				data.add(currentLine);
			}
			
			br.close();
			
			try {
			createClients(data);
			createPlats(data);
			attribuerCommandes(data);
			printFactures();
			} catch (Exception e) {
				System.out.println("Le fichier ne respecte pas le format demandé !");
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void createClients(ArrayList<String> info) {
		int debut = info.indexOf("Clients :");
		int fin = info.indexOf("Plats :");
		
		for (int i = debut + 1; i < fin; i++) {
			if (info.get(i).chars().allMatch(Character::isLetter)) {
				Client.listeClients.add(new Client(info.get(i)));
			} else {
				System.out.println("Erreur de client : " + info.get(i));
			}
		}
		
	}
	
	public static void createPlats(ArrayList<String> info) {
		int debut = info.indexOf("Plats :");
		int fin = info.indexOf("Commandes :");

		for (int i = debut + 1; i < fin; i++) {
			String[] parts = info.get(i).split(" ");
			if (!parts[0].matches("^[a-zA-Z0-9_]*$")) {
				System.out.println("Erreur dans le nom du plat : " + parts[0]);
				continue;
			}
			try {
				Plat.plats.add(new Plat(parts[0], Double.parseDouble(parts[1])));
			} catch (Exception e) {
				System.out.println("Erreur de prix dans le plat : " + parts[0]);
				continue;
			}
		}

	}
	
	public static void attribuerCommandes(ArrayList<String> info) {
		int debut = info.indexOf("Commandes :");
		int fin = info.indexOf("Fin");
		
		for (int i = debut + 1; i < fin; i++) {
			String[] parts = info.get(i).split(" ");
			Client client = Client.getAvecNom(parts[0]);
			if (client == null) {
				continue;
			}
			Plat plat = Plat.getAvecNom(parts[1]);
			if (plat == null) {
				continue;
			}
			int quantite;
			try {
				quantite = Integer.parseInt(parts[2]);
			} catch (Exception e) {
				System.out.println("Erreur dans la quantité de la commande : " + parts[0] + " " + parts[1] + " " + parts[2]);
				continue;
			}
			Commande cmd = new Commande(client, plat, quantite);
			client.commandes.add(cmd);
		}
	}
	
	public static void printClient() {
		Client.listeClients.forEach(client -> {
			System.out.println(client.toString());
		});
	}
	
	public static void printFactures() {
		System.out.println("Bienvenue chez Barette!");
		System.out.println("Factures:");
		DecimalFormat df =  new DecimalFormat("###,##0.00$"); 
		for (Client c : Client.listeClients) {
			System.out.println(c.nom + " " + df.format(c.getPrix()) );
		}
	}
	




}
