package mx.jehualli.ebay.service;

import mx.jehualli.ebay.model.Bid;
import mx.jehualli.ebay.model.Category;
import mx.jehualli.ebay.model.Feedback;
import mx.jehualli.ebay.model.Item;
import mx.jehualli.ebay.model.User;
import mx.jehualli.ebay.util.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The whole marketplace. One instance lives in the ServletContext, so every
 * method runs on container request threads and has to be safe for that.
 */
public class AuctionHouse {

    public static final int SORT_ENDING     = 0;
    public static final int SORT_NEW        = 1;
    public static final int SORT_PRICE_UP   = 2;
    public static final int SORT_PRICE_DOWN = 3;
    public static final int SORT_BIDS       = 4;

    private final Map<String, User> users = new ConcurrentHashMap<String, User>();
    private final Map<Long, Item> items = new ConcurrentHashMap<Long, Item>();
    private final Map<Integer, Category> categories =
        Collections.synchronizedMap(new LinkedHashMap<Integer, Category>());
    private final List<Feedback> feedback =
        Collections.synchronizedList(new ArrayList<Feedback>());

    private final AtomicLong itemIds = new AtomicLong(1000000000L);
    private final long bootedAt = System.currentTimeMillis();

    // ------------------------------------------------------------- members

    public boolean register(String nick, String password, String location) {
        if (nick == null || !nick.matches("[A-Za-z0-9_.-]{2,20}")) return false;
        if (password == null || password.length() == 0) return false;
        if (users.containsKey(nick.toLowerCase())) return false;
        users.put(nick.toLowerCase(),
                  new User(nick, password, location, System.currentTimeMillis()));
        return true;
    }

    public void addUser(User u) { users.put(u.getNick().toLowerCase(), u); }

    public User getUser(String nick) {
        return nick == null ? null : users.get(nick.toLowerCase());
    }

    public boolean checkPassword(String nick, String password) {
        User u = getUser(nick);
        return u != null && u.getPassword().equals(password);
    }

    public int countUsers() { return users.size(); }

    // ---------------------------------------------------------- categories

    public void addCategory(Category c) { categories.put(Integer.valueOf(c.getId()), c); }

    public Category getCategory(int id) { return categories.get(Integer.valueOf(id)); }

    public List<Category> getTopCategories() {
        List<Category> out = new ArrayList<Category>();
        synchronized (categories) {
            for (Category c : categories.values()) if (c.isTop()) out.add(c);
        }
        return out;
    }

    public List<Category> getChildren(int parentId) {
        List<Category> out = new ArrayList<Category>();
        synchronized (categories) {
            for (Category c : categories.values()) {
                if (c.getParentId() == parentId) out.add(c);
            }
        }
        return out;
    }

    /** "Collectibles > Beanie Babies", as the breadcrumb wants it. */
    public List<Category> getPath(int categoryId) {
        List<Category> path = new ArrayList<Category>();
        Category c = getCategory(categoryId);
        while (c != null) {
            path.add(0, c);
            c = c.getParentId() == 0 ? null : getCategory(c.getParentId());
        }
        return path;
    }

    /** Live listings in a category, counting its children. */
    public int countOpenIn(int categoryId) {
        int n = 0;
        for (Item it : items.values()) {
            if (!it.isEnded() && inCategory(it, categoryId)) n++;
        }
        return n;
    }

    private boolean inCategory(Item it, int categoryId) {
        if (categoryId <= 0) return true;
        if (it.getCategoryId() == categoryId) return true;
        Category c = getCategory(it.getCategoryId());
        return c != null && c.getParentId() == categoryId;
    }

    // -------------------------------------------------------------- items

    public Item list(String seller, String title, String description, int categoryId,
                     String condition, String location, long startCents,
                     long reserveCents, int durationDays) {
        long now = System.currentTimeMillis();
        Item it = new Item(nextItemId(), title, description, categoryId,
                           seller, condition, location, startCents, reserveCents,
                           now, now + durationDays * 86400000L);
        items.put(Long.valueOf(it.getId()), it);
        return it;
    }

    public void addItem(Item it) { items.put(Long.valueOf(it.getId()), it); }

    /**
     * The next listing number. Seeding goes through here too, so a member's
     * first listing cannot land on top of a seeded auction.
     */
    public long nextItemId() { return itemIds.incrementAndGet(); }

    public Item getItem(long id) { return items.get(Long.valueOf(id)); }

    public List<Item> allItems() { return new ArrayList<Item>(items.values()); }

    public int countOpen() {
        int n = 0;
        for (Item it : items.values()) if (!it.isEnded()) n++;
        return n;
    }

    public long getUptimeMillis() { return System.currentTimeMillis() - bootedAt; }

    /**
     * The one query behind both Browse and Search.
     */
    public List<Item> find(int categoryId, String keywords, long minCents,
                           long maxCents, boolean completed, int sort) {
        List<Item> out = new ArrayList<Item>();
        for (Item it : items.values()) {
            if (completed != it.isEnded()) continue;
            if (!inCategory(it, categoryId)) continue;
            if (!it.matches(keywords)) continue;
            if (minCents > 0 && it.getCurrentCents() < minCents) continue;
            if (maxCents > 0 && it.getCurrentCents() > maxCents) continue;
            out.add(it);
        }
        sort(out, sort);
        return out;
    }

