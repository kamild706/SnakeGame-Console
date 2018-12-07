package app.model.snake;

import app.model.Coordinates;
import app.model.Direction;

import java.util.LinkedList;

public interface MoveStrategy {

    void move(LinkedList<Coordinates> body, Direction direction);

    boolean isCollision(Coordinates head, Coordinates coordinates, Direction direction);
}
