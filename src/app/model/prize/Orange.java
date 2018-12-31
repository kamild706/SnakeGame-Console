package app.model.prize;

import com.googlecode.lanterna.TextColor;

class Orange extends Prize {

    private int points;

    Orange() {
        points = 2;
        setExtraLife(false);
        setShape("@");
        setColor(new TextColor.RGB(255, 255, 255));
    }

    @Override
    public int getPoints() {
        return points;
    }
}
