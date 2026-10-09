package mx.jehualli.ebay.tags;

import mx.jehualli.ebay.util.Text;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * &lt;ebay:esc value="<%= item.getTitle() %>"/&gt;
 *
 * HTML-escaped output. JSTL's c:out did this from 2002; before that every
 * shop wrote its own, and the ones that did not got their listing titles
 * rewritten by whoever typed &lt;SCRIPT&gt; into a form first.
 */
public class EscTag extends TagSupport {

    private String value;

    public void setValue(String value) { this.value = value; }

    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print(Text.esc(value));
        } catch (IOException e) {
            throw new JspException("esc", e);
        }
        return SKIP_BODY;
    }

    public int doEndTag() {
        value = null;
        return EVAL_PAGE;
    }
}
