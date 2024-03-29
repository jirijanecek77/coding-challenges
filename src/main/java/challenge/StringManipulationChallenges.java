package challenge;

import domain.Trie;
import utils.MathUtils;

import java.util.*;
import java.util.function.Function;
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

    public static int longestStringSubsequence(String s1, String s2) {
        //https://www.hackerrank.com/challenges/common-child/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=strings

        if (s1 == null || s2 == null) {
            throw new InputMismatchException("Both strings must be defined");
        }
        if (s1.length() != s2.length()) {
            throw new InputMismatchException("Strings must be of the same length.");
        }

        int N = s1.length();
        int[][] dp = new int[N + 1][N + 1];

        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= N; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[N][N];
    }

    public static char firstUniqueChar(String str) {

        Queue<Character> unique = new ArrayDeque<>();
        Set<Character> duplicates = new HashSet<>();

        for (char ch : str.toCharArray()) {
            if (!duplicates.contains(ch)) {
                if (unique.contains(ch)) {
                    unique.remove(ch);
                    duplicates.add(ch);
                } else {
                    unique.add(ch);
                }
            }
        }

        return unique.isEmpty() ? Character.MIN_VALUE : unique.peek();
    }

    public static String longestPalindrome(String s) {
        // https://leetcode.com/problems/longest-palindromic-substring/description/
        int n = s.length();
        if (n <= 1) {
            return s;
        }
        String maxPalindrome = "";
        for (int i = 0; i < n; i++) {
            maxPalindrome = maxPalindromeForRange(i, i, s, maxPalindrome);
            maxPalindrome = maxPalindromeForRange(i, i + 1, s, maxPalindrome);
        }
        return maxPalindrome;
    }

    private static String maxPalindromeForRange(int left, int right, String s, String maxPalindrome) {
        int maxLength = maxPalindrome.length();
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            if (right - left + 1 > maxLength) {
                maxPalindrome = s.substring(left, right + 1);
                maxLength = right - left + 1;
            }
            left--;
            right++;
        }
        return maxPalindrome;
    }


    public static List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Trie trie = new Trie();
        Arrays.stream(products).forEach(trie::insert);

        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < searchWord.length(); i++) {
            result.add(trie.findByPrefix(searchWord.substring(0, i + 1), 3));
        }

        return result;
    }

    public static String gcdOfStrings(String str1, String str2) {
        if (!(str1 + str2).equals(str2 + str1)) {
            return "";
        }
        return str1.substring(0, (int) MathUtils.gcd(str1.length(), str2.length()));
    }

    public static int compress(char[] chars) {
        int index = 0, left = 0, right = 1;

        while (right <= chars.length) {
            if (right == chars.length || chars[left] != chars[right]) {
                chars[index++] = chars[left];

                int length = right - left;
                if (length > 1) {
                    Stack<Integer> stack = new Stack<>();
                    while (length > 0) {
                        stack.push(length % 10);
                        length /= 10;
                    }

                    while (!stack.isEmpty()) {
                        chars[index++] = (char) (stack.pop() + '0');
                    }
                }
                left = right;
            }
            right++;
        }
        return index;
    }


    public static int partitionString(String s) {
        // abacaba

        int result = 1;
        Set<Character> mem = new HashSet<>();
        for (char ch : s.toCharArray()) {
            if (mem.contains(ch)) {
                result++;
                mem.clear();
            }
            mem.add(ch);
        }

        return result;
    }


    public static String removeStars(String s) {
        Stack<Character> stack = new Stack<>();
        for (char ch : s.toCharArray()) {
            if (ch == '*' && !stack.isEmpty()) {
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            char ch = stack.pop();
            result.insert(0, ch);
        }
        return result.toString();
    }

    public static int minSteps(String s, String t) {
        // https://leetcode.com/problems/minimum-number-of-steps-to-make-two-strings-anagram/description/?envType=daily-question&envId=2024-01-13

        Map<Integer, Long> counterS = s.chars().boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Integer, Long> counterT = t.chars().boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return counterT.entrySet().stream()
                .mapToInt(target -> {
                    long source = counterS.getOrDefault(target.getKey(), 0L);
                    return target.getValue() > source ? (int) (target.getValue() - source) : 0;
                })
                .sum();
    }
}