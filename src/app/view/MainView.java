package app.view;

import app.model.GameConfig;
import app.utils.MyUtils;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class MainView {

    private final int COLUMNS = GameConfig.RIGHT_BOUNDARY;
    private final int ROWS = GameConfig.BOTTOM_BOUNDARY;

    private Screen screen;
    private final int NEW_GAME = 0;
    private final int SETTINGS = 1;
    private final int EXIT = 2;
    private final String[] MENU_OPTIONS = new String[]{"Nowa gra", "Ustawienia", "Wyjdź"};
    private int selectedMenuPosition = 0;

    private TextColor backgroundColor;
    private TextColor textColor;
    private TextColor selectionBackgroundColor;

    public MainView() {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(new TerminalSize(COLUMNS, ROWS));
        try {
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null);
            screen.startScreen();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        initColors();
        clearScreen();
        printHeader();

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true)
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

    private void waitForUserChoice() {
        while (true) {
            try {
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                        selectedMenuPosition++;
                        if (selectedMenuPosition == MENU_OPTIONS.length)
                            selectedMenuPosition--;
                    }
                    if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                        selectedMenuPosition--;
                        if (selectedMenuPosition < 0)
                            selectedMenuPosition = 0;
                    }
                    if (keyStroke.getKeyType() == KeyType.Enter) {
                        break;
                    }

                    highlightMenuPosition(selectedMenuPosition);
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
        }
        if (position == NEW_GAME) {
            GameView gameView = new GameView(screen);
        }
        if (position == SETTINGS) {
            new SettingsView(screen);
        }
    }

    public static void main(String[] args) {
        new MainView();
    }
}
