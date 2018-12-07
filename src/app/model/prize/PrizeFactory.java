package app.model.prize;

public class PrizeFactory {

    public static Prize getSmallPrize() {
        Prize prize = new SmallPrize();
        return prize;
    }

    public static Prize getMediumPrize() {
        Prize prize = new MediumPrize();
        prize.setExtraSpeed(Math.random() > 0.7);
        return prize;
    }

    public static Prize getBigPrize() {
        Prize prize = new BigPrize();
        prize.setExtraSpeed(Math.random() > 0.75);
        prize.setExtraLife(Math.random() > 0.5);
        return prize;
    }
}
