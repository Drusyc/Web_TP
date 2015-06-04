package controllers;

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
        render();
    }

    public static void hardware() {
        /* Processors */
        List<Processor> processorList = Processor.findAll();

        /* Video cards */
        List<VideoCard> videoCardList = VideoCard.findAll();

        render(processorList, videoCardList);
    }

    public static boolean addProcessor(String name, String manufacturer, Double speed, Integer cores) {
        Processor p = new Processor(name, manufacturer, speed, cores);
        p.save();
        Logger.info("Manage::addProcessor - " + name + " " + manufacturer + " " + speed + " " + cores);
        return true;
    }

    public static void requests() {
        render();
    }
}
