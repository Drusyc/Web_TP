package controllers;

import play.mvc.Controller;

public class Signup extends Controller {

    public static void show() {
        renderTemplate("signup.html");
    }
}
