package challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ArtinChallenges {

    // find number closest to zero, if both positive and negative, take positive
    public static int getClosestToZero(int[] a) {

        int diff = Integer.MAX_VALUE;
        for (int j : a) {
            if (Math.abs(j) < diff) {
                diff = j;
            } else if (Math.abs(j) == diff && j > 0) {
                diff = j;
            }
        }
        return diff;
    }

    // chunk is devided by zero in array, can be head and tail zeros
    public static int countChunks(int[] array) {

        int result = 0;
        boolean inChunk = false;
        for (int i : array) {
            if (i != 0) {
                inChunk = true;
            } else if (inChunk) {
                inChunk = false;
                result++;
            }
        }

        return result + (inChunk ? 1 : 0);
    }

    // Please do not change this interface
    public static interface Node {
        int getValue();

        List<Node> getChildren();
    }


    static class NodeC implements Node {
        private int value;
        private List<Node> children;

        public NodeC() {
            this.value = 0;
            children = null;
        }

        public NodeC(int value, List<Node> children) {
            this.value = value;
            this.children = children;
        }

        public int getValue() {
            return value;
        }

        public List<Node> getChildren() {
            return children;
        }
    }


    /**
     * Please implement this method to
     * traverse the tree and return the sum of the values (domain.Node.getValue()) of all nodes
     * at the level N in the tree.
     * domain.Node root is assumed to be at the level 0. All its children are level 1, etc.
     */
    public static int getLevelSum(Node root, int n) {
        if (root == null)
            return 0;

        if (n > 0) {
            return root.getChildren() == null ? 0 : root.getChildren().stream().mapToInt(node -> getLevelSum(node, n - 1)).sum();
        } else
            return root.getValue();
    }


    /**
     * You need to sort an array of integers by repeatedly reversing
     * the order of the first several elements of it.
     * <p>
     * For example, to sort [11,14,12,13], you need to  reverse the order of the first two (2)
     * elements and get [14,11,12,13], then reverse the order of the first four (4) elements
     * and get [13,12,11,14] and then reverse the order of the first three (3) elements.
     * <p>
     * <p>
     * The method should return array of integers corresponding to the required reversals.
     * For the previous example, given an array [11,14,12,13]
     * the method should return a array with integers [2,4,3].
     */
    public static List<Integer> getReversalsToSort(int[] array) {

        // do until sublist is not empty
        // find largest element
        // reverse the sublist to get it to first position
        // reverse whole sublist
        // repeat with new sublist without the max element

        List<Integer> result = new ArrayList<>();

        while (array.length > 1) {
            int max = findIndexOfMax(array);

            int n = array.length;
            reverseUpTo(array, max);
            if (max > 0 && max < n - 1) result.add(max + 1);
            reverseUpTo(array, n - 1);
            if (max < n - 1) result.add(n);

            array = Arrays.copyOf(array, n - 1);
        }
        return result;
    }

    private static int findIndexOfMax(int[] array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] >= array[max]) {
                max = i;
            }
        }
        return max;
    }

    private static void reverseUpTo(int[] array, int to) {
        int from = 0;
        while (from < to) {
            int temp = array[from];
            array[from] = array[to];
            array[to] = temp;
            from++;
            to--;
        }
    }


    public static int amazonFindMax2DigitNumberInString(String str) {
        int first = 0, second = 0, last = 0;
        for (int i = 0; i < str.length(); i++) {
            int num = str.charAt(i) - '0';
            if (last > first) {
                first = last;
                second = num;
            } else if (last == first) {
                second = Math.max(second, num);
            }
            last = num;
        }

        return first * 10 + second;
    }

    public static boolean amazonGraph(int[] A, int[] B) {
        List<Integer> listA = Arrays.stream(A).boxed().collect(Collectors.toList());
        List<Integer> listB = Arrays.stream(B).boxed().collect(Collectors.toList());
        Collections.rotate(listA, 1);
        return listA.equals(listB);
    }
}
