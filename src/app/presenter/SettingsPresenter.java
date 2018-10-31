package app.presenter;

import app.SettingsContract;
import app.model.GameConfig;

public class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View view;
    private GameConfig gameConfig;

    public SettingsPresenter(SettingsContract.View view) {
        this.view = view;
        gameConfig = GameConfig.getInstance();
    }

    @Override
    public void setCollisionWithBoundaries(boolean flag) {
        gameConfig.setSnakeWrapsOnBoundaries(!flag);
    }

    @Override
    public void setGameLevelEasy() {
        gameConfig.setGameLevel(GameConfig.LEVEL_EASY);
    }

    @Override
    public void setGameLevelMedium() {
        gameConfig.setGameLevel(GameConfig.LEVEL_MEDIUM);
    }

    @Override
    public void setGameLevelHard() {
        gameConfig.setGameLevel(GameConfig.LEVEL_HARD);
    }
}
