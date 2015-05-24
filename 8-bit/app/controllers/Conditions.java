package controllers;

import play.mvc.Controller;

public class Conditions extends Controller {

    public static void show() {
        renderTemplate("conditions.html");
    }
}
