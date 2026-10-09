package mx.jehualli.ebay.seed;

import mx.jehualli.ebay.model.Category;
import mx.jehualli.ebay.model.Item;
import mx.jehualli.ebay.model.User;
import mx.jehualli.ebay.service.AuctionCloser;
import mx.jehualli.ebay.service.AuctionHouse;
import mx.jehualli.ebay.util.Money;

import java.util.Random;

/**
 * A Tuesday evening on eBay in late 1999: Beanie Babies, Furbys, a Pentium
 * III, and somebody selling a Y2K survival kit.
 */
public class SeedData {

    /** id | name | parent */
    private static final String[] CATEGORIES = {
        "1|Collectibles|0",
        "11|Bean Bag Plush|1", "12|Trading Cards|1", "13|Advertising & Memorabilia|1",
        "2|Computers|0",
        "21|Desktop PCs|2", "22|Laptops & PDAs|2", "23|Software|2",
        "3|Photo & Electronics|0",
        "31|Cell Phones|3", "32|Portable Audio|3", "33|Video Games|3",
        "4|Toys & Hobbies|0",
        "41|Action Figures|4", "42|Electronic Pets|4",
        "5|Music|0",
        "51|CDs|5", "52|Records|5",
        "6|Books & Movies|0",
        "61|Books|6", "62|DVD & VHS|6"
    };

    /** nick | location | feedback score | negatives */
    private static final String[] MEMBERS = {
        "beaniequeen99|Columbus, OH|847|3",
        "pokemon_dad|Sacramento, CA|312|1",
        "techguy_sf|San Francisco, CA|1204|7",
        "cheapdeals4u|Newark, NJ|88|12",
        "Auction_King_2000|Las Vegas, NV|5316|22",
        "sunshine_collectibles|Tampa, FL|463|0",
        "bargainbin_bob|Akron, OH|129|4",
        "vintagevinylvicky|Portland, OR|691|2",
        "nerd_cave|Austin, TX|274|1",
        "MomOf3_Ohio|Dayton, OH|41|0",
        "sniper_steve|Boston, MA|1877|9",
        "dollar_bin_dan|Chicago, IL|56|6",
        "y2k_prepper|Boise, ID|17|0",
        "cardshark_77|Brooklyn, NY|933|5",
        "gadget_geek99|Seattle, WA|388|2",
        "grandma_ruth|Mesa, AZ|9|0",
        "midwest_mike|Des Moines, IA|205|1",
        "collect_o_mania|Orlando, FL|1542|11"
    };

