package app.model.prize;

public class PrizeFactory {

    public static Prize createOrange() {
        Prize prize = new Orange();
        return prize;
    }

    public static Prize createBanana() {
        Prize prize = new Banana();
        prize.setExtraLife(Math.random() > 0.75);
        return prize;
    }

    public static Prize createApple() {
        Prize prize = new Apple();
        prize.setExtraLife(Math.random() > 0.65);
        return prize;
    }
}
