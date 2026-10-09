package mx.jehualli.ebay.tags;

import javax.servlet.jsp.tagext.TagSupport;

/** The other half of &lt;ebay:loggedIn&gt;: renders its body only to signed-out visitors. */
public class AnonymousTag extends TagSupport {

    public int doStartTag() {
        return LoggedInTag.signedIn(pageContext.getRequest()) ? SKIP_BODY : EVAL_BODY_INCLUDE;
    }
}
