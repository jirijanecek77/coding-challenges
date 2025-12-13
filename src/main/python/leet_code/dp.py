# https://leetcode.com/problems/taking-maximum-energy-from-the-mystic-dungeon/description/?envType=daily-question&envId=2025-10-10
from collections import Counter
from functools import lru_cache


def maximum_energy(energy: list[int], k: int) -> int:
    n = len(energy)
    i = n - k - 1
    while i >= 0:
        energy[i] += energy[i + k]
        i -= 1
    return max(energy)


def test_maximum_energy():
    assert maximum_energy([5, 2, -10, -5, 1], 3) == 3
    assert maximum_energy([-2, -3, -1], 2) == -1


# https://leetcode.com/problems/maximum-total-damage-with-spell-casting/description/?envType=daily-question&envId=2025-10-11
def maximumTotalDamage(power: list[int]) -> int:
    mp = Counter(power)
    vec = sorted((k, k * v) for k, v in mp.items())
    n = len(vec)

    dp = [0] * (n + 1)
    for i in range(n - 1, -1, -1):
        not_take = dp[i + 1]
        take = vec[i][1]
        j = i + 1
        while j < n and vec[j][0] - vec[i][0] <= 2:
            j += 1
        take += dp[j]
        dp[i] = max(take, not_take)

    return dp[0]


def test_maximumTotalDamage():
    assert maximumTotalDamage([1, 1, 3, 4]) == 6
    assert maximumTotalDamage([7, 1, 6, 6]) == 13


def deleteAndEarn(nums: list[int]) -> int:
    if not nums:
        return 0

    freq = [0] * (max(nums) + 1)
    for n in nums:
        freq[n] += n

    dp = [0] * len(freq)
    dp[1] = freq[1]
    for i in range(2, len(freq)):
        dp[i] = max(freq[i] + dp[i - 2], dp[i - 1])

    return dp[-1]


def test_deleteAndEarn():
    assert deleteAndEarn([2, 2, 3, 3, 3, 4]) == 9
    assert deleteAndEarn([3, 4, 2]) == 6


def longestPalindrome(s: str) -> str:
    if len(s) <= 1:
        return s

    max_len = 1
    max_str = s[0]
    dp = [[False for _ in range(len(s))] for _ in range(len(s))]
    for i in range(len(s)):
        dp[i][i] = True
        for j in range(i):
            if s[j] == s[i] and (i - j <= 2 or dp[j + 1][i - 1]):
                dp[j][i] = True
                if i - j + 1 > max_len:
                    max_len = i - j + 1
                    max_str = s[j : i + 1]
    return max_str


def test_longestPalindrome():
    assert longestPalindrome("babad") == "bab"
    assert longestPalindrome("cbbd") == "bb"
    assert longestPalindrome("ahoj") == "a"


def findMaxForm(strs: list[str], m: int, n: int) -> int:
    dp = {(0, 0): 0}

    for s in strs:
        ones = s.count("1")
        zeroes = s.count("0")
        newdp = {}

        for (prevZeroes, prevOnes), val in dp.items():
            newZeroes, newOnes = prevZeroes + zeroes, prevOnes + ones
            if newZeroes <= m and newOnes <= n:
                if (newZeroes, newOnes) not in dp or dp[(newZeroes, newOnes)] < val + 1:
                    newdp[(newZeroes, newOnes)] = val + 1

        dp.update(newdp)

    return max(dp.values())


def test_findMaxForm():
    assert findMaxForm(["10", "0001", "111001", "1", "0"], 5, 3) == 4


@lru_cache
def knapsack_recursive(
    w: int, weights: tuple[int, ...], prices: tuple[int, ...], i: int
):
    # Base Case
    if i == 0 or w == 0:
        return 0

    # If weight of the nth item is
    # more than Knapsack of capacity W,
    # then this item cannot be included
    if weights[i - 1] > w:
        return knapsack_recursive(w, weights, prices, i - 1)

    # return the maximum of two cases:
    # (1) nth item included
    # (2) not included
    return max(
        prices[i - 1] + knapsack_recursive(w - weights[i - 1], weights, prices, i - 1),
        knapsack_recursive(w, weights, prices, i - 1),
    )


def knapsack_2d_dp(
    capacity: int, weights: tuple[int, ...], prices: tuple[int, ...], n: int
) -> int:
    dp = [[0 for _ in range(capacity + 1)] for _ in range(n + 1)]

    for i in range(1, n + 1):
        for w in range(1, capacity + 1):
            if weights[i - 1] > w:
                dp[i][w] = dp[i - 1][w]
            else:
                dp[i][w] = max(
                    prices[i - 1] + dp[i - 1][w - weights[i - 1]], dp[i - 1][w]
                )

    return dp[-1][-1]


