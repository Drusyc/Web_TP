import java.sql.Date;

import models.Gamer;
import models.OS;
import models.Request;

import org.junit.Test;

import play.test.UnitTest;


public class RequestTest extends UnitTest {
	
	
	/*
	 * Création de requête, insertion dans la base
	 *
	 */
	@Test
	public void createRequest () {
		/* Création d'une requête avec seulement date / game / status */
		Request r1 = new Request(new Date(System.currentTimeMillis()), "testCreationRequest", Request.Status.IN_PROGRESS);
		r1.save();
		
		Request found = Request.findById(r1.getId());
		assertNotNull(found);
		assertEquals(r1, found);
		r1.delete();
	}
	
	/*
	 * Création de requête, insertion dans la base, récupération, suppression
	 *
	 */
	@Test
	public void deleteRequest () {
		/* Création d'une requête avec seulement date / game / status */
		Request r1 = new Request(new Date(System.currentTimeMillis()), "testDeleteRequest", Request.Status.IN_PROGRESS);
		r1.save();
		
		Request found = Request.findById(r1.getId());
		assertNotNull(found);
		
		r1.delete();
		
		found = Request.findById(r1.getId());
		assertNull(found);		
	}
	
	/*
	 * update d'une requête 
	 * 	 *
	 */
	@Test
	public void updateRequest () {
		/* Création d'une requête avec seulement date / game / status */
		Request r1 = new Request(new Date(System.currentTimeMillis()), "wrongNameRequest", Request.Status.IN_PROGRESS);
		r1.save();
		
		Request found = Request.findById(r1.getId());
		assertNotNull(found);
		assertEquals(r1, found);
		
		String tmp = found.getGame();
		
		r1.setGame("rightNameRequest"); r1.save();
		assertNotEquals(r1.getGame(), tmp);
		
		found = Request.findById(r1.getId());
		assertEquals(r1.getGame(), found.getGame());
		r1.delete();
	}
	
	
	/*
	 * Mapping entre Request et OS
	 *  
	 */
	@Test
	public void osRequest () {
		/* * Création de l'OS * */
		OS win81 = new OS("Windows", 8.1); win81.save();
		OS win7 = new OS("Windows", 7.0); win7.save();
		
		/* Création de Request */
		Request r1 = new Request(new Date(System.currentTimeMillis()), "r1win81", Request.Status.IN_PROGRESS);	
		r1.setOS(win81);r1.save();
		
		Request r2 = new Request(new Date(System.currentTimeMillis()), "r2win7", Request.Status.IN_PROGRESS);	
		r2.setOS(win7);r2.save();
		
		
		/* Assert */
		
		Request found = Request.findById(r1.getId());
		assertEquals(win81.getName(), found.getOS().getName());
		assertEquals(win81.getVersion(), found.getOS().getVersion());
		
		assertNotEquals(win7.getVersion(), found.getOS().getVersion());
		
		found = Request.findById(r2.getId());
		assertEquals(win7.getName(), found.getOS().getName());
		assertEquals(win7.getVersion(), found.getOS().getVersion());
		
		assertNotEquals(win81.getVersion(), found.getOS().getVersion());
		
		/*
		 * Ménage..
		 */
		r1.delete();
		r2.delete();
		win81.delete();
		win7.delete();
	}
	
	/*
	 * Mapping entre Request et Gamer
	 *  
	 */
	@Test
	public void gamerRequest () {
		
		/* Création d'user*/
		Gamer ga1 = new Gamer("Toto", "mdp","Toto@test.com", null); ga1.save();
		Gamer ga2 = new Gamer("Titi", "mdp","Titi@test.com", null); ga2.save();
		
		/* Création de request */
		Request r1 = new Request(new Date(System.currentTimeMillis()), "r1userToto", Request.Status.IN_PROGRESS);	
		r1.setRequester(ga1);r1.save();
		
		/* Création de request */
		Request r2 = new Request(new Date(System.currentTimeMillis()), "r2userTiti", Request.Status.IN_PROGRESS);	
		r2.setRequester(ga2);r2.save();
		
		/* Assert */
		Request found = Request.findById(r1.getId());
		assertEquals(ga1.getPseudo(), found.getRequester().getPseudo());
		assertEquals(ga1.getMail(), found.getRequester().getMail());
		
		assertNotEquals(ga2.getPseudo(), found.getRequester().getPseudo());
		
		found = Request.findById(r2.getId());
		assertEquals(ga2.getPseudo(), found.getRequester().getPseudo());
		assertEquals(ga2.getMail(), found.getRequester().getMail());
		
		assertNotEquals(ga1.getPseudo(), found.getRequester().getPseudo());
		
		/* 
		 * Ménage ..
		 */
		r1.delete();
		r2.delete();
		ga1.delete();
		ga2.delete();
	}
}
