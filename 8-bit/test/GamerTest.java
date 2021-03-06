import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import models.Configuration;
import models.Gamer;
import models.Genre;
import models.OS;
import models.Processor;
import models.VideoCard;

import org.junit.Test;

import play.test.UnitTest;


public class GamerTest extends UnitTest {
	
	
	/*
	 * 
	 * Création d'un joueur avec pseudo + mail
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
		
		Gamer ga = new Gamer("createGamer - UnitTest", "mdp", "createGamerTest@test.com",null);ga.save();
    	
    	String id = ga.getPseudo();
    	
    	Gamer jfound = Gamer.findById(id);
    	
    	assertNotNull(jfound);
    	ga.delete();
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
		
		Gamer ga = new Gamer("deleteGamer - UnitTest", "mdp", "deleteGamerTest@test.com", null);ga.save();
    	
    	String id = ga.getPseudo();
    	
    	Gamer jfound = Gamer.findById(id);
    	assertNotNull(jfound);
    	
    	ga.delete();
    	
    	jfound = Gamer.findById(id);
    	assertNull(jfound);
	}
	
	
	/*
	 * 
	 * Création d'un joueur avec pseudo + mail 
	 * 
	 * Le récupère et le modifie
	 *  
	 */
	@Test
	public void updateGamer() {	
		Gamer ga = new Gamer("updateGamer - UnitTest", "mdp", "wrongMail@test.com", null);ga.save();
		
		Gamer found = Gamer.findById(ga.getPseudo());
		assertEquals(ga, found);
		
		String tmp = found.getMail();
		
		ga.setMail("rightMail@test.com"); ga.save();
		assertNotEquals(ga.getMail(), tmp);
		
		found = Gamer.findById(ga.getPseudo());
		assertEquals(ga.getMail(), found.getMail());
		assertEquals(ga, found);
		
		ga.delete();
	}
	
	/*
	 * 
	 * Création d'un joueur avec pseudo + mail 
	 * 
	 * Création de genres pour la table Genres : RPG/ACTION/AVENTURE
	 * 
	 * Mapping entre genres et joueurs
	 *  
	 */
	@Test
	public void gamerGenres() {
		
		/* Création des users Toto et Titi */
		Gamer ga1 = new Gamer("Toto - UnitTest", "mdp", "Toto@test.com", null); ga1.save();
		Gamer ga2 = new Gamer("Titi - UnitTest", "mdp", "Titi@test.com", null); ga2.save();
		
		/* Création des genres RPG / ACTION / AVENTURE */
    	Genre ge1 = new Genre("RPG - UnitTest"); ge1.save();
    	Genre ge2 = new Genre("Action - UnitTest"); ge2.save();
    	Genre ge3 = new Genre("Aventure - UnitTest"); ge3.save();
    	
    	/* Mapping entre le gamer : Toto et les genres : RPG/ACTION */
    	Set<Genre> setga = new HashSet<Genre>();
    	setga.add(ge1);
    	setga.add(ge2);
    	ga1.setPreferredGenres(setga);
    	ga1.save();
    	
    	/* Mapping entre le gamer : Titi et les genres : RPG/ACTION/AVENTURE */
    	Set<Genre> setga1 = new HashSet<Genre>();
    	setga1.add(ge1);
    	setga1.add(ge2);
    	setga1.add(ge3);
    	ga2.setPreferredGenres(setga1);
    	ga2.save();	
    	
    	/* ASSERT */
    	Gamer found = Gamer.findById("Toto - UnitTest");
    	    	
    	Set<Genre> tmp = found.getPreferredGenres();    	
    	assertEquals(tmp.size(),2);
    	assertTrue(tmp.contains(ge1));
    	assertTrue(tmp.contains(ge2));
    	assertFalse(tmp.contains(ge3));
    	
    	found = Gamer.findById("Titi - UnitTest");
    	
    	tmp = found.getPreferredGenres();
    	assertEquals(tmp.size(),3);
    	assertTrue(tmp.contains(ge1));
    	assertTrue(tmp.contains(ge2));
    	assertTrue(tmp.contains(ge3));
    	assertFalse(tmp.contains(new Genre("Reflexion - UnitTest")));
    	
    	ga1.delete();
    	ga2.delete();
    	ge1.delete();
    	ge2.delete();
    	ge3.delete();
	}

	/*
	 * 
	 * Création d'un joueur avec pseudo + mail 
	 * 
	 * Création de config
	 * 
	 * Mapping entre config et joueurs
	 *  
	 */
	@Test
	public void gamerConfig() {

		/* Création des users Toto et Titi */
		Gamer ga1 = new Gamer("Toto - UnitTest", "mdp", "Toto@test.com", null); ga1.save();
		Gamer ga2 = new Gamer("Titi - UnitTest", "mdp", "Titi@test.com", null); ga2.save();
		
		/* Création d'une configuration avec seulement freeDiskSpace et RAM */
		Configuration conf1 = new Configuration("maConfig - UnitTest", 0.5, 2.0); conf1.save();
		
		/* Création d'une configuration avec freeDiskSpace, RAM, Processor / VideoCard / OS */
			/* * Création de Processor * */
			Processor proc = new Processor("i7-3517u - UnitTest", "Intel", 1900.0, 2); proc.save();
			Set<Processor> setProc = new HashSet<Processor>(); setProc.add(proc);
		
			/* * Création de la VideoCard * */
			VideoCard vidCard = new VideoCard("GeForce GT 635M - UnitTest", "NVIDIA", 900, "11"); vidCard.save();
			Set<VideoCard> setVD = new HashSet<VideoCard>(); setVD.add(vidCard); 
		
			/* * Création de l'OS * */
			OS win81 = new OS("Windows - UnitTest", "8.1", Date.valueOf("2013-10-17")); win81.save();
			OS win7 = new OS("Windows - UnitTest", "7", Date.valueOf("2009-10-22")); win7.save();
			
			Set<OS> setOS = new HashSet<OS>(); setOS.add(win7); setOS.add(win81);
			
		Configuration conf2 = new Configuration("maConfig - UnitTest", 0.1, 4.0, setOS, setProc, setVD); conf2.save();
		
		
		/* Mapping Toto <=> conf1 */
		Set<Configuration> setConf1 = new HashSet<Configuration>(); 
		setConf1.add(conf1);
		
		ga1.setConfigurations(setConf1); ga1.save();
		
		/* Mapping Titi <=> conf2 */
		Set<Configuration> setConf2 = new HashSet<Configuration>(); 
		/* /!\ Une conf ne peut être associé qu'à un unique utilisateur /!\ */
		//setConf2.add(conf1); // <--- plante car déjà associé à ga1
		setConf2.add(conf2);
		ga2.setConfigurations(setConf2); ga2.save(); // <-- violation contrainte

		/* 
		 * 
		 * Menage.. 
		 * /!\ Le gamer doit être supprimé avant de supprimer la conf, sinon violation de contrainte de référence
		 * 
		 * */
		ga1.delete();
		ga2.delete();
		conf1.delete();
		conf2.delete();
		proc.delete();
		vidCard.delete();
		win81.delete();
		win7.delete();
	}

}
