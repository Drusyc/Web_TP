package controllers;

import play.mvc.Controller;
import play.mvc.With;
import validators.Check;

@With(Secure.class)
@Check("provider")
public class Manage extends Controller {

    public static void games() {
        render();
    }

    public static void hardware() {
        render();
    }

    public static void requests() {
        render();
    }
}
