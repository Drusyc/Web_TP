package controllers;

import play.mvc.Controller;
import play.mvc.With;

@With(SecurePublic.class)
public class Contact extends Controller {

    public static void index() {
        render();
    }
}
