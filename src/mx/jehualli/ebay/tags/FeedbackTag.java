package mx.jehualli.ebay.tags;

import mx.jehualli.ebay.model.User;
import mx.jehualli.ebay.util.Stars;
import mx.jehualli.ebay.util.Text;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * &lt;ebay:feedback user="<%= seller %>"/&gt;
 *
 * Renders "beaniequeen99 (847) &#9733;" with the star in its colour tier,
 * linked to the member's feedback profile. Every member nick on the site goes
 * through this tag, which is exactly the job a tag library is for.
 */
public class FeedbackTag extends TagSupport {

    private User user;
    private boolean link = true;

    public void setUser(User user) { this.user = user; }
    public void setLink(boolean link) { this.link = link; }

    public int doStartTag() throws JspException {
        if (user == null) return SKIP_BODY;

        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        int score = user.getFeedbackScore();

        StringBuffer b = new StringBuffer();
        if (link) {
            b.append("<A HREF=\"").append(req.getContextPath())
             .append("/feedback?user=").append(Text.esc(user.getNick()))
             .append("\">").append(Text.esc(user.getNick())).append("</A>");
        } else {
            b.append(Text.esc(user.getNick()));
        }
        b.append(" <FONT SIZE=\"1\">(").append(score).append(")</FONT>");
        if (Stars.has(score)) {
            b.append(" <FONT COLOR=\"").append(Stars.color(score))
             .append("\" TITLE=\"").append(Stars.name(score))
             .append("\"><B>&#9733;</B></FONT>");
        }

        try {
            pageContext.getOut().print(b.toString());
        } catch (IOException e) {
            throw new JspException("feedback", e);
        }
        return SKIP_BODY;
    }

    public int doEndTag() {
        user = null;
        link = true;
        return EVAL_PAGE;
    }
}
