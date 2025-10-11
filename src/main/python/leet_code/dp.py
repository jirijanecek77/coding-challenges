# https://leetcode.com/problems/taking-maximum-energy-from-the-mystic-dungeon/description/?envType=daily-question&envId=2025-10-10
from collections import Counter


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
