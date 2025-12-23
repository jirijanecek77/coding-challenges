import math


def calculate_prefix_sum(nums: list[int]) -> list[int]:
    n = len(nums)
    if n == 0:
        return []

    result = [0] * n
    result[0] = nums[0]
    for i in range(1, n):
        result[i] = result[i - 1] + nums[i]
    return result


def pivot_index(nums: list[int]) -> int:
    # https://leetcode.com/problems/find-pivot-index
    prefix_sum = calculate_prefix_sum(nums)
    prefix_sum.insert(0, 0)
    prefix_sum.append(prefix_sum[-1])

    for i in range(1, len(prefix_sum) - 1):
        if prefix_sum[i - 1] == prefix_sum[-1] - prefix_sum[i]:
            return i - 1

    return -1


def sum_odd_length_subarrays(arr: list[int]) -> int:
    # https://leetcode.com/problems/sum-of-all-odd-length-subarrays
    prefix_sum = calculate_prefix_sum(arr)
    prefix_sum.insert(0, 0)
    prefix_sum.append(prefix_sum[-1])

    n = len(prefix_sum)
    result = 0
    for i in range(1, n - 1, 2):
        result += sum(prefix_sum[j] - prefix_sum[j - i] for j in range(i, n - 1))
    return result


if __name__ == "__main__":
    print(sum_odd_length_subarrays([1, 4, 2, 5, 3]))


def maxSubarraySum(nums: list[int], k: int) -> int:
    prefix_sum = 0
    sub_max = -math.inf
    min_so_far = [math.inf] * k
    min_so_far[-1] = 0

    for i, num in enumerate(nums):
        prefix_sum += num
        sub_max = max(sub_max, prefix_sum - min_so_far[i % k])
        min_so_far[i % k] = min(min_so_far[i % k], prefix_sum)

    return sub_max


def test_maxSubarraySum():
    assert maxSubarraySum(nums=[-1, -2, -3, -4, -5], k=4) == -10


def minOperations(boxes: str) -> list[int]:
    n = len(boxes)
    boxes = list(map(int, boxes))
    res = [0] * n

    acc = prefix_sum = 0
    for i in range(n):
        res[i] = acc
        prefix_sum += boxes[i]
        acc += prefix_sum

    acc = sufix_sum = 0
    for i in reversed(range(n)):
        res[i] += acc
        sufix_sum += boxes[i]
        acc += sufix_sum

    return res


def test_minOperations():
    assert minOperations(boxes="001011") == [11, 8, 5, 4, 3, 4]
    assert minOperations(boxes="110") == [1, 1, 3]
