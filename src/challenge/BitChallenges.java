package challenge;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public class BitChallenges {

    public static long swapBits(long x, int i, int j) {
        long ithBit = (x >>> i) & 1;
        long jthBit = (x >>> j) & 1;

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

    public static int unique2N(int[] arr) {
        // find one unique number, the others occur twice

        var result = 0;
        for (int i : arr) {
            result ^= i;  // XOR of two the same numbers is always zero, XOR of a number with zero is always the number
        }

        return result;
    }

    public static int unique3N(int[] arr) {
        // find one unique number, the others occur 3 times

        // split to array by bits
        // sum
        // calc modulo 3 for each item in array
        // transform result to decimal

        int[] sumArr = new int[32];
        for (int i : arr) {
            getSumArray(sumArr, i);
        }

        for (int i = 0; i < sumArr.length; i++) {
            sumArr[i] %= 3;
        }

        return bitArrayToDecimal(sumArr);
    }

    private static int bitArrayToDecimal(int[] arr) {
        var result = 0;
        var pow = 1;

        for (int i : arr) {
            result += pow * i;
            pow <<= 1;
        }

        return result;
    }

    private static void getSumArray(int[] arr, int number) {
        int i = 0;
        while (number > 0) {
            var iThBit = number & 1;
            arr[i] += iThBit;
            number >>= 1;
            i++;
        }
    }

    public static BigInteger pow(int a, int b) {
        BigInteger result = BigInteger.ONE;
        BigInteger base = BigInteger.valueOf(a);
        while (b > 0) {
            if ((b & 1) > 0) {
                result = result.multiply(base);
            }

            base = base.multiply(base);
            b >>= 1;
        }

        return result;
    }
}
