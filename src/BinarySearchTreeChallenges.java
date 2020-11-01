public class BinarySearchTreeChallenges {

    // check if tree is binary search tree
    static boolean checkBST(Node root) {
        if (root == null) return true;


        return (root.left == null || root.left.data < root.data && checkBST(root.left))
                && (root.right == null || root.right.data > root.data && checkBST(root.right));
    }

    static int findLocalMaximum(int[] arr) {
        int x = -1;
        int N = arr.length;
        for (int b = (N - 1) / 2; b >= 1; b /= 2) {
            while (arr[x + b] < arr[x + b + 1]) {
                x += b;
            }
        }
        return arr[x + 1];
    }
}