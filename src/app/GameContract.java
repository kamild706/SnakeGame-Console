package app;

import app.model.Coordinates;

import java.util.LinkedList;

public interface GameContract {

    interface Presenter {
        void changeDirectionToRight();
        void changeDirectionToLeft();
        void changeDirectionToTop();
        void changeDirectionToBottom();
        void startGame();
        void stopGame();
        void setSpeed(long speed);
    }

    interface View {
        void printSnake(LinkedList<Coordinates> coordinates);
        void printPrize(Coordinates coordinates);
        void updateScore(int score);
        void onGameOver();
    }
}
