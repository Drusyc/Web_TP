package validators;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import play.Logger;
import play.data.validation.Check;
import play.db.jpa.Blob;

public class AvatarCheck extends Check {

    private static final long MAX_SIZE = 2000000;
    private static final int MAX_WIDTH = 1920;
    private static final int MAX_HEIGHT = 1920;

    @Override
    public boolean isSatisfied(Object parent, Object image) {
        if (image == null)
            return true;

        if (!(image instanceof Blob)) {
            return false;
        }

        Blob avatar = (Blob) image;

        // Type check (must be JPEG or PNG)
        if (!avatar.type().equals("image/jpeg") && !avatar.type().equals("image/png")) {
            Logger.debug("AvatarCheck::isSatisfied - Wrong type");
            return false;
        }

        // Size check
        if (avatar.getFile().length() > MAX_SIZE) {
            Logger.debug("AvatarCheck::isSatisfied - Wrong size");
            return false;
        }

        // Dimensions check
        try {
            BufferedImage source = ImageIO.read(avatar.getFile());
            int width = source.getWidth();
            int height = source.getHeight();

            if (width > MAX_WIDTH || height > MAX_HEIGHT) {
                Logger.debug("AvatarCheck::isSatisfied - Wrong dimensions");
                return false;
            }
        } catch (IOException e) {
            Logger.debug("AvatarCheck::isSatisfied - Cannot read avatar");
            return false;
        }

        return true;
    }
}
