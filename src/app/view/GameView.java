package app.view;

import app.model.Coordinates;
import app.model.Direction;
import app.model.Game;
import app.model.obstacle.IObstacle;
import app.model.prize.Prize;
import app.model.snake.Snake;
import app.utils.MyUtils;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

public class GameView {

    private MyScreen screen;
    private Coordinates tailOfSnake;
    private boolean isGameOn = false;
    private Map<Coordinates, Prize> prizes;
    private Coordinates lastObstaclePosition;
    private Game game;
    private Snake snake;


    public GameView(MyScreen screen) {
        this.screen = screen;
        this.game = new Game(this);
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
                    KeyType pressedKey = keyStroke.getKeyType();
                    if (pressedKey == KeyType.ArrowDown) {
                        game.changeSnakeDirectionTo(Direction.DOWN);
                    } else if (pressedKey == KeyType.ArrowUp) {
                        game.changeSnakeDirectionTo(Direction.UP);
                    } else if (pressedKey == KeyType.ArrowLeft) {
                        game.changeSnakeDirectionTo(Direction.LEFT);
                    } else if (pressedKey == KeyType.ArrowRight) {
                        game.changeSnakeDirectionTo(Direction.RIGHT);
                    } else if (pressedKey == KeyType.Escape) {
                        game.stopGame();
                    } else if (pressedKey == KeyType.F1) {
                        game.pauseOrResume();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void onGameOver() {
        int leftMargin = 23;
        int topMargin = 3;
        screen.print(leftMargin, topMargin++, "_     _  _____  __   _ _____ _______ _______");
        screen.print(leftMargin, topMargin++, " |____/  |     | | \\  |   |   |______ |     ");
        screen.print(leftMargin, topMargin++, " |    \\_ |_____| |  \\_| __|__ |______ |_____");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
    }

    public void onObstacleMoved(IObstacle obstacle) {
        Coordinates coordinates = obstacle.getCoordinates();
        if (lastObstaclePosition == null) {
            lastObstaclePosition = coordinates.clone();
        } else {
            screen.print(lastObstaclePosition.getX(), lastObstaclePosition.getY(), " ", screen.BACKGROUND_COLOR);
            lastObstaclePosition = coordinates.clone();
        }
        TextColor obstacleColor = new TextColor.RGB(165, 25, 255);
        screen.print(coordinates.getX(), coordinates.getY(), "#", obstacleColor);
    }

    public void onSnakeCreated(Snake snake) {
        this.snake = snake;

        screen.print(screen.COLUMNS - 15, 0, "Wynik: " + snake.getScore());
        screen.print(screen.COLUMNS - 25, 0, "ŻYCIA: " + snake.getLives());
    }

    public void onSnakeMoved() {
        LinkedList<Coordinates> snakeBody = snake.getBody();
        if (tailOfSnake != null)
            screen.print(tailOfSnake.getX(), tailOfSnake.getY(), " ");

        snakeBody.forEach(p -> screen.print(p.getX(), p.getY(), snake.getShape(), snake.getColor()));
        tailOfSnake = snakeBody.getLast();

        rerenderPrizes();
    }

    public void onPrizesInitialized(Map<Coordinates, Prize> prizes) {
        this.prizes = prizes;
        rerenderPrizes();
    }

    private void rerenderPrizes() {
        for (Prize prize : prizes.values()) {
            TextColor awardColor = prize.getColor();
            Coordinates position = prize.getCoordinates();
            String shape = prize.getShape();
            screen.print(position.getX(), position.getY(), shape, awardColor);
        }
        paintBarrier();
    }

    public void onSnakeLivesChanged() {
        screen.print(screen.COLUMNS - 25, 0, "ŻYCIA: " + snake.getLives());
    }

    public void onSnakeScoreChanged() {
        screen.print(screen.COLUMNS - 15, 0, "Wynik: " + snake.getScore());
    }

    public void onSnakeStateChangePossibility() {
        rerenderSnakeState();
    }

    private void rerenderSnakeState() {
        for (int i = 1; i < 15; i++)
            screen.print(i, 0, " ", screen.BACKGROUND_COLOR);
        String state = snake.getSnakeState().toString();
        screen.print(1, 0, "Wąż " + state);
    }
}