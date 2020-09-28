import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

}