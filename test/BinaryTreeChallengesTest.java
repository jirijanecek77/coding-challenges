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

}
