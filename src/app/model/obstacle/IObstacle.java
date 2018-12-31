package app.model.obstacle;

import app.model.Coordinates;

public interface IObstacle {

    void move(int distance);
    Coordinates getCoordinates();
}
