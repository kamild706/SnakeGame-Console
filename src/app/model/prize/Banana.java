package app.model.prize;

public class Banana extends Prize {

    private int points;

    Banana() {
        points = 6;
        setShape("$");
    }

    @Override
    public int getPoints() {
        return points;
    }
}
