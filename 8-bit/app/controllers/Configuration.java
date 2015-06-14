package controllers;

import models.Gamer;
import models.OS;
import models.Processor;
import models.VideoCard;
import play.Logger;
import play.mvc.Controller;
import play.mvc.With;
import validators.Check;

import java.util.*;

@With(Secure.class)
@Check("gamer")
public class Configuration extends Controller {

    public static void index() {
        /* OS */
        List<OS> osList = OS.getAll();

        /* Processors */
        List<Processor> processorList = Processor.getAll(); /* Ordered by manufacturer, name */

        /* Video cards */
        List<VideoCard> videoCardList = VideoCard.getAll(); /* Ordered by manufacturer, name */

        render(osList, processorList, videoCardList);
    }

    public static void add(String name, Double freeDiskSpace, Double ram,
                           Long operatingSystem, String processor, String videoCard) {
        Logger.debug("Configuration::add\n"
                + "-- name: " + name + "\n"
                + "-- freeDiskSpace: " + freeDiskSpace + "\n"
                + "-- ram: " + ram + "\n"
                + "-- operatingSystem: " + operatingSystem + "\n"
                + "-- processor: " + processor + "\n"
                + "-- videoCard: " + videoCard + "\n");

        /* Find OS, processor and video card by id */
        OS os = OS.findById(operatingSystem);
        Processor p = Processor.findById(processor);
        VideoCard v = VideoCard.findById(videoCard);

        Set<OS> setOS = new HashSet<OS>();
        Set<Processor> setP = new HashSet<Processor>();
        Set<VideoCard> setV = new HashSet<VideoCard>();

        setOS.add(os);
        setP.add(p);
        setV.add(v);

        /* Create and save configuration */
        models.Configuration configuration = new models.Configuration(name, freeDiskSpace, ram, setOS, setP, setV);
        configuration.save();
        Logger.info("Configuration::add - Configuration " + name + " created.");

        /* Add it to user configurations */
        Gamer gamer = (Gamer) Secure.loadCurrentUser();
        gamer.getConfigurations().add(configuration);
        gamer.save();
        Logger.info("Configuration::add - Configuration " + name + " added to user " + gamer.getPseudo() + ".");
    }

