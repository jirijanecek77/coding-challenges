import math


def binary_search(arr: list[int], target: int) -> int:
    left = 0
    right = len(arr) - 1
    while left <= right:
        mid = (left + right) // 2
        if arr[mid] >= target:
            right = mid - 1
        else:
            left = mid + 1
    return left


# https://leetcode.com/problems/successful-pairs-of-spells-and-potions/?envType=daily-question&envId=2025-10-08
def successful_pairs(spells: list[int], potions: list[int], success: int) -> list[int]:
    sorted_potions = sorted(potions)
    n = len(potions)

    # binary search for each spell
    return list(
        map(
            lambda spell: n - binary_search(sorted_potions, math.ceil(success / spell)),
            spells,
        )
    )


def test_successful_pairs():
    pairs = successful_pairs(spells=[3, 1, 2], potions=[8, 5, 8], success=16)
    assert pairs == [2, 0, 2]


def searchInsert(nums: list[int], target: int) -> int:
    left = 0
    right = len(nums) - 1
    while left <= right:
        mid = (left + right) // 2
        if nums[mid] >= target:
            right = mid - 1
        else:
            left = mid + 1
    return left


def test_searchInsert():
    assert searchInsert([1, 3, 5, 6], 5) == 2
    assert searchInsert([1, 3, 5, 6], 8) == 4


# https://leetcode.com/problems/maximize-the-minimum-powered-city/description
def maxPower(stations: list[int], r: int, k: int) -> int:
    # https://leetcode.com/problems/maximize-the-minimum-powered-city/solutions/7331763/easiest-explanation-youll-read-today-by-kd3ok/?envType=daily-question&envId=2025-11-07
    n = len(stations)

    diff = [0] * (n + 1)

    # Precompute the influence range of existing stations
    for i in range(n):
        diff[max(0, i - r)] += stations[i]
        diff[min(n, i + r + 1)] -= stations[i]

    # Check if we can achieve at least 'target' power in every city
    def check(target: int) -> bool:
        diff_copy = diff[:]
        prefix = 0
        available = k

        for i in range(n):
            prefix += diff_copy[i]
            if prefix < target:
                need = target - prefix
                if need > available:
                    return False
                available -= need
                prefix += need
                diff_copy[min(n, i + r * 2 + 1)] -= need
        return True

    left, right = 0, sum(stations) + k
    result = 0
    while left <= right:
        mid = (left + right) // 2
        if check(mid):
            result = mid
            left = mid + 1
        else:
            right = mid - 1
    return result


def test_maxPower():
    assert maxPower(stations=[1, 2, 4, 5, 0], r=1, k=2) == 5


def countOperations(num1: int, num2: int) -> int:
    result = 0
    while num1 != 0 and num2 != 0:
        if num1 >= num2:
            num1 -= num2
        else:
            num2 -= num1
        result += 1

    return result


def test_countOperations():
    assert countOperations(num1=2, num2=3) == 3
