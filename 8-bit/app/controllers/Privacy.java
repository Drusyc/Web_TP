package controllers;

import play.mvc.Controller;

public class Privacy extends Controller {

    public static void show() {
        renderTemplate("privacy.html");
    }
}
