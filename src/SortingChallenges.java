import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortingChallenges {

    static int maximumToys(int[] prices, int k) {
        List<Integer> sortedData = Arrays.stream(prices)
                .boxed()
                .sorted()
                .collect(Collectors.toList());

        int sum = 0;
        int i = 0;
        while (sum <= k) {
            sum += sortedData.get(i);
            i++;
        }

        return i - 1;
    }

}
