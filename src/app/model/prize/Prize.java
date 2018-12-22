package app.model.prize;

import app.model.Coordinates;
import com.googlecode.lanterna.TextColor;

import static app.model.GameConfig.*;
import static app.model.GameConfig.TOP_BOUNDARY;

public abstract class Prize {

    private Coordinates coordinates;
    private boolean extraLife = false;
    private String shape;
    private TextColor color;

    Prize() {
        setCoordinates();
    }

    public TextColor getColor() {
        return color;
    }

    void setColor(TextColor color) {
        this.color = color;
    }

    public String getShape() {
        return shape;
    }

    void setShape(String shape) {
        this.shape = shape;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    private void setCoordinates() {
        coordinates = new Coordinates((int) (Math.random() * RIGHT_BOUNDARY + LEFT_BOUNDARY),
                (int) (Math.random() * (BOTTOM_BOUNDARY - TOP_BOUNDARY - 2)) + TOP_BOUNDARY + 1);
    }

    public boolean isExtraLife() {
        return extraLife;
    }

    void setExtraLife(boolean extraLife) {
        this.extraLife = extraLife;
    }

    public abstract int getPoints();
}
