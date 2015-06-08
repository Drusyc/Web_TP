package controllers;

import models.Gamer;
import play.Logger;
import play.mvc.Controller;
import play.mvc.With;
import validators.Check;

@With(Secure.class)
@Check("gamer")
public class Configuration extends Controller {

    public static void index() {
        render();
    }


    public static void compare (String idUser, String idGame) {

        models.Gamer user = (models.Gamer) Secure.loadCurrentUser();
        Logger.debug("User to find : " + user.getPseudo());

        models.Configuration configUser = models.Configuration.findById(Long.parseLong(idUser));
        Logger.debug("Gamer Config to find : " + configUser.getName());

        idGame = idGame.replaceAll("\\+", " ");
        models.Game game = models.Game.findById(idGame);
        Logger.debug("Game : " + game.getName());

        models.Configuration configGame = game.getConfiguration();
        Logger.debug("Game Config to find : " + configGame.getName());

        render(game, user, configGame, configUser);
    }
}
