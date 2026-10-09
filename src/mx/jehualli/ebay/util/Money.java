package mx.jehualli.ebay.util;

import java.text.DecimalFormat;

/**
 * Money is long cents everywhere. Doubles and auction prices do not mix.
 */
public final class Money {

    private Money() { }

    public static String format(long cents) {
        boolean neg = cents < 0;
        long abs = Math.abs(cents);
        String s = "$" + new DecimalFormat("#,##0").format(abs / 100)
                 + "." + (abs % 100 < 10 ? "0" : "") + (abs % 100);
        return neg ? "-" + s : s;
    }

    /** Parses "1,234.56", "$1234.56" or "1234" into cents; -1 if unparseable. */
    public static long parse(String s) {
        if (s == null) return -1L;
        String t = s.trim().replace("$", "").replace(",", "").replace(" ", "");
        if (t.length() == 0) return -1L;
        int dot = t.indexOf('.');
        try {
            if (dot < 0) return Long.parseLong(t) * 100L;
            String whole = dot == 0 ? "0" : t.substring(0, dot);
            String frac = (t.substring(dot + 1) + "00").substring(0, 2);
            long c = Long.parseLong(whole) * 100L + Long.parseLong(frac);
            return c;
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    /**
     * eBay's published bid increment table. The amount a competing bid has to
     * clear the current price by.
     */
    public static long increment(long cents) {
        if (cents <    100L) return    5L;   // under $1.00      -> $0.05
        if (cents <    500L) return   25L;   // $1.00 - $4.99    -> $0.25
        if (cents <   2500L) return   50L;   // $5.00 - $24.99   -> $0.50
        if (cents <  10000L) return  100L;   // $25 - $99.99     -> $1.00
        if (cents <  25000L) return  250L;   // $100 - $249.99   -> $2.50
        if (cents <  50000L) return  500L;   // $250 - $499.99   -> $5.00
        if (cents < 100000L) return 1000L;   // $500 - $999.99   -> $10.00
        if (cents < 250000L) return 2500L;   // $1000 - $2499.99 -> $25.00
        if (cents < 500000L) return 5000L;   // $2500 - $4999.99 -> $50.00
        return 10000L;                       // $5000 and up     -> $100.00
    }
}
