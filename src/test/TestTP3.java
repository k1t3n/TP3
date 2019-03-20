package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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

	@Test
	public void testPlatExiste() {

		Plat plat = new Plat( "Poutine", 1.50 );
	
	}
	
	@Test
	public void testNomPresent() {
		
	}
	
	@Test
	public void testFormatNom() {
		
	}
	
	@Test
	public void testFormatPlat() {
		
	}
	
	@Test
	public void testChiffresErrones() {		
		
	}
	
	@Test
	public void testSortiFacture() {
		
	}

}
