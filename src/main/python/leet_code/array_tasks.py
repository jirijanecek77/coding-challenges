from math import comb, gcd


def final_value_after_operations(operations: list[str]) -> int:
    return sum(map(lambda op: 1 if op in ["X++", "++X"] else -1, operations))


def test_final_value_after_operations():
    assert final_value_after_operations(["++X", "X++", "++X"]) == 3


def hasSameDigits(s: str) -> bool:
    if len(s) < 2:
        return False

    # nums = list(map(int, s))
    # while len(nums) > 2:
    #     nums = [(a + b) % 10 for a, b in pairwise(nums)]
    # return nums[0] == nums[1]
    s = [int(digits) for digits in s]
    n = len(s)
    # let's calculate all the relevant binomial coefficients:
    binomial_coeff = [comb(n - 2, i) for i in range(n - 1)]
    # calculating the final left and right digits
    left = sum([digit * coeff for digit, coeff in zip(s[:-1], binomial_coeff)]) % 10
    right = sum([digit * coeff for digit, coeff in zip(s[1:], binomial_coeff)]) % 10
    return left == right


def test_hasSameDigits():
    assert hasSameDigits("3902") == True
    assert hasSameDigits("34789") == False


def gcdOfStrings(str1: str, str2: str) -> str:
    n1 = len(str1)
    n2 = len(str2)

    n = gcd(n1, n2)
    prefix = str1[:n]
    for i in range(0, n1, n):
        if str1[i : i + n] != prefix:
            return ""

    for i in range(0, n2, n):
        if str2[i : i + n] != prefix:
            return ""

    return prefix


def test_gcdOfStrings():
    assert gcdOfStrings("ABABAB", "ABAB") == "AB"
    assert gcdOfStrings("AA", "A") == "A"
    assert gcdOfStrings("ABCD", "CD") == ""


def missingMultiple(nums: list[int], k: int) -> int:
    primitives = sorted({i for i in nums if i % k == 0})

    n = len(primitives) + 1
    for i, p in zip(range(1, n), primitives):
        if i * k != p:
            return i * k

    return n * k


def test_missingMultiple():
    assert (
        missingMultiple(
            [83, 96, 34, 56, 48, 30, 7, 14, 77, 66, 66, 66, 21, 17, 38, 7, 9], 7
        )
        == 28
    )
