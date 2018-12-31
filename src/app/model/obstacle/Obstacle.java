package app.model.obstacle;

import app.model.Coordinates;

public class Obstacle implements IObstacle {

    private Coordinates coordinates;

    public Obstacle(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public void move(int distance) {
        coordinates.setX(coordinates.getX() + distance);
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }
}
