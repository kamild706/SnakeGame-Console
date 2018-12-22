package app.model.prize;

import com.googlecode.lanterna.TextColor;

class SmallPrize extends Prize {

    private int points;

    SmallPrize() {
        points = 1;
        setExtraLife(false);
        setShape("@");
        setColor(new TextColor.RGB(255, 255, 255));
    }

    @Override
    public int getPoints() {
        return points;
    }
}
