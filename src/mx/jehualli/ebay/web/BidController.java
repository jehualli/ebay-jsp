package mx.jehualli.ebay.web;

import mx.jehualli.ebay.model.Item;
import mx.jehualli.ebay.service.BidResult;
import mx.jehualli.ebay.util.Money;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The two-step bid, as eBay ran it: enter a maximum, look at a review page
 * that spells out what you are committing to, then confirm.
 *
 * Every failure path lands back on the review page with the message and the
 * amount the member typed still in the box. Re-rendering a form with its own
 * bad input is the thing Model 2 exists for.
 */
public class BidController extends BaseController {

    protected void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Item it = house().getItem(longParam(req, "id", -1L));
        if (it == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "No such item number");
            return;
        }

        String amount = param(req, "amount");
        req.setAttribute("item", it);
        req.setAttribute("seller", house().getUser(it.getSeller()));
        req.setAttribute("amount", amount);

        boolean confirming = "1".equals(param(req, "confirm"))
                          && "POST".equals(req.getMethod());

        if (!confirming) {
            // Step one. Catch the obvious problems before the review page.
            String error = precheck(it, nick(req), amount);
            req.setAttribute("error", error);
            forward(req, res, "bid.jsp");
            return;
        }

        // Step two: it counts now.
        BidResult r = house().placeBid(nick(req), it.getId(), amount);
        if (!r.isAccepted()) {
            req.setAttribute("error", r.getMessage());
            forward(req, res, "bid.jsp");
            return;
        }

        req.setAttribute("result", r);
        forward(req, res, "bidok.jsp");
    }

    /** Non-binding validation for the review page. */
    private String precheck(Item it, String bidder, String amount) {
        if (it.isEnded())  return "Bidding has closed on this item.";
        if (it.getSeller().equalsIgnoreCase(bidder)) {
            return "You cannot bid on your own item.";
        }
        if (amount.length() == 0) return null;   // first visit, box is empty
        long max = Money.parse(amount);
        if (max <= 0L) {
            return "Enter your maximum bid as a number, for example 24.99";
        }
        if (max < it.getMinimumBidCents()) {
            return "Your maximum bid must be at least "
                 + Money.format(it.getMinimumBidCents()) + ".";
        }
        return null;
    }
}
