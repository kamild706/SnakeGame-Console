package app.model.game;

import app.model.Coordinates;
import app.model.GameConfig;

import static app.model.GameConfig.*;

public class CollisionFreeGame extends Game {

    @Override
    protected boolean checkCollision() {
        wrapSnake();

        Coordinates head = snake.getHead();

        return head.getX() <= GameConfig.LEFT_BOUNDARY || head.getX() >= GameConfig.RIGHT_BOUNDARY ||
                head.getY() <= GameConfig.TOP_BOUNDARY || head.getY() >= GameConfig.BOTTOM_BOUNDARY;
    }

    private void wrapSnake() {
        Coordinates head = snake.getHead();

        if (head.getX() == LEFT_BOUNDARY) head.setX(RIGHT_BOUNDARY - 1);
        if (head.getX() == RIGHT_BOUNDARY) head.setX(LEFT_BOUNDARY + 1);
        if (head.getY() == TOP_BOUNDARY) head.setY(BOTTOM_BOUNDARY - 1);
        if (head.getY() == BOTTOM_BOUNDARY) head.setY(TOP_BOUNDARY + 1);
    }
}
