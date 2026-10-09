package mx.jehualli.ebay.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * &lt;ebay:loggedIn&gt; ... &lt;/ebay:loggedIn&gt;
 *
 * A conditional tag: return EVAL_BODY_INCLUDE to render the body, SKIP_BODY
 * to drop it. This is how a JSP 1.2 page branches without a scriptlet
 * &lt;% if (...) { %&gt; ... &lt;% } %&gt; straddling its markup.
 */
public class LoggedInTag extends TagSupport {

    public int doStartTag() {
        return signedIn(pageContext.getRequest()) ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    static boolean signedIn(javax.servlet.ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        javax.servlet.http.HttpSession s = req.getSession(false);
        return s != null && s.getAttribute("nick") != null;
    }
}
