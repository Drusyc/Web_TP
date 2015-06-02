package controllers;

import models.Gamer;
import models.User;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import play.Logger;
import play.data.validation.*;
import play.db.jpa.Blob;
import play.i18n.Messages;
import play.mvc.Before;
import play.mvc.Controller;
import utils.BCrypt;
import validators.AvatarCheck;

import java.io.File;
import java.io.IOException;

public class Signup extends Controller {

    private static final String PSEUDO = "pseudo";
    private static final String EMAIL = "email";
    private static final String PSEUDO_TAKEN = "pseudoTaken";

    private static final int THUMB_WIDTH = 35;
    private static final int THUMB_HEIGHT = 35;

    @Before
    /**
     * Checks if user is connected
     */
    public static void checkConnected() throws Throwable {
        if (session.contains("username"))
            redirect("/");
    }

    public static void show(String username, String email) {
        flash.put(PSEUDO, username);
        flash.put(EMAIL, email);
        index();
    }

    public static void index() {
        render();
    }

    /**
     * Verifies a pseudo
     *
     * @param pseudo        The pseudo
     * @return              False if taken, true otherwise
     */
    public static boolean verifyPseudo(String pseudo) {
        return User.findById(pseudo) == null;
    }

    /**
     * Creates an account
     *
     * @param pseudo        The pseudo
     * @param email         The email
     * @param password      The password
     * @param password2     The password verification
     * @param avatar        The avatar (optional)
     */
    public static void createAccount(@Required(message = "requiredPseudo") @MinSize(value = 6, message = "invalidPseudo") @MaxSize(value = 255, message = "invalidPseudo") String pseudo,
                                     @Required(message = "requiredEmail") @Email(message = "invalidEmail") @MaxSize(value = 255, message = "invalidEmail") String email,
                                     @Required(message = "requiredPassword") @MinSize(value = 8, message = "invalidPassword") String password,
                                     @Required(message = "passwordsMustMatch") @Equals(value = "password", message = "passwordsMustMatch") String password2,
                                     @CheckWith(value = AvatarCheck.class, message = "invalidAvatar") Blob avatar) throws Throwable {
        Logger.debug("Signup::createAccount\n"
                + "-- pseudo: " + pseudo + "\n"
                + "-- email: " + email + "\n"
                + "-- password: " + password + "\n"
                + "-- password2: " + password2 + "\n"
                + "-- avatar: " + avatar);

        if (validation.hasErrors()) {
            Logger.debug("Signup::createAccount - Validation errors: " + validation.errorsMap());
            tryAgain(pseudo, email);
        }

        // Create gamer
        User user = new Gamer();
        user.setPseudo(pseudo);

        if (!verifyPseudo(pseudo)) {
            Logger.debug("Signup::createAccount - Pseudo taken");
            validation.addError(PSEUDO, Messages.get(PSEUDO_TAKEN));
            tryAgain(pseudo, email);
        }

        // Set email and hashed password
        user.setMail(email);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

        // Resize and set avatar
        if (avatar != null) {
            toThumb(avatar.getFile());
            user.setAvatar(avatar);
        }

        try {
            user.save();
        } catch (Throwable e) {
            Logger.error("Signup::createAccount - Error while invoking user.save()");
            tryAgain(pseudo, email);
        }

        Logger.info("Signup::createAccount - User " + pseudo + " created.");
        session.put("username", pseudo);
        Secure.redirectToOriginalURL();
    }

    private static void tryAgain(String pseudo, String email) {
        flash.put(PSEUDO, pseudo);
        flash.put(EMAIL, email);
        validation.keep();
        render();
    }

    /**
     * Creates a thumb
     *
     * @param original  The original file
     */
    private static void toThumb(File original) {
        // Create command
        ConvertCmd cmd = new ConvertCmd();

        // Create the operation, add images and operators/options
        IMOperation op = new IMOperation();
        op.addImage(original.getPath());
        op.thumbnail(THUMB_WIDTH, THUMB_HEIGHT);
        op.unsharp(0.1);
        op.gravity("center");
        op.extent(THUMB_WIDTH, THUMB_HEIGHT);
        op.addImage(original.getPath());

        try {
            // Execute the operation
            cmd.run(op);
        } catch (IOException ex) {
            Logger.error("ImageMagic - IOException %s", ex);
        } catch (InterruptedException ex) {
            Logger.error("ImageMagic - InterruptedException %s", ex);
        } catch (IM4JavaException ex) {
            Logger.error("ImageMagic - IM4JavaException %s", ex);
        }
    }
}
