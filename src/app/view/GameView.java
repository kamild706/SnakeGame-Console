package app.view;

import app.model.Coordinates;
import app.model.Direction;
import app.model.Game;
import app.model.prize.Prize;
import app.model.snake.Snake;
import app.utils.MyUtils;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.LinkedList;

public class GameView {

    private MyScreen screen;
//    private GameContract.Presenter presenter;
    private Coordinates tailOfSnake;
    private boolean isGameOn = false;
    private Prize prize;
    private Coordinates lastObstaclePosition;
    private Game game;

    private static final String snakeElement = "\u2b1b";

    public GameView(MyScreen screen) {
        this.screen = screen;
        this.game = new Game(this);
//        presenter = new GamePresenter(this);
        start();
    }

    private void start() {
        showGameBoard();
        game.startGame();
        isGameOn = true;

        while (isGameOn) {
            try {
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                        game.changeSnakeDirectionTo(Direction.DOWN);
                    } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                        game.changeSnakeDirectionTo(Direction.UP);
                    } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
                        game.changeSnakeDirectionTo(Direction.LEFT);
                    } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
                        game.changeSnakeDirectionTo(Direction.RIGHT);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void printSnake(Snake snake) {
        LinkedList<Coordinates> snakeBody = snake.getBody();
        if (tailOfSnake != null)
            screen.print(tailOfSnake.getX(), tailOfSnake.getY(), " ");
        snakeBody.forEach(p -> screen.print(p.getX(), p.getY(), snake.getShape(), snake.getColor()));
        tailOfSnake = snakeBody.getLast();
    }

    public void printPrize() {
        printPrize(prize);
    }

    public void printPrize(Prize prize) {
        this.prize = prize;
        TextColor awardColor = prize.getColor();
        Coordinates position = prize.getCoordinates();
        String shape = prize.getShape();
        screen.print(position.getX(), position.getY(), shape, awardColor);
        paintBarrier();
    }

    public void onGameOver() {
        int leftMargin = 23;
        int topMargin = 3;
        screen.print(leftMargin, topMargin++, "_     _  _____  __   _ _____ _______ _______");
        screen.print(leftMargin, topMargin++, " |____/  |     | | \\  |   |   |______ |     ");
        screen.print(leftMargin, topMargin++, " |    \\_ |_____| |  \\_| __|__ |______ |_____");
//        screen.print(31, topMargin + 5, "Nacisnij ENTER aby powrocic");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*while (true) {
            try {
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }*/

        isGameOn = false;
    }

    private void paintBarrier() {
        String frame = MyUtils.repeatString("=", screen.COLUMNS);
        screen.print(0, 1, frame);
    }

    private void showGameBoard() {
        screen.clearScreen();

        String frame = MyUtils.repeatString("=", screen.COLUMNS);
        screen.print(0, 1, frame);
        /*updateScore(0);
        updateLives(2);*/
    }

    public void updateScore(int score) {
        screen.print(screen.COLUMNS - 15, 0, "Wynik: " + score);
    }

    public void updateLives(int lives) {
        screen.print(screen.COLUMNS - 25, 0, "ŻYCIA: " + lives);
    }

    public void printObstacle(Coordinates coordinates) {
        if (lastObstaclePosition == null) {
            lastObstaclePosition = coordinates.clone();
        } else {
            screen.print(lastObstaclePosition.getX(), lastObstaclePosition.getY(), " ", screen.BACKGROUND_COLOR);
            lastObstaclePosition = coordinates.clone();
        }
        TextColor awardColor = new TextColor.RGB(0, 0, 0);
        screen.print(coordinates.getX(), coordinates.getY(), "#", awardColor);
    }
}
