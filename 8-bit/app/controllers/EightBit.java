package controllers;

import play.mvc.Controller;

public class EightBit extends Controller {

    public static void index() {
        renderTemplate("index.html");
    }
}