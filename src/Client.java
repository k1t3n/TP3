import java.util.ArrayList;

public class Client {

	public static ArrayList<Client> listeClients = new ArrayList<Client>();
	
	
	String nom;
	ArrayList<Commande> commandes = new ArrayList<Commande>();
	
	
	public Client() {
		
	}
	
	
	public Client(String nom) {
		this.nom = nom;
		this.commandes = new ArrayList<Commande>();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(nom);
		for (Commande cmd : commandes) {
			sb.append(" " + cmd.plat.nom + " " + cmd.quantite);
		}
		return sb.toString();
	}
	
	
	public static Client getAvecNom(String nom) {
		for (Client c : listeClients) {
			if (c.nom.equals(nom)) {
				return c;
			}
		}
		return null;
	}
	
	public double getPrix() {
		double prix = 0;
		double TPS = 0.05;
		double TVQ = 0.1;
		double aAjouter;
		for (Commande cmd : commandes) {
			aAjouter = cmd.plat.prix * TPS;
			aAjouter += cmd.plat.prix * TVQ;
			prix += ( cmd.plat.prix  + aAjouter )* cmd.quantite;
		}
		return prix;
	}
	
}
