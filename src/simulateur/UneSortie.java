/*_______________________________________________________________________*/
/*
 * Créé le 6 nov. 2005 à 07:33:02
 */
package simulateur;

import java.io.*;

/*_______________________________________________________________________*/
/**
 * @author Jean-Louis IMBERT
 */
/*_______________________________________________________________________*/
public class UneSortie
{
	/*_______________________________________________________________________*/
	/*_______________________________________________________________________*/
	/** fichier de sortie */
	public static PrintStream out = System.out ;
	/*_______________________________________________________________________*/
	/** Ouverture du fichier de sortie.
	 * @param name Le nom du fichier de sortie.
	 */
	public static void ouvrir(String name)
	{
		try
		{
			out = new PrintStream(
				new BufferedOutputStream(new FileOutputStream(name))) ;
		}catch (IOException e)
		{ouvrir() ;}
	}
	/*_______________________________________________________________________*/
	/** Ouverture de la sortie par défaut (la sortie standard).
	 */
	public static void ouvrir()
	{
		out = System.out ;
	}
	/*_______________________________________________________________________*/
	/** Fermer le fichier de sortie.
	 */
	public static void close()
	{
		out.close() ;
	}
}

/*_______________________________________________________________________*/
/*      Fin du fichier Sortie.java
/*_______________________________________________________________________*/
