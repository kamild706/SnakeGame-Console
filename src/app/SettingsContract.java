package app;

public interface SettingsContract {

    interface Presenter {
        void setCollisionWithBoundaries(boolean flag);
        void setGameLevelEasy();
        void setGameLevelMedium();
        void setGameLevelHard();
    }

    interface View {

    }
}
