import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import models.Configuration;
import models.OS;
import models.Processor;
import models.VideoCard;

import org.junit.Test;

import play.test.UnitTest;


public class ConfigurationTest extends UnitTest {

	/*
	 * Création de confs 
	 * 
	 * Insertion dans bd
	 * 
	 */
	@Test
	public void createConf () {
		/* Création avec seulement freeDiskSpace et RAM */
		Configuration conf1 = new Configuration("maConfig",0.5, 2.0); conf1.save();

		/* Création avec freeDiskSpace, RAM, Processor / VideoCard / OS */
			/* * Création de Processor * */
			Processor proc = new Processor("i7-3517u", "Intel", 1900.0, 2); proc.save();
			Set<Processor> setProc = new HashSet<Processor>(); setProc.add(proc);
		
			/* * Création de la VideoCard * */
			VideoCard vidCard = new VideoCard("GeForce GT 635M", "NVIDIA", 900.0, "11"); vidCard.save();
			Set<VideoCard> setVD = new HashSet<VideoCard>(); setVD.add(vidCard); 
		
			/* * Création de l'OS * */
			OS win81 = new OS("Windows", "8.1", Date.valueOf("2013-10-17")); win81.save();
			OS win7 = new OS("Windows", "7", Date.valueOf("2009-10-22")); win7.save();
			
			Set<OS> setOS = new HashSet<OS>(); setOS.add(win7); setOS.add(win81);
			
		Configuration conf2 = new Configuration("maConfig",0.1, 4.0, setOS, setProc, setVD); conf2.save();
		
		/* ASSERT */
		Configuration found = Configuration.findById(conf1.getId());
		assertEquals(conf1, found);
		
		found = Configuration.findById(conf2.getId());
		assertEquals(conf2, found);
		assertTrue(found.getOperatingSystems().containsAll(setOS));
		assertTrue(found.getProcessors().containsAll(setProc));
		assertTrue(found.getVideoCards().containsAll(setVD));
		
		/* 
		 * 
		 * Ménage..
		 * /!\ Supprimer les conf avant les proc/cartes, sinon violation de contraintes de réf /!\
		 * 
		 */
		
		conf1.delete();
		conf2.delete();
		vidCard.delete();
		proc.delete();
		win81.delete();
		win7.delete();
	}

	/*
	 * Création de confs 
	 * 
	 * Insertion dans bd
	 * 
	 */
	@Test
	public void deleteConf () {
		/* Création avec seulement freeDiskSpace et RAM */
		Configuration conf1 = new Configuration("maConfig",0.5, 2.0); conf1.save();
		
		Configuration found = Configuration.findById(conf1.getId());
		assertEquals(conf1,found);
		
		conf1.delete();
		
		found = Configuration.findById(conf1.getId());
		assertNull(found);
	}
	
	/*
	 * Création, insertion et modification d'une config 
	 */
	@Test
	public void updateConf () {
		/* Création avec seulement freeDiskSpace et RAM */
		Configuration conf1 = new Configuration("maConfig",0.5, 2.0); conf1.save();
		
		/* Création avec freeDiskSpace, RAM, Processor / VideoCard / OS */
			/* * Création de Processor * */
			Processor proc = new Processor("i7-3517u", "Intel", 1900.0, 2); proc.save();
			Set<Processor> setProc = new HashSet<Processor>(); setProc.add(proc);
		
			/* * Création de la VideoCard * */
			VideoCard vidCard = new VideoCard("GeForce GT 635M", "NVIDIA", 900.0, "11"); vidCard.save();
			Set<VideoCard> setVD = new HashSet<VideoCard>(); setVD.add(vidCard); 
		
			/* * Création de l'OS * */		
			OS win81 = new OS("Windows", "8.1", Date.valueOf("2013-10-17")); win81.save();
			OS win7 = new OS("Windows", "7", Date.valueOf("2009-10-22")); win7.save();
			
			Set<OS> setOS = new HashSet<OS>(); setOS.add(win7); setOS.add(win81);
			

		Configuration found = Configuration.findById(conf1.getId());
		assertEquals(conf1, found);
		
		conf1.setOperatingSystems(setOS);
		conf1.setProcessors(setProc);
		conf1.setVideoCards(setVD);
		conf1.save();
		
		found = Configuration.findById(conf1.getId());
		assertEquals(conf1, found);		
		assertTrue(found.getOperatingSystems().containsAll(conf1.getOperatingSystems()));
		assertTrue(found.getProcessors().containsAll(conf1.getProcessors()));
		assertTrue(found.getVideoCards().containsAll(conf1.getVideoCards()));
		
		/* 
		 * 
		 * Ménage..
		 * /!\ Supprimer les conf avant les proc/cartes, sinon violation de contraintes de réf /!\
		 * 
		 */
		
		conf1.delete();
		vidCard.delete();
		proc.delete();
		win81.delete();
		win7.delete();
	}
	
}
