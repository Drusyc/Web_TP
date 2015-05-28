package controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import models.Game;
import play.Logger;
import play.mvc.Controller;
import play.mvc.With;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@With(Secure.class)
public class Search extends Controller {

    public static void show() {
        renderTemplate("search.html");
    }
    
    public static void findGame(String game) {
    	/* Récupération des résultats */
    	List<Game> games = Game.findByName(game);
    	List<String> url = new ArrayList<String>();
    	
    	for (Game g : games) {
    		url.add("/game/" + g.getName().replaceAll(" ", "+"));
    		Logger.debug(g.getName() + " ->" + "/game/" + g.getName().replaceAll(" ", "+"));
		}	

        renderTemplate("search.html", games, url);
    }
 
    public static void findGameJSON(String game) {
    	/* Récupération des résultats */
    	List<Game> games = Game.findByName(game);
    	
    	/* Création du JSON et conversion des résultats */
    	Type listType = new TypeToken<List<Game>>() {}.getType();

    	Gson r = new Gson();
    	String results = r.toJson(games, listType);
    	
    	/* Envoie réponse */
    	renderJSON(results);
    }
}
