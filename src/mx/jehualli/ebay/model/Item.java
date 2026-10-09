package mx.jehualli.ebay.model;

import mx.jehualli.ebay.util.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * One auction listing, and the proxy-bidding state machine that goes with it.
 *
 * The rule that makes eBay eBay: a bidder enters the most they will pay, and
 * the system bids only as much as it needs to. So the displayed price is
 * driven by the *loser's* maximum, not the winner's.
 */
public class Item {

    private final long id;
    private final String title;
    private final String description;
    private final int categoryId;
    private final String seller;
    private final String condition;
    private final String location;
    private final long startCents;
    private final long reserveCents;      // 0 = no reserve
    private final long startedAt;
    private final long endsAt;

    private long currentCents;
    private String leader;                // current high bidder
    private long leaderMaxCents;          // the leader's secret proxy maximum
    private int bidCount;
    private boolean closed;
    private String winner;

    private final List<Bid> bids = Collections.synchronizedList(new ArrayList<Bid>());

    public Item(long id, String title, String description, int categoryId,
                String seller, String condition, String location,
                long startCents, long reserveCents, long startedAt, long endsAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.seller = seller;
        this.condition = condition;
        this.location = location;
        this.startCents = startCents;
        this.reserveCents = reserveCents;
        this.startedAt = startedAt;
        this.endsAt = endsAt;
        this.currentCents = startCents;
    }

    public long getId()            { return id; }
    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public int getCategoryId()     { return categoryId; }
    public String getSeller()      { return seller; }
    public String getCondition()   { return condition; }
    public String getLocation()    { return location; }
    public long getStartCents()    { return startCents; }
    public long getReserveCents()  { return reserveCents; }
    public long getStartedAt()     { return startedAt; }
    public long getEndsAt()        { return endsAt; }

    public synchronized long getCurrentCents()   { return currentCents; }
    public synchronized String getLeader()       { return leader; }
    public synchronized long getLeaderMaxCents() { return leaderMaxCents; }
    public synchronized int getBidCount()        { return bidCount; }
    public synchronized boolean isClosed()       { return closed; }
    public synchronized String getWinner()       { return winner; }

    public List<Bid> getBids() {
        synchronized (bids) {
            return new ArrayList<Bid>(bids);
        }
    }

    public boolean isEnded() {
        return closed || System.currentTimeMillis() >= endsAt;
    }

    public long getMillisLeft() {
        return Math.max(0L, endsAt - System.currentTimeMillis());
    }

    public boolean hasReserve() { return reserveCents > 0L; }

    public synchronized boolean isReserveMet() {
        return reserveCents <= 0L || currentCents >= reserveCents;
    }

    /** What the next competing bidder has to enter, at minimum. */
    public synchronized long getMinimumBidCents() {
        if (bidCount == 0) return startCents;
        return currentCents + Money.increment(currentCents);
    }

    // ------------------------------------------------------------- bidding

    /**
     * Applies a proxy bid. This method is the only writer of the price
     * fields, and it is synchronized so two bids arriving on two request
     * threads cannot interleave. Go through AuctionHouse.placeBid rather
     * than calling it directly: the validation lives there.
     *
     * @return the bidder who now leads
     */
    public synchronized String applyBid(String bidder, long maxCents) {
        if (bidCount == 0) {
            currentCents = startCents;
            leader = bidder;
            leaderMaxCents = maxCents;
            bidCount++;
            bids.add(new Bid(id, bidder, maxCents, currentCents));
            return bidder;
        }

        if (bidder.equalsIgnoreCase(leader)) {
            // Raising your own ceiling. The price does not move.
            leaderMaxCents = Math.max(leaderMaxCents, maxCents);
            bidCount++;
            bids.add(new Bid(id, bidder, maxCents, currentCents));
            return bidder;
        }

        if (maxCents > leaderMaxCents) {
            // The challenger wins the lead, but only pays one increment over
            // what the old leader was prepared to go to.
            long price = Math.min(maxCents,
                                  leaderMaxCents + Money.increment(leaderMaxCents));
            currentCents = price;
            leader = bidder;
            leaderMaxCents = maxCents;
            bidCount++;
            bids.add(new Bid(id, bidder, maxCents, price));
            return bidder;
        }

        // Outbid on arrival: the standing proxy answers automatically and the
        // price rises to just over the challenger's maximum. Both halves go
        // into the history, which is why eBay bid histories read as a
        // cascade -- most of those rows were placed by nobody.
        long price = Math.min(leaderMaxCents, maxCents + Money.increment(maxCents));
        bids.add(new Bid(id, bidder, maxCents, maxCents));
        bidCount++;
        currentCents = price;
        bids.add(new Bid(id, leader, leaderMaxCents, price));
        bidCount++;
        return leader;
    }

    /** Called by the auction clock when the listing's time is up. */
    public synchronized void close() {
        if (closed) return;
        closed = true;
        winner = (leader != null && isReserveMet()) ? leader : null;
    }

    public boolean matches(String keywords) {
        if (keywords == null || keywords.trim().length() == 0) return true;
        String hay = (title + " " + description).toLowerCase();
        String[] words = keywords.toLowerCase().trim().split("\\s+");
        for (int i = 0; i < words.length; i++) {
            if (hay.indexOf(words[i]) < 0) return false;
        }
        return true;
    }
}
