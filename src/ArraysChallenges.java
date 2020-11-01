public class ArraysChallenges {

    static int minimumBribes(int[] q) {
        int N = q.length;
        int bribes = 0;

        for (int i = N - 1; i >= 0; i--) {
            if (q[i] - (i + 1) > 2) {
                return -1;
            }
            for (int j = Math.max(0, q[i] - 2); j < i; j++)
                if (q[j] > q[i]) bribes++;
        }

        return bribes;
    }

    static int minimumSwaps(int[] arr) {
        int N = arr.length;
        int swaps = 0;

        for (int i = 0; i < N; i++) {
            if (arr[i] != i + 1) {
                swap(arr, i, arr[i] - 1);
                swaps++;
            }
        }

        if (swaps == 0) return 0;

        return swaps + minimumSwaps(arr);
    }

    private static void swap(int[] arr, int i1, int i2) {
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    static long arrayManipulation(int n, int[][] queries) {
        long[] temp = new long[n + 1];

        for (int[] query : queries) {
            int k = query[2];
            temp[query[0] - 1] += k;
            temp[query[1]] -= k;
        }

        long result = 0;
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += temp[i];
            result = Math.max(result, sum);
        }

        return result;
    }

    static int maximumSubarraySum(int[] arr) {
        int sum = 0, best = 0;

        for (int item : arr) {
            sum = Math.max(item, sum + item);
            best = Math.max(best, sum);
        }
        return best;
    }
}
