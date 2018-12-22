package app.model.snake;

import app.model.Coordinates;
import app.model.Direction;

import java.util.LinkedList;

public class Snake {

    private LinkedList<Coordinates> body;
    private Direction headedTo;
    private int lives = 2;
    private MoveStrategy moveStrategy;

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

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

    public Snake() {
        headedTo = Direction.RIGHT;
        body = new LinkedList<>();
        body.add(new Coordinates(13, 10));
        body.add(new Coordinates(12, 10));
        body.add(new Coordinates(11, 10));

        moveStrategy = new RegularMove();
    }

    public void extendSnake() {
        body.add(1, body.getFirst().clone());
    }

    public void changeDirection(Direction direction) {
        if (!direction.isContradictoryTo(headedTo)) {
            headedTo = direction;
        }
    }

    public void move() {
        /*try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        moveStrategy.move(body, headedTo);
    }
}
