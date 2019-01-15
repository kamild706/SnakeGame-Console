package app.model;


import app.model.obstacle.DoubleDistance;
import app.model.obstacle.Obstacle;
import app.model.obstacle.IObstacle;
import app.model.obstacle.Poison;
import app.model.prize.Prize;
import app.model.prize.PrizeFactory;
import app.model.snake.Snake;
import app.utils.MyUtils;
import app.view.GameView;

import java.util.*;

import static app.model.GameConfig.*;

public class Game {

    private Snake snake;
    private Map<Coordinates, Prize> prizes;
    private IObstacle obstacle;

    private boolean directionChangeOccurred = false;
    private boolean isGameOn = false;
    private boolean initNewState = true;
    private Timer gameTimer;
    private GameConfig config;
    private GameView view;
    private int counter = 0;

    public Game(GameView view) {
        this.view = view;
        this.config = GameConfig.getInstance();
    }

    private void createPrize() {
        double random = Math.random();
        Prize prize;
        if (random < 0.5) {
            prize = PrizeFactory.createOrange();
        } else if (random < 0.8) {
            prize = PrizeFactory.createBanana();
        } else {
            prize = PrizeFactory.createApple();
        }
        prizes.put(prize.getCoordinates(), prize);
    }

    private void createObstacle() {
        obstacle = new Obstacle(getRandomCoordinates());
        double random = Math.random();
        if (random < 0.25) {
            obstacle = new DoubleDistance(obstacle);
        } else if (random < 0.5) {
            obstacle = new Poison(new DoubleDistance(obstacle));
        } else if (random < 0.75) {
            obstacle = new Poison(obstacle);
        }
    }

    private Coordinates getRandomCoordinates() {
        return new Coordinates((int) (Math.random() * RIGHT_BOUNDARY + LEFT_BOUNDARY),
                (int) (Math.random() * (BOTTOM_BOUNDARY - TOP_BOUNDARY - 2)) + TOP_BOUNDARY + 1);
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
        Coordinates head = snake.getHead();
        return prizes.containsKey(head);
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
        isGameOn = true;
        if (initNewState) {
            initNewState = false;
            snake = new Snake();
            view.onSnakeCreated(snake);

            prizes = new HashMap<>();
            createPrize();
            createPrize();
            createPrize();
            view.onPrizesInitialized(prizes);

            createObstacle();
        }
        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                directionChangeOccurred = false;
                snake.move();

                view.onSnakeMoved();

                wrapSnake();
                if (isBoundaryCollision()) {
                    stopGame();
                }

                if (snake.isBodyCollision() || obstacle != null && isObstacleCollision()) {
                    if (snake.getLives() > 1) {
                        if (snake.isBodyCollision())
                            snake.handleCollision(null);
                        else
                            snake.handleCollision(obstacle);
                        view.onSnakeLivesChanged();
                        view.onSnakeStateChangePossibility();
                    } else {
                        stopGame();
                    }
                }

                if (isPrizeAcquired()) {
                    Prize prize = prizes.get(snake.getHead());
                    prizes.remove(prize.getCoordinates());
                    snake.consumePrize(prize);
                    snake.extendSnake();

                    view.onSnakeLivesChanged();
                    view.onSnakeScoreChanged();
                    view.onSnakeStateChangePossibility();
                    createPrize();
                }

                handleObstacle();
            }
        }, 0, config.getGameLevel());
    }

    private void handleObstacle() {
        if (obstacle != null && isObstacleOutOfScreen()) {
            obstacle = null;
        }

        if (obstacle == null && Math.random() < 0.3) {
            createObstacle();
        }

        if (obstacle != null) {
            Coordinates distance = new Coordinates(
                    MyUtils.getRandomNumberInRange(0, 2),
                    MyUtils.getRandomNumberInRange(0, 2));

            counter++;
            if (counter == 4) {
                counter = 0;
                obstacle.move(distance);
            }
            view.onObstacleMoved(obstacle);
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
        view.onGameOver();
    }

    public void pauseOrResume() {
        if (isGameOn) {
            gameTimer.cancel();
            isGameOn = false;
        } else {
            startGame();
        }
    }
}
