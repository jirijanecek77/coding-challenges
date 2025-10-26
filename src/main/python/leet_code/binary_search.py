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
