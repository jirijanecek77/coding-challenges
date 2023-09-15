package challenge;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DictionariesChallenges {

    static boolean checkMagazine(String[] magazine, String[] note) {
        //https://www.hackerrank.com/challenges/ctci-ransom-note/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps
        List<String> magazineList = new LinkedList<>(Arrays.asList(magazine));

        for (String n : note) {
            if (!magazineList.remove(n)) return false;
        }

        return true;
    }

    static String twoStrings(String s1, String s2) {
        // https://www.hackerrank.com/challenges/two-strings/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps
        Set<Character> set1 = s1.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
        Set<Character> set2 = s2.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());

        return set1.removeAll(set2) ? "YES" : "NO";
    }

    static int sherlockAndAnagrams(String s) {
        //https://www.hackerrank.com/challenges/sherlock-and-anagrams/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps
        int result = 0;

        for (int len = 1; len < s.length(); len++) {
            for (int i = 0; i < s.length() - len; i++) {
                String sub1 = s.substring(i, i + len);
                for (int j = i + 1; j < s.length() - len + 1; j++) {
                    String sub2 = s.substring(j, j + len);
                    result += isAnagram(sub1, sub2) ? 1 : 0;
                }
            }
        }
        return result;
    }

    private static boolean isAnagram(String pattern, String word) {
        List<Character> buffer = pattern.chars().mapToObj(e -> (char) e).collect(toList());

        for (char ch : word.toCharArray()) {
            buffer.remove(Character.valueOf(ch));
        }

        return buffer.isEmpty();
    }

    static long countTriplets(List<Long> arr, long r) {
        Map<Long, Long> right = new HashMap<>();
        for (Long number : arr) {
            Long item = right.get(number);
            right.put(number, item == null ? 1L : item + 1L);
        }

        Map<Long, Long> left = new HashMap<>();

        long result = 0L;
        for (long i : arr) {
            long j = i / r;
            long k = i * r;

            right.put(i, right.get(i) - 1L);
            if (left.containsKey(j) && left.get(j) > 0 &&
                    right.containsKey(k) && right.get(k) > 0
                    && i % r == 0) {
                result += left.get(j) * right.get(k);
            }

            if (left.containsKey(i)) {
                left.put(i, left.get(i) + 1L);
            } else {
                left.put(i, 1L);
            }
        }

        return result;
    }

    static List<Integer> freqQuery(List<List<Integer>> queries) {
        List<Integer> output = new ArrayList<>();

        Map<Integer, Integer> data = new HashMap<>(); // number to occurrences map
        Map<Integer, Integer> freqs = new HashMap<>(); // occurrences
        for (List<Integer> query : queries) {

            Integer value = query.get(1);
            int freq = data.getOrDefault(value, 0);
            switch (query.get(0)) {
                case 1: //insert
                    freqs.put(freq, freqs.getOrDefault(freq, 0) - 1);
                    data.put(value, freq + 1);
                    freqs.put(freq + 1, freqs.getOrDefault(freq + 1, 0) + 1);
                    break;
                case 2: //delete
                    if (freq > 0) {
                        freqs.put(freq - 1, freqs.getOrDefault(freq - 1, 0) + 1);
                        data.put(value, freq - 1);
                        freqs.put(freq, freqs.getOrDefault(freq, 0) - 1);
                    }
                    break;
                case 3: //query
                    output.add(freqs.getOrDefault(value, 0) > 0 ? 1 : 0);
            }

        }

        return output;
    }
}
