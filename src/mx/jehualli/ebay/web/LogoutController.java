package mx.jehualli.ebay.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/** Sign out. */
public class LogoutController extends BaseController {

    protected void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s != null) s.invalidate();
        redirect(req, res, "/");
    }
}
