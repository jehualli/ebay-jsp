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
 * Sell Your Item. A long form with real validation: on failure the view gets
 * the error list and the member's own input back, so nothing they typed is
 * lost. On success we redirect rather than forward, so a refresh does not
 * list the item twice.
 */
public class SellController extends BaseController {

    protected void handle(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        AuctionHouse house = house();
        req.setAttribute("leaves", leafCategories(house));

        if (!"POST".equals(req.getMethod())) {
            forward(req, res, "sell.jsp");
            return;
        }

        String title = param(req, "title");
        String description = param(req, "description");
        int cat = intParam(req, "cat", 0);
        String condition = param(req, "condition");
        long start = Money.parse(param(req, "start"));
        String reserveText = param(req, "reserve");
        long reserve = reserveText.length() == 0 ? 0L : Money.parse(reserveText);
        int days = intParam(req, "days", 7);

        List<String> errors = new ArrayList<String>();
        if (title.length() < 5 || title.length() > 60) {
            errors.add("Title must be between 5 and 60 characters.");
        }
        if (description.length() < 10) {
            errors.add("Please write at least a sentence of description.");
        }
        if (house.getCategory(cat) == null || house.getCategory(cat).isTop()) {
            errors.add("Choose a category for your item.");
        }
        if (start <= 0L) {
            errors.add("Enter a starting price, for example 9.99");
        }
        if (reserveText.length() > 0 && reserve <= 0L) {
            errors.add("The reserve price must be a number, or left blank.");
        }
        if (reserve > 0L && start > 0L && reserve < start) {
            errors.add("A reserve price cannot be lower than the starting price.");
        }
        if (days != 3 && days != 5 && days != 7 && days != 10) {
            errors.add("Duration must be 3, 5, 7 or 10 days.");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            forward(req, res, "sell.jsp");
            return;
        }

        Item it = house.list(nick(req), title, description, cat, condition,
                             me(req).getLocation(), start, reserve, days);
        redirect(req, res, "/item?id=" + it.getId() + "&listed=1");
    }

    private List<mx.jehualli.ebay.model.Category> leafCategories(AuctionHouse house) {
        List<mx.jehualli.ebay.model.Category> out = new ArrayList<mx.jehualli.ebay.model.Category>();
        List<mx.jehualli.ebay.model.Category> tops = house.getTopCategories();
        for (int i = 0; i < tops.size(); i++) {
            out.addAll(house.getChildren(tops.get(i).getId()));
        }
        return out;
    }
}
