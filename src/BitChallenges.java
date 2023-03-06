import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public class BitChallenges {

    public static long swapBits(long x, int i, int j) {
        long ithBit = (x >>> i) & 1;
        long jthBit = (x >>> j) & 1;
        int a = 0xFFFF;

        if (ithBit != jthBit) {
            long bitMask = (1L << i) | (1L << j);
            return x ^ bitMask;
        }
        return x;
    }

    public static int closestPowerOf(int n) {
        int i = 0, a = 1;

        while (a <= n) {
            a = a << 1;
            i++;
        }

        return i - 1;
    }

    public static int[] sortBy1s(int[] arr) {
        /*
        Given an integer array arr. You have to sort the integers in the array in ascending order by the number
        of 1's in their binary representation and in case of two or more integers have the same number of 1's
        you have to sort them in ascending order.
         */

        return Arrays.stream(arr)
                .boxed()
                .collect(Collectors.groupingBy(BitChallenges::countBits))
                .entrySet().stream().sorted(Map.Entry.comparingByKey())
                .flatMap(e -> e.getValue().stream().sorted())
                .mapToInt(i -> i)
                .toArray();
    }

    private static int countBits(int n) {
        int result = 0;
        while (n > 0) {
            n = n & (n - 1);
            result++;
        }
        return result;
    }
}
