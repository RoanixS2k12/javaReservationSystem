/*________________________________________________________*/
/**
 * Fichier : Spectacle.java
 *
 * créé le 27 sept. 2010 à 14:38:51
 *
 * Auteur : Jean-Louis IMBERT
 */
package bureauVente;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

/*________________________________________________________*/
/**
 */
public class Spectacle extends Observable
{
	/** Titre du spectacle. */
	private String titre ;
	/** Heure du spectacle. */
	private GregorianCalendar date ;
	/** Salle du spectacle. */
	private Salle salle ;
	/** La liste des places libres. */
	private TreeMap<String,Place> libres ;
	/** La liste des places louées. */
	private Map<String,Place> reservees = new TreeMap<String,Place>() ;
	/** Drapeau à <code>true</code> si la salle est complète. */
	private boolean complet = false ;
	/*________________________________________________________*/
	/** Constructeur unique.
	 * @param titre Le titre du spectacle.
	 * @param date La date et l'heure du spectacle.
	 * @param salle La salle dans laquelle le spectacle va se dérouler.
	 */
	public Spectacle(String titre, GregorianCalendar date, Salle salle)
	{
		setTitre(titre);
		setDate(date);
		setSalle(salle);
	}
	/*________________________________________________________*/
	/** Permet d'obtenir le titre du spectacle.
	 * @return Le titre du spectacle.
	 */
	public String getTitre()
	{
		return titre;
	}
	/*________________________________________________________*/
	/** Modifie le titre du spectacle.
	 * @param titre Le titre du spectacle.
	 */
	private void setTitre(String titre)
	{
		this.titre = titre;
	}
	/*________________________________________________________*/
	/** Permet d'obtenir la date et l'heure du spectacle.
	 * @return La date et l'heure du spectacle.
	 */
	public GregorianCalendar getdate()
	{
		return date;
	}
	/*________________________________________________________*/
	/** Modifie la date et l'heure du spectacle.
	 * @param date La date et l'heure du spectacle.
	 */
	private void setDate(GregorianCalendar date)
	{
		this.date = date;
	}
	/*________________________________________________________*/
	/** Permet d'obtenir la salle dans laquelle le spectacle va se dérouler.
	 * @return La salle dans laquelle le spectacle va se dérouler.
	 */
	public Salle getSalle()
	{
		return salle;
	}
	/*________________________________________________________*/
	/** Modifie la salle dans laquelle le spectacle va se dérouler.
	 * @param salle La salle dans laquelle le spectacle va se dérouler.
	 */
	private void setSalle(Salle salle)
	{
		this.salle = salle;
		libres = salle.listeDesPlaces() ;
	}
    /*_______________________________________________________________________*/
    /** Retourne une chaîne de caractères correspondant aux places
     * disponibles.
     * @return Retourne une chaîne de caractères correspondant aux 
     * 		places disponibles.
     */
    public String placesLibres()
    {
        return listePlaces(libres) ;
    }
    /*_______________________________________________________________________*/
    /** Retourne la liste des numéros des places libres.
     * @return Retourne a liste des numéros des places libres.
     */
    public List<String> getPlacesDisponibles()
    {
    	return new ArrayList<String>(libres.keySet()) ;
    }
    /*_______________________________________________________________________*/
    /** Retourne une chaîne de caractères correspondant aux places
     * réservés.
     * @return Retourne une chaîne de caractères correspondant aux 
     * 		places réservés.
     */
    public String placesReservees()
    {
        return listePlaces(reservees) ;
    }
    /*_______________________________________________________________________*/
    /** Retourne une chaîne de caractères correspondant aux places
     * de la liste donnée.
     * @param liste La liste des places accompagnées de leurs clés.
     * @return Retourne une chaîne de caractères correspondant aux 
     * 		places de la liste donnée.
     */
    private String listePlaces(Map<String,Place> liste)
    {
        StringBuilder texte = new StringBuilder() ;
        for (Iterator<Place> it = liste.values().iterator(); it.hasNext();)
            texte.append(it.next()+"\n") ;
        return texte.toString() ;
    }
    /*_______________________________________________________________________*/
    /** Description des places du spectacle.
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuilder texte = new StringBuilder() ;
		texte.append(toStringShort()).append("\n") ;
        texte.append(placesReservees()) ;
        texte.append("-----------------\n") ;
        texte.append(placesLibres()) ;
        return texte.toString() ;
    }
    /*_______________________________________________________________________*/
    /** Description des places du spectacle.
     * @return Le libellé du spectacle, y compris date et lieu.
     * @see java.lang.Object#toString()
     */
    public String toStringShort()
    {
        StringBuilder texte = new StringBuilder(titre) ;
		String date = String.format("%1$td/%1$tM/%1$tY à %1$tHh%1$tM", getdate()) ;
        texte.append(" du ").append(date) ;
        texte.append("  (salle ").append(salle.getNom()).append(")") ;
        return texte.toString() ;
    }
    /*_______________________________________________________________________*/
    /** Réserver une place.
     * @param rangee La rangée de la place à réserver.
     * @param siege Le numéro du siège à réserver.
     * @return <code>true</code> si la réservation s'est déroulée
     * 		normalement, <code>false</code> si la place est déjà réservée.
     */
    public boolean reserver(String rangee, int siege)
    {
    	String num = Place.getName(rangee, siege);
    	boolean libre = libres.containsKey(num) ;
        if (libre)
        {
        	Place place = libres.remove(num) ;
        	place.setLibre(false) ;
        	reservees.put(num,place) ;
        	informer();
        }
        return libre ;
    }
    /*_______________________________________________________________________*/
    /** Réserver une place.
     * @return Le numéro de la place réservée.
     */
    public String reserver()
    { 	String key = null ;
    	if (libres.size()!=0)
    	{
    		key = libres.firstKey() ;
        	Place place = libres.remove(key) ;
        	place.setLibre(false) ;
        	reservees.put(place.getName(),place) ;
        	informer();
        }
        return key ;
    }
    /*_______________________________________________________________________*/
    /** Liberer une place.
     * @param rangee La rangée de la place réservée.
     * @param siege Le numéro du siège réservée.
     * @return <code>true</code> si la libération s'est déroulée
     * 		normalement, <code>false</code> si la place est déjà libre.
     */
    public boolean liberer(String rangee, int siege)
    {
    	String num = Place.getName(rangee, siege) ;
    	return liberer(num);
    }
    /*_______________________________________________________________________*/
    /** Liberer une place.
     * @param num Le numéro de la place à libérer.
     * @return <code>true</code> si la libération s'est déroulée
     * 		normalement, <code>false</code> si la place est déjà libre.
     */
    public boolean liberer(String num)
    {
    	boolean informer = libres.isEmpty() ;
    	boolean libere = reservees.containsKey(num) ;
        if (libere)
        {
        	Place place = reservees.remove(num) ;
        	place.setLibre(true) ;
        	libres.put(num,place) ;
       		if (informer) informer() ;
        }
        return libere ;
    }
    /*_______________________________________________________________________*/
    /** Informer les abonnées s'il n'y a plus de place, 
     * ou s'il n'y en avait plus et que quelques places se sont libérées.
     */
    private  void informer()
    {
    	if ((libres.size()==0 && !complet)
    		|| (libres.size()!=0 && complet))
    	{
    		complet = !complet ;
    		setChanged();
    		notifyObservers(new Boolean(complet)) ;
    	}
    }
    /*_______________________________________________________________________*/
    /** Indique s'il reste des places.
     * @return Retourne <code>true</code> s'il reste des places,
     * 		<code>false</code> si toutes les places sont réservées.
     */
    public boolean isComplet()
    {
    	return placesDisponibles()==0 ;
    }
    /*_______________________________________________________________________*/
    /** Retourne le nombre de places encore disponibles.
     * @return Retourne le nombre de places encore disponibles.
     */
    public int placesDisponibles()
    {
    	return libres.size();
    }
	/*________________________________________________________*/
	/** Retourne le nombre de places disponibles.
	 * @return Le nombre de places disponibles.
	 */
	public int getNbPlacesDispo()
	{
		return libres.size();
	}
}

/*________________________________________________________*/
/* Fin du fichier Spectacle.java
/*________________________________________________________*/