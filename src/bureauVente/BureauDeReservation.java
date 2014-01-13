/*_______________________________________________________________________*/
/*
 * Cr�� le 5 nov. 2005 � 22:26:54
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
	
	/** nom du bureau de r�servation. */
	String name ;

	/** Le nombre de bureaux de r�servation cr��s. */
	private static int nbBureaux = 0;

	/*_______________________________________________________________________*/
	/** Constructeur unique.
	 * @param name Le nom du bureau de r�servation.
	 */
	public BureauDeReservation(String name)
	{
		setName(name) ;
		setCentrale() ;
	}
	/*________________________________________________________*/
	/** Modifie le nom du bureau de r�servation. Si le nom 
	 * donn� en param�tre est null ou vide, un nom sera g�n�r�.
	 * @param name Le nom du bureau.
	 */
	private void setName(String name)
	{
		if (name!=null && name.trim().length()==0)
			name = null ;
		if (name==null)
			name = "Bureau de r�servation "+(++nbBureaux ) ;
		this.name = name;
	}
	/*_______________________________________________________________________*/
	/** Associe une centrale de ventes au bureau.
//	 * @param centrale La centrale � associer.
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
	 * @param nom Le nom de la personne qui r�serve.
	 * @param nbPlacesDemandees Le nombre de places � r�server.
	 * @param titreSpectacle Le titre du spectacle ;
	 * @param date La date du spectacle.
	 * @return Le nombre de places r�ellement r�serv�es.
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
			texte.append(String.format("%1$td/%1$tM/%1$tY � %1$tHh%1$tM", date)).append("\n") ;
			texte.append(" il ne reste que ").append((placesDispo.size())).append(" places / ") ;
			texte.append(nbPlacesDemandees).append(" demand�es.") ;
			nbPlacesDemandees = 0 ;
		}
		else
		{
			texte.append(name).append(" : place r�serv�e(s) --> ").append(nbPlacesDemandees) ;
			texte.append(" par ").append(nom).append(" au spectacle ").append(titreSpectacle) ;
			texte.append(" du ").append(String.format("%1$td/%1$tM/%1$tY � %1$tHh%1$tM", date)) ;
		}
		UneSortie.out.println(texte) ;
		return nbPlacesDemandees ;
	}
	/*_______________________________________________________________________*/
	/** lib�rer une place.
	 * @param titreSpectacle Le titre du spectacle.
	 * @param date La date du spectacle.
	 * @param place La place � lib�rer.
	 */
	public void liberer(String titreSpectacle, GregorianCalendar date, String place)
	{
		String spectacle = centrale.liberer(titreSpectacle, date, place) ;
		if (spectacle!=null)
			UneSortie.out.println(
				String.format("%s : %s lib�r�e --> %s", name, spectacle ,place)) ;
	}
	/*_______________________________________________________________________*/
	/** Mise � jour. 
	 * @param o La centrale de ventes
	 * @param arg Les informations concernant la mise � jour.
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
			String date = String.format("%1$td/%1$tM/%1$tY � %1$tHh%1$tM", info.get(1)) ;
			texte.append(date).append(" : ") ;
			String complet = (((Boolean)info.get(2)).booleanValue()? "complet" : "places disponibles") ;
			texte.append(complet).append(".") ;
			UneSortie.out.println(texte) ;
		}
	}
	/*_______________________________________________________________________*/
	/** Retourne le nom du bureau de r�servation.
	 * @return Retourne le nom du bureau de r�servation.
	 */
	public String getName()
	{
		return name;
	}
}

/*_______________________________________________________________________*/
/*      Fin du fichier bureauDeReservation.java
/*_______________________________________________________________________*/
