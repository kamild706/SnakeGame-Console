package app.view;

import app.GameContract;
import app.model.Coordinates;
import app.presenter.GamePresenter;
import app.utils.MyUtils;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.LinkedList;

public class GameView implements GameContract.View {

    private MyScreen screen;
    private GameContract.Presenter presenter;
    private Coordinates tailOfSnake;
    private boolean isGameOn = false;
    private Coordinates prize;

    private static final String snakeElement = "\u2b1b";

    public GameView(MyScreen screen) {
        this.screen = screen;
        presenter = new GamePresenter(this);
        start();
    }

    private void start() {
        showGameBoard();
        presenter.startGame();

        isGameOn = true;

        while (isGameOn) {
            try {
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                        presenter.changeDirectionToBottom();
                    } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                        presenter.changeDirectionToTop();
                    } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
                        presenter.changeDirectionToLeft();
                    } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
                        presenter.changeDirectionToRight();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    @Override
    public void printSnake(LinkedList<Coordinates> snakeBody) {
        TextColor snakeColor = new TextColor.RGB(0, 77, 178);
        if (tailOfSnake != null)
            screen.print(tailOfSnake.getX(), tailOfSnake.getY(), " ");
        snakeBody.forEach(p -> screen.print(p.getX(), p.getY(), snakeElement, snakeColor));
        tailOfSnake = snakeBody.getLast();
    }

    @Override
    public void printPrize() {
        printPrize(prize);
    }

    @Override
    public void printPrize(Coordinates prize) {
        this.prize = prize;
        TextColor awardColor = new TextColor.RGB(0, 0, 255);
        screen.print(prize.getX(), prize.getY(), "X", awardColor);
    }

    @Override
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

    private void showGameBoard() {
        screen.clearScreen();

        String frame = MyUtils.repeatString("=", screen.COLUMNS);
        screen.print(0, 1, frame);
        updateScore(0);
    }

    @Override
    public void updateScore(int score) {
        screen.print(screen.COLUMNS - 15, 0, "Wynik: " + score);
    }
}
