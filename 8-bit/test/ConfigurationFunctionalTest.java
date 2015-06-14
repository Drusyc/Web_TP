import controllers.Secure;
import models.*;
import org.junit.Test;

import play.Logger;
import play.mvc.With;
import play.test.FunctionalTest;
import validators.Check;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ConfigurationFunctionalTest extends FunctionalTest {

    @Test
    public void addConfig() {

        /* Création avec freeDiskSpace, RAM, Processor / VideoCard / OS */
        /* * Création de Processor * */
        Processor proc = new Processor("i7-3517u - FunctionalTest", "Intel", 1900.0, 2); proc.save();

        /* * Création de la VideoCard * */
        VideoCard vidCard = new VideoCard("GeForce GT 635M - FunctionalTest", "NVIDIA", 900, "11"); vidCard.save();

        /* * Création de l'OS * */
        OS win81 = new OS("Windows - FunctionalTest", "8.1", Date.valueOf("2013-10-17")); win81.save();

        Set<OS> setOS = new HashSet<OS>(); setOS.add(win81);

        Long winId = win81.getId();
        Logger.debug("ID : " + winId);

        Gamer ga = new Gamer("gamer", "password", "test@test.com",null);ga.save();
        try {
            controllers.Secure.authenticate("gamer", "password", true);
        } catch (Throwable throwable) {
            ga.delete();
            win81.delete();
            vidCard.delete();
            proc.delete();

            throwable.printStackTrace();
        }

        controllers.Configuration.add("maConfig", 10.0, 4.0, winId, "i7-3517u - FunctionalTest",
                "GeForce GT 635M - FunctionalTest");

        List<Configuration> listConf = models.Configuration.findByName("maConfig");
        for(Configuration g : listConf) {
            Logger.debug("Resultats : " + g.getName() + "\n");
        }
        assertTrue(listConf.size() == 1);
        assertTrue(listConf.get(0).getName().compareTo("maConfig") == 0);


        /*
         * Menage..
         */
        ga.delete();
        listConf.get(0).delete();
        win81.delete();
        vidCard.delete();
        proc.delete();

    }

}
