package app.model.obstacle;

import app.model.Coordinates;

public class Obstacle implements IObstacle {

    private Coordinates coordinates;
    private double damagingPower = 0.15;

    public Obstacle(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public void move(Coordinates distance) {
        coordinates.setX(coordinates.getX() + distance.getX());
        coordinates.setY(coordinates.getY() + distance.getY());
    }

    @Override
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public double getDamagingPower() {
        return damagingPower;
    }
}
