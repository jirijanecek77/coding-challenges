from collections import Counter
from itertools import groupby, pairwise


def removeAnagrams(words: list[str]) -> list[str]:
    return [next(g) for _, g in groupby(words, Counter)]


def test_removeAnagrams():
    assert removeAnagrams(["abba", "baba", "bbaa", "cd", "cd"]) == ["abba", "cd"]


def hasIncreasingSubarrays(nums: list[int], k: int) -> bool:
    counter = 1
    for a, b in pairwise(nums):
        if a >= b:
            if counter < k:
                counter = 0
            else:
                counter = -k
        counter += 1
        if counter == 0 or counter == 2 * k:
            return True
    return False


def test_hasIncreasingSubarrays():
    assert hasIncreasingSubarrays(nums=[-15, 19], k=1) == True
    assert hasIncreasingSubarrays(nums=[2, 5, 7, 8, 9, 2, 3, 4, 3, 1], k=3) == True
    assert hasIncreasingSubarrays(nums=[1, 2, 3, 4, 4, 4, 4, 5, 6, 7], k=5) == False
    assert hasIncreasingSubarrays(nums=[5, 8, -2, -1], k=2) == True


# https://leetcode.com/problems/adjacent-increasing-subarrays-detection-ii/?envType=daily-question&envId=2025-10-15
def maxIncreasingSubarrays(nums: list[int]) -> int:
    curr_counter = 1
    last_counter = 0
    result = 0
    for a, b in pairwise(nums):
        if b > a:
            curr_counter += 1
        else:
            last_counter = curr_counter
            curr_counter = 1

        candidate = max(curr_counter // 2, min(last_counter, curr_counter))
        result = max(result, candidate)
    return result


def test_maxIncreasingSubarrays():
    assert maxIncreasingSubarrays(nums=[5, 8, -2, -1]) == 2
    assert maxIncreasingSubarrays(nums=[1, 2, 3, 4, 4, 4, 4, 5, 6, 7]) == 2
    assert maxIncreasingSubarrays(nums=[-15, 19]) == 1
    assert maxIncreasingSubarrays(nums=[2, 5, 7, 8, 9, 2, 3, 4, 3, 1]) == 3
