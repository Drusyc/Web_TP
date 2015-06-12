import java.sql.Date;
import java.util.*;

import models.Configuration;
import models.Game;
import models.Gamer;
import models.Genre;
import models.OS;
import models.Processor;
import models.Provider;
import models.VideoCard;

import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;
import utils.BCrypt;

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
/*
		OS win81 = new OS("Windows", "8.1", Date.valueOf("2013-10-17")); win81.save();
		OS win7 = new OS("Windows", "7", Date.valueOf("2009-10-22")); win7.save();
		OS winXP = new OS("Windows", "XP", Date.valueOf("2001-10-25")); winXP.save();
		OS macOSx = new OS("OS X", "10.0", Date.valueOf("2014-10-16")); macOSx.save();
*/
		OS win81 = new OS("Windows", "8.1"); win81.save();
		OS win7 = new OS("Windows", "7"); win7.save();
		OS winXP = new OS("Windows", "XP"); winXP.save();
		OS macOSx = new OS("OS X", "10.0"); macOSx.save();

		/*
		 * ******* PROVIDER ***********
		 */
		
		Provider prov1 = new Provider();
		
		prov1.setPseudo("Provider1");
		prov1.setMail("provider1@gmail.com");
		prov1.setPassword(BCrypt.hashpw("provider1", BCrypt.gensalt()));

		prov1.save();
		
		
    	/*
    	 * ******* GAMES ***********
    	 */
		Game game1 = new Game("Starcraft 2");
		
		Set<String> setDevGame1 = new HashSet<String>(); setDevGame1.add("Blizzard Entertainment");
		Set<String> setModeGame1 = new HashSet<String>(); setModeGame1.add("Solo"); setModeGame1.add("Multi");
		Map<String, Date> setReleaseGame1 = new HashMap<String, Date>();
		/*setReleaseGame1.put("EU", Date.valueOf("2012-07-27"));
		setReleaseGame1.put("USA", Date.valueOf("2012-07-27"));
		setReleaseGame1.put("JAPAN", Date.valueOf("2012-07-27"));*/
		Set<Genre> setGenreGame1 = new HashSet<Genre>(); setGenreGame1.add(genreStrategie);
		setGenreGame1.add(genreReflexion);
		
		/* Création avec freeDiskSpace, RAM, Processor / VideoCard / OS */
		/* * Création de Processor * */
		Processor procGame1 = new Processor("D Processor 925", "Intel", 3000.0, 2); procGame1.save();
		Set<Processor> setProcGame1 = new HashSet<Processor>(); setProcGame1.add(procGame1);
	
		/* * Création de la VideoCard * */
		VideoCard vidCardGame1 = new VideoCard("GeForce GT 7600", "NVIDIA", 1400, "9.0"); vidCardGame1.save();
		Set<VideoCard> setVDGame1 = new HashSet<VideoCard>(); setVDGame1.add(vidCardGame1);
	
		Set<OS> setOSGame1 = new HashSet<OS>(); setOSGame1.add(win7); setOSGame1.add(win81); setOSGame1.add(winXP);
		
		Configuration confGame1 = new Configuration("conf-Starcraft2", 1.5, 20.0, setOSGame1, setProcGame1, setVDGame1);
		confGame1.save();
		
		game1.setReleaseDates(setReleaseGame1); game1.setDevelopers(setDevGame1); game1.setModes(setModeGame1);
		game1.setGenres(setGenreGame1); game1.setConfiguration(confGame1); game1.setProvider(prov1);
		game1.save();

		Set<Game> setGames = new HashSet<Game>(); setGames.add(game1);
		prov1.setGames(setGames);
		prov1.save();

		
		Game game2 = new Game ("Les Sims 3");
		Set<String> setDevGame2 = new HashSet<String>(); setDevGame2.add("Maxis");
		Set<String> setModeGame2 = new HashSet<String>(); setModeGame2.add("Solo");
		Map<String, Date> setReleaseGame2 = new HashMap<String, Date>();
		/*setReleaseGame2.put("EU", Date.valueOf("2009-06-09"));
		setReleaseGame2.put("USA", Date.valueOf("2009-06-09"));
		setReleaseGame2.put("JAPAN", Date.valueOf("2009-06-09"));*/

		Set<Genre> setGenreGame2 = new HashSet<Genre>(); setGenreGame2.add(genreReflexion);
		
		/* Création avec freeDiskSpace, RAM, Processor / VideoCard / OS */
		/* * Création de Processor * */
		Processor procGame2 = new Processor("Processor E5300", "Intel", 2600.0, 2); procGame2.save();
		Set <Processor> setProcGame2 = new HashSet<Processor>(); setProcGame2.add(procGame2);
	
		/* * Création de la VideoCard * */
		VideoCard vidCardGame2 = new VideoCard("GeForce G100", "NVIDIA", 500, "10.0"); vidCardGame2.save();
		Set<VideoCard> setVDGame2 = new HashSet<VideoCard>(); setVDGame2.add(vidCardGame2);
		Set<OS> setOSGame2 = new HashSet<OS>();	setOSGame2.add(win7); setOSGame2.add(win81); setOSGame2.add(winXP);
		
		Configuration confGame2 = new Configuration("conf-Sims3", 0.5, 7.1, setOSGame2, setProcGame2, setVDGame2);
		confGame2.save();
		
		game2.setReleaseDates(setReleaseGame2); game2.setDevelopers(setDevGame2); game2.setModes(setModeGame2);
		game2.setGenres(setGenreGame2); game2.setConfiguration(confGame2);
		game2.save();
		
		/*
		 * ******* GAMER ***********
		 */
		
		Gamer gamer1 = new Gamer("Thomas", BCrypt.hashpw("password", BCrypt.gensalt()), "Thomas@gmail.com", null); 

		Set<Genre> setGenreGamer1 = new HashSet<Genre>();
		setGenreGamer1.add(genreRPG); setGenreGamer1.add(genreStrategie);
		gamer1.setPreferredGenres(setGenreGamer1);

		Processor proc1Gamer1 = new Processor("i7-4710HQ", "Intel", 3500.0, 4); proc1Gamer1.save();
		Set<Processor> setProc1Gamer1 = new HashSet<Processor>(); setProc1Gamer1.add(proc1Gamer1);
		VideoCard vdCard1Gamer1 = new VideoCard("GeForce GTX 970M", "NVIDIA", 7000, "12"); vdCard1Gamer1.save();
		Set<VideoCard> setVidCard1Gamer1 = new HashSet<VideoCard>(); setVidCard1Gamer1.add(vdCard1Gamer1);
		Set<OS> setOS1Gamer1 = new HashSet<OS>(); setOS1Gamer1.add(win81);
		Configuration conf1Gamer1 = new Configuration("conf-Thomas-msi", 500.0, 16.0, setOS1Gamer1, setProc1Gamer1, setVidCard1Gamer1);
		conf1Gamer1.save();

		Processor proc2Gamer1 = new Processor("i7-3517u", "Intel", 1900.0, 2); proc2Gamer1.save();
		Set<Processor> setProc2Gamer1 = new HashSet<Processor>(); setProc2Gamer1.add(proc2Gamer1);
		VideoCard vdCard2Gamer1 = new VideoCard("GeForce GT 635M", "NVIDIA", 900, "11"); vdCard2Gamer1.save();
		Set<VideoCard> setVidCard2Gamer1 = new HashSet<VideoCard>(); setVidCard2Gamer1.add(vdCard2Gamer1);
		Set<OS> setOS2Gamer1 = new HashSet<OS>(); setOS2Gamer1.add(win81);
		Configuration conf2Gamer1 = new Configuration("conf-Thomas-asus", 500.0, 4.0, setOS2Gamer1, setProc2Gamer1, setVidCard2Gamer1);
		conf2Gamer1.save();


		Set<Configuration> setConfGamer1 = new HashSet<Configuration>(); setConfGamer1.add(conf1Gamer1); setConfGamer1.add(conf2Gamer1);
		
		gamer1.setConfigurations(setConfGamer1);
		gamer1.save();

		
		Gamer gamer2 = new Gamer("Steven", BCrypt.hashpw("password", BCrypt.gensalt()), "Steven@gmail.com", null);

		Set<Genre> setGenreGamer2 = new HashSet<Genre>();
		setGenreGamer2.add(genreAction); setGenreGamer2.add(genreAventure); setGenreGamer2.add(genreRPG);
		gamer2.setPreferredGenres(setGenreGamer2);
		
		Processor procGamer2 = new Processor("Core 2 Duo", "Intel", 2400.0, 2); procGamer2.save();
		Set<Processor> setProcGamer2 = new HashSet<Processor>(); setProcGamer2.add(procGamer2);
		VideoCard vdCardGamer2 = new VideoCard("GeForce 320M", "NVIDIA", 790, "10.1"); vdCardGamer2.save();
		Set<VideoCard> setVidCardGamer2 = new HashSet<VideoCard>(); setVidCardGamer2.add(vdCardGamer2);
		Set<OS> setOSGamer2 = new HashSet<OS>(); setOSGamer2.add(macOSx);
		Configuration confGamer2 = new Configuration("conf-Steven", 0.25, 4.0, setOSGamer2, setProcGamer2, setVidCardGamer2);
		confGamer2.save();

		Set<Configuration> setConfGamer2 = new HashSet<Configuration>();
		setConfGamer2.add(confGamer2);

		gamer2.setConfigurations(setConfGamer2);
		gamer2.save();
		
		/*
		 * ******** REQUEST *************
		 */
	/*
		Request r1 = new Request(new Date(Calendar.getInstance().getTime().getTime()), "Diablo 3", Request.Status.IN_PROGRESS);
		r1.setOS(win81);
		r1.setRequester(gamer1);r1.save();
	*/
    }
    
}