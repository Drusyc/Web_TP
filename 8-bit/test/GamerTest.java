import java.util.HashSet;
import java.util.Set;

import models.Configuration;
import models.Gamer;
import models.Genre;

import org.junit.Test;

import play.test.UnitTest;


public class GamerTest extends UnitTest {
	
	
	/*
	 * 
	 * Création d'un joueur avec pseudo + mail + 2 genres de jeu préf
	 * 
	 * Insertion dans la bd
	 * 
	 * Asserts :
	 * 	- Joueur bien trouvé
	 * 	- Tableau de genres bien récupéré dans la table GamersPreferredGenres
	 *  - Le tableau correspond à celui de départ
	 *  
	 */
	@Test
	public void createGamer() {
		
		Gamer ga = new Gamer();
    	
    	ga.setPseudo("createGamerTest");
    	ga.setMail("createGamerTest@test.com");
    	
    	Set<Genre> setulu = new HashSet<Genre>();
    	setulu.add(Genre.Action);
    	setulu.add(Genre.Role_Playing);
    	
    	ga.setPreferredGenres(setulu);
    	ga.save();
    	
    	String id = ga.getPseudo();
    	
    	Gamer jfound = Gamer.findById(id);
    	
    	assertNotNull(jfound);
    	assertEquals(jfound.getPreferredGenres().size(), 2);
    	assertEquals(jfound.getPreferredGenres(), setulu);
    	
   	}
	
	/*
	 * 
	 * Création d'un joueur avec pseudo + mail 
	 * 
	 * Insertion dans la bd
	 * 
	 * Récupération et suppression de l'entrée
	 * 
	 * Asserts :
	 * 	- Joueur bien trouvé
	 * 	- Joueur bien supprimé
	 *  
	 */
	@Test
	public void deleteGamer() {
		
		Gamer ga = new Gamer();
    	
    	ga.setPseudo("deleteGamerTest");
    	ga.setMail("deleteGamerTest@test.com");
    	ga.save();
    	
    	String id = ga.getPseudo();
    	
    	Gamer jfound = Gamer.findById(id);
    	assertNotNull(jfound);
    	
    	ga.delete();
    	
    	jfound = Gamer.findById(id);
    	assertNull(jfound);
	}
	
	
}
