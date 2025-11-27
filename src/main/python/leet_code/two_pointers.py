import operator
from itertools import accumulate


def reverseVowels(s: str) -> str:
    left = 0
    right = len(s) - 1
    vowels = {"a", "e", "i", "o", "u", "A", "E", "I", "O", "U"}
    s = list(s)
    while left < right:
        if s[left] not in vowels:
            left += 1
        elif s[right] not in vowels:
            right -= 1
        else:
            s[left], s[right] = s[right], s[left]
            left += 1
            right -= 1

    return "".join(s)


def test_reverseVowels():
    assert reverseVowels("hello") == "holle"
    assert reverseVowels("leetcode") == "leotcede"


def prisonAfterNDays(cells: list[int], n: int) -> list[int]:

    def calc(arr: list[int]) -> list[int]:
        new_state = [0] * len(arr)
        for i in range(1, len(arr) - 1):
            new_state[i] = 1 if arr[i - 1] == arr[i + 1] else 0
        return new_state

    tortoise = cells
    hare = calc(tortoise)

    before_cycle_counter = 0
    while hare != tortoise:
        tortoise = calc(tortoise)
        hare = calc(calc(hare))
        before_cycle_counter += 1

    cycle_counter = 1
    tortoise = calc(tortoise)

    while hare != tortoise:
        tortoise = calc(tortoise)
        cycle_counter += 1

    remains = (n - before_cycle_counter) % cycle_counter
    for i in range(remains):
        tortoise = calc(tortoise)

    return tortoise


def test_prisonAfterNDays():
    assert prisonAfterNDays(cells=[0, 1, 0, 1, 1, 0, 0, 1], n=7) == [
        0,
        0,
        1,
        1,
        0,
        0,
        0,
        0,
    ]
    assert prisonAfterNDays(cells=[1, 0, 0, 1, 0, 0, 1, 0], n=1000000000) == [
        0,
        0,
        1,
        1,
        1,
        1,
        1,
        0,
    ]


def balancedStringSplit(s: str) -> int:
    prefix_sum = list(accumulate(map(lambda x: 1 if x == "L" else -1, s)))
    return prefix_sum.count(0)


def test_balancedStringSplit():
    assert balancedStringSplit(s="RLRRLLRLRL") == 4


def product_of_array_except_self(nums: list[int]) -> list[int]:
    n = len(nums)
    acc = 1
    result = list(accumulate(nums[: n - 1], operator.mul, initial=acc))
    for i in reversed(range(n)):
        result[i] *= acc
        acc *= nums[i]

    return result


def test_product_of_array_except_self():
    assert product_of_array_except_self(nums=[1, 2, 3, 4]) == [24, 12, 8, 6]
    assert product_of_array_except_self(nums=[-1, 1, 0, -3, 3]) == [0, 0, 9, 0, 0]


def rangeAddQueries(n: int, queries: list[list[int]]) -> list[list[int]]:
    prefix_sum = [[0] * n for _ in range(n)]
    for query in queries:
        row1, col1, row2, col2 = query
        for row in range(row1, row2 + 1):
            prefix_sum[row][col1] += 1
            if col2 + 1 < n:
                prefix_sum[row][col2 + 1] -= 1

    return [list(accumulate(row)) for row in prefix_sum]


def test_rangeAddQueries():
    assert rangeAddQueries(n=3, queries=[[1, 1, 2, 2], [0, 0, 1, 1]]) == [
        [1, 1, 0],
        [1, 2, 1],
        [0, 1, 1],
    ]
