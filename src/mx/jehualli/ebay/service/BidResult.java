package mx.jehualli.ebay.service;

import mx.jehualli.ebay.model.Item;

/**
 * The outcome of a bid attempt, in the shape a JSP wants it: a flag, a
 * message, and the item to re-render.
 */
public class BidResult {

    public static final int LEADING      = 0;
    public static final int OUTBID       = 1;
    public static final int ENDED        = 2;
    public static final int OWN_ITEM     = 3;
    public static final int BAD_AMOUNT   = 4;
    public static final int TOO_LOW      = 5;
    public static final int NO_SUCH_ITEM = 6;

    private final int code;
    private final Item item;
    private final String message;

    public BidResult(int code, Item item, String message) {
        this.code = code;
        this.item = item;
        this.message = message;
    }

    public int getCode()       { return code; }
    public Item getItem()      { return item; }
    public String getMessage() { return message; }

    public boolean isAccepted() { return code == LEADING || code == OUTBID; }
    public boolean isLeading()  { return code == LEADING; }
}
