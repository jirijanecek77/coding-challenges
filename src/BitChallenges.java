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
}
