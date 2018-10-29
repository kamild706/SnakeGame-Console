package app.model;

public enum Direction {
    UP(1), RIGHT(2), DOWN(1), LEFT(2);

    private final int value;

    Direction(int value) {
        this.value = value;
    }

    boolean isContradictoryTo(Direction direction) {
        return this.value == direction.value;
    }
}
