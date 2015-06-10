package controllers;

import models.Processor;
import models.VideoCard;
import play.Logger;
import play.mvc.Controller;
import play.mvc.With;
import validators.Check;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

@With(Secure.class)
@Check("gamer")
public class Configuration extends Controller {

    public static void index() {
        render();
    }


    public static void compare (String idUser, String idGame) {

        /* Récupération de l'utilisateur */
        models.Gamer user = (models.Gamer) Secure.loadCurrentUser();
        Logger.debug("User to find : " + user.getPseudo());

        /* Récupération de la configuration de l'utilisateur */
        models.Configuration configUser = models.Configuration.findById(Long.parseLong(idUser));
        Logger.debug("Gamer Config to find : " + configUser.getName());

        /* Récupération du jeu */
        idGame = idGame.replaceAll("\\+", " ");
        models.Game game = models.Game.findById(idGame);
        Logger.debug("Game : " + game.getName());

        /* Récupération de la configuration du jeu*/
        models.Configuration configGame = game.getConfiguration();
        Logger.debug("Game Config to find : " + configGame.getName());

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
        Logger.debug("minProcGame : " + minProcGame);

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

        if (eval.get("VitesseProcesseur").compareTo("OK") == 0 || eval.get("NombreDeCoeurs").compareTo("OK") == 0) {
            eval.put("Processeur", "OK");
        }

        Set<VideoCard> vidCardGame = configGame.getVideoCards();
        /* Récupération de la carte vidéo la "moins" puissante du jeu, comparant les vitesses */
        minSpeed = Double.MAX_VALUE;
        VideoCard minVdCard = null;
        for (VideoCard vd : vidCardGame) {
            if (vd.getMemory() < minSpeed) {
                minVdCard = vd;
                minSpeed = vd.getMemory();
            }
        }


        Set<VideoCard> vidCardUser = configUser.getVideoCards();
        /* Récupération de la carte vidéo la "moins" puissante du user, comparant les vitesses */
        maxSpeed = Double.MIN_VALUE;
        VideoCard maxVDUser = null;
        for (VideoCard vd : vidCardGame) {
            if (vd.getMemory() > maxSpeed) {
                maxVDUser = vd;
                maxSpeed = vd.getMemory();
            }
        }



        /* Comparaison des cartes vidéos */
            /* Comparaison de la vitesse */
        if (maxSpeed <= minSpeed)  {
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

        if (eval.get("VitesseCarteVideo").compareTo("OK") == 0 || eval.get("DirectX").compareTo("OK") == 0) {
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

        render(game, user, configGame, configUser, minProcGame, maxProcUser, minVdCard, maxVDUser, eval);
    }
}
