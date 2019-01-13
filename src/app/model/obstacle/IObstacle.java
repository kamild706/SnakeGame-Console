package app.model.obstacle;

import app.model.Coordinates;

public interface IObstacle {

    void move(Coordinates distance);
    Coordinates getCoordinates();
    double getDamagingPower();
}
