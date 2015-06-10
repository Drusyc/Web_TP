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
}