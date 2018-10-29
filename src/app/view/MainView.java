package app.view;

import app.GameContract;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import app.model.Coordinates;
import app.presenter.GamePresenter;
import app.utils.MyUtils;

import java.io.IOException;
import java.util.LinkedList;

public class MainView implements GameContract.View {

    private final GameContract.Presenter presenter;
    private Screen screen;
    private TextColor backgroundColor;
    private TextColor textColor;
    private TextColor selectionBackgroundColor;
    private static final int COLUMNS = 90;
    private static final int ROWS = 28;
    private static final int NEW_GAME = 0;
    private static final int RANKING = 1;
    private static final int SETTINGS = 2;
    private static final int EXIT = 3;
    private static final String[] MENU_OPTIONS = new String[]{"Nowa gra", "Ranking", "Ustawienia", "Wyjdź"};
    private int selectedMenuPosition = 0;
    private Coordinates tailOfSnake;
    private boolean isGameOn = false;
    private Coordinates prize;

    private static final String snakeElement = "\u2b1b";

    public MainView() throws IOException, InterruptedException {

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(new TerminalSize(COLUMNS, ROWS));
        Terminal terminal = terminalFactory.createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();

        initColors();

        clearScreen();
        printHeader();

        Thread.sleep(2500);
        presenter = new GamePresenter(this);
        showMainMenu();

    }

