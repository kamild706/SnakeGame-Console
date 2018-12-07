package app.model.obstacle;

public class DoubleMoveDecorator extends ObstacleDecorator {

    public DoubleMoveDecorator(ObstacleInterface obstacleInterface) {
        super(obstacleInterface);
    }

    @Override
    public void move(int distance) {
        super.move(distance * 2);
    }
}
