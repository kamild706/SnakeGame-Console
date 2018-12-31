package app.model.prize;

import com.googlecode.lanterna.TextColor;

public class Apple extends Prize {

    private int points;

    Apple() {
        points = 10;
        setExtraLife(false);
        setShape("#");
        setColor(new TextColor.RGB(255, 255, 255));
    }

    @Override
    public int getPoints() {
        return points;
    }
}
