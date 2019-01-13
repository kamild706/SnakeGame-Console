package app.model.snake;

import app.model.Coordinates;
import app.model.Direction;
import app.model.obstacle.IObstacle;
import app.model.prize.Prize;
import com.googlecode.lanterna.TextColor;

import java.util.LinkedList;

public class Snake {

    private final String shape = "\u2b1b";
    private final TextColor color = new TextColor.RGB(178, 87, 0);

    private LinkedList<Coordinates> body;
    private Direction headedTo;
    private int lives = 5;
    private int score = 0;
    private SnakeState snakeState;


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public SnakeState getSnakeState() {
        return snakeState;
    }

    public void setSnakeState(SnakeState snakeState) {
        this.snakeState = snakeState;
    }

    public LinkedList<Coordinates> getBody() {
        return body;
    }

    public Coordinates getHead() {
        return body.getFirst();
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public String getShape() {
        return shape;
    }

    public TextColor getColor() {
        return color;
    }

    public void incrementLives() {
        lives++;
    }

    public Snake() {
        headedTo = Direction.RIGHT;
        body = new LinkedList<>();
        body.add(new Coordinates(13, 10));
        body.add(new Coordinates(12, 10));
        body.add(new Coordinates(11, 10));

        snakeState = new HealthySnake();
    }

    public void extendSnake() {
        body.add(1, body.getFirst().clone());
    }

    public void changeDirection(Direction direction) {
        if (!direction.isContradictoryTo(headedTo)) {
            headedTo = direction;
        }
    }

    public boolean isBodyCollision() {
        Coordinates head = body.getFirst();

        for (int i = 1; i < body.size(); i++) {
            if (body.get(i).equals(head)) {
                return true;
            }
        }
        return false;
    }

    public Direction getDirection() {
        return headedTo;
    }

    public void consumePrize(Prize prize) {
        snakeState.consumePrize(this, prize);
    }

    public void move() {
        snakeState.move(this);
    }

    public void handleCollision(IObstacle obstacle) {
        snakeState.handleCollision(this, obstacle);
    }
}
