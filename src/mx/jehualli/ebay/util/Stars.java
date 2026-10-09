package mx.jehualli.ebay.util;

/**
 * The feedback star. eBay's colour tiers, which members took very seriously.
 */
public final class Stars {

    private Stars() { }

    private static final int[]    FLOOR  = { 10, 50, 100, 500, 1000, 5000, 10000 };
    private static final String[] COLOR  = { "#FFD700", "#40A0FF", "#40E0D0",
                                             "#A020F0", "#FF0000", "#00A000", "#FF00FF" };
    private static final String[] NAME   = { "Yellow star", "Blue star", "Turquoise star",
                                             "Purple star", "Red star", "Green star",
                                             "Shooting star" };

    public static boolean has(int score) { return score >= FLOOR[0]; }

    public static String color(int score) {
        String c = COLOR[0];
        for (int i = 0; i < FLOOR.length; i++) if (score >= FLOOR[i]) c = COLOR[i];
        return c;
    }

    public static String name(int score) {
        String n = NAME[0];
        for (int i = 0; i < FLOOR.length; i++) if (score >= FLOOR[i]) n = NAME[i];
        return n;
    }
}
