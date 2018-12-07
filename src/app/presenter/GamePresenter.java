package app.presenter;

import app.GameContract;
import app.model.*;
import app.model.game.CollisionFreeGame;
import app.model.game.CollisionGame;
import app.model.game.Game;

import java.util.LinkedList;

public class GamePresenter implements GameContract.Presenter {

    private GameContract.View view;
    private Game game;

    public GamePresenter(GameContract.View view) {
        this.view = view;
        if (GameConfig.getInstance().isSnakeWrapsOnBoundaries()) {
            game = new CollisionFreeGame();
        } else {
            game = new CollisionGame();
        }
        game.setPresenter(this);
    }

    @Override
    public void changeDirectionToRight() {
        game.changeSnakeDirectionTo(Direction.RIGHT);
    }

    @Override
    public void changeDirectionToLeft() {
        game.changeSnakeDirectionTo(Direction.LEFT);
    }

    @Override
    public void changeDirectionToTop() {
        game.changeSnakeDirectionTo(Direction.UP);
    }

    @Override
    public void changeDirectionToBottom() {
        game.changeSnakeDirectionTo(Direction.DOWN);
    }

    @Override
    public void startGame() {
        game.startGame();
    }

    @Override
    public void stopGame() {
        game.stopGame();
    }

    public void notifySnakeMoved(LinkedList<Coordinates> coordinates) {
        view.printSnake(coordinates);
        view.printPrize();
    }

    public void notifyPrizeCreated(Coordinates prizeCoordinates) {
        view.printPrize(prizeCoordinates);
    }

    public void notifyCollisionOccurred() {
        view.onGameOver();
    }

    public void notifyPrizeAcquired(int userScore) {
        view.updateScore(userScore);
    }

    public void notifyLivesChangeOccurred(int lives) {
        view.updateLives(lives);
    }

    public void notifyObstacleMoved(Coordinates coordinates) {
        view.printObstacle(coordinates);
    }
}
