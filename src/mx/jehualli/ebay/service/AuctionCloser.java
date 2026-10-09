package mx.jehualli.ebay.service;

import mx.jehualli.ebay.model.Item;
import mx.jehualli.ebay.model.User;
import mx.jehualli.ebay.util.Money;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The clock. Closes auctions when their time is up, and lets the seeded
 * members snipe: a rival proxy bid arriving in the last minutes is the
 * single most eBay thing there is.
 */
public class AuctionCloser {

    private static final long TICK_MS    = 3000L;
    private static final long SNIPE_ZONE = 180000L;   // last 3 minutes
    private static final long ACTIVE_ZONE = 3600000L; // last hour

    private final AuctionHouse house;
    private final Random rnd = new Random();
    private Timer timer;

    /** Members who will bid against you. */
    private final List<String> bots = new ArrayList<String>();

    public AuctionCloser(AuctionHouse house) {
        this.house = house;
    }

    public void addBot(String nick) { bots.add(nick); }

    public void start() {
        timer = new Timer("ebay-auction-clock", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    tick();
                } catch (RuntimeException e) {
                    e.printStackTrace();   // a dead clock would freeze every auction
                }
            }
        }, TICK_MS, TICK_MS);
    }

    public void stop() {
        if (timer != null) timer.cancel();
    }

    private void tick() {
        long now = System.currentTimeMillis();

        for (Item it : house.allItems()) {
            if (it.isClosed()) continue;

            if (now >= it.getEndsAt()) {
                house.closeItem(it);
                continue;
            }

            long left = it.getEndsAt() - now;
            if (left > ACTIVE_ZONE || bots.isEmpty()) continue;

            // Snipers crowd the last three minutes; the rest of the hour is quiet.
            double chance = (left <= SNIPE_ZONE) ? 0.35 : 0.02;
            if (it.getBidCount() == 0) chance = chance / 2.0;
            if (rnd.nextDouble() > chance) continue;

            snipe(it);
        }
    }

    private void snipe(Item it) {
        String bot = bots.get(rnd.nextInt(bots.size()));
        if (bot.equalsIgnoreCase(it.getSeller())) return;
        if (bot.equalsIgnoreCase(it.getLeader())) return;
        User u = house.getUser(bot);
        if (u == null) return;

        // A believable maximum: the minimum, plus a few increments of nerve.
        long minimum = it.getMinimumBidCents();
        long max = minimum + Money.increment(minimum) * rnd.nextInt(4);
        house.placeBid(bot, it.getId(), Money.format(max).substring(1).replace(",", ""));
    }
}
