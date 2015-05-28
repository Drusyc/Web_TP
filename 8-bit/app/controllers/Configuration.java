package controllers;

import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Configuration extends Controller {

    public static void show() {
        renderTemplate("configuration.html");
    }
}