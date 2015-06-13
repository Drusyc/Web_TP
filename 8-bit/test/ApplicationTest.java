import java.sql.Date;
import java.util.*;

import models.Configuration;
import models.Game;
import models.Gamer;
import models.Genre;
import models.OS;
import models.Processor;
import models.Provider;
import models.VideoCard;

import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;
import utils.BCrypt;

public class ApplicationTest extends FunctionalTest {

    @Test
    public void testThatIndexPageWorks() {
        Response response = GET("/");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);
    }
    
}