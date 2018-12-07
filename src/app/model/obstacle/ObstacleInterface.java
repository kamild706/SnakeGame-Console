package app.model.obstacle;

import app.model.Coordinates;

public interface ObstacleInterface {

    void move(int distance);
    Coordinates getCoordinates();
}
