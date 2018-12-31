package app.model.snake;

import app.model.Coordinates;
import app.model.prize.Prize;

import java.util.LinkedList;

public class HealthySnake implements SnakeState {
    @Override
    public void move(Snake snake) {
        LinkedList<Coordinates> body = snake.getBody();
        body.removeLast();
        Coordinates head = body.getFirst().clone();
        head.move(snake.getDirection());
        body.addFirst(head);
    }

    @Override
    public void consumePrize(Snake snake, Prize prize) {
        snake.setScore(snake.getScore() + prize.getPoints());
        if (prize.isExtraLife()) {
            snake.incrementLives();
        }
    }

    @Override
    public void handleCollision(Snake snake) {
        if (Math.random() < 0.95) {
            snake.setLives(snake.getLives() - 1);
            if (snake.getLives() < 5) {
                snake.setSnakeState(new WeakenedSnake());
            }
        }
    }
}
