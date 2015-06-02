package controllers;

import java.io.InputStream;
import play.Logger;
import play.db.jpa.Blob;
import play.mvc.Controller;
import play.mvc.With;
import validators.Check;

@With(Secure.class)
@Check("gamer")
public class Game extends Controller {
	
	/* */
	
    public static void index(String name) {
    	String gameName = name.replaceAll("\\+", " ");
    	Logger.debug("Game to find : " + gameName);
    	models.Game game = models.Game.findById(gameName);
    	
    	Logger.debug("Game found : " + game);
    	if (game == null) {
    		renderTemplate("Search/index.html");
    	}
    	
    	//models.Gamer gamer = models.Gamer.findById();
    	
        render(game);
    }
    
    public static void gamePhoto (String gameName) {
    	if (gameName.length() == 0) {
    		return;
    	}
    	
    	models.Game ga = models.Game.findById(gameName);
		if (ga.getPhoto() == null || ga.getPhoto().get() == null) {
			return;
		}

    	Blob photo = ga.getPhoto();
    	
    	response.setContentTypeIfNotSet(photo.type());
    	InputStream inSt = photo.get();
    	renderBinary(inSt);
    }

}
