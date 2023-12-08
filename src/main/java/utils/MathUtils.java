package utils;

import java.util.List;

public class MathUtils {

    public static long gcd(long num1, long num2) {
        if (num2 == 0)
            return num1;
        return gcd(num2, num1 % num2);
    }

    public static long lcm(List<Long> arr) {
        long lcm = arr.get(0);
        for (int i = 1; i < arr.size(); i++) {
            long num1 = lcm;
            long num2 = arr.get(i);
            long gcd_val = gcd(num1, num2);
            lcm = (lcm * arr.get(i)) / gcd_val;
        }
        return lcm;
    }
}
