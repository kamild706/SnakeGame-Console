package app.model;

abstract class Prize implements Cloneable {

    private Coordinates coordinates;
    private int points;
    private boolean extraLife = false;

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

    abstract protected Object clone();
}
