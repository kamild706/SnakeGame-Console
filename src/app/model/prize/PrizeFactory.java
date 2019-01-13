package app.model.prize;

import com.googlecode.lanterna.TextColor;

public class PrizeFactory {

    public static Prize createOrange() {
        Prize prize = new Orange();
        prize.setExtraLife(false);
        prize.setColor(new TextColor.RGB(0, 173, 0));
        return prize;
    }

    public static Prize createBanana() {
        Prize prize = new Banana();
        prize.setExtraLife(Math.random() > 0.75);
        prize.setColor(new TextColor.RGB(255, 255, 255));
        return prize;
    }

    public static Prize createApple() {
        Prize prize = new Apple();
        prize.setExtraLife(Math.random() > 0.65);
        prize.setColor(new TextColor.RGB(204, 20, 64));
        return prize;
    }
}
