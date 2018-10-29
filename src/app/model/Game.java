package app.model;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    public static final int TOP_BOUNDARY = 1;
    public static final int BOTTOM_BOUNDARY = 28;
    public static final int RIGHT_BOUNDARY = 90;
    public static final int LEFT_BOUNDARY = -1;

    public static final long LEVEL_EASY = 800;
    public static final long LEVEL_MEDIUM = 100;
    public static final long LEVEL_HARD = 40;

    private ArrayList<Observer> observers = new ArrayList<>();

    private Snake snake;
    private Prize prize;
    private int score = 0;
    private long gameInterval = LEVEL_EASY;

    private boolean wrapSnakeOnBoundaries = false;
    private boolean isGameOn = false;
    private Timer gameTimer;

    public void setWrapSnakeOnBoundaries(boolean wrapSnakeOnBoundaries) {
        this.wrapSnakeOnBoundaries = wrapSnakeOnBoundaries;
    }

    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void createPrize() {
        int x = (int) (Math.random() * RIGHT_BOUNDARY + LEFT_BOUNDARY);
        int y = (int) (Math.random() * (BOTTOM_BOUNDARY - TOP_BOUNDARY - 2)) + TOP_BOUNDARY + 1;
        prize = new Prize(x, y);

        observers.forEach(p -> p.notifyPrizeCreated(prize.getCoordinates()));
    }

    private void wrapSnake() {
        Coordinates head = snake.getHead();

        if (head.getX() == LEFT_BOUNDARY) head.setX(RIGHT_BOUNDARY - 1);
        if (head.getX() == RIGHT_BOUNDARY) head.setX(LEFT_BOUNDARY + 1);
        if (head.getY() == TOP_BOUNDARY) head.setY(BOTTOM_BOUNDARY - 1);
        if (head.getY() == BOTTOM_BOUNDARY) head.setY(TOP_BOUNDARY + 1);
    }

    private boolean isCollision() {
        Coordinates head = snake.getHead();

        if (head.getX() == LEFT_BOUNDARY || head.getX() == RIGHT_BOUNDARY ||
                head.getY() == TOP_BOUNDARY || head.getY() == BOTTOM_BOUNDARY) {
            return true;
        }

        for (int i = 1; i < snake.getBody().size(); i++) {
            if (snake.getBody().get(i).equals(head)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPrizeAcquired() {
        return snake.getHead().equals(prize.getCoordinates());
    }


    public void changeSnakeDirectionTo(Direction direction) {
        snake.changeDirection(direction);
    }

    public void startGame() {
        if (!isGameOn) {
            snake = new Snake();
            createPrize();
            isGameOn = true;
            gameTimer = new Timer();
            gameTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    snake.move();
                    if (wrapSnakeOnBoundaries) {
                        wrapSnake();
                    }

                    observers.forEach(p -> p.notifySnakeMoved(snake.getBody()));

                    if (isCollision()) {
                        isGameOn = false;
                        stopGame();
                        observers.forEach(Observer::notifyCollisionOccurred);
                    }
                    if (isPrizeAcquired()) {
                        score += prize.getPoints();
                        observers.forEach(p -> p.notifyPrizeAcquired(score));
                        snake.extendSnake();
                        createPrize();
                    }
                }
            }, 0, gameInterval);
        }
    }


    public void stopGame() {
        gameTimer.cancel();
        isGameOn = false;
    }

    public void setSpeed(long speed) {
        gameInterval = speed;
    }


}
