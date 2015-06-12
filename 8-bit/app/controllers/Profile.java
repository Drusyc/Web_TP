package controllers;

import models.Gamer;
import models.Genre;
import models.User;
import play.Logger;
import play.data.validation.*;
import play.mvc.Controller;
import play.mvc.With;
import utils.BCrypt;
import validators.Check;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@With(Secure.class)
@Check("gamer")
public class Profile extends Controller {

    public static void index() {
        render();
    }

    public static void changeMail (@Required(message = "requiredEmail") @Email(message = "invalidEmail")
                                   @MaxSize(value = 255, message = "invalidEmail") String email) throws Throwable {
        Logger.debug("Profile::changeMail\n"
                + "-- mail: " + email + "\n");

        User user = Secure.loadCurrentUser();
        user.setMail(email);
        try {
            user.save();
        } catch (Throwable e) {
            Logger.error("Signup::createAccount - Error while invoking user.save()");
        }

        renderTemplate("Profile/index.html");
    }

    public static void changePassword(@Required(message = "requiredPassword") @MinSize(value = 8, message = "invalidPassword") String password,
                                      @Required(message = "passwordsMustMatch") @Equals(value = "password", message = "passwordsMustMatch") String password2)
                                      throws Throwable {

        Logger.debug("Profile::changePassword\n"
                + "-- password: " + password + "\n"
                + "-- password2: " + password2 + "\n");

        User user = Secure.loadCurrentUser();
        Logger.debug("Profile::changePassword\n" +
                "-- user: " + user.getPseudo());

        // Set email and hashed password
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

        try {
            user.save();
        } catch (Throwable e) {
            Logger.error("Signup::createAccount - Error while invoking user.save()");
        }

        renderTemplate("Profile/index.html");
    }

    public static void changeGenres(String genreReflexion,
                                    String genreAction,
                                    String genreAventure,
                                    String genreRPG,
                                    String genreStrategie) {

        Set<Genre> newGenre = new HashSet<Genre>();

        if (genreReflexion != null) {
            newGenre.add(new Genre(Genre.Reflexion));
        }

        if (genreAction != null) {
            newGenre.add(new Genre(Genre.Action));
        }

        if (genreAventure != null) {
            newGenre.add(new Genre(Genre.Aventure));
        }

        if (genreRPG != null) {
            newGenre.add(new Genre(Genre.RPG));
        }

        if (genreStrategie != null) {
            newGenre.add(new Genre(Genre.Strategie));
        }

        Gamer gamer = (Gamer) Secure.loadCurrentUser();
        gamer.setPreferredGenres(newGenre);
        gamer.save();

        renderTemplate("Profile/index.html");
    }

}
