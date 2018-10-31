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

public class MyScreen {

    public final int COLUMNS = GameConfig.RIGHT_BOUNDARY;
    public final int ROWS = GameConfig.BOTTOM_BOUNDARY;

    private Screen screen;
    public final TextColor BACKGROUND_COLOR;
    public final TextColor TEXT_COLOR;
    public final TextColor SELECTED_BACKGROUND;

    private int selectedMenuPosition = 0;

    public MyScreen() {
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

        BACKGROUND_COLOR = new TextColor.RGB(132, 95, 35);
        TEXT_COLOR = new TextColor.RGB(255, 255, 255);
        SELECTED_BACKGROUND = new TextColor.RGB(132, 64, 35);
    }

    public void clearScreen() {
        screen.clear();
        TextGraphics textGraphics = screen.newTextGraphics();

        textGraphics.setBackgroundColor(BACKGROUND_COLOR);
        String text = MyUtils.repeatString(" ", COLUMNS);
        for (int i = 0; i < ROWS; i++) {
            textGraphics.putString(0, i, text);
        }

        refreshScreen();
    }

    public void refreshScreen() {
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(int fromLeft, int fromTop, String text) {
        print(fromLeft, fromTop, text, TEXT_COLOR);
    }

    public void print(int fromLeft, int fromTop, String text, TextColor foregroundColor) {
        print(fromLeft, fromTop, text, foregroundColor, BACKGROUND_COLOR);
    }

    public void print(int fromLeft, int fromTop, String text, TextColor foregroundColor, TextColor backgroundColor) {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(foregroundColor);
        textGraphics.setBackgroundColor(backgroundColor);
        textGraphics.putString(fromLeft, fromTop, text);
        refreshScreen();
    }

    public KeyStroke pollInput() throws IOException {
        return screen.pollInput();
    }

    public void highlightMenuPosition(int position, String[] options) {
        for (int i = 0; i < options.length; i++) {
            print(40, 20 + i * 2, options[i]);
        }
        selectedMenuPosition = position;
        print(40, 20 + 2 * position, options[position], TEXT_COLOR, SELECTED_BACKGROUND);
        refreshScreen();
    }

    public Integer waitForUserChoice(String[] options) {
        selectedMenuPosition = 0;
        while (true) {
            try {
                KeyStroke keyStroke = pollInput();
                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.ArrowDown) {
                        selectedMenuPosition++;
                        if (selectedMenuPosition == options.length)
                            selectedMenuPosition--;
                    }
                    if (keyStroke.getKeyType() == KeyType.ArrowUp) {
                        selectedMenuPosition--;
                        if (selectedMenuPosition < 0)
                            selectedMenuPosition = 0;
                    }
                    if (keyStroke.getKeyType() == KeyType.Enter) {
                        return selectedMenuPosition;
                    }
                    if (keyStroke.getKeyType() == KeyType.Escape) {
                        return null;
                    }

                    highlightMenuPosition(selectedMenuPosition, options);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
