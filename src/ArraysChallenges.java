import java.util.*;

public class ArraysChallenges {

    static int minimumBribes(int[] q) {
        int N = q.length;
        int bribes = 0;

        for (int i = N - 1; i >= 0; i--) {
            if (q[i] - (i + 1) > 2) {
                return -1;
            }
            for (int j = Math.max(0, q[i] - 2); j < i; j++)
                if (q[j] > q[i]) bribes++;
        }

        return bribes;
    }

    static int minimumSwaps(int[] arr) {
        int N = arr.length;
        int swaps = 0;

        for (int i = 0; i < N; i++) {
            if (arr[i] != i + 1) {
                swap(arr, i, arr[i] - 1);
                swaps++;
            }
        }

        if (swaps == 0) return 0;

        return swaps + minimumSwaps(arr);
    }

    private static void swap(int[] arr, int i1, int i2) {
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    static long arrayManipulation(int n, int[][] queries) {
        long[] temp = new long[n + 1];

        for (int[] query : queries) {
            int k = query[2];
            temp[query[0] - 1] += k;
            temp[query[1]] -= k;
        }

        long result = 0;
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += temp[i];
            result = Math.max(result, sum);
        }

        return result;
    }

    static int maximumSubarraySum(int[] arr) {
        int sum = 0, best = 0;

        for (int item : arr) {
            sum = Math.max(item, sum + item);
            best = Math.max(best, sum);
        }
        return best;
    }

    static boolean zeroSubarraySum(int[] arr) {
        Set<Integer> sums = new HashSet<>();
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];

            // if prefix sum is zero or found same prefix sum before
            if (sum == 0 || sums.contains(sum)) {
                return true;
            }

            sums.add(sum);
        }
        return false;
    }


    static int maximumNonAdjacentSum(int[] arr) {
        int incl = arr[0];
        int excl = 0;
        int excl_new;
        int i;

        for (i = 1; i < arr.length; i++) {
            // Current max excluding i
            excl_new = Math.max(incl, excl);

            // Current max including i
            incl = excl + arr[i];
            excl = excl_new;
        }

        // Return max of incl and excl
        return Math.max(incl, excl);
    }

    static int longestDecreasingSubarray(int[] arr) {
        int n = arr.length;
        int lds[] = new int[n];
        int i, j, max = 0;

        for (i = 0; i < n; i++)
            lds[i] = 1;

        for (i = 1; i < n; i++) {
            for (j = 0; j < i; j++) {
                if (arr[i] < arr[j] && lds[i] < lds[j] + 1) {
                    lds[i] = lds[j] + 1;
                }
            }
        }

        for (i = 0; i < n; i++) {
            if (max < lds[i]) {
                max = lds[i];
            }
        }

        return max;
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

    static int secondLargest(int[] arr) {
        Arrays.sort(arr);

        int i;
        for (i = arr.length - 2; i >= 0 && arr[i + 1] == arr[i]; i--) ;

        if (i < 0) {
            return -1;
        }
        return arr[i];
    }

    public static int kThLargest(int[] data, int k) {
        var dataSet = Arrays.stream(data)
                .collect(() -> new TreeSet<Integer>(), TreeSet::add, TreeSet::addAll);
        var it = dataSet.iterator();

        for (int i = 1; i <= k; i++) {
            if (!it.hasNext()) {
                return 0;
            }
            if (i == k) {
                return it.next();
            }
            it.next();
        }
        return 0;
    }

    public static int oneInteger(int[] data) {
        // You are given a list of integers nums. You can reduce the length of nums by taking any two integers,
        // removing them, and appending their sum to the end.
        // The cost of doing this is the sum of the two integers you removed.
        // Return the minimum total cost of reducing nums to one integer.
        // Note : Cost can be negative also.
        // Constraints: n ≤ 100,000 where n is length of nums.

        var result = 0;
        var queue = Arrays.stream(data)
                .collect(() -> new PriorityQueue<Integer>(), PriorityQueue::add, PriorityQueue::addAll);

        while (queue.size() > 1) {
            var first = queue.poll();
            var second = queue.poll();

            queue.add(first + second);
            result += first + second;
        }

        return result;
    }

    public static int maxScoreRemovingStones(int a, int b, int c) {
        // Maximum Score From Removing Stones
        // You are playing a solitaire game with three piles of stones of sizes a, b, and c respectively.
        // Each turn you choose two different non-empty piles, take one stone from each, and add 1 point to your score.
        // The game stops when there are fewer than two non-empty piles (meaning there are no more available moves).
        // Given three integers a, b, and c, return the maximum score you can get.

        var queue = new PriorityQueue<Integer>(3, (x, y) -> -x.compareTo(y));
        queue.add(a);
        queue.add(b);
        queue.add(c);

        var result = 0;
        while (queue.size() > 1) {
            var first = queue.poll();
            var second = queue.poll();

            if (first > 1) {
                queue.add(first - 1);
            }
            if (second > 1) {
                queue.add(second - 1);
            }
            result += 1;
        }

        return result;
    }

    public static int[] findKthClosest(int[] nums, int k, int value) {
        // nums is sorted asc
        var index = Math.abs(Arrays.binarySearch(nums, value));

        int left = index, right = index;
        for (int i = 0; i < k; i++) {
            if (nums[index] - nums[left] <= nums[right] - nums[index]) {
                left -= 1;
            } else {
                right += 1;
            }
        }

        return Arrays.copyOfRange(nums, left, right);
    }
}
