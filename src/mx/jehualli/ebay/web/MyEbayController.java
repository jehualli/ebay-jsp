package mx.jehualli.ebay.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** My eBay: four views of the same member, one tab strip. */
public class MyEbayController extends BaseController {

    protected void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String nick = nick(req);
        String view = param(req, "view");
        if (view.length() == 0) view = "bidding";

        if ("selling".equals(view)) {
            req.setAttribute("items", house().selling(nick, false));
        } else if ("won".equals(view)) {
            req.setAttribute("items", house().won(nick));
        } else if ("watching".equals(view)) {
            req.setAttribute("items", house().watching(nick));
        } else if ("sold".equals(view)) {
            req.setAttribute("items", house().selling(nick, true));
        } else {
            view = "bidding";
            req.setAttribute("items", house().bidding(nick));
        }

        req.setAttribute("view", view);
        req.setAttribute("me", me(req));
        forward(req, res, "myebay.jsp");
    }
}
