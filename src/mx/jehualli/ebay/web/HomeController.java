package mx.jehualli.ebay.web;

import mx.jehualli.ebay.model.Item;
import mx.jehualli.ebay.service.AuctionHouse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/** The front page: categories, what is ending, and what everyone is fighting over. */
public class HomeController extends BaseController {

    protected void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        AuctionHouse house = house();

        List<Item> ending = house.find(0, null, 0L, 0L, false, AuctionHouse.SORT_ENDING);
        List<Item> hot    = house.find(0, null, 0L, 0L, false, AuctionHouse.SORT_BIDS);

        req.setAttribute("categories", house.getTopCategories());
        req.setAttribute("ending", ending.subList(0, Math.min(8, ending.size())));
        req.setAttribute("hot", hot.subList(0, Math.min(8, hot.size())));
        req.setAttribute("openCount", Integer.valueOf(house.countOpen()));
        req.setAttribute("memberCount", Integer.valueOf(house.countUsers()));

        forward(req, res, "home.jsp");
    }
}
