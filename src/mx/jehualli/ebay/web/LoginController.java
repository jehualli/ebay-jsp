package mx.jehualli.ebay.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/** Sign in, or register a new member. */
public class LoginController extends BaseController {

    protected void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        if (nick(req) != null && !"POST".equals(req.getMethod())) {
            redirect(req, res, "/");
            return;
        }

        String nick = param(req, "nick");
        req.setAttribute("nick", nick);

        if ("POST".equals(req.getMethod())) {
            String password = param(req, "password");
            boolean registering = req.getParameter("register") != null;
            String error = null;

            if (registering) {
                String location = param(req, "location");
                if (location.length() == 0) location = "Anytown, USA";
                if (!house().register(nick, password, location)) {
                    error = "That User ID is taken, or the ID or password is invalid. "
                          + "User IDs are 2-20 characters.";
                }
            }

            if (error == null && !house().checkPassword(nick, password)) {
                error = house().getUser(nick) == null
                    ? "We have no member with that User ID. Use Register to create one."
                    : "The password you entered does not match that User ID.";
            }

            if (error == null) {
                HttpSession old = req.getSession(false);
                String after = old == null ? null : (String) old.getAttribute("afterSignIn");
                if (old != null) old.invalidate();      // no session fixation

                HttpSession s = req.getSession(true);
                s.setAttribute("nick", house().getUser(nick).getNick());
                redirect(req, res, after == null ? "/" : after);
                return;
            }
            req.setAttribute("error", error);
        }

        forward(req, res, "login.jsp");
    }
}
