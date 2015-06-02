import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.Configuration;
import models.Game;
import models.Genre;
import models.Provider;

import org.junit.Test;

import play.test.UnitTest;


public class GameTest extends UnitTest {
	
	/*
	 * Création d'un game, insertion dans la base
	 *
	 */
	@Test
	public void createGame () {
		/* Création d'un game */		
		Game game1 = new Game("Mario Kart 8", null); 
		
		Set<String> setDev = new HashSet<String>(); setDev.add("Nintendo"); setDev.add("Bandai");
		Set<String> setMode = new HashSet<String>(); setMode.add("Solo"); setMode.add("Multi");
		Map<String, Date> setRelease = new HashMap<String, Date>();
		setRelease.put("EU", Date.valueOf("2014-05-30"));
		setRelease.put("USA", Date.valueOf("2014-05-30"));
		setRelease.put("JAPAN", Date.valueOf("2014-05-29"));
		
		game1.setReleaseDates(setRelease); game1.setDevelopers(setDev); game1.setModes(setMode); 
		game1.save();
		
		assertNotNull(Game.findById(game1.getName()));
		
		game1.delete();
	}
	
	
	/*
	 * Création d'un game, Insertion dans la bd
	 * 
	 * Récupération et suppression de l'entrée
	 *  
	 */
	@Test
	public void deleteGame() {
		/* Création d'un game */
		Game game1 = new Game("testCreateGame", null); game1.save();
		
		Game found = Game.findById(game1.getName());
		assertEquals(game1, found);
		
		game1.delete();
		found = Game.findById(game1.getName());
		assertNull(found);
	}
	
	
	/*
	 * 
	 * Création d'un game
	 * 
	 * Le récupère et le modifie
	 *  
	 */
	@Test
	public void updateGame() {	
		/* Création d'un game */
		Game game1 = new Game("updateGameName", null); game1.save();
		
		Game found = Game.findById(game1.getName());
		assertEquals(game1, found);
		assertEquals(game1.getName(), found.getName());
		assertNull(found.getDevelopers());
		
		Set<String> setDev = new HashSet<String>(); setDev.add("Nintendo");
		game1.setDevelopers(setDev);
		
		game1.save();
		found = Game.findById(game1.getName());
		assertEquals(game1.getName(), found.getName());
		assertNotNull(found.getDevelopers());
		
		game1.delete();
	}
	
	
	/*
	 * 
	 * Mapping entre Game <=> Genre
	 * 
	 */
	@Test
	public void gameGenres () {
		
		/* Création d'un game */
		Game game1 = new Game("8 Trak Oiram", null); 
		
		/* Création des genres RPG / ACTION / AVENTURE */
    	Genre ge1 = new Genre(Genre.RPG); ge1.save();
    	Genre ge2 = new Genre(Genre.Action); ge2.save();
    	Genre ge3 = new Genre(Genre.Aventure); ge3.save();
    	
    	Set<Genre> setGenre = new HashSet<Genre>();
    	setGenre.add(ge2); setGenre.add(ge3);
    	
    	game1.setGenres(setGenre);
    	game1.save();
    	
    	/* Assert */
    	Game found = Game.findById(game1.getName());
    	assertEquals(game1, found);
    	assertEquals(game1.getName(), found.getName());
    	
    	Set<Genre> tmp = found.getGenres();    	
    	assertEquals(tmp.size(),2);
    	assertTrue(tmp.contains(new Genre(Genre.Aventure)));
    	assertTrue(tmp.contains(new Genre(Genre.Action)));
    	assertFalse(tmp.contains(new Genre(Genre.RPG)));
    	
    	/* Ménage.. */    
    	game1.delete();	
    	ge1.delete();
    	ge2.delete();
    	ge3.delete();
	}
	
	/*
	 * Mapping game <=> Provider
	 */
	@Test
	public void gameProvider() {
		/* Création d'un Provider */
		Provider prov1 = new Provider("Oiram Srob", "mdp","Bowser@test.com",null); prov1.save();

		/* Création d'un game */
		Game game1 = new Game("8 Trak Oiram", null); 
		game1.setProvider(prov1); game1.save();
		
		/* Assert */
		Game found = Game.findById(game1.getName());
    	assertEquals(game1, found);
    	assertEquals(game1.getName(), found.getName());
    	assertEquals(game1.getProvider().getPseudo(), prov1.getPseudo());    	
		
		/*
		 * Ménage..
		 */
		game1.delete();
		prov1.delete();
	}
	
	/*
	 * Mapping game <=> Configuration
	 */
	@Test
	public void gameConfiguration () {
		/* Création avec seulement freeDiskSpace et RAM */
		Configuration conf1 = new Configuration("maConfig", 500, 2000); conf1.save();

		/* Création d'un game */
		Game game1 = new Game("8 Trak Oiram", null); 
		game1.setConfiguration(conf1); game1.save();
		
		/* Assert */
		Game found = Game.findById(game1.getName());
    	assertEquals(game1, found);
    	assertEquals(game1.getName(), found.getName());
    	assertEquals(game1.getConfiguration().getId(), conf1.getId());
		
		/*
		 * Ménage..
		 */
		game1.delete();
		conf1.delete();
	}
	
	

}
