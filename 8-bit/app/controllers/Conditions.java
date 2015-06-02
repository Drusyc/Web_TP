package controllers;

import play.mvc.Controller;
import play.mvc.With;

@With(SecurePublic.class)
public class Conditions extends Controller {

    public static void index() {
        render();
    }
}
