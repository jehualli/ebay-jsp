package mx.jehualli.ebay.util;

/** Escaping and the date format eBay printed on every listing. */
public final class Text {

    private Text() { }

    public static String esc(String s) {
        if (s == null) return "";
        StringBuffer b = new StringBuffer(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '&':  b.append("&amp;");  break;
                case '<':  b.append("&lt;");   break;
                case '>':  b.append("&gt;");   break;
                case '"':  b.append("&quot;"); break;
                case '\'': b.append("&#39;");  break;
                default:   b.append(c);
            }
        }
        return b.toString();
    }

    /**
     * "Dec-14-99 17:32:05 PST", the eBay listing timestamp.
     *
     * Dates are shifted back so the current year prints as 1999. Everything
     * else about the clock is real -- only the label moves.
     */
    private static final int ERA_SHIFT =
        java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) - 1999;

    public static String stamp(long millis) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(millis);
        c.add(java.util.Calendar.YEAR, -ERA_SHIFT);
        java.text.SimpleDateFormat f =
            new java.text.SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        return f.format(c.getTime()) + " PST";
    }

    /** "2d 04h", "14m 09s" -- coarse at a distance, precise at the end. */
    public static String countdown(long millis) {
        if (millis <= 0L) return "ENDED";
        long s = millis / 1000L;
        long d = s / 86400L, h = (s % 86400L) / 3600L, m = (s % 3600L) / 60L;
        if (d > 0) return d + "d " + pad(h) + "h";
        if (h > 0) return h + "h " + pad(m) + "m";
        return m + "m " + pad(s % 60L) + "s";
    }

    private static String pad(long n) { return (n < 10 ? "0" : "") + n; }
}
