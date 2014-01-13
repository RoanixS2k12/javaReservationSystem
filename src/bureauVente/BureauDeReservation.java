/*_______________________________________________________________________*/
/*
 * Créé le 5 nov. 2005 à 22:26:54
 */
package bureauVente;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import simulateur.UneSortie;

/*_______________________________________________________________________*/
/**
 * @author Jean-Louis IMBERT
 */
/*_______________________________________________________________________*/
public class BureauDeReservation implements Observer
{
	/** centrale des ventes. */
	CentraleVentes centrale ;
	
	/** nom du bureau de réservation. */
	String name ;

	/** Le nombre de bureaux de réservation créés. */
	private static int nbBureaux = 0;

	/*_______________________________________________________________________*/
	/** Constructeur unique.
	 * @param name Le nom du bureau de réservation.
	 */
	public BureauDeReservation(String name)
	{
		setName(name) ;
		setCentrale() ;
	}
	/*________________________________________________________*/
	/** Modifie le nom du bureau de réservation. Si le nom 
	 * donné en paramètre est null ou vide, un nom sera généré.
	 * @param name Le nom du bureau.
	 */
	private void setName(String name)
	{
		if (name!=null && name.trim().length()==0)
			name = null ;
		if (name==null)
			name = "Bureau de réservation "+(++nbBureaux ) ;
		this.name = name;
	}
	/*_______________________________________________________________________*/
	/** Associe une centrale de ventes au bureau.
//	 * @param centrale La centrale à associer.
	 */
//	public void setCentrale(CentraleVentes centrale)
	private void setCentrale()
	{
		if (this.centrale==null)
		{
			this.centrale = CentraleVentes.getInstance();
			centrale.addObserver(this) ;
		}
	}
	/*_______________________________________________________________________*/
	/** Reserver des places pour un spectacle.
	 * @param nom Le nom de la personne qui réserve.
	 * @param nbPlacesDemandees Le nombre de places à réserver.
	 * @param titreSpectacle Le titre du spectacle ;
	 * @param date La date du spectacle.
	 * @return Le nombre de places réellement réservées.
	 */
	public int reserver(String nom, int nbPlacesDemandees, String titreSpectacle, 
			GregorianCalendar date)
	{
		List<String> placesDispo = centrale.getPlaces(titreSpectacle, date, nbPlacesDemandees) ;
		StringBuffer texte = new StringBuffer() ;
		if (placesDispo.size()<nbPlacesDemandees)
		{
			texte.append(name).append(" : pas assez de places pour ") ;
			texte.append(nom).append(" au spectacle ").append(titreSpectacle).append(" du ") ;			
			texte.append(String.format("%1$td/%1$tM/%1$tY à %1$tHh%1$tM", date)).append("\n") ;
			texte.append(" il ne reste que ").append((placesDispo.size())).append(" places / ") ;
			texte.append(nbPlacesDemandees).append(" demandées.") ;
			nbPlacesDemandees = 0 ;
		}
		else
		{
			texte.append(name).append(" : place réservée(s) --> ").append(nbPlacesDemandees) ;
			texte.append(" par ").append(nom).append(" au spectacle ").append(titreSpectacle) ;
			texte.append(" du ").append(String.format("%1$td/%1$tM/%1$tY à %1$tHh%1$tM", date)) ;
		}
		UneSortie.out.println(texte) ;
		return nbPlacesDemandees ;
	}
	/*_______________________________________________________________________*/
	/** libérer une place.
	 * @param titreSpectacle Le titre du spectacle.
	 * @param date La date du spectacle.
	 * @param place La place à libérer.
	 */
	public void liberer(String titreSpectacle, GregorianCalendar date, String place)
	{
		String spectacle = centrale.liberer(titreSpectacle, date, place) ;
		if (spectacle!=null)
			UneSortie.out.println(
				String.format("%s : %s libérée --> %s", name, spectacle ,place)) ;
	}
	/*_______________________________________________________________________*/
	/** Mise à jour. 
	 * @param o La centrale de ventes
	 * @param arg Les informations concernant la mise à jour.
	 */
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg)
	{
		if (o instanceof CentraleVentes)
		{
			@SuppressWarnings("unchecked")
			List<Object> info = (List<Object>) arg ;
			StringBuffer texte = new StringBuffer() ;
			texte.append("Bureau ").append(getName()).append(" : ") ;
			String titreSpectacle = info.get(0).toString() ;
			texte.append(titreSpectacle).append(" le ") ;
			String date = String.format("%1$td/%1$tM/%1$tY à %1$tHh%1$tM", info.get(1)) ;
			texte.append(date).append(" : ") ;
			String complet = (((Boolean)info.get(2)).booleanValue()? "complet" : "places disponibles") ;
			texte.append(complet).append(".") ;
			UneSortie.out.println(texte) ;
		}
	}
	/*_______________________________________________________________________*/
	/** Retourne le nom du bureau de réservation.
	 * @return Retourne le nom du bureau de réservation.
	 */
	public String getName()
	{
		return name;
	}
}

/*_______________________________________________________________________*/
/*      Fin du fichier bureauDeReservation.java
/*_______________________________________________________________________*/
