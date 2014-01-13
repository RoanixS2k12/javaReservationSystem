/*_______________________________________________________________________*/
/*
 * Créé le 5 nov. 2005 à 20:58:01
 */
package test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import bureauVente.*;
import simulateur.* ;

/*_______________________________________________________________________*/
/**
 * @author Jean-Louis IMBERT
 */
/*_______________________________________________________________________*/
public class Test
{
	List<Salle> salles = new ArrayList<Salle>() ;
	List<String> spectacles = new ArrayList<String>() ;
	List<GregorianCalendar> dateSpectacles = new ArrayList<GregorianCalendar>() ;
	List<BureauDeReservation> bureaux = new ArrayList<BureauDeReservation>() ;
	CentraleVentes centrale ;
	/*_______________________________________________________________________*/
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Test t = new Test() ;
		t.init() ;
		t.test2() ;
//		t.test3();
	}
	
	/*________________________________________________________*/
	/**
	 */
	private void init()
	{
		salles.add(new Salle("Jean Cocteau", 2, 3)) ;
		salles.add(new Salle("Zénith Auvergne", 3, 5)) ;
		salles.add(new Salle("Jean-Philippe Rameau", 2, 4)) ;

		centrale = CentraleVentes.getInstance() ;

		nouveauSpectacle("J. Halliday", 
				new GregorianCalendar(2010, 10, 4, 20, 30), 
				salles.get(1)) ;
		nouveauSpectacle("Concert de Noël", 
				new GregorianCalendar(2010, 11, 26, 21, 00), 
				salles.get(0)) ;	
		
		bureaux.add(new BureauDeReservation("Fnac Jaude")) ;
		bureaux.add(new BureauDeReservation("Syndicat d'initiative")) ;
		bureaux.add(new BureauDeReservation("Office du Tourisme")) ;

	}
	/*________________________________________________________*/
	/** Mise en place d'un nouveau spectacle.
	 * @param titre Le titre du spectacle.
	 * @param date La date et l'heure du spectacle.
	 * @param salle La salle dans laquelle le spectacle va se dérouler.
	 */
	private void nouveauSpectacle(String titre,
			GregorianCalendar date, Salle salle)
	{
		spectacles.add(titre) ;
		dateSpectacles.add(date) ;
		centrale.addSpectacle(titre, date, salle) ;
	}
	/*________________________________________________________*/
	/**
	 */
	private void test2()
	{
		BureauDeReservation bureau1 = bureaux.get(0) ;
		BureauDeReservation bureau2 = bureaux.get(1) ;
		System.out.println(centrale) ;
		bureau1.reserver("Jean",3,spectacles.get(1), dateSpectacles.get(1)) ;
		bureau2.reserver("Marie", 1,spectacles.get(1), dateSpectacles.get(1));
		bureau2.reserver("Samy",4,spectacles.get(0), dateSpectacles.get(0));
		System.out.println(centrale) ;
		bureau1.reserver("Sophie",5,spectacles.get(1), dateSpectacles.get(1)) ;
		bureau1.reserver("Quentin",2,spectacles.get(1), dateSpectacles.get(1)) ;
		bureau1.reserver("Kévin",1,spectacles.get(0), dateSpectacles.get(0)) ;
		System.out.println(centrale) ;
		bureau2.liberer(spectacles.get(0), dateSpectacles.get(0), Place.getName("A", 3));
		bureau2.liberer(spectacles.get(1), dateSpectacles.get(1), Place.getName("A", 2)) ;
		System.out.println(centrale) ;
	}
	
}

/*_______________________________________________________________________*/
/*      Fin du fichier Test.java
/*_______________________________________________________________________*/
