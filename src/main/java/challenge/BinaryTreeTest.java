package challenge;

import domain.Node;

import static challenge.BinaryTreeChallenges.sumRootToLeaf;

public class BinaryTreeTest {

    public static void assertTest(boolean condition) {
        if (!condition) {
            throw new AssertionError("test failed");
        }
        System.out.println("test passed");
    }

    public static void main(String[] args) {
        assertTest(sumRootToLeaf(new Node(1,
                new Node(0, new Node(0, null, null), new Node(1, null, null)),
                new Node(1, new Node(0, null, null), new Node(1, null, null)))
        ) == 22);
    }
}
