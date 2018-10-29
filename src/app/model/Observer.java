package app.model;

import java.util.LinkedList;

public interface Observer {

    void notifySnakeMoved(LinkedList<Coordinates> coordinates);
    void notifyPrizeCreated(Coordinates prizeCoordinates);
    void notifyCollisionOccurred();
    void notifyPrizeAcquired(int userScore);
}