    /**
     * title | category | seller | condition | start$ | reserve$ | ends in minutes
     */
    private static final String[] LISTINGS = {
        "RARE!! Princess Diana Beanie Baby MINT w/ TAG ERRORS L@@K|11|beaniequeen99|Used|9.99|250|2",
        "Peanut the Royal Blue Elephant Beanie - RETIRED 1995|11|beaniequeen99|Used|49.99|0|95",
        "Beanie Babies lot of 40 MINT w/tags NO RESERVE!!|11|MomOf3_Ohio|Used|25.00|0|1440",
        "McDonalds Teenie Beanie Babies COMPLETE SET 1998|11|sunshine_collectibles|New|12.50|0|380",
        "Beanie Baby Acrylic Display Case - holds 12|11|bargainbin_bob|New|8.99|0|2880",
        "1st Edition Charizard Pokemon Holo #4/102 NEAR MINT|12|pokemon_dad|Used|99.00|500|6",
        "Pokemon Jungle Set COMPLETE 64 cards - must see!|12|pokemon_dad|Used|39.99|0|770",
        "Mark McGwire 70th Home Run Commemorative Card|12|cardshark_77|New|14.99|0|1150",
        "1989 Upper Deck Ken Griffey Jr ROOKIE #1 PSA READY|12|cardshark_77|Used|75.00|150|2870",
        "Y2K SURVIVAL KIT - BE PREPARED!! ONLY 60 DAYS LEFT|13|y2k_prepper|New|39.95|0|430",
        "STAR WARS Episode I Movie Poster Style B ROLLED|13|collect_o_mania|New|19.99|0|1700",
        "Vintage Coca-Cola Metal Sign - REPRODUCTION|13|grandma_ruth|New|4.99|0|2400",
        "Apple iMac G3 Bondi Blue 233MHz 32MB - WORKS GREAT|21|techguy_sf|Used|175.00|300|18",
        "Gateway 2000 Pentium III 500MHz 128MB 10GB CD-RW|21|techguy_sf|Used|449.00|0|610",
        "Iomega Zip Drive 100MB Parallel + 5 disks|21|nerd_cave|Used|29.99|0|1290",
        "3dfx Voodoo3 3000 AGP 16MB Video Card|21|nerd_cave|Used|59.00|0|2100",
        "Palm Pilot V PDA w/ cradle + stylus + case|22|gadget_geek99|Used|129.00|0|145",
        "Compaq Presario 1245 Laptop 466MHz 32MB WIN98|22|cheapdeals4u|Used|299.99|450|1980",
        "Windows 98 Second Edition FULL VERSION w/ COA|23|cheapdeals4u|New|65.00|0|520",
        "Microsoft Office 97 Professional CD + Key|23|bargainbin_bob|Used|24.99|0|1610",
        "Nokia 5110 UNLOCKED + 3 Xpress-on covers|31|gadget_geek99|Used|45.00|0|9",
        "Motorola StarTAC 7860 Flip Phone MINT|31|midwest_mike|Used|89.99|120|880",
        "Sony Discman ESP2 D-E301 Anti-Skip - tested|32|midwest_mike|Used|22.00|0|260",
        "Panasonic ShockWave Boombox RX-DT505 LOUD!!|32|dollar_bin_dan|Used|35.00|0|1450",
        "Sony Walkman WM-FX290 AM/FM Cassette|32|grandma_ruth|Used|9.99|0|2650",
        "SEGA Dreamcast Console + Sonic Adventure NTSC|33|nerd_cave|Used|119.00|0|4",
        "Nintendo 64 w/ GoldenEye 007 and Mario Kart 64|33|midwest_mike|Used|85.00|0|330",
        "Sony PlayStation + 12 games + 2 controllers LOT|33|dollar_bin_dan|Used|99.99|175|1120",
        "Game Boy Color ATOMIC PURPLE + Pokemon Red|33|pokemon_dad|Used|55.00|0|2300",
        "Star Wars Episode I Darth Maul 12 inch figure MIB|41|collect_o_mania|New|29.99|0|55",
        "NIB Cabbage Patch Kids Snacktime Doll (RECALLED)|41|sunshine_collectibles|New|85.00|150|1830",
        "FURBY Electronic Pet NEW IN BOX Tiger Electronics|42|sunshine_collectibles|New|39.99|0|12",
        "Tamagotchi Original 1997 Bandai - WORKS PERFECT|42|grandma_ruth|Used|14.99|0|700",
        "Giga Pets Dinosaur - Tiger Electronics 1997|42|bargainbin_bob|Used|6.99|0|2550",
        "Backstreet Boys Millennium CD SEALED|51|dollar_bin_dan|New|7.99|0|490",
        "Now Thats What I Call Music Vol 1 CD 1998|51|cheapdeals4u|New|5.99|0|1350",
        "Nirvana Nevermind ORIGINAL VINYL LP 1991 DGC|52|vintagevinylvicky|Used|45.00|80|205",
        "Pink Floyd Dark Side of the Moon LP - 1st press|52|vintagevinylvicky|Used|89.00|0|1690",
        "Harry Potter and the Sorcerers Stone 1st Ed HC|61|collect_o_mania|Used|150.00|400|38",
        "Chicken Soup for the Soul Paperback LOT of 6|61|MomOf3_Ohio|Used|9.99|0|2000",
        "The Matrix DVD Warner 1999 NEW SEALED|62|techguy_sf|New|17.99|0|165",
        "Titanic VHS 2-Tape Collectors Set|62|grandma_ruth|Used|6.50|0|2750"
    };

    private static final String[] DESCRIPTIONS = {
        "Up for auction is this fine item from my personal collection. Smoke free "
        + "home! Please see my other auctions, I combine shipping.",
        "This has been in storage for years and is in excellent shape. Buyer pays "
        + "$4.50 shipping. Money order or personal check only, check must clear.",
        "L@@K! You will not find another one like this. Ships USPS Priority Mail "
        + "within 48 hours of payment. I ship worldwide!",
        "Great condition, see photo (coming soon, my scanner is acting up). Any "
        + "questions please e-mail me before bidding. Thanks and happy bidding!!",
        "From a smoke free pet free home. Item is exactly as pictured. Winning "
        + "bidder to contact me within 3 days of auction close."
    };

