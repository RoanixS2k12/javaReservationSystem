/*________________________________________________________*/
/**
 * Fichier : Salle.java
 *
 * créé le 27 sept. 2010 à 14:22:06
 *
 * Auteur : Jean-Louis IMBERT
 */
package bureauVente;

import java.util.TreeMap;

/*________________________________________________________*/
/**
 */
public class Salle
{
	/** le nom de la salle.*/
	private String nom ;
	/** Nombre de rangées. */
	private int nbRangees ;
	/** nombre de sièges par rangée. */
	private  int nbParRangee ;
	/** Le nombre de salles créées.*/
	private static int nbSalles = 0 ;
	/*________________________________________________________*/
	/**
	 * @param nom
	 * @param nbRangees
	 * @param nbParRangee
	 */
	public Salle(String nom, int nbRangees, int nbParRangee)
	{
		setNom(nom);
		setNbRangees(nbRangees) ;
		setNbParRangee(nbParRangee) ;
	}
	/*________________________________________________________*/
	/** Permet d'obtenir le nom de la salle.
	 * @return Le nom de la salle.
	 */
	public String getNom()
	{
		return nom;
	}
	/*________________________________________________________*/
	/** Modifie le nom de la salle. Ce nom est généré si 
	 * le nom donné en paramètre est incorrect.
	 * @param nom Le nom de la salle.
	 */
	private void setNom(String nom)
	{
		if (nom!=null && nom.trim().length()==0)
			nom = null ;
		if (nom==null)
			nom = "Salle "+(++nbSalles ) ;
		this.nom = nom;
	}
	/*________________________________________________________*/
	/** Modifie le nombre de rangées dans la salle.
	 * @param nbRangees Le nombre de rangées dans la salle.
	 */
	private void setNbRangees(int nbRangees)
	{
		this.nbRangees = nbRangees;
	}
	/*________________________________________________________*/
	/** Modifie le nombre de sièges par rangée dans la salle.
	 * @param nbParRangee Le nombre de sièges par rangée dans la salle.
	 */
	private void setNbParRangee(int nbParRangee)
	{
		this.nbParRangee = nbParRangee;
	}
	/*_______________________________________________________________________*/
	/** Création des places.
	 * @return nbRangees Une map des plpaces de la salle.
	 */
	public TreeMap<String,Place> listeDesPlaces()
	{
		/** La liste des places libres. */
		TreeMap<String,Place> listePlaces = new TreeMap<String,Place>() ;
		StringBuilder laRangee = new StringBuilder() ;
		
		for (int rang=0 ; rang<nbRangees ; rang++)
		{	incrementer(laRangee) ;
			String rangee = laRangee.toString();
			for (int num=1 ; num<=nbParRangee ; num++)
			{
				Place p = new Place(rangee,num) ;
				listePlaces.put(p.getName(),p) ;
			}
		}
		return listePlaces ;
	}
	/*_______________________________________________________________________*/
	/** Incrémenter le code des rangées.
	 * @param texte Le StringBuilder contenant le code rangée.
	 */
	private void incrementer(StringBuilder texte)
	{
		int i = texte.length() ;
		boolean full = true ;
		
		while (i>0)
		{
			char c = texte.charAt(--i) ;
			if (c=='Z')
				texte.setCharAt(i,'A') ;
			else 
			{
				texte.setCharAt(i,++c) ;
				full = false ;
				break ;
			}
		}
		if (full) texte.insert(0,'A') ;
	}
    /*_______________________________________________________________________*/
}

/*________________________________________________________*/
/* Fin du fichier Salle.java
/*________________________________________________________*/