package app.model;


import app.model.*;
import app.model.obstacle.DoubleMoveDecorator;
import app.model.obstacle.Obstacle;
import app.model.obstacle.ObstacleInterface;
import app.model.prize.Prize;
import app.model.prize.PrizeFactory;
import app.model.snake.FasterMove;
import app.model.snake.RegularMove;
import app.model.snake.Snake;
import app.presenter.GamePresenter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static app.model.GameConfig.*;

public class Game {

    protected Snake snake;
    private Prize prize;
    private ObstacleInterface obstacle;
    private int score = 0;

    private boolean directionChangeOccurred = false;
    private boolean isGameOn = false;
    private Timer gameTimer;
    private GameConfig config;
    private GamePresenter presenter;

    public Game() {
        config = GameConfig.getInstance();
    }

    public void setPresenter(GamePresenter presenter) {
        this.presenter = presenter;
    }

    private void createPrize() {
        double random = Math.random();
        if (random < 0.5) {
            prize = PrizeFactory.getSmallPrize();
        } else if (random < 0.8) {
            prize = PrizeFactory.getMediumPrize();
        } else {
            prize = PrizeFactory.getBigPrize();
        }

        prize.setCoordinates(getRandomCoordinates());

        presenter.notifyPrizeCreated(prize.getCoordinates());
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

        return head.getX() <= GameConfig.LEFT_BOUNDARY || head.getX() >= GameConfig.RIGHT_BOUNDARY ||
                head.getY() <= GameConfig.TOP_BOUNDARY || head.getY() >= GameConfig.BOTTOM_BOUNDARY;
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

    private void wrapSnake() {
        if (!config.isSnakeWrapsOnBoundaries()) return;

        Coordinates head = snake.getHead();

        if (head.getX() == LEFT_BOUNDARY) head.setX(RIGHT_BOUNDARY - 1);
        if (head.getX() == RIGHT_BOUNDARY) head.setX(LEFT_BOUNDARY + 1);
        if (head.getY() == TOP_BOUNDARY) head.setY(BOTTOM_BOUNDARY - 1);
        if (head.getY() == BOTTOM_BOUNDARY) head.setY(TOP_BOUNDARY + 1);
    }

    public void startGame() {
        if (!isGameOn) {
            isGameOn = true;
            snake = new Snake();
            createPrize();
            createObstacle();

            gameTimer = new Timer();
            gameTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    directionChangeOccurred = false;
                    snake.move();

                    presenter.notifySnakeMoved(snake.getBody());
                    wrapSnake();
                    if (isBoundaryCollision()) {
                        stopGame();
                        System.out.println(1);
                        presenter.notifyCollisionOccurred();
                    }

                    if (isBodyCollision() || obstacle != null && isObstacleCollision()) {
                        if (snake.getLives() > 1) {
                            snake.decrementLives();
                            presenter.notifyLivesChangeOccurred(snake.getLives());
                        } else {
                            stopGame();
                            System.out.println(2);
                            presenter.notifyCollisionOccurred();
                        }
                    }

                    if (isPrizeAcquired()) {
                        score += prize.getPoints();
//                        changeSnakeSpeed();
                        if (prize.isExtraLife()) {
                            snake.incrementLives();
                            presenter.notifyLivesChangeOccurred(snake.getLives());
                        }

                        presenter.notifyPrizeAcquired(score);
                        snake.extendSnake();
                        createPrize();
                    }

                    handleObstacle();
                }
            }, 0, config.getGameLevel());
        }
    }

    private void handleObstacle() {
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
            presenter.notifyObstacleMoved(obstacle.getCoordinates());
        }
    }

    private boolean isObstacleOutOfScreen() {
        Coordinates head = obstacle.getCoordinates();
        return head.getX() <= LEFT_BOUNDARY || head.getX() >= RIGHT_BOUNDARY ||
                head.getY() <= TOP_BOUNDARY || head.getY() >= BOTTOM_BOUNDARY;
    }

    public void stopGame() {
        gameTimer.cancel();
        isGameOn = false;
    }
}
