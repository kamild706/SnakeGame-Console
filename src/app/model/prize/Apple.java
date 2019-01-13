package app.model.prize;

public class Apple extends Prize {

    private int points;

    Apple() {
        points = 10;
        setShape("#");
    }

    @Override
    public int getPoints() {
        return points;
    }
}
