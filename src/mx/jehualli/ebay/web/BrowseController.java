package mx.jehualli.ebay.web;

import mx.jehualli.ebay.model.Item;
import mx.jehualli.ebay.service.AuctionHouse;
import mx.jehualli.ebay.util.Money;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Browse and Search are the same query with different forms in front of it,
 * so they share one controller and one view.
 */
public class BrowseController extends BaseController {

    private static final int PER_PAGE = 25;

    protected void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        AuctionHouse house = house();

        int cat        = intParam(req, "cat", 0);
        String q       = param(req, "q");
        long min       = Money.parse(param(req, "min"));
        long max       = Money.parse(param(req, "max"));
        boolean done   = "1".equals(param(req, "completed"));
        int sort       = intParam(req, "sort", AuctionHouse.SORT_ENDING);
        int page       = Math.max(1, intParam(req, "page", 1));

        List<Item> all = house.find(cat, q, min, max, done, sort);

        // "View seller's other items" narrows the same result set.
        String seller = param(req, "seller");
        if (seller.length() > 0) {
            List<Item> mine = new ArrayList<Item>();
            for (Item it : all) {
                if (it.getSeller().equalsIgnoreCase(seller)) mine.add(it);
            }
            all = mine;
        }

        int pages = (all.size() + PER_PAGE - 1) / PER_PAGE;
        if (page > pages && pages > 0) page = pages;
        int from = (page - 1) * PER_PAGE;
        int to = Math.min(all.size(), from + PER_PAGE);
        List<Item> shown = from < to ? new ArrayList<Item>(all.subList(from, to))
                                     : new ArrayList<Item>();

        // The link base the pagination tag appends &page=N to.
        StringBuffer base = new StringBuffer(req.getContextPath())
            .append(req.getServletPath()).append("?sort=").append(sort);
        if (cat > 0)  base.append("&amp;cat=").append(cat);
        if (q.length() > 0) base.append("&amp;q=").append(enc(q));
        if (min > 0)  base.append("&amp;min=").append(min / 100);
        if (max > 0)  base.append("&amp;max=").append(max / 100);
        if (done)     base.append("&amp;completed=1");
        if (seller.length() > 0) base.append("&amp;seller=").append(enc(seller));

        req.setAttribute("items", shown);
        req.setAttribute("total", Integer.valueOf(all.size()));
        req.setAttribute("page", Integer.valueOf(page));
        req.setAttribute("pages", Integer.valueOf(pages));
        req.setAttribute("pageBase", base.toString());
        req.setAttribute("cat", Integer.valueOf(cat));
        req.setAttribute("path", house.getPath(cat));
        req.setAttribute("children", house.getChildren(cat));
        req.setAttribute("q", q);
        req.setAttribute("seller", seller);
        req.setAttribute("sort", Integer.valueOf(sort));
        req.setAttribute("completed", Boolean.valueOf(done));
        req.setAttribute("isSearch", Boolean.valueOf(
            "/search".equals(req.getServletPath())));

        forward(req, res, "browse.jsp");
    }

    private static String enc(String s) {
        try {
            return java.net.URLEncoder.encode(s, "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return "";
        }
    }
}
