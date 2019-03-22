package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;
import main.Client;
import main.Commande;
import main.Facture;
import main.Plat;

public class TestTP3 {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCreatePlatErreur() {
		Client.listeClients.clear();

		ArrayList<String> info = new ArrayList<String>();
		info.add("Plats :");
		info.add("Poutine 10.5");
		info.add( "22" );
		info.add( "Commandes :" );

		Facture.createPlats( info );
		Assert.assertFalse( Facture.erreurs.isEmpty() );
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCreatePlatErreurNom() {
		Client.listeClients.clear();

		ArrayList<String> info = new ArrayList<String>();
		info.add("Plats :");
		info.add("Poutine 10,5");
		info.add( "P@utine 10,5" );
		info.add( "Commandes :" );

		Facture.createPlats( info );
		Assert.assertFalse( Facture.erreurs.isEmpty() );
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testErreursClients() {
		String[] data = {"Clients :", " ", "12385" ,"Plats :"};
		Facture.createClients(new ArrayList<String>(Arrays.asList(data)));
		Assert.assertNotNull(Facture.erreurs.get(0), Facture.erreurs);
	}
	@Test
	@SuppressWarnings("deprecation")
	public void testValideClients() {
		String[] data = {"Clients :", " ", "Paul" ,"Plats :"};
		Facture.createClients(new ArrayList<String>(Arrays.asList(data)));
		Assert.assertNotNull(Facture.erreurs.get(0), Facture.erreurs);
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void testAttribuerClientInexistant() {
		Client.listeClients.clear();

		ArrayList<String> info = new ArrayList<String>();
		info.add( "Clients : ");
		info.add("Plats :");
		info.add("Poutine 10,5");
		info.add( "Commandes :" );
		info.add( "C�line Poutine 1" );
		info.add( "Fin" );

		Facture.attribuerCommandes( info );
		Assert.assertFalse( Facture.erreurs.isEmpty() );
		
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void testAttribuerPlatInexistant() {
		Client.listeClients.clear();

		ArrayList<String> info = new ArrayList<String>();
		info.add( "Clients : ");
		info.add( "Roger" );
		info.add("Plats :");
		info.add( "Commandes :" );
		info.add( "Roger test 1" );
		info.add( "Fin" );
		
		Facture.createClients( info );
		Facture.attribuerCommandes( info );
		Assert.assertFalse( Facture.erreurs.isEmpty() );
		
	}
	


}
