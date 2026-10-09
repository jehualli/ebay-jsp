package mx.jehualli.ebay.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** The help page. A view with no model, but it still goes through a controller. */
public class HelpController extends BaseController {

    protected void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        forward(req, res, "help.jsp");
    }
}
