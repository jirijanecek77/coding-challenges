package domain;

public record Node(int data, Node left, Node right) {

    public Node(int data) {
        this(data, null, null);
    }
}

