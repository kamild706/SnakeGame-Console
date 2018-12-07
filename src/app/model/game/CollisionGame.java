package app.model.game;

import app.model.Coordinates;
import app.model.GameConfig;

public class CollisionGame extends Game {

    @Override
    protected boolean checkCollision() {
        Coordinates head = snake.getHead();

        return head.getX() <= GameConfig.LEFT_BOUNDARY || head.getX() >= GameConfig.RIGHT_BOUNDARY ||
                head.getY() <= GameConfig.TOP_BOUNDARY || head.getY() >= GameConfig.BOTTOM_BOUNDARY;
    }
}
