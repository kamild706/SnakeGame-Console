package app.model;

import java.util.Objects;

public class Coordinates {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates clone() {
        return new Coordinates(this.x, this.y);
    }

    public void move(Direction direction) {
        if (direction == Direction.UP) {
            y--;
        }
        if (direction == Direction.RIGHT) {
            x++;
        }
        if (direction == Direction.DOWN) {
            y++;
        }
        if (direction == Direction.LEFT) {
            x--;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }
}
