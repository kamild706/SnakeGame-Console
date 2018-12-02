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
    }

    interface View {
        void printSnake(LinkedList<Coordinates> coordinates);
        void printPrize(Coordinates coordinates);
        void printPrize();
        void updateScore(int score);
        void onGameOver();
        void updateLives(int lives);
        void printObstacle(Coordinates coordinates);
    }
}
