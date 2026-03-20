package challenge;

import java.util.*;

public class HeapChallenges {
    public static int findKthLargest(List<Integer> nums, int k) {
        Queue<Integer> queue = new PriorityQueue<>(nums.size());

        for (int num : nums) {
            queue.add(num);

            if (queue.size() > k) {
                queue.poll();
            }
        }

        return queue.isEmpty() ? -1 : queue.peek();
    }


    public static List<List<Integer>> kClosestPoints(List<List<Integer>> points, int k) {
        Queue<List<Integer>> queue = new PriorityQueue<>(points.size(), Comparator.comparing(a -> -(Math.pow(a.get(0), 2) + Math.pow(a.get(1), 2))));

        for (List<Integer> point : points) {
            queue.add(point);

            if (queue.size() > k) {
                queue.poll();
            }
        }

        List<List<Integer>> res = new ArrayList<>(queue.size());
        while (!queue.isEmpty()) {
            res.add(0, queue.poll());
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(kClosestPoints(List.of(List.of(3, 3), List.of(5, -1), List.of(-2, 4)), 2));
    }

}
