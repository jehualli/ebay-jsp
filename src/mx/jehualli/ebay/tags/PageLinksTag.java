package mx.jehualli.ebay.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * &lt;ebay:pageLinks page="2" pages="9" base="/browse?cat=11&amp;sort=0"/&gt;
 *
 * The "&lt; Previous 1 2 [3] 4 5 Next &gt;" strip under every result table.
 * Pagination is the classic case for a tag: it is pure presentation, it
 * appears on four different pages, and nobody wants it in a servlet.
 */
public class PageLinksTag extends TagSupport {

    private static final int WINDOW = 5;

    private int page;
    private int pages;
    private String base;

    public void setPage(int page)   { this.page = page; }
    public void setPages(int pages) { this.pages = pages; }
    public void setBase(String base) { this.base = base; }

    public int doStartTag() throws JspException {
        if (pages <= 1) return SKIP_BODY;

        String sep = (base.indexOf('?') < 0) ? "?" : "&amp;";
        StringBuffer b = new StringBuffer("<FONT SIZE=\"2\">");

        if (page > 1) {
            b.append("<A HREF=\"").append(base).append(sep).append("page=")
             .append(page - 1).append("\">&lt; Previous</A> &nbsp;");
        }

        int from = Math.max(1, page - WINDOW / 2);
        int to = Math.min(pages, from + WINDOW - 1);
        from = Math.max(1, to - WINDOW + 1);

        for (int i = from; i <= to; i++) {
            if (i == page) {
                b.append("<B>[").append(i).append("]</B> ");
            } else {
                b.append("<A HREF=\"").append(base).append(sep).append("page=")
                 .append(i).append("\">").append(i).append("</A> ");
            }
        }

        if (page < pages) {
            b.append("&nbsp;<A HREF=\"").append(base).append(sep).append("page=")
             .append(page + 1).append("\">Next &gt;</A>");
        }
        b.append("</FONT>");

        try {
            pageContext.getOut().print(b.toString());
        } catch (IOException e) {
            throw new JspException("pageLinks", e);
        }
        return SKIP_BODY;
    }

    public int doEndTag() {
        page = 0;
        pages = 0;
        base = null;
        return EVAL_PAGE;
    }
}
