package mx.jehualli.ebay.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Bidding, selling, watching and leaving feedback all need a signed-in
 * member. Browsing and searching do not -- eBay's whole growth strategy was
 * that anyone could look.
 */
public class SignInFilter implements Filter {

    public void init(FilterConfig cfg) { }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        javax.servlet.http.HttpSession s = req.getSession(false);
        if (s != null && s.getAttribute("nick") != null) {
            chain.doFilter(request, response);
            return;
        }

        String target = req.getServletPath();
        if (req.getQueryString() != null) target += "?" + req.getQueryString();
        req.getSession(true).setAttribute("afterSignIn", target);
        res.sendRedirect(req.getContextPath() + "/login?why=required");
    }

    public void destroy() { }
}
