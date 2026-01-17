from itertools import combinations


def fractionToDecimal(numerator: int, denominator: int) -> str:
    sign = "-" if numerator * denominator < 0 else ""
    numerator = abs(numerator)
    num = numerator // denominator
    rem = numerator % denominator
    res = [str(num)]
    seen = [rem]
    numerator = rem

    if rem == 0:
        return sign + "".join(res)

    res.append(".")

    while rem > 0:
        numerator *= 10
        if numerator < denominator:
            res.append("0")
            continue

        num = numerator // denominator
        res.append(str(num))
        rem = numerator % denominator
        if rem in seen:
            index = seen.index(rem) + 2

            return sign + "".join(res[:index]) + "(" + "".join(res[index:]) + ")"
        seen.append(rem)
        numerator = rem

    return sign + "".join(res)


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
