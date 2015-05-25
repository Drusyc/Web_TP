package controllers;

import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Search extends Controller {

    public static void show() {
        renderTemplate("search.html");
    }
}
