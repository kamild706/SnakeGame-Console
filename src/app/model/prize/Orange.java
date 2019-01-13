package app.model.prize;

class Orange extends Prize {

    private int points;

    Orange() {
        points = 2;
        setShape("@");
    }

    @Override
    public int getPoints() {
        return points;
    }
}
