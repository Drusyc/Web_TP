package controllers;

import play.Logger;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Game extends Controller {
	
    public static void index(String name) {
    	String gameName = name.replaceAll("\\+", " ");
    	Logger.debug("Game::index - Game to find: " + gameName);
    	models.Game game = models.Game.findById(gameName);
    	
    	Logger.debug("Game::index - Game found: " + game);
    	if (game == null) {
    		renderTemplate("Search/index.html");
    	}
    	
        render(game);
    }
}
