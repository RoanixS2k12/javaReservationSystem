/*_______________________________________________________________________*/
/*
 * Créé le 4 nov. 2005 à 16:03:03
 */
package bureauVente;

/*_______________________________________________________________________*/
/**
 * @author Jean-Louis IMBERT
 */
/*_______________________________________________________________________*/
public class Place implements Comparable<Place>
{
	/** Rangée.*/
	private final String rangee ;
	
	/** Numéro de la place dans la rangée. */
	private final int num ;
	
	/** Libre. */
	private boolean libre;

	/*_______________________________________________________________________*/
	/** Les places sont numérotée à la construction et cette numérotation 
	 * est immuables.
	 * @param rangee La rangée.
	 * @param num Le numéro dans la rangée.
	 */
	protected Place(String rangee, int num)
	{
		this.rangee = rangee;
		this.num = num;
		this.libre = true;
	}
	/*_______________________________________________________________________*/
	/** Donne le statut de la place.
	 * @return Le statut sous la forme : "numéro de place 
	 * 		(libre ou réservée)".
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		if (libre)
			 return getName()+ "  (libre)" ;
		else return getName()+ "  (réservée)" ;
	}
	/*_______________________________________________________________________*/
	/** Indique si la place est libre ou non.
	 * @return Retourne <code>true</code> si la place est libre, 
	 * 		ou <code>false</code> si la place est réservée.
	 */
	public boolean isLibre()
	{
		return libre;
	}
	/*_______________________________________________________________________*/
	/** Permet de réserver ou de libérer la place.
	 * @param libre La valeur booléenne indiquant si la place sera
	 * 		libre ou non.
	 */
	void setLibre(boolean libre)
	{
		this.libre = libre;
	}
	/*_______________________________________________________________________*/
	/** Compare deux places suivant leurs numérotations. 
	 * La comparaison se fait par ordre alphabétique des rangées, 
	 * et par ordre de numéro dans la même rangée.
	 * 
	 * @param p La place à comparer à la place courante.
	 * @return Un entier négatif si la place courante précède p, positif
	 * 		si elle la suit, 0 si c'est la même place.
	 */
	public int compareTo(Place p)
	{
		return getName().compareTo(p.getName()) ;
	}
	/*_______________________________________________________________________*/
	/** Indique si un objet donné est égal à l'objet courant.
	 * @param p La place à comparer.
	 * @return <code>true</code> si les deux place sont égales, 
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
	 * rangée et son numéro dans la rangée.
	 * @param rangee La rangée de la place à réserver.
     * @param num Le numéro du siège à réserver.
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
