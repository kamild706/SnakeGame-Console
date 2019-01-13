package app.model;

public class GameConfig {

    public static final int TOP_BOUNDARY = 1;
    public static final int BOTTOM_BOUNDARY = 28;
    public static final int RIGHT_BOUNDARY = 90;
    public static final int LEFT_BOUNDARY = -1;

    public static final long LEVEL_EASY = 350;
    public static final long LEVEL_MEDIUM = 130;
    public static final long LEVEL_HARD = 50;

    private long gameLevel;
    private boolean snakeWrapsOnBoundaries;

    public long getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(long gameLevel) {
        this.gameLevel = gameLevel;
    }

    public boolean isSnakeWrapsOnBoundaries() {
        return snakeWrapsOnBoundaries;
    }

    public void setSnakeWrapsOnBoundaries(boolean snakeWrapsOnBoundaries) {
        this.snakeWrapsOnBoundaries = snakeWrapsOnBoundaries;
    }

    private static GameConfig ourInstance = new GameConfig();

    public static GameConfig getInstance() {
        return ourInstance;
    }

    private GameConfig() {
        gameLevel = LEVEL_EASY;
        snakeWrapsOnBoundaries = false;
    }
}
