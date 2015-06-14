package controllers;

import play.*;
import play.test.*;
import models.*;

import play.mvc.Controller;
import play.mvc.With;

@With(SecurePublic.class)
public class About extends Controller {

    public static void index() {
        //Fixtures.loadModels("data.test.yml");
        render();
    }
}
