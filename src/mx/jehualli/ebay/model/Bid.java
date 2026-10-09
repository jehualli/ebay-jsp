package mx.jehualli.ebay.model;

/**
 * One row of the bid history.
 *
 * Two amounts matter: what the bidder was willing to pay (their proxy
 * maximum, secret while the auction runs) and what the bid actually moved the
 * price to. eBay only ever showed the second one.
 */
public class Bid {

    private final long itemId;
    private final String bidder;
    private final long maxCents;
    private final long priceCents;
    private final long time;

    public Bid(long itemId, String bidder, long maxCents, long priceCents) {
        this.itemId = itemId;
        this.bidder = bidder;
        this.maxCents = maxCents;
        this.priceCents = priceCents;
        this.time = System.currentTimeMillis();
    }

    public long getItemId()     { return itemId; }
    public String getBidder()   { return bidder; }
    public long getMaxCents()   { return maxCents; }
    public long getPriceCents() { return priceCents; }
    public long getTime()       { return time; }
}
