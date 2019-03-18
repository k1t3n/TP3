import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Facture {


	static ArrayList<String> erreurs = new ArrayList<String>();


	public static void main(String[] args) {
		try {
			readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void readFile() throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader( System.getProperty("user.dir") + "\\bin\\commandes.txt"));
			
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
			sauvegarderFactures();
			createClients(data);
			createPlats(data);
			attribuerCommandes(data);
			printFactures();
			} catch (Exception e) {
				System.out.println("Le fichier ne respecte pas le format demand� !");
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void sauvegarderFactures() {
		PrintStream out;
		try {
			Calendar c = Calendar.getInstance();
			out = new PrintStream(new FileOutputStream("Facture-du-" + c.get(Calendar.YEAR) + "Y" + c.get(Calendar.MONTH) + "M" + c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.HOUR) + "H" + c.get(Calendar.MINUTE) + ".txt"));
			System.setOut(out);
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
				erreurs.add("Erreur de client : " + info.get(i) + "\nExplication : Le nom doit �tre des caract�res.");
			}
		}
		
	}
	
	public static void createPlats(ArrayList<String> info) {
		int debut = info.indexOf("Plats :");
		int fin = info.indexOf("Commandes :");

		for (int i = debut + 1; i < fin; i++) {
			String[] parts = info.get(i).split(" ");
			if (!parts[0].matches("^[a-zA-Z0-9_]*$")) {
				erreurs.add("Erreur dans le nom du plat : " + parts[0] + "\nExplication : Le nom du plat doit �tre des caract�res.");
				continue;
			}
			try {
				Plat.plats.add(new Plat(parts[0], Double.parseDouble(parts[1])));
			} catch (Exception e) {
				erreurs.add( "Erreur de prix dans le plat : " + parts[0] + "\nExplication : Le prix du plat doit �tre des chiffres.");
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
				erreurs.add( "Erreur dans la quantit� de la commande : " + parts[0] + " " + parts[1] + " " + parts[2] 
						+ "\nExplication : Format attendu : Nom + Plat + Quantit�");			 
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
		printErreur();
		System.out.println("Bienvenue chez Barette!");
		System.out.println("Factures:");
		DecimalFormat df =  new DecimalFormat("###,##0.00$"); 
		for (Client c : Client.listeClients) {
			if (c.getPrix() != 0)
			System.out.println(c.nom + " " + df.format(c.getPrix()) );
		}
	}
	public static void printErreur() {
		
		if ( !erreurs.isEmpty() ) {
			System.out.println( "Erreur survenu : " );

			for ( String string : erreurs ) {
				System.out.println( string  + "\n");
			}

			System.out.println();
	}
	}
}
