import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.*;

import play.test.*;
import play.mvc.*;
import play.mvc.Http.*;
import models.*;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }
    
    
    /*
     * 
     * 
     * 
     */
    @Test
    public void fullDbTest () {
    	/*
    	 * ******* GENRES **********
    	 */
    	Genre genreRPG = new Genre(Genre.RPG); genreRPG.save();
    	Genre genreAction = new Genre(Genre.Action); genreAction.save();
    	Genre genreAventure = new Genre(Genre.Aventure); genreAventure.save();
    	Genre genreStrategie = new Genre(Genre.Strategie); genreStrategie.save();
    	Genre genreReflexion = new Genre(Genre.Reflexion); genreReflexion.save();
    	
    	/*
    	 * ******** OS ************
    	 */
    	
		OS win81 = new OS("Windows", 8.1); win81.save();
		OS win7 = new OS("Windows", 7.0); win7.save();
		OS winXP = new OS("Windows", 6.0); winXP.save();
    	
    	/*
    	 * ******* GAMES ***********
    	 */
		Game game1 = new Game("Starcraft 2", null); 
		
		Set<String> setDev = new HashSet<String>(); setDev.add("Blizzard Entertainment"); 
		Set<String> setMode = new HashSet<String>(); setMode.add("Solo"); setMode.add("Multi");
		Map<String, Date> setRelease = new HashMap<String, Date>();
		setRelease.put("EU", Date.valueOf("2012-07-27"));
		setRelease.put("USA", Date.valueOf("2012-07-27"));
		setRelease.put("JAPAN", Date.valueOf("2012-07-27"));
		Set<Genre> setGenre = new HashSet<Genre>(); setGenre.add(genreStrategie);
		
		/* Création avec freeDiskSpace, RAM, Processor / VideoCard / OS */
		/* * Création de Processor * */
		Processor proc1 = new Processor("D Processor 925", "Intel", 3000.0, 2); proc1.save();
		Set<Processor> setProc = new HashSet<Processor>(); setProc.add(proc1);
	
		/* * Création de la VideoCard * */
		VideoCard vidCard1 = new VideoCard("GeForce GT 635M", "", "NVIDIA", 1400.0, "9.0"); vidCard1.save();
		Set<VideoCard> setVD = new HashSet<VideoCard>(); setVD.add(vidCard1); 
	
		Set<OS> setOS = new HashSet<OS>(); setOS.add(win7); setOS.add(win81); setOS.add(winXP);
		
		Configuration conf1 = new Configuration(1500, 20000, setOS, setProc, setVD); conf1.save();
		
		game1.setReleaseDates(setRelease); game1.setDevelopers(setDev); game1.setModes(setMode); 
		game1.setGenres(setGenre); game1.setConfiguration(conf1);
		game1.save();
		
		
		Game game2 = new Game ("Les Sims 3", null);
		setDev.clear(); setDev.add("Maxis");
		setMode.clear(); setMode.add("Solo");
		setRelease.clear(); 
		setRelease.put("EU", Date.valueOf("2009-06-09"));
		setRelease.put("USA", Date.valueOf("2009-06-09"));
		setRelease.put("JAPAN", Date.valueOf("2009-06-09"));
		setGenre.clear(); setGenre.add(genreReflexion);
		
		/* Création avec freeDiskSpace, RAM, Processor / VideoCard / OS */
		/* * Création de Processor * */
		Processor proc2 = new Processor("Processor E5300", "Intel", 2600.0, 2); proc2.save();
		setProc.clear(); setProc.add(proc2);
	
		/* * Création de la VideoCard * */
		VideoCard vidCard2 = new VideoCard("GeForce G100", "", "NVIDIA", 500.0, "10.0"); vidCard2.save();
		setVD.clear(); setVD.add(vidCard2); 
	
		setOS.clear(); setOS.add(win7); setOS.add(win81); setOS.add(winXP);
		
		Configuration conf2 = new Configuration(500, 7100, setOS, setProc, setVD); conf2.save();
		
		game2.setReleaseDates(setRelease); game2.setDevelopers(setDev); game2.setModes(setMode); 
		game2.setGenres(setGenre); game2.setConfiguration(conf2);
		game2.save();
		
		/*
		 * ******* PROVIDER ***********
		 */
		
		/*
		 * ******* GAME ***********
		 */
		
    }
    
}