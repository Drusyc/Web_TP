package controllers;

import play.mvc.Controller;
import play.mvc.With;
import validators.Check;

@With(Secure.class)
@Check("gamer")
public class Messages extends Controller {

    public static void index() {
        render();
    }
}
