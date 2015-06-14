package controllers;

import models.Game;

import play.mvc.Controller;
import play.mvc.With;
import play.test.Fixtures;

@With(SecurePublic.class)
public class Application extends Controller {

    public static void index() {

        /* Peuple la base de données si elle est vide */
        if (Game.findAll().size() == 0) {
            Fixtures.loadModels("data.test.yml");
        }

        render();
    }
}