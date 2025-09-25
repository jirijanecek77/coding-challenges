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
