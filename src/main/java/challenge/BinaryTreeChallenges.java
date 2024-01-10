package challenge;

import domain.Node;
import domain.Pair;

import java.util.*;

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

        Queue<Node> q = new LinkedList<>();

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

    public static int goodNodes(Node root) {
        // https://leetcode.com/problems/count-good-nodes-in-binary-tree/?envType=study-plan-v2&envId=leetcode-75
        return dfsGoodNodes(root, root.data());
    }

    private static int dfsGoodNodes(Node root, int value) {
        if (root == null) {
            return 0;
        }

        return (root.data() >= value ? 1 : 0) + dfsGoodNodes(root.left(), Math.max(value, root.data())) + dfsGoodNodes(root.right(), Math.max(value, root.data()));
    }

    public static List<List<Integer>> levelOrder(Node root) {
        // https://leetcode.com/problems/binary-tree-level-order-traversal/

        List<List<Integer>> result = new ArrayList<>();
        Queue<Pair<Node, Integer>> queue = new LinkedList<>();
        if (root != null) {
            queue.add(Pair.of(root, 0));
        }
        while (!queue.isEmpty()) {
            Pair<Node, Integer> pair = queue.poll();
            Node node = pair.first();
            int nodeLevel = pair.second();

            if (nodeLevel == result.size()) {
                result.add(new ArrayList<>());
            }

            result.get(nodeLevel).add(node.data());

            if (node.left() != null) {
                queue.add(Pair.of(node.left(), nodeLevel + 1));
            }
            if (node.right() != null) {
                queue.add(Pair.of(node.right(), nodeLevel + 1));
            }
        }

        return result;
    }

    public static int amountOfTime(Node root, int start) {
        // https://leetcode.com/problems/amount-of-time-for-binary-tree-to-be-infected/description/?envType=daily-question&envId=2024-01-10

        Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
        createGraph(root, adjacencyList);

        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
        queue.add(Pair.of(start, 0));
        Set<Integer> visited = new HashSet<>();
        int result = 0;

        while (!queue.isEmpty()) {
            var pair = queue.poll();
            int vertex = pair.first();
            int time = pair.second();

            if (visited.contains(vertex)) {
                continue;
            }
            visited.add(vertex);
            result = Math.max(result, time);

            if (adjacencyList.containsKey(vertex)) {
                adjacencyList.get(vertex).forEach(adj -> queue.add(Pair.of(adj, time + 1)));
            }
        }
        return result;
    }

    private static void createGraph(Node node, Map<Integer, List<Integer>> adjacencyList) {
        if (node == null) {
            return;
        }

        if (node.left() != null) {
            adjacencyList.computeIfAbsent(node.data(), k -> new ArrayList<>()).add(node.left().data());
            adjacencyList.computeIfAbsent(node.left().data(), k -> new ArrayList<>()).add(node.data());
        }

        if (node.right() != null) {
            adjacencyList.computeIfAbsent(node.data(), k -> new ArrayList<>()).add(node.right().data());
            adjacencyList.computeIfAbsent(node.right().data(), k -> new ArrayList<>()).add(node.data());
        }

        createGraph(node.left(), adjacencyList);
        createGraph(node.right(), adjacencyList);
    }
}