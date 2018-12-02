package app.model;

import java.util.Hashtable;

public class PrizeCache {

    public final static String SMALL = "small";
    public final static String MEDIUM = "medium";
    public final static String BIG = "big";

    private static Hashtable<String, Prize> map = new Hashtable<>();

    public static Prize getPrize(String prizeID) {
        Prize prize = map.get(prizeID);
        return (Prize) prize.clone();
    }

    public static void initCache() {
        map.put(SMALL, new SmallPrize());
        map.put(MEDIUM, new MediumPrize());
        map.put(BIG, new BigPrize());
    }
}
