from itertools import combinations


def fractionToDecimal(numerator: int, denominator: int) -> str:
    if numerator == 0:
        return "0"

    res = []

    if (numerator < 0) ^ (denominator < 0):
        res.append("-")
    numerator = abs(numerator)
    denominator = abs(denominator)

    num, rem = divmod(numerator, denominator)
    res.append(str(num))
    if rem == 0:
        return "".join(res)

    res.append(".")
    seen = {}
    while rem:
        if rem in seen:
            index = seen[rem]
            return f'{"".join(res[:index])}({"".join(res[index:])})'
        seen[rem] = len(res)

        rem *= 10
        num, rem = divmod(rem, denominator)
        res.append(str(num))

    return "".join(res)


def test_fractionToDecimal():
    assert fractionToDecimal(numerator=-50, denominator=8) == "-6.25"
    assert fractionToDecimal(numerator=22, denominator=7) == "3.(142857)"
    assert fractionToDecimal(numerator=1, denominator=6) == "0.1(6)"
    assert fractionToDecimal(numerator=4, denominator=333) == "0.(012)"
    assert fractionToDecimal(1, 2) == "0.5"
    assert fractionToDecimal(2, 1) == "2"


# https://leetcode.com/problems/find-the-largest-area-of-square-inside-two-rectangles/description/?envType=daily-question&envId=2026-01-17
def largestSquareArea(bottomLeft: list[list[int]], topRight: list[list[int]]) -> int:
    res = 0
    for ((x1, y1), (x2, y2)), ((x3, y3), (x4, y4)) in combinations(
        zip(bottomLeft, topRight), 2
    ):
        x = min(x2, x4) - max(x1, x3)
        y = min(y2, y4) - max(y1, y3)
        res = max(res, min(x, y))
    return res**2


def test_largestSquareArea():
    assert (
        largestSquareArea(
            bottomLeft=[[1, 1], [3, 3], [3, 1]], topRight=[[2, 2], [4, 4], [4, 2]]
        )
        == 0
    )
    assert (
        largestSquareArea(
            bottomLeft=[[1, 1], [2, 2], [3, 1]], topRight=[[3, 3], [4, 4], [6, 6]]
        )
        == 1
    )


def to_base62(num: int) -> str:
    BASE62_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    res = ""
    while num:
        num, rem = divmod(num, 62)
        res = BASE62_ALPHABET[rem] + res
    return res or "0"


def test_to_base62():
    assert to_base62(1) == "1"
    assert to_base62(519019116161116161) == "cL70az61gn"
