package app.model.snake;

import app.model.Coordinates;
import app.model.prize.Prize;

import java.util.LinkedList;

public class WeakenedSnake implements SnakeState {
    @Override
    public void move(Snake snake) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LinkedList<Coordinates> body = snake.getBody();
        body.removeLast();
        Coordinates head = body.getFirst().clone();
        head.move(snake.getDirection());
        body.addFirst(head);
    }

    @Override
    public void consumePrize(Snake snake, Prize prize) {
        int points = prize.getPoints();
        snake.setScore(snake.getScore() + (int) (points * 0.5));
        if (prize.isExtraLife()) {
            snake.incrementLives();
        }

        if (snake.getLives() == 5) {
            snake.setSnakeState(new HealthySnake());
        }
    }

    @Override
    public void handleCollision(Snake snake) {
        snake.setLives(snake.getLives() - 1);
    }
}
