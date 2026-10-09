package mx.jehualli.ebay.tags;

import mx.jehualli.ebay.util.Text;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * &lt;ebay:timeLeft end="<%= item.getEndsAt() %>"/&gt;
 *
 * Turns red inside the last hour, which is the only typography that matters
 * on an auction site.
 */
public class TimeLeftTag extends TagSupport {

    private static final long HOUR = 3600000L;

    private long end;

    public void setEnd(long end) { this.end = end; }

    public int doStartTag() throws JspException {
        long left = end - System.currentTimeMillis();
        StringBuffer b = new StringBuffer();
        if (left <= 0L) {
            b.append("<FONT COLOR=\"#808080\">ENDED</FONT>");
        } else if (left < HOUR) {
            b.append("<FONT COLOR=\"#CC0000\"><B>").append(Text.countdown(left))
             .append("</B></FONT>");
        } else {
            b.append(Text.countdown(left));
        }
        try {
            pageContext.getOut().print(b.toString());
        } catch (IOException e) {
            throw new JspException("timeLeft", e);
        }
        return SKIP_BODY;
    }

    public int doEndTag() {
        end = 0L;
        return EVAL_PAGE;
    }
}
