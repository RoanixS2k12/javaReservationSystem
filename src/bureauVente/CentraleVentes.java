/*_______________________________________________________________________*/
/*
 * Cr�� le 4 nov. 2005 � 16:01:59
 */
package bureauVente;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
/*_______________________________________________________________________*/
/**
 * @author Jean-Louis IMBERT
 */
/*_______________________________________________________________________*/
public class CentraleVentes extends Observable 
	implements Serializable, Observer
{
	/** Num�ro de s�rialisation. */
	private static final long serialVersionUID = -2753597922754229346L;
	/** L'intance unique. */
	private static CentraleVentes instance ;
	/** Liste des spectacle. La cl� est constitu�e du titre du spectacle et de la date. */
	private Map<String, Spectacle> listeSpectacles = new HashMap<String, Spectacle>() ;
	/*_______________________________________________________________________*/
	/** Cr�ation de la centrale de des ventes, et des places � vendre.
	 */
	private CentraleVentes()
	{
	}
	/*________________________________________________________*/
	/** Permet d'obtenir la centrale des ventes.
	 * @return la centrale des ventes.
	 */
	public static CentraleVentes getInstance()
	{
		if (instance==null)
			instance = new CentraleVentes() ;
		return instance ;
	}
	/*________________________________________________________*/
	/** Cr�ation d'un nouveau spectacle.
	 * @param titre Le titre du spectacle.
	 * @param date La date et l'heure du spectacle.
	 * @param salle La salle dans laquelle le spectacle va se d�rouler.
	 */
	public void addSpectacle(String titre, GregorianCalendar date, Salle salle)
	{
		String cle = getCleSpectacle(titre, date) ;
		if (!listeSpectacles.containsKey(cle))
			listeSpectacles.put(cle, new Spectacle(titre, date, salle)) ;
		listeSpectacles.get(cle).addObserver(this) ;
	}
	/*________________________________________________________*/
	/** Cr�e une cl� pour une spectacle d'apr�s le titre et la date donn�e.
	 * @param titre Le titre du spectacle.
	 * @param date La date du spectacle.
	 * @return La cl� associ�e au titre et � la date donn�e.
	 */
	public String getCleSpectacle(String titre, GregorianCalendar date)
	{
		return titre+";"+dateToString(date) ;
	}
	/*________________________________________________________*/
	/** Transforme une date en cha�ne de caract�res "JJ/MM/AA-hh:mm:ss".
	 * @param date La date � transformer.
	 * @return La date sous forme d'une cha�ne de caract�res de la
	 * 		forme "JJ/MM/AA-hh:mm:ss"..
	 */
	public String dateToString(GregorianCalendar date)
	{
		Date dat = date.getTime() ;
		DateFormat d = DateFormat.getDateInstance(DateFormat.SHORT);
		DateFormat t = DateFormat.getTimeInstance(DateFormat.MEDIUM);
		String texte = d.format(dat)+"-"+t.format(dat);
		return texte ;
	}
	/*________________________________________________________*/
	/** Transforme une date donn�e sous forme de cha�ne de caract�res
	 * en une GregorianCalendar.
	 * @param texte Un texte repr�sentant une date sous la forme
	 * 		"JJ/MM/AA-hh:mm:ss".
	 * @return Une GregorianCalendar repr�sentat la date donn�e en
	 * 		param�tre, ou <code>null</code> si la date n'est pas
	 * 		correcte.
	 */
	public GregorianCalendar stringToDate(String texte)
	{
		String[] tab = texte.split("-");
		String[] tabDate = tab[0].split("/");
		String[] tabTime = tab[1].split(":");
		GregorianCalendar date = null ;
		if (tabDate.length==3 && tabTime.length==3)
		{
			try
			{
				int jour = Integer.parseInt(tabDate[0]) ;
				int mois = Integer.parseInt(tabDate[1])-1 ;
				int annee = Integer.parseInt(tabDate[2]) ;
				if (annee<30) annee+=2000 ;
				else annee+=1900 ;
				int heures = Integer.parseInt(tabTime[0]) ;
				int minutes = Integer.parseInt(tabTime[1]) ;
				int secondes = Integer.parseInt(tabTime[2]) ;
				date = new GregorianCalendar(annee, mois, jour, heures, minutes, secondes);
			} catch (Exception e){}
		}
		return date ;
	}
    /*_______________________________________________________________________*/
    /** Informer les abonn�es s'il n'y a plus de place, 
     * ou s'il n'y en avait plus et que quelques places se sont lib�r�es.
     * @param source la source du message.
     * @param objet l'objet du message.
     */
    public void update(Observable source, Object objet)
    {
    	if (source instanceof Spectacle)
    	{
    		Spectacle concert = (Spectacle)source ;
    		List<Object> info = new ArrayList<Object>() ;
    		info.add(concert.getTitre()) ;
    		info.add(concert.getdate()) ;
    		info.add(objet) ;
    		setChanged();
    		notifyObservers(info) ;
    	}
    }
	/*________________________________________________________*/
	/** R�serve les places demand�es sauf s'il reste moins de places. 
	 * Dans ce cas, la liste retourn�e est la liste des places encore 
	 * disponibles, mais elle ne sont pas encore r�serv�es.
	 * @param titreConcert Le titre du concert.
	 * @param date La date du concert.
	 * @param nbPlacesDemandees Le nombre de places demand�es
	 * @return Une liste de places r�serv�es s'il y a assez de place 
	 * 		pour satidfaire la demande, la liste des num�ros de places
	 * 		encore disponibles dans le cas contraire.
	 */
	public List<String> getPlaces(String titreConcert, GregorianCalendar date, int nbPlacesDemandees)
	{
		Spectacle concert = listeSpectacles.get(getCleSpectacle(titreConcert, date));
		int nbDispo = concert.getNbPlacesDispo() ;
		List<String> listePlaces ;
		if (nbDispo<nbPlacesDemandees)
			listePlaces = concert.getPlacesDisponibles() ;
		else
		{
			listePlaces = new ArrayList<String>() ;
			while (nbPlacesDemandees-->0)
				listePlaces.add(concert.reserver()) ;
		}
		return listePlaces ;
	}
	/*________________________________________________________*/
	/** Lib�re une place.
	 * @param titreSpectacle Le titre du spectacle.
	 * @param date La date du spectacle.
	 * @param place Le num�ro de la place.
     * @return Le libell� du spectacle, y compris date et lieu, si la place a �t� lib�r�e, 
     * 		<code>null</code> si la place n'est pas r�serv�e ou n'existe pas.
	 */
	public String liberer(String titreSpectacle, GregorianCalendar date, String place)
	{
		Spectacle spectacle = listeSpectacles.get(getCleSpectacle(titreSpectacle, date));
		if (spectacle.liberer(place)) 
			return spectacle.toStringShort() ;
		else return null ;
	}
    /*_______________________________________________________________________*/
    /** Description des places du concert.
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuilder texte = new StringBuilder() ;
		texte.append("_______________________________________\n");
        for (Spectacle sp : listeSpectacles.values())
        	texte.append(sp).append("\n_________________________________\n") ;
        return texte.toString() ;
    }
}

/*_______________________________________________________________________*/
/*      Fin du fichier CentraleVentes.java
/*_______________________________________________________________________*/
