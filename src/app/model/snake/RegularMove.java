package app.model.snake;

import app.model.Coordinates;
import app.model.Direction;

import java.util.LinkedList;

public class RegularMove implements MoveStrategy {
    @Override
    public void move(LinkedList<Coordinates> body, Direction direction) {
        body.removeLast();
        Coordinates head = body.getFirst().clone();
        head.move(direction);
        body.addFirst(head);
    }

    @Override
    public boolean isCollision(Coordinates head, Coordinates coordinates, Direction direction) {
        return head.equals(coordinates);
    }
}
