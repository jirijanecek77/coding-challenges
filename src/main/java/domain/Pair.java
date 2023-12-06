package domain;

public record Pair<K, V>(K first, V second) {

    public static <K, V> Pair<K, V> of(K first, V second) {
        return new Pair<>(first, second);
    }

    @Override
    public String toString() {
        return "(" + first + "," + second + ')';
    }
}
