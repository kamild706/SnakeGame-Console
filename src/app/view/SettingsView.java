package app.view;

import app.SettingsContract;
import app.model.GameConfig;
import app.presenter.SettingsPresenter;
import app.utils.MyUtils;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;


public class SettingsView implements SettingsContract.View {

    private SettingsContract.Presenter presenter;
    private Screen screen;
    private boolean exitRequested = false;

    private final int COLUMNS = GameConfig.RIGHT_BOUNDARY;
    private final int ROWS = GameConfig.BOTTOM_BOUNDARY;

    private final int GAME_LEVEL = 0;
    private final int BOUNDARY_COLLISION = 1;
    private final int EXIT = 2;
    private final String[] MENU_OPTIONS = new String[]{"Poziom trudnosci", "Kolizja ze sciana", "Powrot"};
    private final String[] GAME_LEVELS = new String[]{"Latwy", "Sredni", "Trudny"};
    private final String[] COLLISION_OPTIONS = new String[]{"Tak", "Nie"};
    private final int LEVEL_EASY = 0;
    private final int LEVEL_MEDIUM = 1;
    private final int LEVEL_HARD = 2;
    private final int COLLISION_YES = 0;
    private final int COLLISION_NO = 1;
    private int selectedMenuPosition = 0;

    private TextColor backgroundColor;
    private TextColor textColor;
    private TextColor selectionBackgroundColor;

    public SettingsView(Screen screen) {
        this.screen = screen;
        this.presenter = new SettingsPresenter(this);

        initColors();

        while (!exitRequested)
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

    private void showMainMenu() {
        showMenu(MENU_OPTIONS);
        Integer choice = waitForUserChoice(MENU_OPTIONS);
        if (choice != null) {
            handleMenuChoice(choice);
        }
    }

    private void showMenu(String[] options) {
        clearScreen();

        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(textColor);
        textGraphics.setBackgroundColor(backgroundColor);

        for (int i = 0; i < options.length; i++) {
            textGraphics.putString(40, 20 + i * 2, options[i]);
        }

        highlightMenuPosition(selectedMenuPosition, options);
    }

    private void highlightMenuPosition(int position, String[] options) {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(textColor);
        textGraphics.setBackgroundColor(backgroundColor);
        for (int i = 0; i < options.length; i++) {
            textGraphics.putString(40, 20 + i * 2, options[i]);
        }

        textGraphics.setBackgroundColor(selectionBackgroundColor);
        textGraphics.putString(40, 20 + 2 * position, options[position]);

        refreshScreen();
    }

    private Integer waitForUserChoice(String[] options) {
        while (true) {
            try {
                KeyStroke keyStroke = screen.pollInput();
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

    private void handleMenuChoice(int position) {
        if (position == EXIT) {
            exitRequested = true;
        }
        if (position == GAME_LEVEL) {
            showGameLevelMenu();
        }
        if (position == BOUNDARY_COLLISION) {
            showBoundaryCollisionMenu();
        }
    }

    private void showBoundaryCollisionMenu() {
        selectedMenuPosition = 0;
        showMenu(COLLISION_OPTIONS);
        Integer choice = waitForUserChoice(COLLISION_OPTIONS);
        if (choice != null)
            handleCollisionChoice(choice);
    }

    private void showGameLevelMenu() {
        selectedMenuPosition = 0;
        showMenu(GAME_LEVELS);
        Integer choice = waitForUserChoice(GAME_LEVELS);
        if (choice != null)
            handleGameLevelChoice(choice);
    }

    private void handleCollisionChoice(int position) {
        if (position == COLLISION_YES) {
            presenter.setCollisionWithBoundaries(true);
        }
        if (position == COLLISION_NO) {
            presenter.setCollisionWithBoundaries(false);
        }
    }

    private void handleGameLevelChoice(int position) {
        if (position == LEVEL_EASY) {
            presenter.setGameLevelEasy();
        }
        if (position == LEVEL_MEDIUM) {
            presenter.setGameLevelMedium();
        }
        if (position == LEVEL_HARD) {
            presenter.setGameLevelHard();
        }
    }
}
