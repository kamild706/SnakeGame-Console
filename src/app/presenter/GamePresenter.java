package app.presenter;

import app.GameContract;
import app.model.Coordinates;
import app.model.Direction;
import app.model.Prize;
import app.model.Snake;

import java.util.Timer;
import java.util.TimerTask;

public class GamePresenter implements GameContract.Presenter {

    public static final int TOP_BOUNDARY = 1;
    public static final int BOTTOM_BOUNDARY = 28;
    public static final int RIGHT_BOUNDARY = 90;
    public static final int LEFT_BOUNDARY = -1;

    private Snake snake;
    private Prize prize;
    private int score = 0;
    private boolean wrapSnakeOnBoundaries = false;
    private boolean isGameOn = false;
    private GameContract.View view;
    private Timer gameTimer;


    public void setWrapSnakeOnBoundaries(boolean wrapSnakeOnBoundaries) {
        this.wrapSnakeOnBoundaries = wrapSnakeOnBoundaries;
    }

    public GamePresenter(GameContract.View view) {
        this.view = view;
        snake = new Snake();
    }

    public void createPrize() {
        int x = (int) (Math.random() * RIGHT_BOUNDARY + LEFT_BOUNDARY);
        int y = (int) (Math.random() * (BOTTOM_BOUNDARY - TOP_BOUNDARY - 2)) + TOP_BOUNDARY + 1;
        prize = new Prize(x, y);

        view.printPrize(prize.getCoordinates());
    }

    /*public void gameIteration() {
        isGameOn = true;
        while (true) {
            snake.move();
            if (wrapSnakeOnBoundaries)
                wrapSnake();
            if (isCollision()) {
                isGameOn = false;
            }
            if (isPrizeAcquired()) {
                score += prize.getPoints();
                snake.extendSnake();
                createPrize();
            }
        }
    }*/

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

    @Override
    public void changeDirectionToRight() {
        snake.changeDirection(Direction.RIGHT);
    }

    @Override
    public void changeDirectionToLeft() {
        snake.changeDirection(Direction.LEFT);
    }

    @Override
    public void changeDirectionToTop() {
        snake.changeDirection(Direction.UP);
    }

    @Override
    public void changeDirectionToBottom() {
        snake.changeDirection(Direction.DOWN);
    }

    @Override
    public void startGame() {
        if (!isGameOn) {
            gameTimer = new Timer();
            createPrize();

            gameTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    snake.move();
                    if (wrapSnakeOnBoundaries) {
                        wrapSnake();
                    }

                    view.printSnake(snake.getBody());

                    if (isCollision()) {
                        isGameOn = false;
                        stopGame();
                        view.onGameOver();
                    }
                    if (isPrizeAcquired()) {
                        score += prize.getPoints();
                        view.updateScore(score);
                        snake.extendSnake();
                        createPrize();
                    }
                }
            }, 0, 100);
        }
    }

    @Override
    public void stopGame() {
        gameTimer.cancel();
    }

    @Override
    public void setSpeed(int speed) {

    }
}
