package domain;

public class Node {
    public int data;
    public Node left = null;
    public Node right = null;

    public Node(int data) {
        this.data = data;
    }

    public Node(int data, Node left, Node right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}

