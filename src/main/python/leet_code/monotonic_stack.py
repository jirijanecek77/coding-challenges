# https://leetcode.com/problems/minimum-operations-to-convert-all-elements-to-zero/description/?envType=daily-question&envId=2025-11-10
from numpy.ma.core import max_val


def minOperations(nums: list[int]) -> int:
    stack = []
    ans = 0
    for num in nums:
        while stack and stack[-1] > num:
            stack.pop()
        if num and (not stack or stack[-1] != num):
            stack.append(num)
            ans += 1
    return ans


def test_minOperations():
    assert minOperations([3, 1, 2, 1]) == 3
    assert minOperations([5, 6, 7, 8, 9]) == 5
    assert minOperations([5, 3, 3, 6, 2, 1]) == 5
