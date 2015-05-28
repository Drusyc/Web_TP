package controllers;

import play.mvc.Before;
import play.mvc.Controller;

/**
 * A controller that sets the current user in the renderArgs object.
 * Use this controller to annotate public controllers that need access to the 'user' object
 * in the templates.
 */
public class SecurePublic extends Controller {
    /**
     * Sets the current user (if there's one) in the renderArgs object.
     */
    @Before
    static void setCurrentUserInRenderArgs() {
        Secure.loadCurrentUser();
    }
}
