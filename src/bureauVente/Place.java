/*_______________________________________________________________________*/
/*
 * Cr�� le 4 nov. 2005 � 16:03:03
 */
package bureauVente;

/*_______________________________________________________________________*/
/**
 * @author Jean-Louis IMBERT
 */
/*_______________________________________________________________________*/
public class Place implements Comparable<Place>
{
	/** Rang�e.*/
	private final String rangee ;
	
	/** Num�ro de la place dans la rang�e. */
	private final int num ;
	
	/** Libre. */
	private boolean libre;

	/*_______________________________________________________________________*/
	/** Les places sont num�rot�e � la construction et cette num�rotation 
	 * est immuables.
	 * @param rangee La rang�e.
	 * @param num Le num�ro dans la rang�e.
	 */
	protected Place(String rangee, int num)
	{
		this.rangee = rangee;
		this.num = num;
		this.libre = true;
	}
	/*_______________________________________________________________________*/
	/** Donne le statut de la place.
	 * @return Le statut sous la forme : "num�ro de place 
	 * 		(libre ou r�serv�e)".
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		if (libre)
			 return getName()+ "  (libre)" ;
		else return getName()+ "  (r�serv�e)" ;
	}
	/*_______________________________________________________________________*/
	/** Indique si la place est libre ou non.
	 * @return Retourne <code>true</code> si la place est libre, 
	 * 		ou <code>false</code> si la place est r�serv�e.
	 */
	public boolean isLibre()
	{
		return libre;
	}
	/*_______________________________________________________________________*/
	/** Permet de r�server ou de lib�rer la place.
	 * @param libre La valeur bool�enne indiquant si la place sera
	 * 		libre ou non.
	 */
	void setLibre(boolean libre)
	{
		this.libre = libre;
	}
	/*_______________________________________________________________________*/
	/** Compare deux places suivant leurs num�rotations. 
	 * La comparaison se fait par ordre alphab�tique des rang�es, 
	 * et par ordre de num�ro dans la m�me rang�e.
	 * 
	 * @param p La place � comparer � la place courante.
	 * @return Un entier n�gatif si la place courante pr�c�de p, positif
	 * 		si elle la suit, 0 si c'est la m�me place.
	 */
	public int compareTo(Place p)
	{
		return getName().compareTo(p.getName()) ;
	}
	/*_______________________________________________________________________*/
	/** Indique si un objet donn� est �gal � l'objet courant.
	 * @param p La place � comparer.
	 * @return <code>true</code> si les deux place sont �gales, 
	 * 		<code>false</code> sinon.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object p)
	{
		boolean reponse = false ;
		if (p instanceof Place)
			reponse = (compareTo((Place)p)==0) ;
		return reponse ;
	}
	/*________________________________________________________*/
	/** Retourne le code de hashage de la place.
	 * @return le code de hashage de la place.
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return getName().hashCode() ;
	}
	/*________________________________________________________*/
	/** Permet d'obtenir le nom de la place.
	 * @return Le nom de la place.
	 */
	public String getName()
	{
		return getName(rangee, num) ;
	}
	/*________________________________________________________*/
	/** Permet d'obtenir le nom d'une place connaissant sa 
	 * rang�e et son num�ro dans la rang�e.
	 * @param rangee La rang�e de la place � r�server.
     * @param num Le num�ro du si�ge � r�server.
	 * @return Le nom de la place.
	 */
	public static String getName(String rangee, int num)
	{
		return String.format("%-2s %03d", rangee, num) ;
	}
}

/*_______________________________________________________________________*/
/*      Fin du fichier Place.java
/*_______________________________________________________________________*/
