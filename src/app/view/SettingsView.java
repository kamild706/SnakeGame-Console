package app.view;

import app.SettingsContract;
import app.presenter.SettingsPresenter;


public class SettingsView implements SettingsContract.View {

    private SettingsContract.Presenter presenter;
    private MyScreen screen;
    private boolean exitRequested = false;

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


    public SettingsView(MyScreen screen) {
        this.screen = screen;
        this.presenter = new SettingsPresenter(this);

        while (!exitRequested)
            showMainMenu();
    }

    private void showMainMenu() {
        showMenu(MENU_OPTIONS);
        Integer choice = screen.waitForUserChoice(MENU_OPTIONS);
        if (choice != null) {
            handleMenuChoice(choice);
        }
    }

    private void showMenu(String[] options) {
        screen.clearScreen();

        for (int i = 0; i < options.length; i++) {
            screen.print(40, 20 + i * 2, options[i]);
        }

        screen.highlightMenuPosition(0, options);
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
        showMenu(COLLISION_OPTIONS);
        Integer choice = screen.waitForUserChoice(COLLISION_OPTIONS);
        if (choice != null)
            handleCollisionChoice(choice);
    }

    private void showGameLevelMenu() {
        showMenu(GAME_LEVELS);
        Integer choice = screen.waitForUserChoice(GAME_LEVELS);
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
