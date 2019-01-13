package app.model.snake;

import app.model.Coordinates;
import app.model.obstacle.IObstacle;
import app.model.prize.Prize;

import java.util.LinkedList;

public class PoisonedSnake implements SnakeState {

    private int collectedPrizes = 0;

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
        collectedPrizes++;
        if (collectedPrizes == 3) {
            if (snake.getLives() >= 5) {
                snake.setSnakeState(new HealthySnake());
            } else {
                snake.setSnakeState(new WeakenedSnake());
            }
        }
    }

    @Override
    public void handleCollision(Snake snake, IObstacle obstacle) {
        snake.setLives(snake.getLives() - 1);
    }

    @Override
    public String toString() {
        return "otruty";
    }
}
