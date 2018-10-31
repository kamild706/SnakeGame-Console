package app.view;

import app.GameContract;
import app.model.Coordinates;
import app.model.GameConfig;
import app.presenter.GamePresenter;
import app.utils.MyUtils;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.LinkedList;

public class GameView implements GameContract.View {

    private final int COLUMNS = GameConfig.RIGHT_BOUNDARY;
    private final int ROWS = GameConfig.BOTTOM_BOUNDARY;

    private Screen screen;
    private GameContract.Presenter presenter;
    private Coordinates tailOfSnake;
    private boolean isGameOn = false;
    private Coordinates prize;

    private TextColor backgroundColor;
    private TextColor textColor;

    private static final String snakeElement = "\u2b1b";

    public GameView(Screen screen) {
        this.screen = screen;
        presenter = new GamePresenter(this);
        initColors();
        start();
    }

    private void initColors() {
        backgroundColor = new TextColor.RGB(132, 95, 35);
        textColor = new TextColor.RGB(255, 255, 255);
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
            print(tailOfSnake.getX(), tailOfSnake.getY(), " ");
        snakeBody.forEach(p -> print(p.getX(), p.getY(), snakeElement, snakeColor));
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
        print(prize.getX(), prize.getY(), "X", awardColor);
    }

    @Override
    public void onGameOver() {
        int leftMargin = 23;
        int topMargin = 3;
        print(leftMargin, topMargin++, "_     _  _____  __   _ _____ _______ _______");
        print(leftMargin, topMargin++, " |____/  |     | | \\  |   |   |______ |     ");
        print(leftMargin, topMargin++, " |    \\_ |_____| |  \\_| __|__ |______ |_____");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isGameOn = false;
    }

    private void clearScreen() {
        screen.clear();
        TextGraphics textGraphics = screen.newTextGraphics();

        textGraphics.setBackgroundColor(backgroundColor);
        String text = MyUtils.repeatString(" ", COLUMNS);
        for (int i = 0; i < ROWS; i++) {
            textGraphics.putString(0, i, text);
        }

        refreshScreen();
    }

    private void refreshScreen() {
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showGameBoard() {
        clearScreen();

        String frame = MyUtils.repeatString("=", COLUMNS);
        print(0, 1, frame);
        updateScore(0);
    }

    @Override
    public void updateScore(int score) {
        print(COLUMNS - 15, 0, "Wynik: " + score);
    }

    private void print(int fromLeft, int fromTop, String text) {
        print(fromLeft, fromTop, text, textColor);
    }

    private void print(int fromLeft, int fromTop, String text, TextColor foregroundColor) {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(foregroundColor);
        textGraphics.setBackgroundColor(backgroundColor);
        textGraphics.putString(fromLeft, fromTop, text);
        refreshScreen();
    }

}
