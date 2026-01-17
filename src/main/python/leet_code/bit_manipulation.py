import math


def is_power_of_four(n: int) -> bool:
    return n > 0 and (n & (n - 1)) == 0 and n % 3 == 1


def test_is_power_of_four():
    assert is_power_of_four(16)
    assert not is_power_of_four(8)
    assert not is_power_of_four(15)


def reverse(x: int) -> int:
    res = 0
    while x > 0:
        x, y = divmod(x, 10)
        res = res * 10 + y
    return res


def test_reverse():
    assert reverse(123) == 321


def prefixesDivBy5(nums: list[int]) -> list[bool]:
    result = []
    acc = 0
    for i in range(len(nums)):
        acc = (nums[i] + (acc << 1)) % 5
        print(acc)
        result.append(acc == 0)

    return result


def test_prefixesDivBy5():
    assert prefixesDivBy5(nums=[1, 1, 0, 0, 0, 1, 0, 0, 1]) == [
        False,
        False,
        False,
        False,
        False,
        False,
        False,
        False,
        False,
    ]


# https://leetcode.com/problems/number-of-1-bits/description/
def hammingWeight(n: int) -> int:
    res = 0
    while n > 0:
        n &= n - 1
        res += 1

    return res


def test_hammingWeight():
    assert hammingWeight(11) == 3


def bestClosingTime(customers: str) -> int:
    customers_binary = 0
    for i in map(lambda c: 1 if c == "Y" else 0, customers):
        customers_binary += i
        customers_binary <<= 1
    customers_binary >>= 1

    opened_binary = 0
    res = 0
    min_penalty = math.inf
    n = len(customers)
    for i in range(n + 1):
        penalty = (customers_binary ^ opened_binary).bit_count()
        if penalty < min_penalty:
            min_penalty = penalty
            res = i
        opened_binary += 2 ** (n - i - 1)

    return res


def test_bestClosingTime():
    assert bestClosingTime(customers="YYYY") == 4
    assert bestClosingTime(customers="YYNY") == 2
