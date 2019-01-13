package app.model.snake;

import app.model.obstacle.IObstacle;
import app.model.prize.Prize;
import com.sun.istack.internal.Nullable;

public interface SnakeState {
    void move(Snake snake);
    void consumePrize(Snake snake, Prize prize);
    void handleCollision(Snake snake, @Nullable IObstacle obstacle);
}
