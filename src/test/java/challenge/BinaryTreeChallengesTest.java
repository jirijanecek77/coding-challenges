package challenge;

import domain.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinaryTreeChallengesTest {

    @Test
    public void testCheckBST() {
        Node root = new Node(4,
                tree(2, new Node(1), new Node(3)),
                tree(6, new Node(5), new Node(7))
        );

        assertTrue(BinaryTreeChallenges.checkBST(root));
    }

    @Test
    public void testBTHeight() {
        Node root = new Node(4,
                tree(2, new Node(1), tree(3, new Node(0), null)),
                tree(6, new Node(5), null)
        );

        assertEquals(4, BinaryTreeChallenges.treeHeight(root));
        assertEquals(4, BinaryTreeChallenges.treeHeightIterative(root));
    }

    private static Node tree(int rootVal, Node leftNode, Node rightNode) {
        return new Node(rootVal, leftNode, rightNode);
    }

    @Test
    void goodNodes() {
        Node root = new Node(3,
                new Node(1, new Node(3), null),
                new Node(4, new Node(1), new Node(5))
        );

        assertEquals(4, BinaryTreeChallenges.goodNodes(root));
    }

    @Test
    void levelOrder() {
        Node root = new Node(3,
                new Node(9),
                new Node(20, new Node(15), new Node(7))
        );

        assertEquals(3, BinaryTreeChallenges.levelOrder(root).size());
    }

    @Test
    void amountOfTime() {
        Node root = new Node(1,
                new Node(5, null, new Node(4, new Node(9), new Node(2))),
                new Node(3, new Node(10), new Node(6))
        );

        assertEquals(4, BinaryTreeChallenges.amountOfTime(root, 3));
    }

    @Test
    void maxAncestorDiff() {
        Node root1 = new Node(8,
                new Node(3, new Node(1), new Node(6, new Node(4), new Node(7))),
                new Node(10, null, new Node(14, new Node(13), null))
        );

        assertEquals(7, BinaryTreeChallenges.maxAncestorDiff(root1));

        Node root2 = new Node(8, null,
                new Node(1, new Node(5, new Node(2, new Node(7), new Node(3)), new Node(4)), new Node(6, new Node(0), null))
        );

        assertEquals(8, BinaryTreeChallenges.maxAncestorDiff(root2));

        assertEquals(0, BinaryTreeChallenges.maxAncestorDiff(null));
    }
}
