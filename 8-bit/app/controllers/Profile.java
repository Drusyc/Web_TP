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

    private static final String EMAIL = "email";

    public static void index() {
        render();
    }

    public static void changeMail (@Required(message = "requiredEmail") @Email(message = "invalidEmail")
                                   @MaxSize(value = 255, message = "invalidEmail") String email) throws Throwable {
        Logger.debug("Profile::changeMail\n"
                + "-- mail: " + email + "\n");

        if (validation.hasErrors()) {
            Logger.debug("Profile::changeMail - Validation errors: " + validation.errorsMap());
            flash.put(EMAIL, email);
            validation.keep();
            index();
        }

        User user = Secure.loadCurrentUser();
        if (user != null) {
            user.setMail(email);
            try {
                user.save();
            } catch (Throwable e) {
                Logger.error("Profile::changeMail - Error while invoking user.save()");
            }
        }

        index();
    }

    public static void changePassword(@Required(message = "requiredPassword") @MinSize(value = 8, message = "invalidPassword") String password,
                                      @Required(message = "passwordsMustMatch") @Equals(value = "password", message = "passwordsMustMatch") String password2)
                                      throws Throwable {
        Logger.debug("Profile::changePassword\n"
                + "-- password: " + password + "\n"
                + "-- password2: " + password2 + "\n");

        if (validation.hasErrors()) {
            Logger.debug("Profile::changePassword - Validation errors: " + validation.errorsMap());
            validation.keep();
            index();
        }

        User user = Secure.loadCurrentUser();

        if (user != null) {
            Logger.debug("Profile::changePassword\n" +
                    "-- user: " + user.getPseudo());
            // Set email and hashed password
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

            try {
                user.save();
            } catch (Throwable e) {
                Logger.error("Profile::changePassword - Error while invoking user.save()");
            }
        }

        index();
    }

    public static void changeGenres(List<String> genres) {
        Logger.debug("Profile::changeGenres\n"
                + "-- genres: " + genres);

        Set<Genre> newGenre = new HashSet<Genre>();

        if (genres != null) {
            for (String genreName : genres) {
                if (genreName != null && genreName.length() > 0) {
                    Genre g = Genre.findById(genreName);
                    newGenre.add(g);
                }
            }
        }

        Gamer gamer = (Gamer) Secure.loadCurrentUser();

        if (gamer != null) {
            gamer.setPreferredGenres(newGenre);
            gamer.save();
        }

        index();
    }

}
