package mx.jehualli.ebay.tags;

import mx.jehualli.ebay.util.Money;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * &lt;ebay:money cents="<%= item.getCurrentCents() %>"/&gt;
 *
 * A JSP 1.1/1.2 custom tag: a handler class, a setter per attribute, and an
 * entry in the TLD. This is what the era had instead of ${...} formatting.
 */
public class MoneyTag extends TagSupport {

    private long cents;

    public void setCents(long cents) { this.cents = cents; }

    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print(Money.format(cents));
        } catch (IOException e) {
            throw new JspException("money", e);
        }
        return SKIP_BODY;
    }

    /** Tag handlers are pooled and reused, so reset state on the way out. */
    public int doEndTag() {
        cents = 0L;
        return EVAL_PAGE;
    }
}