    private static final String[] PRAISE = {
        "A+++++ WOULD BUY AGAIN!!! GREAT EBAYER",
        "Fast payment, smooth transaction, THANKS!",
        "Item exactly as described. Highly recommended!!",
        "Super fast shipping, A+ seller, will deal with again",
        "GREAT COMMUNICATION. 5 STARS. AAA+++",
        "Perfect eBayer, a credit to the community!"
    };

    private static final String[] NEUTRAL = {
        "Item ok but took 3 weeks to arrive",
        "Package was a bit beat up, item fine",
        "Slow to respond to e-mail but transaction completed"
    };

    private static final String[] COMPLAINT = {
        "NEVER SENT ITEM. AVOID THIS BIDDER!!",
        "Item not as described, would not refund. BEWARE",
        "Bid then backed out. Waste of my time."
    };

    public static void load(AuctionHouse house, AuctionCloser closer) {
        Random rnd = new Random(1999L);   // fixed seed: same marketplace every boot
        long now = System.currentTimeMillis();

        for (int i = 0; i < CATEGORIES.length; i++) {
            String[] p = CATEGORIES[i].split("\\|");
            house.addCategory(new Category(Integer.parseInt(p[0]), p[1],
                                           Integer.parseInt(p[2])));
        }

        for (int i = 0; i < MEMBERS.length; i++) {
            String[] p = MEMBERS[i].split("\\|");
            User u = new User(p[0], "ebay", p[1],
                              now - (long) (200 + rnd.nextInt(900)) * 86400000L);
            u.seedFeedback(Integer.parseInt(p[2]), Integer.parseInt(p[3]));
            house.addUser(u);
            closer.addBot(p[0]);
        }

        for (int i = 0; i < LISTINGS.length; i++) {
            String[] p = LISTINGS[i].split("\\|");
            int minutesLeft = Integer.parseInt(p[6]);
            long ends = now + minutesLeft * 60000L;
            long started = ends - (long) (3 + rnd.nextInt(7)) * 86400000L;

            Item it = new Item(house.nextItemId(), p[0], DESCRIPTIONS[rnd.nextInt(DESCRIPTIONS.length)],
                               Integer.parseInt(p[1]), p[2], p[3],
                               house.getUser(p[2]).getLocation(),
                               Money.parse(p[4]), Money.parse(p[5]), started, ends);
            house.addItem(it);

            // Give most listings a bidding history so the site looks alive.
            int bids = rnd.nextInt(6);
            if (minutesLeft < 60) bids += 2 + rnd.nextInt(5);
            for (int b = 0; b < bids; b++) {
                String bidder = MEMBERS[rnd.nextInt(MEMBERS.length)].split("\\|")[0];
                if (bidder.equals(p[2])) continue;
                long minimum = it.getMinimumBidCents();
                long max = minimum + Money.increment(minimum) * rnd.nextInt(5);
                house.placeBid(bidder, it.getId(), String.valueOf(max / 100.0));
            }
        }

        // Feedback, mostly glowing, occasionally not.
        for (int i = 0; i < MEMBERS.length; i++) {
            String to = MEMBERS[i].split("\\|")[0];
            int n = 3 + rnd.nextInt(4);
            for (int k = 0; k < n; k++) {
                String from = MEMBERS[rnd.nextInt(MEMBERS.length)].split("\\|")[0];
                if (from.equals(to)) continue;
                int roll = rnd.nextInt(12);
                if (roll == 0) {
                    house.leaveFeedback(from, to, 0L, -1,
                                        COMPLAINT[rnd.nextInt(COMPLAINT.length)]);
                } else if (roll == 1) {
                    house.leaveFeedback(from, to, 0L, 0,
                                        NEUTRAL[rnd.nextInt(NEUTRAL.length)]);
                } else {
                    house.leaveFeedback(from, to, 0L, 1,
                                        PRAISE[rnd.nextInt(PRAISE.length)]);
                }
            }
        }

        // The demo account.
        house.register("guest", "ebay", "Anytown, USA");
    }
}
