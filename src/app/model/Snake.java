package app.model;

import java.util.LinkedList;

public class Snake {

    private LinkedList<Coordinates> body;
    private Direction headedTo;
    private int lives = 2;

    public LinkedList<Coordinates> getBody() {
        return body;
    }

    public Coordinates getHead() {
        return body.getFirst();
    }

    public int getLives() {
        return lives;
    }

    public void incrementLives() {
        lives++;
    }

    public void decrementLives() {
        lives--;
    }

    public Coordinates getTail() {
        return body.getLast();
    }

    public Snake() {
        headedTo = Direction.RIGHT;
        body = new LinkedList<>();
        body.add(new Coordinates(13, 10));
        body.add(new Coordinates(12, 10));
        body.add(new Coordinates(11, 10));
    }

    public void extendSnake() {
        body.add(1, body.getFirst().clone());
    }

    public boolean changeDirection(Direction direction) {
        if (!direction.isContradictoryTo(headedTo)) {
            headedTo = direction;
            return true;
        }
        return false;
    }

    public void move() {
        body.removeLast();
        Coordinates head = body.getFirst().clone();
        head.move(headedTo);


        body.addFirst(head);
    }
}
