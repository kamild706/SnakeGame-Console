package app.model.obstacle;

public class Poison extends ObstacleDecorator {

    public Poison(IObstacle obstacle) {
        super(obstacle);
    }

    @Override
    public double getDamagingPower() {
        return super.getDamagingPower() + 0.9;
    }
}
