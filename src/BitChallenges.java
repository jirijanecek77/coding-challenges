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

    public static int closestPowerOf( int n) {
        int i = 0, a = 1;

        while (a <= n) {
            a = a << 1;
            i++;
        }

        return i - 1;
    }
}
