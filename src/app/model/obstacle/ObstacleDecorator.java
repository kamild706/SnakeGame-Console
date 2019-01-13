package app.model.obstacle;

import app.model.Coordinates;

public abstract class ObstacleDecorator implements IObstacle {

    private IObstacle obstacle;

    public ObstacleDecorator(IObstacle obstacle) {
        this.obstacle = obstacle;
    }

    @Override
    public void move(Coordinates distance) {
        obstacle.move(distance);
    }

    @Override
    public Coordinates getCoordinates() {
        return obstacle.getCoordinates();
    }

    @Override
    public double getDamagingPower() {
        return obstacle.getDamagingPower();
    }
}
