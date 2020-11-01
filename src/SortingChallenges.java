import java.util.Arrays;

public class SortingChallenges {

    static int maximumToys(int[] prices, int k) {
        Arrays.sort(prices);

        int sum = 0;
        int i = 0;
        while (sum <= k) {
            sum += prices[i];
            i++;
        }

        return i - 1;
    }

}
