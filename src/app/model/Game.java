package app.model;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static app.model.GameConfig.*;
import static app.model.PrizeCache.*;

public class Game {

    private ArrayList<Observer> observers = new ArrayList<>();

    private Snake snake;
    private Prize prize;
    private ObstacleInterface obstacle;
    private int score = 0;

    private boolean directionChangeOccurred = false;
    private boolean isGameOn = false;
    private Timer gameTimer;


    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public Game() {
        PrizeCache.initCache();
    }

    public void createPrize() {
        double random = Math.random();
        if (random < 0.5) {
            prize = (Prize) PrizeCache.getPrize(SMALL).clone();
        } else if (random < 0.8) {
            prize = (Prize) PrizeCache.getPrize(MEDIUM).clone();
        } else {
            prize = (Prize) PrizeCache.getPrize(BIG).clone();
        }

        prize.setCoordinates(getRandomCoordinates());

        observers.forEach(p -> p.notifyPrizeCreated(prize.getCoordinates()));
    }

    private void createObstacle() {
        obstacle = new Obstacle(getRandomCoordinates());
        double random = Math.random();
        if (random < 0.7) {
            obstacle = new DoubleMoveDecorator(obstacle);
        }
    }

    private Coordinates getRandomCoordinates() {
        return new Coordinates((int) (Math.random() * RIGHT_BOUNDARY + LEFT_BOUNDARY),
                (int) (Math.random() * (BOTTOM_BOUNDARY - TOP_BOUNDARY - 2)) + TOP_BOUNDARY + 1);
    }

    private void wrapSnake() {
        Coordinates head = snake.getHead();

        if (head.getX() == LEFT_BOUNDARY) head.setX(RIGHT_BOUNDARY - 1);
        if (head.getX() == RIGHT_BOUNDARY) head.setX(LEFT_BOUNDARY + 1);
        if (head.getY() == TOP_BOUNDARY) head.setY(BOTTOM_BOUNDARY - 1);
        if (head.getY() == BOTTOM_BOUNDARY) head.setY(TOP_BOUNDARY + 1);
    }

    private boolean isBodyCollision() {
        Coordinates head = snake.getHead();

        for (int i = 1; i < snake.getBody().size(); i++) {
            if (snake.getBody().get(i).equals(head)) {
                return true;
            }
        }
        return false;
    }

    private boolean isObstacleCollision() {
        Coordinates obstacleCoordinates = obstacle.getCoordinates();
        Coordinates head = snake.getHead();
        return head.equals(obstacleCoordinates);
    }

    private boolean isBoundaryCollision() {
        Coordinates head = snake.getHead();

        if (head.getX() == LEFT_BOUNDARY || head.getX() == RIGHT_BOUNDARY ||
                head.getY() == TOP_BOUNDARY || head.getY() == BOTTOM_BOUNDARY) {
            return true;
        }
        return false;
    }

    private boolean isPrizeAcquired() {
        return snake.getHead().equals(prize.getCoordinates());
    }

    public void changeSnakeDirectionTo(Direction direction) {
        if (!directionChangeOccurred) {
            snake.changeDirection(direction);
            directionChangeOccurred = true;
        }
    }

    public void startGame() {
        if (!isGameOn) {
            snake = new Snake();
            createPrize();
            createObstacle();
            isGameOn = true;
            gameTimer = new Timer();
            gameTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    directionChangeOccurred = false;
                    snake.move();
                    if (GameConfig.getInstance().isSnakeWrapsOnBoundaries()) {
                        wrapSnake();
                    }

                    observers.forEach(p -> p.notifySnakeMoved(snake.getBody()));

                    if (isBoundaryCollision()) {
                        stopGame();
                        observers.forEach(Observer::notifyCollisionOccurred);
                    }
                    if (isBodyCollision() || obstacle != null && isObstacleCollision()) {
                        if (snake.getLives() > 1) {
                            snake.decrementLives();
                            observers.forEach(p -> p.notifyLivesChangeOccurred(snake.getLives()));
                        } else {
                            stopGame();
                            observers.forEach(Observer::notifyCollisionOccurred);
                        }
                    }
                    if (isPrizeAcquired()) {
                        score += prize.getPoints();
                        if (prize.isExtraLife()) {
                            snake.incrementLives();
                            observers.forEach(p -> p.notifyLivesChangeOccurred(snake.getLives()));
                        }

                        observers.forEach(p -> p.notifyPrizeAcquired(score));
                        snake.extendSnake();
                        createPrize();
                    }
                    if (obstacle != null && isObstacleOutOfScreen()) {
                        obstacle = null;
                        if (Math.random() < 0.7) {
                            createObstacle();
                        }
                    }

                    if (obstacle == null && Math.random() < 0.7) {
                        createObstacle();
                    }

                    if (obstacle != null) {
                        obstacle.move(Math.random() < 0.7 ? 0 : 1);
                        observers.forEach(p -> p.notifyObstacleMoved(obstacle.getCoordinates()));
                    }
                    System.out.println(obstacle == null);
                }
            }, 0, GameConfig.getInstance().getGameLevel());
        }
    }

    private boolean isObstacleOutOfScreen() {
        Coordinates head = obstacle.getCoordinates();
        if (head.getX() <= LEFT_BOUNDARY || head.getX() >= RIGHT_BOUNDARY ||
                head.getY() <= TOP_BOUNDARY || head.getY() >= BOTTOM_BOUNDARY) {
            return true;
        }
        return false;
    }

    public void stopGame() {
        gameTimer.cancel();
        isGameOn = false;
    }
}
