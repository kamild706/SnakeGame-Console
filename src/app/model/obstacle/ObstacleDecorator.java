package app.model.obstacle;

import app.model.Coordinates;

public abstract class ObstacleDecorator implements ObstacleInterface {

    private ObstacleInterface obstacle;

    public ObstacleDecorator(ObstacleInterface obstacle) {
        this.obstacle = obstacle;
    }

    @Override
    public void move(int distance) {
        obstacle.move(distance);
    }

    @Override
    public Coordinates getCoordinates() {
        return obstacle.getCoordinates();
    }
}
