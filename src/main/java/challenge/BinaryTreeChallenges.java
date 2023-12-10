package challenge;

import domain.Node;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeChallenges {

    // check if tree is binary search tree
    static boolean checkBST(Node root) {
        if (root == null) return true;


        return (root.left() == null || root.left().data() < root.data() && checkBST(root.left()))
                && (root.right() == null || root.right().data() > root.data() && checkBST(root.right()));
    }

    public static int treeHeight(Node root) {
        if (root == null) {
            return 0;
        }

        var leftHeight = treeHeight(root.left());
        var rightHeight = treeHeight(root.right());

        if (leftHeight > rightHeight) {
            return leftHeight + 1;
        }
        return rightHeight + 1;
    }

    public static int treeHeightIterative(Node node) {
        if (node == null) {
            return 0;
        }

        Queue<Node> q = new LinkedList();

        // Enqueue Root and initialize height
        q.add(node);
        int height = 0;

        while (true) {
            // nodeCount (queue size) indicates number of nodes
            // at current level.
            int nodeCount = q.size();
            if (nodeCount == 0) {
                return height;
            }
            height++;

            // Dequeue all nodes of current level and Enqueue all
            // nodes of next level
            while (nodeCount > 0) {
                Node newnode = q.peek();
                q.remove();
                if (newnode.left() != null)
                    q.add(newnode.left());
                if (newnode.right() != null)
                    q.add(newnode.right());
                nodeCount--;
            }
        }
    }
}