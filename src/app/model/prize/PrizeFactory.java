package app.model.prize;

public class PrizeFactory {

    public static Prize getSmallPrize() {
        Prize prize = new SmallPrize();
        return prize;
    }

    public static Prize getMediumPrize() {
        Prize prize = new MediumPrize();
        prize.setExtraLife(Math.random() > 0.95);
        return prize;
    }

    public static Prize getBigPrize() {
        Prize prize = new BigPrize();
        prize.setExtraLife(Math.random() > 0.65);
        return prize;
    }
}
