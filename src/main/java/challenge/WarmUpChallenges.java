package challenge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WarmUpChallenges {

    static int sockMerchant(int n, int[] ar) {
        //https://www.hackerrank.com/challenges/sock-merchant/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup
        int result = 0;
        Set<Integer> foundSocks = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int sock = ar[i];
            if (foundSocks.contains(sock)) {
                foundSocks.remove(sock);
                result++;
            } else {
                foundSocks.add(sock);
            }
        }
        return result;
    }

    static int countingValleys(int n, String s) {

        int valleys = 0;
        int altitude = 0;

        for (char ch : s.toCharArray()) {
            if (ch == 'D') {
                if (altitude == 0) {
                    valleys++;
                }
                altitude--;
            } else {
                altitude++;
            }
        }

        return valleys;
    }

    static int jumpingOnClouds(int[] c) {
        int N = c.length;
        int jumps = -1;
        for (int i = 0; i < N; i++, jumps++) {
            if (i < N - 2 && c[i + 2] == 0) i++;
        }

        return jumps;
    }

    static long repeatedString(String s, long n) {
        int sampleLength = s.length();

        long wholeOccurrences = n / sampleLength;
        long partialLength = n % sampleLength;

        List<Boolean> occurrences = s.chars()
                .mapToObj(ch -> ch == 'a')
                .collect(Collectors.toList());

        long result = 0L;
        if (partialLength > 0) {
            result += occurrences.subList(0, (int) partialLength).stream()
                    .filter(e -> e)
                    .count();
        }

        if (wholeOccurrences > 0) {
            result += occurrences.stream().filter(e -> e).count() * wholeOccurrences;
        }

        return result;
    }

}
