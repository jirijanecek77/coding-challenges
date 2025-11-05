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


# https://leetcode.com/problems/smallest-missing-non-negative-integer-after-operations/?envType=daily-question&envId=2025-10-16
def findSmallestInteger(nums: list[int], value: int) -> int:
    arr = list(range(value))
    for num in nums:
        arr[num % value] += value
    return min(arr)


def test_findSmallestInteger():
    assert (
        findSmallestInteger(
            nums=[0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1], value=2
        )
        == 15
    )
    assert findSmallestInteger(nums=[3, 0, 3, 2, 4, 2, 1, 1, 0, 4], value=5) == 10
    assert findSmallestInteger(nums=[1, 3, 5, 7], value=2) == 0
    assert findSmallestInteger(nums=[1, -10, 7, 13, 6, 8], value=5) == 4
    assert findSmallestInteger(nums=[1, -10, 7, 13, 6, 8], value=7) == 2


# https://leetcode.com/problems/maximum-number-of-distinct-elements-after-operations/description/?envType=daily-question&envId=2025-10-18
def maxDistinctElements(nums: list[int], k: int) -> int:
    nums.sort()
    result = 0
    last_picked = nums[0] - k - 1
    for num in nums:
        lower_bound = num - k
        upper_bound = num + k
        if last_picked < lower_bound:
            last_picked = lower_bound
        else:
            last_picked += 1
        if last_picked <= upper_bound:
            result += 1
        else:
            last_picked -= 1
    return result


def test_maxDistinctElements():
    assert maxDistinctElements(nums=[4, 4, 4, 4], k=1) == 3
    assert maxDistinctElements(nums=[1, 2, 2, 3, 3, 4], k=2) == 6


def findXSum(nums: list[int], k: int, x: int) -> list[int]:
    return [
        sum(
            k * v
            for k, v in sorted(
                Counter(nums[i : i + k]).items(),
                key=lambda x: (x[1], x[0]),
                reverse=True,
            )[:x]
        )
        for i in range(len(nums) - k + 1)
    ]


def test_findXSum():
    assert findXSum(nums=[3, 8, 7, 8, 7, 5], k=2, x=2) == [11, 15, 15, 15, 12]
    assert findXSum(nums=[1, 1, 2, 2, 3, 4, 2, 3], k=6, x=2) == [6, 10, 12]


def frequencySort(s: str) -> str:
    freq = sorted(Counter(s).items(), key=lambda x: (x[1], x[0]), reverse=True)
    return "".join(ch * f for ch, f in freq)


def test_frequencySort():
    assert frequencySort("tree") == "eetr"
