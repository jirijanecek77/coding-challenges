package domain;

public record Interval(int low, int high) {
    @Override
    public String toString() {
        return "[" + low + "," + high + ']';
    }
}