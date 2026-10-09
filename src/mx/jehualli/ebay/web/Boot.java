package mx.jehualli.ebay.web;

import mx.jehualli.ebay.seed.SeedData;
import mx.jehualli.ebay.service.AuctionCloser;
import mx.jehualli.ebay.service.AuctionHouse;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/** Builds the marketplace at deploy time and starts the auction clock. */
public class Boot implements ServletContextListener {

    static final String HOUSE  = "ebay.house";
    static final String CLOSER = "ebay.closer";

    public void contextInitialized(ServletContextEvent e) {
        ServletContext ctx = e.getServletContext();

        AuctionHouse house = new AuctionHouse();
        AuctionCloser closer = new AuctionCloser(house);
        SeedData.load(house, closer);
        closer.start();

        ctx.setAttribute(HOUSE, house);
        ctx.setAttribute(CLOSER, closer);
        ctx.log("eBay online: " + house.countUsers() + " members, "
                + house.countOpen() + " live auctions.");
    }

    public void contextDestroyed(ServletContextEvent e) {
        AuctionCloser c = (AuctionCloser) e.getServletContext().getAttribute(CLOSER);
        if (c != null) c.stop();
    }

    public static AuctionHouse house(ServletContext ctx) {
        return (AuctionHouse) ctx.getAttribute(HOUSE);
    }
}