    public void sort(List<Item> list, int sort) {
        Comparator<Item> c;
        switch (sort) {
            case SORT_NEW:
                c = new Comparator<Item>() {
                    public int compare(Item a, Item b) {
                        return sign(b.getStartedAt() - a.getStartedAt());
                    }
                };
                break;
            case SORT_PRICE_UP:
                c = new Comparator<Item>() {
                    public int compare(Item a, Item b) {
                        return sign(a.getCurrentCents() - b.getCurrentCents());
                    }
                };
                break;
            case SORT_PRICE_DOWN:
                c = new Comparator<Item>() {
                    public int compare(Item a, Item b) {
                        return sign(b.getCurrentCents() - a.getCurrentCents());
                    }
                };
                break;
            case SORT_BIDS:
                c = new Comparator<Item>() {
                    public int compare(Item a, Item b) {
                        return b.getBidCount() - a.getBidCount();
                    }
                };
                break;
            default:
                c = new Comparator<Item>() {
                    public int compare(Item a, Item b) {
                        return sign(a.getEndsAt() - b.getEndsAt());
                    }
                };
        }
        Collections.sort(list, c);
    }

    private static int sign(long l) { return l < 0 ? -1 : (l > 0 ? 1 : 0); }

    // ------------------------------------------------------------ bidding

    public BidResult placeBid(String bidder, long itemId, String amountText) {
        Item it = getItem(itemId);
        if (it == null) {
            return new BidResult(BidResult.NO_SUCH_ITEM, null,
                                 "That item number does not exist.");
        }
        if (it.isEnded()) {
            return new BidResult(BidResult.ENDED, it,
                                 "Bidding has closed on this item.");
        }
        if (it.getSeller().equalsIgnoreCase(bidder)) {
            return new BidResult(BidResult.OWN_ITEM, it,
                                 "You cannot bid on your own item.");
        }

        long max = Money.parse(amountText);
        if (max <= 0L) {
            return new BidResult(BidResult.BAD_AMOUNT, it,
                                 "Enter your maximum bid as a number, for example 24.99");
        }
        long minimum = it.getMinimumBidCents();
        if (max < minimum) {
            return new BidResult(BidResult.TOO_LOW, it,
                                 "Your maximum bid must be at least "
                                 + Money.format(minimum) + ".");
        }

        String leader = it.applyBid(bidder, max);
        if (bidder.equalsIgnoreCase(leader)) {
            return new BidResult(BidResult.LEADING, it,
                                 it.isReserveMet() || !it.hasReserve()
                                 ? "You are the current high bidder."
                                 : "You are the high bidder, but the reserve price "
                                   + "has not been met.");
        }
        return new BidResult(BidResult.OUTBID, it,
                             "You have been outbid by another bidder's maximum. "
                             + "The current price is now "
                             + Money.format(it.getCurrentCents()) + ".");
    }

    void closeItem(Item it) { it.close(); }

    // --------------------------------------------------------- my ebay

    public List<Item> selling(String nick, boolean completed) {
        List<Item> out = new ArrayList<Item>();
        for (Item it : items.values()) {
            if (it.getSeller().equalsIgnoreCase(nick) && it.isEnded() == completed) {
                out.add(it);
            }
        }
        sort(out, SORT_ENDING);
        return out;
    }

    public List<Item> bidding(String nick) {
        List<Item> out = new ArrayList<Item>();
        for (Item it : items.values()) {
            if (it.isEnded()) continue;
            if (hasBid(it, nick)) out.add(it);
        }
        sort(out, SORT_ENDING);
        return out;
    }

    public List<Item> won(String nick) {
        List<Item> out = new ArrayList<Item>();
        for (Item it : items.values()) {
            if (it.isEnded() && nick.equalsIgnoreCase(it.getWinner())) out.add(it);
        }
        sort(out, SORT_ENDING);
        return out;
    }

    public List<Item> watching(String nick) {
        User u = getUser(nick);
        List<Item> out = new ArrayList<Item>();
        if (u == null) return out;
        for (Long id : new ArrayList<Long>(u.getWatching())) {
            Item it = getItem(id.longValue());
            if (it != null) out.add(it);
        }
        sort(out, SORT_ENDING);
        return out;
    }

    public boolean hasBid(Item it, String nick) {
        for (Bid b : it.getBids()) {
            if (b.getBidder().equalsIgnoreCase(nick)) return true;
        }
        return false;
    }

    // -------------------------------------------------------- feedback

    public void leaveFeedback(String from, String to, long itemId, int rating,
                              String comment) {
        User target = getUser(to);
        if (target == null) return;
        feedback.add(new Feedback(from, to, itemId, rating, comment, true));
        target.addFeedback(rating);
    }

    public List<Feedback> feedbackFor(String nick) {
        List<Feedback> out = new ArrayList<Feedback>();
        synchronized (feedback) {
            for (Feedback f : feedback) {
                if (f.getTo().equalsIgnoreCase(nick)) out.add(f);
            }
        }
        Collections.reverse(out);
        return out;
    }
}
