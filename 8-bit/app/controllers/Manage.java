package controllers;

import models.Game;
import models.Processor;
import models.VideoCard;
import play.Logger;
import play.mvc.Controller;
import play.mvc.With;
import validators.Check;

import java.util.List;

@With(Secure.class)
@Check("provider")
public class Manage extends Controller {

    public static void games() {
        /* Games */
        List<Game> gameList = Game.getAll();

        render(gameList);
    }

    public static void hardware() {
        /* Processors */
        List<Processor> processorList = Processor.getAll();

        /* Video cards */
        List<VideoCard> videoCardList = VideoCard.getAll();

        render(processorList, videoCardList);
    }

    public static void addProcessor(String name, String manufacturer, Double speed, Integer cores) {
        Processor p = new Processor(name, manufacturer, speed, cores);
        p.save();
        Logger.info("Manage::addProcessor - " + name + " " + manufacturer + " " + speed + " " + cores);
    }

    public static void addVideoCard(String name, String manufacturer, Integer speed, String versionDirectX) {
        VideoCard v = new VideoCard(name, manufacturer, speed, versionDirectX);
        v.save();
        Logger.info("Manage::addVideoCard - " + name + " " + manufacturer + " " + speed + " " + versionDirectX);
    }

    public static void requests() {
        render();
    }
}
