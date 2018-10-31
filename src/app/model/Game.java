package app.model;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static app.model.GameConfig.*;

public class Game {



    private ArrayList<Observer> observers = new ArrayList<>();

    private Snake snake;
    private Prize prize;
    private int score = 0;

//    private boolean wrapSnakeOnBoundaries = false;
    private boolean isGameOn = false;
    private Timer gameTimer;

    /*public void setWrapSnakeOnBoundaries(boolean wrapSnakeOnBoundaries) {
        this.wrapSnakeOnBoundaries = wrapSnakeOnBoundaries;
    }*/

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
                    if (GameConfig.getInstance().isSnakeWrapsOnBoundaries()) {
                        wrapSnake();
                    }

                    observers.forEach(p -> p.notifySnakeMoved(snake.getBody()));

                    if (isCollision()) {
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
            }, 0, GameConfig.getInstance().getGameLevel());
        }
    }


    public void stopGame() {
        gameTimer.cancel();
        isGameOn = false;
    }


}
