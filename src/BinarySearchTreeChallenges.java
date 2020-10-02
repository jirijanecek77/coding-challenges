public class BinarySearchTreeChallenges {

    // check if tree is binary search tree
    static boolean checkBST(Node root) {
        if (root == null) return true;


        return (root.left == null || root.left.data < root.data && checkBST(root.left))
                && (root.right == null || root.right.data > root.data && checkBST(root.right));
    }
}