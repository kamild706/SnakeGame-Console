package app.view;

public class MainView {

    private MyScreen screen;
    private final int NEW_GAME = 0;
    private final int SETTINGS = 1;
    private final int EXIT = 2;
    private final String[] MENU_OPTIONS = new String[]{"Nowa gra", "Ustawienia", "Wyjdź"};

    public MainView() {
        screen = new MyScreen();
        screen.clearScreen();
        printHeader();

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true)
            showMainMenu();
    }

    private void printHeader() {
        int leftMargin = 15;
        int topMargin = 5;

        screen.print(leftMargin, topMargin++, " ▄▄▄▄▄▄▄▄▄▄▄  ▄▄        ▄  ▄▄▄▄▄▄▄▄▄▄▄  ▄    ▄  ▄▄▄▄▄▄▄▄▄▄▄ ");
        screen.print(leftMargin, topMargin++, "▐░░░░░░░░░░░▌▐░░▌      ▐░▌▐░░░░░░░░░░░▌▐░▌  ▐░▌▐░░░░░░░░░░░▌");
        screen.print(leftMargin, topMargin++, "▐░█▀▀▀▀▀▀▀▀▀ ▐░▌░▌     ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌ ▐░▌ ▐░█▀▀▀▀▀▀▀▀▀ ");
        screen.print(leftMargin, topMargin++, "▐░▌          ▐░▌▐░▌    ▐░▌▐░▌       ▐░▌▐░▌▐░▌  ▐░▌          ");
        screen.print(leftMargin, topMargin++, "▐░█▄▄▄▄▄▄▄▄▄ ▐░▌ ▐░▌   ▐░▌▐░█▄▄▄▄▄▄▄█░▌▐░▌░▌   ▐░█▄▄▄▄▄▄▄▄▄ ");
        screen.print(leftMargin, topMargin++, "▐░░░░░░░░░░░▌▐░▌  ▐░▌  ▐░▌▐░░░░░░░░░░░▌▐░░▌    ▐░░░░░░░░░░░▌");
        screen.print(leftMargin, topMargin++, " ▀▀▀▀▀▀▀▀▀█░▌▐░▌   ▐░▌ ▐░▌▐░█▀▀▀▀▀▀▀█░▌▐░▌░▌   ▐░█▀▀▀▀▀▀▀▀▀ ");
        screen.print(leftMargin, topMargin++, "          ▐░▌▐░▌    ▐░▌▐░▌▐░▌       ▐░▌▐░▌▐░▌  ▐░▌          ");
        screen.print(leftMargin, topMargin++, " ▄▄▄▄▄▄▄▄▄█░▌▐░▌     ▐░▐░▌▐░▌       ▐░▌▐░▌ ▐░▌ ▐░█▄▄▄▄▄▄▄▄▄ ");
        screen.print(leftMargin, topMargin++, "▐░░░░░░░░░░░▌▐░▌      ▐░░▌▐░▌       ▐░▌▐░▌  ▐░▌▐░░░░░░░░░░░▌");
        screen.print(leftMargin, topMargin++, " ▀▀▀▀▀▀▀▀▀▀▀  ▀        ▀▀  ▀         ▀  ▀    ▀  ▀▀▀▀▀▀▀▀▀▀▀");

        screen.refreshScreen();
    }

    private void showMainMenu() {
        screen.clearScreen();

        int leftMargin = 17;
        int topMargin = 1;
        screen.print(leftMargin, topMargin++, "           /^\\/^\\");
        screen.print(leftMargin, topMargin++, "         _|__|  O|");
        screen.print(leftMargin, topMargin++, "\\/     /~     \\_/ \\");
        screen.print(leftMargin, topMargin++, " \\____|__________/  \\");
        screen.print(leftMargin, topMargin++, "        \\_______      \\");
        screen.print(leftMargin, topMargin++, "                `\\     \\                 \\");
        screen.print(leftMargin, topMargin++, "                  |     |                  \\");
        screen.print(leftMargin, topMargin++, "                 /      /                    \\");
        screen.print(leftMargin, topMargin++, "                /     /                       \\\\");
        screen.print(leftMargin, topMargin++, "              /      /                         \\ \\");
        screen.print(leftMargin, topMargin++, "             /     /                            \\  \\");
        screen.print(leftMargin, topMargin++, "           /     /             _----_            \\   \\");
        screen.print(leftMargin, topMargin++, "          /     /           _-~      ~-_         |   |");
        screen.print(leftMargin, topMargin++, "         (      (        _-~    _--_    ~-_     _/   |");
        screen.print(leftMargin, topMargin++, "          \\      ~-____-~    _-~    ~-_    ~-_-~    /");
        screen.print(leftMargin, topMargin++, "            ~-_           _-~          ~-_       _-~");
        screen.print(leftMargin, topMargin++, "               ~--______-~                ~-___-~");

        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            screen.print(40, 20 + i * 2, MENU_OPTIONS[i]);
        }

        screen.highlightMenuPosition(0, MENU_OPTIONS);
        Integer choice = screen.waitForUserChoice(MENU_OPTIONS);
        if (choice != null) {
            handleMenuChoice(choice);
        }
    }

    private void handleMenuChoice(int position) {
        if (position == EXIT) {
            System.exit(0);
        }
        if (position == NEW_GAME) {
            new GameView(screen);
        }
        if (position == SETTINGS) {
            new SettingsView(screen);
        }
    }

    public static void main(String[] args) {
        new MainView();
    }
}
