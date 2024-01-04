package challenge;

import domain.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
}
