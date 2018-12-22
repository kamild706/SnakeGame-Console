package app.model.prize;

import com.googlecode.lanterna.TextColor;

public class MediumPrize extends Prize {

    private int points;

    MediumPrize() {
        points = 3;
        setExtraLife(false);
        setShape("$");
        setColor(new TextColor.RGB(255, 255, 255));
    }

    @Override
    public int getPoints() {
        return points;
    }
}
