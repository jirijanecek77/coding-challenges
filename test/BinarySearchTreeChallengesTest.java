import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinarySearchTreeChallengesTest {

    @Test
    public void testCheckBST() {
        Node root = new Node(4,
                tree(2, 1, 3),
                tree(6, 5, 7)
        );

        assertTrue(BinarySearchTreeChallenges.checkBST(root));
    }

    private static Node tree(int rootVal, int leftVal, int rightVal) {
        return new Node(rootVal, new Node(leftVal), new Node(rightVal));
    }

    @Test
    void findLocalMaximum() {
        int[] arr = {1, 3, 4, 5, 6, 5, 4, 3};
        assertEquals(6, BinarySearchTreeChallenges.findLocalMaximum(arr));
    }
}
