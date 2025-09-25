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
    # https://leetcode.com/problems/find-pivot-index/description
    prefix_sum = calculate_prefix_sum(nums)
    prefix_sum.insert(0, 0)
    prefix_sum.append(prefix_sum[-1])

    for i in range(1, len(prefix_sum) - 1):
        if prefix_sum[i - 1] == prefix_sum[-1] - prefix_sum[i]:
            return i - 1

    return -1
