package app.model.prize;

import com.googlecode.lanterna.TextColor;

public class Banana extends Prize {

    private int points;

    Banana() {
        points = 6;
        setExtraLife(false);
        setShape("$");
        setColor(new TextColor.RGB(255, 255, 255));
    }

    @Override
    public int getPoints() {
        return points;
    }
}
