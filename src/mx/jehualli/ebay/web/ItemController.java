package mx.jehualli.ebay.web;

import mx.jehualli.ebay.model.Item;
import mx.jehualli.ebay.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** One listing: description, bid history, seller, and the bid box. */
public class ItemController extends BaseController {

    protected void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Item it = house().getItem(longParam(req, "id", -1L));
        if (it == null) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "No such item number");
            return;
        }

        User me = me(req);
        req.setAttribute("item", it);
        req.setAttribute("seller", house().getUser(it.getSeller()));
        req.setAttribute("bids", it.getBids());
        req.setAttribute("path", house().getPath(it.getCategoryId()));
        req.setAttribute("watching", Boolean.valueOf(
            me != null && me.getWatching().contains(Long.valueOf(it.getId()))));

        forward(req, res, "item.jsp");
    }
}
