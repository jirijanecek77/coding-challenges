# https://leetcode.com/problems/taking-maximum-energy-from-the-mystic-dungeon/description/?envType=daily-question&envId=2025-10-10
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
