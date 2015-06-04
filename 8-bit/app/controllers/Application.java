package controllers;

import models.*;
import play.Logger;
import play.db.jpa.Blob;
import play.mvc.Controller;
import play.mvc.With;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@With(SecurePublic.class)
public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void renderAvatar(String pseudo) {
        User user = User.findById(pseudo);
        notFoundIfNull(user);

        Blob avatar = user.getAvatar();
        if (avatar.exists()) {
            response.setContentTypeIfNotSet(avatar.type());
            renderBinary(avatar.get());
        } else {
            // Default avatar
            File file = new File("public/images/avatars/default.png");

            try {
                FileInputStream fis = new FileInputStream(file);
                response.setContentTypeIfNotSet("image/png");
                renderBinary(fis);
            } catch (IOException e) {
                Logger.error("Application::renderAvatar - " + e);
            }
        }
    }
}