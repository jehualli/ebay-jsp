package mx.jehualli.ebay.web;

import mx.jehualli.ebay.model.User;
import mx.jehualli.ebay.service.AuctionHouse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Model 2 in one class: the servlet is the controller. It reads parameters,
 * talks to the model, drops the results into request scope under known names,
 * and forwards to a JSP that does nothing but render them.
 *
 * No subclass holds mutable state. One servlet instance serves every request
 * thread, so a field here would be a field shared by every visitor at once.
 */
public abstract class BaseController extends HttpServlet {

    /** Views live under /WEB-INF so no one can request a JSP directly. */
    private static final String VIEWS = "/WEB-INF/jsp/";

    protected abstract void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException;

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.setCharacterEncoding("ISO-8859-1");
        res.setContentType("text/html; charset=iso-8859-1");
        handle(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doGet(req, res);
    }

    protected void forward(HttpServletRequest req, HttpServletResponse res, String view)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher(VIEWS + view).forward(req, res);
    }

    protected void redirect(HttpServletRequest req, HttpServletResponse res, String path)
            throws IOException {
        res.sendRedirect(req.getContextPath() + path);
    }

    // ------------------------------------------------------------ shortcuts

    protected AuctionHouse house() { return Boot.house(getServletContext()); }

    protected String nick(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s == null ? null : (String) s.getAttribute("nick");
    }

    protected User me(HttpServletRequest req) {
        return house().getUser(nick(req));
    }

    protected static String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v == null ? "" : v.trim();
    }

    protected static int intParam(HttpServletRequest req, String name, int dflt) {
        try {
            return Integer.parseInt(param(req, name));
        } catch (NumberFormatException e) {
            return dflt;
        }
    }

    protected static long longParam(HttpServletRequest req, String name, long dflt) {
        try {
            return Long.parseLong(param(req, name));
        } catch (NumberFormatException e) {
            return dflt;
        }
    }
}
