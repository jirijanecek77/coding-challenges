from collections import defaultdict


# https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
def longest_substring_without_repeating_characters(s: str) -> int:
    # standard sliding window approach, keep track of unique characters
    left = 0
    s = list(s)
    n = len(s)
    res = 0
    window = set()
    for right in range(n):
        while s[right] in window:
            window.remove(s[left])
            left += 1
        window.add(s[right])
        res = max(res, len(window))

    return res


def test_longest_substring_without_repeating_characters():
    assert longest_substring_without_repeating_characters(s="abcabcbb") == 3


# https://leetcode.com/problems/longest-repeating-character-replacement/
def characterReplacement(s: str, k: int) -> int:
    # sliding window approach, keep track of max frequency within all characters
    # window size - most frequent character must be less than k (changes)
    left = max_freq = res = 0
    s = list(s)
    n = len(s)
    window = defaultdict(int)
    for right in range(n):
        window[s[right]] += 1

        max_freq = max(max_freq, window[s[right]])
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
    n = len(s)
    left = 0
    ones = 0
    ans = ""

    for right in range(n):
        if s[right] == "1":
            ones += 1
        while left < n and (s[left] == "0" or ones > k):
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
