package app.model.snake;

import app.model.prize.Prize;

public interface SnakeState {
    void move(Snake snake);
    void consumePrize(Snake snake, Prize prize);
    void handleCollision(Snake snake);
}
