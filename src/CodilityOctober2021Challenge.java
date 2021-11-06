public class CodilityOctober2021Challenge {
    public int solution(int[] A) {
        // https://app.codility.com/programmers/custom_challenge/spooktober_2021/

        int max = 0;
        int[] left = calcLeft(A);
        int[] right = calcRight(A);
        for (int i = 0; i < A.length; i++) {
            int current = A[i];

            int result = current
                    + (i > 0 ? left[i - 1] : 0)
                    + (i < A.length - 1 ? right[i + 1] : 0);

            if (result > max) {
                max = result;
            }
        }

        return max;
    }

    private int[] calcLeft(int[] A) {
        int[] result = new int[A.length];

        int last = 0;
        for (int i = 0; i < A.length; i++) {
            result[i] = (last + A[i]) / 2;
            last = result[i];
        }

        return result;
    }

    private int[] calcRight(int[] A) {
        int[] result = new int[A.length];

        int last = 0;
        for (int i = A.length - 1; i >= 0; i--) {
            result[i] = (last + A[i]) / 2;
            last = result[i];
        }

        return result;
    }
}
