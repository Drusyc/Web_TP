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
import play.mvc.Controller;
import utils.BCrypt;
import validators.AvatarCheck;

import java.io.File;
import java.io.IOException;

public class Signup extends Controller {

    private static final String PSEUDO = "pseudo";
    private static final String EMAIL = "email";
    private static final String PSEUDO_TAKEN = "pseudoTaken";
    private static final String ERROR_CREATING_ACCOUNT = "errorCreatingAccount";
    private static final String ACCOUNT_CREATED = "accountCreated";

    private static final int THUMB_WIDTH = 48;
    private static final int THUMB_HEIGHT = 48;

    public static void show() {
        renderTemplate("signup.html");
    }

    /**
     * Creates an account
     *
     * @param pseudo        The pseudo
     * @param email         The email
     * @param password      The password
     * @param password2     The password verification
     */
    public static void createAccount(@Required(message = "requiredPseudo") String pseudo,
                                     @Required @Email(message = "invalidEmail") String email,
                                     @Required @Min(value = 8, message = "invalidPassword") String password,
                                     @Required @Equals(message = "passwordsMustMatch", value = "password") String password2,
                                     @CheckWith(AvatarCheck.class) Blob avatar) {
        if (validation.hasErrors()) {
            tryAgain(pseudo, email);
        }

        // Create gamer
        User user = new Gamer();
        user.setPseudo(pseudo);

        if (User.find(pseudo) != null) {
            validation.addError(PSEUDO, Messages.get(PSEUDO_TAKEN));
            tryAgain(pseudo, email);
        }

        // Set email and hashed password
        user.setMail(email);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

        // Resize and set avatar
        toThumb(avatar.getFile());
        user.setAvatar(avatar);

        try {
            user.save();
        } catch (Throwable e) {
            Logger.error("Error while invoking user.save()");
            flash.error(Messages.get(ERROR_CREATING_ACCOUNT));
            tryAgain(pseudo, email);
        }

        Logger.info("User " + pseudo + " created.");
        flash.success(Messages.get(ACCOUNT_CREATED));
        show();
    }

    private static void tryAgain(String pseudo, String email) {
        flash.put(PSEUDO, pseudo);
        flash.put(EMAIL, email);
        validation.keep();
        show();
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
