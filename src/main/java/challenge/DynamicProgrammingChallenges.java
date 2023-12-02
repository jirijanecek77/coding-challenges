package challenge;

import domain.Pair;

import java.util.HashMap;
import java.util.Map;

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
            return  sum == target ? 1 : 0;
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
}
