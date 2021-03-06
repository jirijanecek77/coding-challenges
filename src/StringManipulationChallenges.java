import java.util.*;
import java.util.stream.Collectors;

public class StringManipulationChallenges {


    static String isValidSherlock(String s) {
        // https://www.hackerrank.com/challenges/sherlock-and-valid-string/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=strings&h_r=next-challenge&h_v=zen

        Map<Character, Integer> data = new HashMap<>(); // char to occurrences map
        Map<Integer, Integer> freqs = new HashMap<>(); // occurrences

        for (char ch : s.toCharArray()) {
            int freq = data.getOrDefault(ch, 0);

            freqs.put(freq, freqs.getOrDefault(freq, 0) - 1);

            data.put(ch, freq + 1);
            freqs.put(freq + 1, freqs.getOrDefault(freq + 1, 0) + 1);
        }

        List<Map.Entry<Integer, Integer>> results = freqs.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .collect(Collectors.toList());

        long size = results.size();
        if (size == 1) {
            return "YES";
        } else if (size == 2) {
            Optional<Map.Entry<Integer, Integer>> valueOne = results.stream()
                    .filter(e -> e.getValue() == 1)
                    .findFirst();

            if (!valueOne.isPresent()) return "NO";
            freqs.remove(valueOne.get().getKey());

            return Math.abs(results.stream()
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .get()
                    - valueOne.get().getKey()) == 1
                    || valueOne
                    .map(e -> e.getKey() * e.getValue()).get() == 1 ? "YES" : "NO";
        }
        return "NO";
    }

    public static int read_and_find_repetitions(String short_s, String long_s) {
        int result = 0, shortLen = short_s.length();
        if (shortLen > long_s.length()) return 0;

        for (int longIdx = 0, shortIdx = 0; longIdx <= long_s.length(); longIdx++, shortIdx++) {
            if (longIdx == long_s.length() || long_s.charAt(longIdx) != short_s.charAt(shortIdx % shortLen)) {
                if (shortIdx % shortLen == 0) {
                    result = Math.max(result, shortIdx / shortLen);
                }
                shortIdx = 0;
            }
        }

        return result;
    }

    public static int count_invalid_boxes(List<List<String>> box_template_list) {
        return box_template_list.stream()
                .mapToInt(e -> {
                    String box = e.get(0);
                    String template = e.get(1);

                    if (!compareUnordered(box, template)) return 1;
                    return 0;
                })
                .sum();
    }

    private static boolean compareUnordered(String box, String template) {
        if (box.length() != template.length()) return false;

        char[] boxItems = box.toCharArray();
        Arrays.sort(boxItems);
        char[] templateItems = template.toCharArray();
        Arrays.sort(templateItems);

        return Arrays.equals(boxItems, templateItems);
    }

}