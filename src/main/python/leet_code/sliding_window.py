from collections import defaultdict


# https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
def longest_substring_without_repeating_characters(s: str) -> int:
    # standard sliding window approach, keep track of unique characters and its position
    left = 0
    res = 0
    seen = {}
    for right, ch in enumerate(s):
        if ch in seen:
            left = max(left, seen[ch] + 1)
        seen[ch] = right
        res = max(res, right - left + 1)

    return res


def test_longest_substring_without_repeating_characters():
    assert longest_substring_without_repeating_characters(s="abba") == 2
    assert longest_substring_without_repeating_characters(s="abcabcbb") == 3
    assert longest_substring_without_repeating_characters(s="bbbbb") == 1
    assert longest_substring_without_repeating_characters(s="pwwkew") == 3


# https://leetcode.com/problems/longest-repeating-character-replacement/
def characterReplacement(s: str, k: int) -> int:
    # sliding window approach, keep track of max frequency within all characters
    # window size - most frequent character must be less than k (changes)
    left = max_freq = res = 0
    window = defaultdict(int)
    for right, ch in enumerate(s):
        window[ch] += 1

        max_freq = max(max_freq, window[ch])
        while right - left + 1 - max_freq > k:
            window[s[left]] -= 1
            left += 1
        res = max(res, right - left + 1)
    return res


def test_characterReplacement():
    assert characterReplacement(s="ABBBBACAADAAEAAB", k=2) == 8
    assert characterReplacement(s="ABAB", k=2) == 4
    assert characterReplacement(s="AABABBA", k=1) == 4


def shortestBeautifulSubstring(s: str, k: int) -> str:
    # https://leetcode.com/problems/shortest-and-lexicographically-smallest-beautiful-string/description/?envType=daily-question&envId=2026-08-26
    left = 0
    ones = 0
    ans = ""

    for right, ch in enumerate(s):
        if ch == "1":
            ones += 1
        while left < len(s) and (s[left] == "0" or ones > k):
            if s[left] == "1":
                ones -= 1
            left += 1

        if ones == k:
            candidate = s[left : right + 1]
            if (
                not ans
                or len(candidate) < len(ans)
                or (len(candidate) == len(ans) and candidate < ans)
            ):
                ans = candidate

    return ans


def test_shortestBeautifulSubstring():
    assert shortestBeautifulSubstring(s="100011001", k=3) == "11001"
    assert shortestBeautifulSubstring(s="100011001", k=1) == "1"
    assert shortestBeautifulSubstring(s="0000", k=1) == ""
