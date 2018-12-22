package app.model.prize;

import com.googlecode.lanterna.TextColor;

public class BigPrize extends Prize {

    private int points;

    BigPrize() {
        points = 5;
        setExtraLife(false);
        setShape("#");
        setColor(new TextColor.RGB(255, 255, 255));
    }

    @Override
    public int getPoints() {
        return points;
    }
}