    public static void compare(String idUser, String idGame) {

        /* Récupération de l'utilisateur */
        models.Gamer user = (models.Gamer) Secure.loadCurrentUser();
        Logger.debug("Configuration::compare - User to find : " + user.getPseudo());

        /* Récupération de la configuration de l'utilisateur */
        models.Configuration configUser = models.Configuration.findById(Long.parseLong(idUser));
        Logger.debug("Configuration::compare - Gamer configuration to find : " + configUser.getName());

        /* Récupération du jeu */
        idGame = idGame.replaceAll("\\+", " ");
        models.Game game = models.Game.findById(idGame);
        Logger.debug("Configuration::compare - Game : " + game.getName());

        /* Récupération de la configuration du jeu*/
        models.Configuration configGame = game.getConfiguration();
        Logger.debug("Configuration::compare - Game configuration to find : " + configGame.getName());

        /* Création de la map pour stocker les résultats */
        Map<String, String> eval = new HashMap<String, String>();
        eval.put("EspaceMemoire","KO");
        eval.put("RAM","KO");
        eval.put("Processeur","KO");
        eval.put("VitesseProcesseur","KO");
        eval.put("NombreDeCoeurs","KO");
        eval.put("CarteVideo","KO");
        eval.put("VitesseCarteVideo","KO");
        eval.put("DirectX","KO");
        eval.put("Final", "KO");

        Set<Processor> procGame = configGame.getProcessors();
        /* Récupération du processeur le "moins" puissant du jeu, en comparant les vitesses */
        Double minSpeed = Double.MAX_VALUE;
        Processor minProcGame = null;
        for (Processor proc : procGame) {
            if (proc.getSpeed() < minSpeed) {
                minProcGame = proc;
                minSpeed = proc.getSpeed();
            }
        }

        Set<Processor> procUser = configUser.getProcessors();
        /* Récupération du processeur le "moins" puissant du gamer, en comparant les vitesses */
        Double maxSpeed = Double.MIN_VALUE;
        Processor maxProcUser = null;
        for (Processor proc : procUser) {
            if (proc.getSpeed() > maxSpeed) {
                maxProcUser = proc;
                maxSpeed = proc.getSpeed();
            }
        }

        /* Comparaison des processeurs */
            /* Comparaison de la vitesse */
        if (minSpeed <= maxSpeed)  {
            /* Si "vitesse du proc requis" <= "vitesse du proc disponibles" */
            eval.put("VitesseProcesseur", "OK");
        }
            /* Comparaison de nombre de coeurs */
        if (minProcGame.getCores() <= maxProcUser.getCores()) {
            /* Si "nombre de coeurs requis" <= "nombre de coeurs disponibles" */
            eval.put("NombreDeCoeurs", "OK");
        }

        if (eval.get("VitesseProcesseur").compareTo("OK") == 0 && eval.get("NombreDeCoeurs").compareTo("OK") == 0) {
            eval.put("Processeur", "OK");
        }

        Set<VideoCard> vidCardGame = configGame.getVideoCards();
        /* Récupération de la carte vidéo la "moins" puissante du jeu, comparant les vitesses */
        Integer minVSpeed = Integer.MAX_VALUE;
        VideoCard minVdCard = null;
        for (VideoCard vd : vidCardGame) {
            if (vd.getSpeed() < minVSpeed) {
                minVdCard = vd;
                minVSpeed = vd.getSpeed();
            }
        }

        Set<VideoCard> vidCardUser = configUser.getVideoCards();
        /* Récupération de la carte vidéo la "moins" puissante du user, comparant les vitesses */
        Integer maxVSpeed = Integer.MIN_VALUE;
        VideoCard maxVDUser = null;
        for (VideoCard vd : vidCardUser) {
            if (vd.getSpeed() > maxVSpeed) {
                maxVDUser = vd;
                maxVSpeed = vd.getSpeed();
            }
        }

        /* Comparaison des cartes vidéos */
            /* Comparaison de la vitesse */
        if (maxVSpeed >= minVSpeed)  {
            /* Si "vitesse du proc requis" <= "vitesse du proc disponibles" */
            eval.put("VitesseCarteVideo", "OK");
        }

        /* Comparaison des versions DirectX */
        Scanner sc1 = new Scanner(minVdCard.getVersionDirectX().replaceAll("\\.", ","));
        Scanner sc2 = new Scanner(maxVDUser.getVersionDirectX().replaceAll("\\.", ","));

        Double minVersion = Double.parseDouble(sc1.findInLine("^(\\d\\*.)?\\d+"));
        Double maxVersion = Double.parseDouble(sc2.findInLine("^(\\d\\*.)?\\d+"));
        String minCharVersion = sc1.findInLine("[a-z]$");
        String maxCharVersion = sc2.findInLine("[a-z]$");

        if (minCharVersion == null) {
            minCharVersion = " ";
        }

        if (maxCharVersion == null) {
            maxCharVersion = " ";
        }

        if (minVersion < maxVersion) {
            /* Si "nombre de coeurs requis" <= "nombre de coeurs disponibles" */
            eval.put("DirectX", "OK");
        } else if (minVersion.compareTo(maxVersion) == 0 &&
                minCharVersion.compareTo(maxCharVersion) <= 0) {
            eval.put("DirectX", "OK");
        }

        if (eval.get("VitesseCarteVideo").compareTo("OK") == 0 && eval.get("DirectX").compareTo("OK") == 0) {
            eval.put("CarteVideo", "OK");
        }

        /* Comparaison de la RAM */
        if (configGame.getRAM() <= configUser.getRAM()) {
            eval.put("RAM", "OK");
        }

        /* Comparaison freeDiskSpace */
        if (configGame.getFreeDiskSpace() <= configUser.getFreeDiskSpace()) {
            eval.put("EspaceMemoire", "OK");
        }

        /* Résultat final */
        if (eval.get("EspaceMemoire").compareTo("OK") == 0
                && eval.get("RAM").compareTo("OK") == 0
                && eval.get("Processeur").compareTo("OK") == 0
                && eval.get("CarteVideo").compareTo("OK") == 0) {
            eval.put("Final", "OK");
        }

        render(game, user, configGame, configUser, minProcGame, maxProcUser, minVdCard, maxVDUser, eval);
    }
}
