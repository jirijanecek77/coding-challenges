# https://leetcode.com/problems/maximum-units-on-a-truck/description/
from functools import lru_cache


def maximum_units(boxTypes: list[list[int]], truckSize: int) -> int:
    boxTypes.sort(key=lambda x: x[1], reverse=True)
    units = 0

    for boxes, unitsPerBox in boxTypes:
        take = min(truckSize, boxes)
        units += take * unitsPerBox
        truckSize -= take
        if truckSize == 0:
            break

    return units


def test_maximum_units():
    assert (
        maximum_units(
            boxTypes=[
                [2, 1],
                [4, 4],
                [3, 1],
                [4, 1],
                [2, 4],
                [3, 4],
                [1, 3],
                [4, 3],
                [5, 3],
                [5, 3],
            ],
            truckSize=13,
        )
    ) == 48


# greedy solution - finds minimum number of coins needed to make amount - not always find optimal solution
def greedy_coins(coins: list[int], amount: int) -> int:
    coins.sort()
    result = 0
    while amount > 0:
        coin = coins.pop()
        take = amount // coin
        result += take
        amount -= take * coin
    return result


# recursive solution with memoization - finds minimum number of coins needed to make amount
def recursive_coins(coins: list[int], amount: int) -> int:
    @lru_cache
    def solve(remainder: int) -> int:
        if remainder < 0:
            return float("inf")
        if remainder == 0:
            return 0
        return min(1 + solve(remainder - coin) for coin in coins)

    return solve(amount)


# dynamic programming solution - finds minimum number of coins needed to make amount
def dp_coins(coins: list[int], amount: int) -> int:
    dp = [float("inf") for _ in range(amount + 1)]
    dp[0] = 0
    for coin in coins:
        for i in range(coin, amount + 1):
            dp[i] = min(dp[i], dp[i - coin] + 1)
    return dp[amount]


def test_coins():
    assert greedy_coins([1, 3, 4], 6) == 3
    assert greedy_coins([1, 3, 5, 10, 20, 50, 100, 200], 520) == 4

    assert recursive_coins([1, 3, 4], 6) == 2

    assert dp_coins([1, 3, 4], 6) == 2
    assert dp_coins([1, 3, 5, 10, 20, 50, 100, 200], 520) == 4


# https://leetcode.com/problems/minimum-time-to-make-rope-colorful/?envType=daily-question&envId=2025-11-03
def minCost(colors: str, neededTime: list[int]) -> int:
    result = 0
    curr_max = 0
    for i, time in enumerate(neededTime):
        if i > 0 and colors[i] != colors[i - 1]:
            curr_max = 0
        result += min(curr_max, time)
        curr_max = max(curr_max, time)

    return result


def test_minCost():
    assert minCost(colors="abaac", neededTime=[1, 2, 3, 4, 5]) == 3
