package domain;

public record Coordinate(int x, int y) {
    @Override
    public String toString() {
        return "(" + x + ", " + y + ')';
    }
}
