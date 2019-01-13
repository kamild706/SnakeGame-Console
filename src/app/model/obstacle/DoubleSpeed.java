package app.model.obstacle;

import app.model.Coordinates;

public class DoubleSpeed extends ObstacleDecorator {

    public DoubleSpeed(IObstacle obstacleInterface) {
        super(obstacleInterface);
    }

    @Override
    public void move(Coordinates distance) {
        distance.setX(distance.getX() * 2);
        distance.setY(distance.getY() * 2);
        super.move(distance);
    }
}