    private void initColors() {
        backgroundColor = new TextColor.RGB(132, 95, 35);
        textColor = new TextColor.RGB(255, 255, 255);
        selectionBackgroundColor = new TextColor.RGB(132, 64, 35);
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

    private void waitForUserChoice() {
        while (true) {
            try {
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowDown) {
                    selectedMenuPosition++;
                    if (selectedMenuPosition == MENU_OPTIONS.length) selectedMenuPosition--;
                    highlightMenuPosition(selectedMenuPosition);
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowUp) {
                    selectedMenuPosition--;
                    if (selectedMenuPosition < 0) selectedMenuPosition = 0;
                    highlightMenuPosition(selectedMenuPosition);
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.Enter) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        handleMenuChoice(selectedMenuPosition);
    }

    private void handleMenuChoice(int position) {
        if (position == EXIT) {
            System.exit(0);
        } else if (position == NEW_GAME) {
            gameLoop();
        }
    }

    private void gameLoop() {
        showGameBoard();
        presenter.startGame();

        isGameOn = true;

        while (isGameOn) {
            try {
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowDown) {
                    presenter.changeDirectionToBottom();
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowUp) {
                    presenter.changeDirectionToTop();
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowLeft) {
                    presenter.changeDirectionToLeft();
                } else if (keyStroke != null && keyStroke.getKeyType() == KeyType.ArrowRight) {
                    presenter.changeDirectionToRight();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void showMainMenu() {
        clearScreen();

        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(textColor);
        textGraphics.setBackgroundColor(backgroundColor);

        int leftMargin = 17;
        int topMargin = 1;
        textGraphics.putString(leftMargin, topMargin++, "           /^\\/^\\");
        textGraphics.putString(leftMargin, topMargin++, "         _|__|  O|");
        textGraphics.putString(leftMargin, topMargin++, "\\/     /~     \\_/ \\");
        textGraphics.putString(leftMargin, topMargin++, " \\____|__________/  \\");
        textGraphics.putString(leftMargin, topMargin++, "        \\_______      \\");
        textGraphics.putString(leftMargin, topMargin++, "                `\\     \\                 \\");
        textGraphics.putString(leftMargin, topMargin++, "                  |     |                  \\");
        textGraphics.putString(leftMargin, topMargin++, "                 /      /                    \\");
        textGraphics.putString(leftMargin, topMargin++, "                /     /                       \\\\");
        textGraphics.putString(leftMargin, topMargin++, "              /      /                         \\ \\");
        textGraphics.putString(leftMargin, topMargin++, "             /     /                            \\  \\");
        textGraphics.putString(leftMargin, topMargin++, "           /     /             _----_            \\   \\");
        textGraphics.putString(leftMargin, topMargin++, "          /     /           _-~      ~-_         |   |");
        textGraphics.putString(leftMargin, topMargin++, "         (      (        _-~    _--_    ~-_     _/   |");
        textGraphics.putString(leftMargin, topMargin++, "          \\      ~-____-~    _-~    ~-_    ~-_-~    /");
        textGraphics.putString(leftMargin, topMargin++, "            ~-_           _-~          ~-_       _-~");
        textGraphics.putString(leftMargin, topMargin++, "               ~--______-~                ~-___-~");


        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            textGraphics.putString(40, 20 + i * 2, MENU_OPTIONS[i]);
        }
//        textGraphics.drawLine(35, 5, 35, 23, '|');
//        textGraphics.drawLine(55, 5, 55, 23, '|');
//        textGraphics.drawLine(35, 5, 55, 5, '-');
//        textGraphics.drawLine(35, 23, 55, 23, '-');
        refreshScreen();

        highlightMenuPosition(selectedMenuPosition);
        waitForUserChoice();
    }

    private void highlightMenuPosition(int position) {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(textColor);
        textGraphics.setBackgroundColor(backgroundColor);
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            textGraphics.putString(40, 20 + i * 2, MENU_OPTIONS[i]);
        }

        textGraphics.setBackgroundColor(selectionBackgroundColor);
        textGraphics.putString(40, 20 + 2 * position, MENU_OPTIONS[position]);

        refreshScreen();
    }

    private void refreshScreen() {
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printHeader() {
        int leftMargin = 15;
        int topMargin = 5;

        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(textColor);
        textGraphics.setBackgroundColor(backgroundColor);
        textGraphics.putString(leftMargin, topMargin++, " ▄▄▄▄▄▄▄▄▄▄▄  ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄    ▄  ▄▄▄▄▄▄▄▄▄▄▄ ");
        textGraphics.putString(leftMargin, topMargin++, "▐░░░░░░░░░░░▌▐░░▌      ▐░▌▐░░░░░░░░░░░▌▐░▌  ▐░▌▐░░░░░░░░░░░▌");
        textGraphics.putString(leftMargin, topMargin++, "▐░█▀▀▀▀▀▀▀▀▀ ▐░▌░▌     ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌ ▐░▌ ▐░█▀▀▀▀▀▀▀▀▀ ");
        textGraphics.putString(leftMargin, topMargin++, "▐░▌          ▐░▌▐░▌    ▐░▌▐░▌       ▐░▌▐░▌▐░▌  ▐░▌          ");
        textGraphics.putString(leftMargin, topMargin++, "▐░█▄▄▄▄▄▄▄▄▄ ▐░▌ ▐░▌   ▐░▌▐░█▄▄▄▄▄▄▄█░▌▐░▌░▌   ▐░█▄▄▄▄▄▄▄▄▄ ");
        textGraphics.putString(leftMargin, topMargin++, "▐░░░░░░░░░░░▌▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌▐░░▌    ▐░░░░░░░░░░░▌");
        textGraphics.putString(leftMargin, topMargin++, " ▀▀▀▀▀▀▀▀▀█░▌▐░▌   ▐░▌ ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌░▌   ▐░█▀▀▀▀▀▀▀▀▀ ");
        textGraphics.putString(leftMargin, topMargin++, "          ▐░▌▐░▌    ▐░▌▐░▌▐░▌       ▐░▌▐░▌▐░▌  ▐░▌          ");
        textGraphics.putString(leftMargin, topMargin++, " ▄▄▄▄▄▄▄▄▄█░▌▐░▌     ▐░▐░▌▐░▌       ▐░▌▐░▌ ▐░▌ ▐░█▄▄▄▄▄▄▄▄▄ ");
        textGraphics.putString(leftMargin, topMargin++, "▐░░░░░░░░░░░▌▐░▌      ▐░░▌▐░▌       ▐░▌▐░▌  ▐░▌▐░░░░░░░░░░░▌");
        textGraphics.putString(leftMargin, topMargin++, " ▀▀▀▀▀▀▀▀▀▀▀  ▀        ▀▀  ▀         ▀  ▀    ▀  ▀▀▀▀▀▀▀▀▀▀▀");

        refreshScreen();
    }

    @Override
    public void printSnake(LinkedList<Coordinates> snakeBody) {
        TextColor snakeColor = new TextColor.RGB(0, 77, 178);
        if (tailOfSnake != null)
            print(tailOfSnake.getX(), tailOfSnake.getY(), " ");
        snakeBody.forEach(p -> print(p.getX(), p.getY(), snakeElement, snakeColor));
        tailOfSnake = snakeBody.getLast();
    }

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
        isGameOn = false;
        int leftMargin = 23;
        int topMargin= 3;
        print(leftMargin, topMargin++, "_     _  _____  __   _ _____ _______ _______");
        print(leftMargin, topMargin++, " |____/  |     | | \\  |   |   |______ |     ");
        print(leftMargin, topMargin++, " |    \\_ |_____| |  \\_| __|__ |______ |_____");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showMainMenu();
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

    public static void main(String[] args) throws IOException, InterruptedException {
        MainView window = new MainView();
    }
}
