package mx.jehualli.ebay.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/** A registered member. A JavaBean, so JSPs can reach it with jsp:getProperty. */
public class User {

    private final String nick;
    private String password;
    private String email;
    private String location;
    private final long joined;
    private int feedbackScore;
    private int positives;
    private int neutrals;
    private int negatives;

    private final Set<Long> watching =
        Collections.synchronizedSet(new LinkedHashSet<Long>());

    public User(String nick, String password, String location, long joined) {
        this.nick = nick;
        this.password = password;
        this.location = location;
        this.joined = joined;
    }

    public String getNick()     { return nick; }
    public String getPassword() { return password; }
    public void setPassword(String p) { this.password = p; }

    public String getEmail()    { return email == null ? nick + "@aol.com" : email; }
    public void setEmail(String e) { this.email = e; }

    public String getLocation() { return location; }
    public void setLocation(String l) { this.location = l; }

    public long getJoined()     { return joined; }

    public int getFeedbackScore() { return feedbackScore; }
    public int getPositives()     { return positives; }
    public int getNeutrals()      { return neutrals; }
    public int getNegatives()     { return negatives; }

    public synchronized void addFeedback(int rating) {
        feedbackScore += rating;
        if (rating > 0) positives++;
        else if (rating < 0) negatives++;
        else neutrals++;
    }

    /** Seeds a member with a plausible history without writing every comment. */
    public synchronized void seedFeedback(int score, int neg) {
        this.feedbackScore = score;
        this.positives = score + neg;
        this.negatives = neg;
    }

    /** The percentage eBay printed next to every nick. */
    public String getPositivePercent() {
        int rated = positives + negatives;
        if (rated == 0) return "--";
        return (positives * 100 / rated) + "%";
    }

    public Set<Long> getWatching() { return watching; }
}
