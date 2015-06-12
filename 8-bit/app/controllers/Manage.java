package controllers;

import java.sql.Date;

import models.*;
import models.Configuration;
import models.Game;
import play.Logger;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;
import validators.Check;

import java.util.*;

@With(Secure.class)
@Check("provider")
public class Manage extends Controller {

    public static void games() {
        /* OS */
        List<OS> osList = OS.getAll();

        /* Processors */
        List<Processor> processorList = Processor.getAll(); /* Ordered by manufacturer, name */

        /* Video cards */
        List<VideoCard> videoCardList = VideoCard.getAll(); /* Ordered by manufacturer, name */

        /* Games */
        List<Game> gameList = Game.getAll(); /* Ordered by name */

        /* Genres */
        List<Genre> genreList = Genre.getAll(); /* Ordered by name */

        render(osList, processorList, videoCardList, gameList, genreList);
    }

    public static void hardware() {
        /* Processors */
        List<Processor> processorList = Processor.getAll();

        /* Video cards */
        List<VideoCard> videoCardList = VideoCard.getAll();

        render(processorList, videoCardList);
    }

    public static void addGame(
            @Required String name,
            @Required String genre1,
            @Required Long configurationOs1,
            @Required String configurationProcessor1,
            @Required String configurationVideoCard1,
            String developers,
            String genre2,
            String modes,
            String euDate,
            String usDate,
            String japanDate,
            Double configurationSpace,
            Double configurationRam,
            Long configurationOs2,
            String configurationProcessor2,
            String configurationVideoCard2) {
        Logger.debug("Manage::addGame\n"
                + "-- name: " + name + "\n"
                + "-- developers: " + developers + "\n"
                + "-- genre1: " + genre1 + "\n"
                + "-- genre2: " + genre2 + "\n"
                + "-- modes: " + modes + "\n"
                + "-- euDate: " + euDate + "\n"
                + "-- usDate: " + usDate + "\n"
                + "-- japanDate: " + japanDate + "\n"
                + "-- configurationSpace: " + configurationSpace + "\n"
                + "-- configurationRam: " + configurationRam + "\n"
                + "-- configurationOs1: " + configurationOs1 + "\n"
                + "-- configurationOs2: " + configurationOs2 + "\n"
                + "-- configurationProcessor1: " + configurationProcessor1 + "\n"
                + "-- configurationProcessor2: " + configurationProcessor2 + "\n"
                + "-- configurationVideoCard1: " + configurationVideoCard1 + "\n"
                + "-- configurationVideoCard2: " + configurationVideoCard2 + "\n");

        if (!validation.hasErrors()) {
            /* Create configuration, set name, disk space and RAM */
            Configuration configuration = new Configuration();
            configuration.setName(name);
            configuration.setFreeDiskSpace(configurationSpace);
            configuration.setRAM(configurationRam);

            /* Set configuration operating systems */
            Set<OS> os = new HashSet<OS>();
            OS os1 = OS.findById(configurationOs1);
            os.add(os1);
            if (configurationOs2 != null && configurationOs2 > 0) {
                OS os2 = OS.findById(configurationOs2);
                os.add(os2);
            }
            configuration.setOperatingSystems(os);

            /* Set configuration processors */
            Set<Processor> processors = new HashSet<Processor>();
            Processor p1 = Processor.findById(configurationProcessor1);
            processors.add(p1);
            if (configurationProcessor2 != null && configurationProcessor2.length() > 0) {
                Processor p2 = Processor.findById(configurationProcessor2);
                processors.add(p2);
            }
            configuration.setProcessors(processors);

            /* Set configuration video cards */
            Set<VideoCard> videoCards = new HashSet<VideoCard>();
            VideoCard v1 = VideoCard.findById(configurationVideoCard1);
            videoCards.add(v1);
            if (configurationVideoCard2 != null && configurationVideoCard2.length() > 0) {
                VideoCard v2 = VideoCard.findById(configurationVideoCard2);
                videoCards.add(v2);
            }
            configuration.setVideoCards(videoCards);


            /* Create game, set name */
            Game game = new Game();
            game.setName(name);

            /* Set developers */
            Set<String> setDevelopers = new HashSet<String>(Arrays.asList(developers.split(", ")));
            game.setDevelopers(setDevelopers);

            /* Set modes */
            Set<String> setModes = new HashSet<String>(Arrays.asList(modes.split(", ")));
            game.setModes(setModes);

            /* Set release dates */
            Map<String, Date> releaseDates = new HashMap<String, Date>();
            if (euDate != null && euDate.length() > 0) {
                try {
                    releaseDates.put("EU", Date.valueOf(euDate));
                } catch (Exception e) {
                    Logger.error("Manage::addGame - Error while invoking Date.valueOf()");
                }
            }
            if (usDate != null && usDate.length() > 0) {
                try {
                    releaseDates.put("USA", Date.valueOf(usDate));
                } catch (Exception e) {
                    Logger.error("Manage::addGame - Error while invoking Date.valueOf()");
                }
            }
            if (japanDate != null && japanDate.length() > 0) {
                try {
                    releaseDates.put("JAPAN", Date.valueOf(japanDate));
                } catch (Exception e) {
                    Logger.error("Manage::addGame - Error while invoking Date.valueOf()");
                }
            }
            game.setReleaseDates(releaseDates);

            /* Set genres */
            Set<Genre> genres = new HashSet<Genre>();
            Genre g1 = Genre.findById(genre1);
            genres.add(g1);
            if (genre2 != null && genre2.length() > 0) {
                Genre g2 = Genre.findById(genre2);
                genres.add(g2);
            }
            game.setGenres(genres);

            /* Set configuration */
            game.setConfiguration(configuration);

            /* Set provider */
            game.setProvider((Provider) Secure.loadCurrentUser());


            /* Save configuration */
            configuration.save();
            Logger.info("Manage::addGame - Configuration " + name + " created.");

            /* Save game */
            game.save();
            Logger.info("Manage::addGame - Game " + name + " created.");
        } else {
            Logger.debug("Manage::addGame - Validation errors: " + validation.errorsMap());
        }

        redirect("/manage/games");
    }

    public static void addProcessor(String name, String manufacturer, Double speed, Integer cores) {
        Logger.debug("Manage::addProcessor\n"
                        + "-- name: " + name + "\n"
                        + "-- manufacturer: " + manufacturer + "\n"
                        + "-- speed: " + speed + "\n"
                        + "-- cores: " + cores + "\n");

        /* Create and save processor */
        Processor p = new Processor(name, manufacturer, speed, cores);
        p.save();
        Logger.info("Manage::addProcessor - Processor " + name + " created.");
    }

    public static void addVideoCard(String name, String manufacturer, Integer speed, String versionDirectX) {
        Logger.debug("Manage::addProcessor\n"
                + "-- name: " + name + "\n"
                + "-- manufacturer: " + manufacturer + "\n"
                + "-- speed: " + speed + "\n"
                + "-- versionDirectX: " + versionDirectX + "\n");

        /* Create and save video card */
        VideoCard v = new VideoCard(name, manufacturer, speed, versionDirectX);
        v.save();
        Logger.info("Manage::addVideoCard - Video card " + name + " created.");
    }

    public static void requests() {
        render();
    }
}
