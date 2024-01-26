package challenge;

import domain.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DynamicProgrammingChallenges {

    public static boolean abbreviation(String a, String b) {

        int indexA = 0, indexB = 0;
        while (indexA < a.length() && indexB < b.length()) {
            if (Character.toUpperCase(a.charAt(indexA)) == b.charAt(indexB)) {
                indexA++;
                indexB++;
            } else if (Character.isLowerCase(a.charAt(indexA))) {
                indexA++;
            } else {
                return false;
            }
        }
        return indexB == b.length() && a.substring(indexA).chars().allMatch(Character::isLowerCase);
    }

    public static int targetSum(int[] nums, int target) {
        Map<Pair<Integer, Integer>, Integer> dp = new HashMap<>();
        return backtracking(0, 0, nums, target, dp);
    }

    private static int backtracking(int index, int sum, int[] nums, int target, Map<Pair<Integer, Integer>, Integer> dp) {
        if (index == nums.length) {
            return sum == target ? 1 : 0;
        }

        Integer cached = dp.get(new Pair<>(sum, index));
        if (cached != null) {
            return cached;
        }

        int result = backtracking(index + 1, sum + nums[index], nums, target, dp)
                + backtracking(index + 1, sum - nums[index], nums, target, dp);
        dp.put(new Pair<>(sum, index), result);
        return result;
    }


    public static int minDifficulty(int[] jobDifficulties, int days) {
        // https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/description/?envType=daily-question&envId=2023-12-29
        if (days > jobDifficulties.length) return -1;
        Integer[][] dp = new Integer[jobDifficulties.length][days + 1];
        int res = diff(0, jobDifficulties, days, dp);
        if (res == Integer.MAX_VALUE) return -1;
        return res;
    }

    private static int diff(int curr, int[] jobDifficulties, int days, Integer[][] dp) {
        if (curr >= jobDifficulties.length && days <= 0) return 0;
        if (curr >= jobDifficulties.length || days <= 0) return Integer.MAX_VALUE;
        if (dp[curr][days] != null) return dp[curr][days];

        int maxDiff = -1;
        int res = Integer.MAX_VALUE;

        for (int i = curr; i < jobDifficulties.length; i++) {
            maxDiff = Math.max(maxDiff, jobDifficulties[i]);
            int temp = diff(i + 1, jobDifficulties, days - 1, dp);
            if (temp != Integer.MAX_VALUE) res = Math.min(res, maxDiff + temp);
        }
        return dp[curr][days] = res;
    }

    public static int minOperations(int[] nums) {
        //https://leetcode.com/problems/minimum-number-of-operations-to-make-array-empty/?envType=daily-question&envId=2024-01-04

        Map<Integer, Long> freq = Arrays.stream(nums).boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        int maxFreq = (int) Math.max(freq.values().stream().mapToLong(e -> e).max().orElse(0), 4);

        int[] dp = new int[maxFreq + 1];
        dp[0] = 0;
        dp[1] = -1;
        dp[2] = 1;
        dp[3] = 1;
        dp[4] = 2;
        dp(dp);

        return freq.values().stream().map(Long::intValue).mapToInt(e -> dp[e]).reduce((a, b) -> (a == -1 || b == -1) ? -1 : a + b).orElse(-1);
    }

    private static void dp(int[] dp) {
        for (int i = 5; i < dp.length; i++) {
            dp[i] = Math.min(dp[i - 2], dp[i - 3]) + 1;
        }
    }

    public static int lengthOfLIS(int[] nums) {
        // https://leetcode.com/problems/longest-increasing-subsequence/submissions/1137415316/?envType=daily-question&envId=2024-01-05
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        return Arrays.stream(dp).max().orElse(0);
    }

    public static int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        // https://leetcode.com/problems/maximum-profit-in-job-scheduling/?envType=daily-question&envId=2024-01-06

        int n = startTime.length;
        List<Job> jobs = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            jobs.add(new Job(startTime[i], endTime[i], profit[i]));
        }
        jobs.sort(Comparator.comparing(e -> e.endTime));

        int[] dp = new int[n + 1];

        for (int i = 0; i < n; i++) {
            Job job = jobs.get(i);
            int startTimeValue = job.startTime;
            int profitValue = job.profit;

            int latestNonConflictJobIndex = upperBound(jobs, i, startTimeValue);
            dp[i + 1] = Math.max(dp[i], dp[latestNonConflictJobIndex] + profitValue);
        }

        return dp[n];
    }

    private static int upperBound(List<Job> jobs, int endIndex, int targetTime) {
        int low = 0;
        int high = endIndex;

        while (low < high) {
            int mid = (low + high) / 2;
            if (jobs.get(mid).endTime <= targetTime) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    record Job(int startTime, int endTime, int profit) {
    }

    public static int minFallingPathSum(int[][] matrix) {
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        int result = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            result = Math.min(result, dpMinFallingPathSum(0, i, matrix, dp));
        }
        return result;
    }

    private static int dpMinFallingPathSum(int row, int col, int[][] matrix, int[][] dp) {
        if (row == matrix.length) {
            return 0;
        }
        if (dp[row][col] != Integer.MAX_VALUE) {
            return dp[row][col];
        }

        IntStream.range(-1, 2)
                .map(c -> c + col)
                .filter(nextCol -> nextCol >= 0 && nextCol < matrix[0].length)
                .forEach(nextCol -> dp[row][col] = Math.min(dp[row][col], matrix[row][col] + dpMinFallingPathSum(row + 1, nextCol, matrix, dp)));

        return dp[row][col];
    }

}