def knapsack_1d_dp(
    capacity: int, weights: tuple[int, ...], prices: tuple[int, ...], n: int
) -> int:
    dp = [0 for i in range(capacity + 1)]

    for i in range(1, n + 1):

        # Starting from back,
        # so that we also have data of
        # previous computation when taking i-1 items
        for w in range(capacity, 0, -1):
            if weights[i - 1] <= w:
                # Finding the maximum value
                dp[w] = max(dp[w], dp[w - weights[i - 1]] + prices[i - 1])
    return dp[-1]


def knapsack_problem(capacity: int, weights: list[int], prices: list[int]) -> int:
    return knapsack_1d_dp(capacity, tuple(weights), tuple(prices), len(prices))


def test_knapsack_problem():
    # assert knapsack_problem(50, [10, 20, 30], [60, 100, 120]) == 220
    assert knapsack_problem(5, [1, 2, 3, 4], [5, 2, 3, 2]) == 8
    # assert knapsack_problem(10, [1, 2, 3, 4], [1, 2, 3, 5]) == 11


# https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/description/
def numRollsToTarget(n: int, k: int, target: int) -> int:
    mod = 10**9 + 7

    prev = [0] * (target + 1)
    curr = [0] * (target + 1)

    prev[0] = 1

    for i in range(1, n + 1):
        for j in range(1, target + 1):
            ans = 0
            for x in range(1, k + 1):
                if j - x >= 0:
                    ans += prev[j - x] % mod
            curr[j] = ans
        prev = curr[:]
    return int(prev[-1] % mod)


def test_numRollsToTarget():
    assert numRollsToTarget(n=30, k=30, target=500) == 222616187


# https://leetcode.com/problems/paths-in-matrix-whose-sum-is-divisible-by-k/description/?envType=daily-question&envId=2025-11-26
def numberOfPaths(grid: list[list[int]], k: int) -> int:
    memo = {}

    def dfs(row: int, col: int, reminder: int) -> int:
        rows = len(grid)
        cols = len(grid[0])
        if row > rows - 1 or col > cols - 1:
            return 0

        if (row, col, reminder) in memo:
            return memo[(row, col, reminder)]

        curr_reminder = (reminder + grid[row][col]) % k
        if row == rows - 1 and col == cols - 1:
            res = 1 if curr_reminder == 0 else 0
            memo[(row, col, reminder)] = res
            return res

        res = (dfs(row + 1, col, curr_reminder) + dfs(row, col + 1, curr_reminder)) % (
            10**9 + 7
        )
        memo[(row, col, reminder)] = res
        return res

    return dfs(0, 0, 0)


def numberOfPaths_dp(grid: list[list[int]], k: int) -> int:
    rows, cols = len(grid), len(grid[0])

    dp = [[[0] * k for _ in range(cols + 1)] for _ in range(rows + 1)]
    dp[1][1][grid[0][0] % k] = 1
    for row in range(rows):
        for col in range(cols):
            if row == 0 and col == 0:
                continue
            val = grid[row][col] % k
            for idx in range(k):
                k_idx = (k + idx - val) % k
                res = dp[row + 1][col][k_idx] + dp[row][col + 1][k_idx]
                dp[row + 1][col + 1][idx] = res % 1000000007
    return dp[-1][-1][0]


def test_numberOfPaths():
    assert numberOfPaths_dp(grid=[[5, 2, 4], [3, 0, 5], [0, 7, 2]], k=3) == 2


def uniquePaths(m: int, n: int) -> int:
    memo = {}

    def dfs(x, y) -> int:
        if (x, y) in memo:
            return memo[(x, y)]
        if x >= m or y >= n:
            memo[(x, y)] = 0
        elif x == m - 1 and y == n - 1:
            memo[(x, y)] = 1
        else:
            memo[(x, y)] = dfs(x + 1, y) + dfs(x, y + 1)
        return memo[(x, y)]

    return dfs(0, 0)


def uniquePaths_bottom_up(m: int, n: int) -> int:
    dp = [[1] * n for _ in range(m)]

    for i in range(1, m):
        for j in range(1, n):
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
        print(dp)

    return dp[-1][-1]


def test_uniquePaths():
    assert uniquePaths(3, 7) == 28
