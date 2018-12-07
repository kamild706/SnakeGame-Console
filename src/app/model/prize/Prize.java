package app.model.prize;

import app.model.Coordinates;

public abstract class Prize {

    private Coordinates coordinates;
    private int points;
    private boolean extraLife = false;
    private boolean extraSpeed = false;

    public boolean isExtraSpeed() {
        return extraSpeed;
    }

    public void setExtraSpeed(boolean extraSpeed) {
        this.extraSpeed = extraSpeed;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getPoints() {
        return points;
    }

    public Prize(int points) {
        this.points = points;
    }

    public boolean isExtraLife() {
        return extraLife;
    }

    public void setExtraLife(boolean extraLife) {
        this.extraLife = extraLife;
    }
}
