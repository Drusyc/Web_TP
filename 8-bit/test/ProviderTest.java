import java.util.HashSet;
import java.util.Set;

import models.Game;
import models.Provider;

import org.junit.Test;

import play.test.UnitTest;


public class ProviderTest  extends UnitTest {
	
	/*
	 * Création d'un provider, insertion dans la base
	 *
	 */
	@Test
	public void createProvider () {
		/* Création d'un Provider */		
		Provider prov1 = new Provider("createProviderTest", "mdp", "createProviderTest@test.com",null); 
		prov1.save();
		
		assertNotNull(Provider.findById(prov1.getPseudo()));
		
		prov1.delete();
	}
	
	
	/*
	 * Création d'un provider, Insertion dans la bd
	 * 
	 * Récupération et suppression de l'entrée
	 *  
	 */
	@Test
	public void deleteGame() {
		/* Création d'un Provider */
		Provider prov1 = new Provider("deleteProviderTest", "mdp", "deleteProviderTest@test.com",null); 
		prov1.save();
		
		Provider found =  Provider.findById(prov1.getPseudo());
		assertEquals(prov1, found);
		
		prov1.delete();
		found = Game.findById(prov1.getPseudo());
		assertNull(found);
	}
	
	
	/*
	 * 
	 * Création d'un Provider
	 * 
	 * Le récupère et le modifie
	 *  
	 */
	@Test
	public void updateProvider() {	
		/* Création d'un Provider */
		Provider prov1 = new Provider("updateProviderTest", "mdp", "wrongMail@test.com",null); 
		prov1.save();
		
		Provider found = Provider.findById(prov1.getPseudo());
		assertEquals(prov1, found);
		assertEquals(prov1.getPseudo(), found.getPseudo());
		assertEquals(prov1.getMail(), found.getMail());
		
		String tmp = prov1.getMail();
		prov1.setMail("rightMail@test.com");
		assertNotEquals(tmp, prov1.getMail());
		
		prov1.save();
		found = Provider.findById(prov1.getPseudo());
		assertEquals(prov1.getPseudo(), found.getPseudo());
		assertNotEquals(tmp, found.getMail());
		assertEquals(prov1.getMail(), found.getMail());
		
		prov1.delete();
	}
	
	/*
	 * Mapping provider <=> game
	 */
	@Test
	public void providerGame () {
		/* Création d'un game */		
		Game game1 = new Game("Mario Kart 8", null); game1.save();
		Game game2 = new Game("Mario Kart 7", null); game2.save();
		Game game3 = new Game("Mario Kart 6", null); game3.save();
		Game game4 = new Game("RandomGame", null); game4.save();
		
		Set<Game> setGame = new HashSet<Game>();
		setGame.add(game1); setGame.add(game2); setGame.add(game3);
		
		/* Création d'un Provider */
		Provider prov1 = new Provider("Nihoné", "mdp", "Nihonhéarobaselu@test.com",null);
		prov1.setGames(setGame); prov1.save();
		
		/* Assert */
		Provider found = Provider.findById(prov1.getPseudo());
		assertEquals(prov1, found);
		assertTrue(found.getGames().containsAll(setGame));
		
		/* Ménage.. */
		prov1.delete();
		game1.delete();
		game2.delete();
		game3.delete();
		game4.delete();
	}
	
	

}
