package app.model;

public class Prize {

    private Coordinates coordinates;
    private int points;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getPoints() {
        return points;
    }

    public Prize(int x, int y) {
        this(x, y, 1);
    }

    public Prize(int x, int y, int points) {
        this.coordinates = new Coordinates(x, y);
        this.points = points;
    }
}
