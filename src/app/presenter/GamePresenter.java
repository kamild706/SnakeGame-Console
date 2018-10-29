package app.presenter;

import app.GameContract;
import app.model.*;

import java.util.LinkedList;

public class GamePresenter implements GameContract.Presenter, Observer {

    private GameContract.View view;
    private Game game;

    public GamePresenter(GameContract.View view) {
        this.view = view;
        game = new Game();
        game.addObserver(this);
        game.setSpeed(Game.LEVEL_HARD);
        game.setWrapSnakeOnBoundaries(true);
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

    @Override
    public void setSpeed(long speed) {
        game.setSpeed(speed);
    }

    @Override
    public void notifySnakeMoved(LinkedList<Coordinates> coordinates) {
        view.printSnake(coordinates);
    }

    @Override
    public void notifyPrizeCreated(Coordinates prizeCoordinates) {
        view.printPrize(prizeCoordinates);
    }

    @Override
    public void notifyCollisionOccurred() {
        view.onGameOver();
    }

    @Override
    public void notifyPrizeAcquired(int userScore) {
        view.updateScore(userScore);
    }
}
