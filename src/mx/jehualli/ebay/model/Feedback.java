package mx.jehualli.ebay.model;

/** One feedback comment. +1 praise, 0 neutral, -1 complaint. */
public class Feedback {

    private final String from;
    private final String to;
    private final long itemId;
    private final int rating;
    private final String comment;
    private final long time;
    private final boolean fromBuyer;

    public Feedback(String from, String to, long itemId, int rating,
                    String comment, boolean fromBuyer) {
        this.from = from;
        this.to = to;
        this.itemId = itemId;
        this.rating = rating;
        this.comment = comment;
        this.fromBuyer = fromBuyer;
        this.time = System.currentTimeMillis();
    }

    public String getFrom()    { return from; }
    public String getTo()      { return to; }
    public long getItemId()    { return itemId; }
    public int getRating()     { return rating; }
    public String getComment() { return comment; }
    public long getTime()      { return time; }
    public boolean isFromBuyer() { return fromBuyer; }

    public String getRatingLabel() {
        return rating > 0 ? "Praise" : (rating < 0 ? "Complaint" : "Neutral");
    }
}
