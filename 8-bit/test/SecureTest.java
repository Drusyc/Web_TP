import org.junit.Before;
import org.junit.Test;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class SecureTest extends FunctionalTest {

    @Before
    public void clearSession() {
         GET("/logout");
    }

    @Test
    public void testSecurity() {
        /* On vérifie qu'un utilisateur non connecté est redirigé vers la page d'inscription */
        Response response = GET("/configuration");
        assertStatus(302, response);
        assertHeaderEquals("Location", "/signup", response);

        response = GET("/search");
        assertStatus(302, response);
        assertHeaderEquals("Location", "/signup", response);

        response = GET("/suggestions");
        assertStatus(302, response);
        assertHeaderEquals("Location", "/signup", response);

        response = GET("/manage");
        assertStatus(302, response);
        assertHeaderEquals("Location", "/signup", response);

    }
}