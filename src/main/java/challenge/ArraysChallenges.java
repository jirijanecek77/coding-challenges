package challenge;

import domain.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        for (int j : arr) {
            sum += j;

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
        int first = -1, second = first;
        for (int num : arr) {
            if (num > first) {
                second = first;
                first = num;
            } else if (num < first && num > second) {
                second = num;
            }
        }
        return second;
    }

    public static int kThLargest(int[] data, int k) {
        if (k > data.length) {
            return -1;
        }

        var queue = Arrays.stream(data)
                .collect(() -> new PriorityQueue<Integer>(), PriorityQueue::add, PriorityQueue::addAll);

        int num = 0;
        while (k-- > 0 && !queue.isEmpty()) {
            num = queue.poll();
        }
        return num;
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

    public static int minCost(String colors, int[] neededTime) {
        // https://leetcode.com/problems/minimum-time-to-make-rope-colorful/description/?envType=daily-question&envId=2023-12-27
        int minCost = 0;
        int sum = 0, maxTime = 0;

        for (int i = 0; i < colors.length(); i++) {
            char ch = colors.charAt(i);

            if (ch == colors.charAt(Math.max(0, i - 1))) {
                sum += neededTime[i];
                maxTime = Math.max(maxTime, neededTime[i]);
            } else {
                // sum all except, max
                minCost += sum - maxTime;

                sum = neededTime[i];
                maxTime = neededTime[i];
            }
        }

        minCost += sum - maxTime;
        return minCost;
    }

    public static boolean makeEqual(String[] words) {
        return Arrays.stream(words)
                .flatMapToInt(String::chars)
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values().stream()
                .allMatch(e -> e % words.length == 0);
    }

    public static int minOperations(String s) {
        int minStart1 = 0, minStart0 = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i % 2 == 0) {
                if (s.charAt(i) == '0') {
                    minStart1++;
                } else {
                    minStart0++;
                }
            } else {
                if (s.charAt(i) == '1') {
                    minStart1++;
                } else {
                    minStart0++;
                }
            }
        }
        return Math.min(minStart0, minStart1);
    }

    public static int maxLengthBetweenEqualCharacters(String s) {
        int maxLen = 0;
        Map<Character, Integer> positions = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (positions.containsKey(ch)) {
                maxLen = Math.max(maxLen, i - positions.get(ch));
            } else {
                positions.put(ch, i);
            }
        }

        return maxLen - 1;
    }

    public static int findContentChildren(int[] g, int[] s) {
        //https://leetcode.com/problems/assign-cookies/?envType=daily-question&envId=2024-01-01

        Arrays.sort(g);
        Arrays.sort(s);

        int i = 0;
        for (int j = 0; j < s.length && i < g.length; j++) {
            if (s[j] >= g[i]) {
                i++;
            }
        }

        return i;
    }

    public static List<List<Integer>> findMatrix(int[] nums) {
        //https://leetcode.com/problems/convert-an-array-into-a-2d-array-with-conditions/?envType=daily-question&envId=2024-01-02

        Map<Integer, Long> counter = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        int maxCount = (int) counter.values().stream().mapToLong(e -> e).max().orElse(0);

        List<List<Integer>> result = new ArrayList<>();
        IntStream.range(1, maxCount + 1)
                .mapToObj(count -> counter.entrySet().stream().filter(e -> e.getValue() >= count).map(Map.Entry::getKey).toList())
                .forEach(result::add);
        return result;
    }


    public static int numberOfBeams(String[] bank) {
        // https://leetcode.com/problems/number-of-laser-beams-in-a-bank/
        int sum = 0, lastDevices = 0;
        for (String row : bank) {
            int devices = (int) row.chars().filter(ch -> ch == '1').count();
            if (devices > 0) {
                sum += devices * lastDevices;
                lastDevices = devices;
            }
        }

        return sum;
    }

    public static boolean increasingTriplet(int[] nums) {
        // https://leetcode.com/problems/increasing-triplet-subsequence/description/
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;

        if (nums.length < 3) {
            return false;
        }

        for (int num : nums) {
            if (num <= min1) {
                min1 = num;
            } else if (num <= min2) {
                min2 = num;
            } else {
                return true;
            }
        }
        return false;
    }


    public static int kthFactor(int n, int k) {
        List<Integer> factors = new ArrayList<>();

        for (int i = 1; i <= n && factors.size() < k; i++) {
            int factor = n % i;
            if (factor == 0) {
                factors.add(i);
            }
        }
        return factors.size() == k ? factors.get(factors.size() - 1) : -1;
    }

    public static int largestAltitude(int[] gain) {
        int sum = 0, max = Integer.MIN_VALUE;
        for (int i : gain) {
            max = Math.max(max, sum);
            sum += i;
        }
        return Math.max(max, sum);
    }

    public static int pivotIndex(int[] nums) {
        int left = 0, right = 0;
        for (int num : nums) {
            right += num;
        }

        for (int i = 0; i < nums.length; i++) {
            left += i == 0 ? 0 : nums[i - 1];
            right -= nums[i];
            if (left == right) {
                return i;
            }
        }

        return -1;
    }

    public static boolean find132pattern(int[] nums) {
        int min = Integer.MAX_VALUE;
        Stack<Pair<Integer, Integer>> stack = new Stack<>();

        for (int num : nums) {
            while (!stack.isEmpty() && num >= stack.peek().first()) {
                stack.pop();
            }
            if (!stack.isEmpty() && num > stack.peek().second()) {
                return true;
            }
            stack.push(Pair.of(num, min));
            min = Math.min(min, num);
        }

        return false;
    }


    public static List<List<Integer>> findWinners(int[][] matches) {
        // https://leetcode.com/problems/find-players-with-zero-or-one-losses/description/?envType=daily-question&envId=2024-01-15
        Set<Integer> winners = new HashSet<>();
        Map<Integer, Integer> losers = new HashMap<>();
        for (int[] match : matches) {
            int winner = match[0];
            int loser = match[1];

            if (!losers.containsKey(winner)) {
                winners.add(winner);
            }
            winners.remove(loser);

            losers.put(loser, losers.getOrDefault(loser, 0) + 1);
        }

        return List.of(
                winners.stream().sorted().toList(),
                losers.entrySet().stream().filter(e -> e.getValue() == 1).map(Map.Entry::getKey).sorted().toList()
        );
    }
}
