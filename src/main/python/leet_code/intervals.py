import math


# https://leetcode.com/problems/set-intersection-size-at-least-two/description/?envType=daily-question&envId=2025-11-20
def intersectionSizeTwo(intervals: list[list[int]]) -> int:
    intervals.sort(key=lambda x: (x[1], -x[0]))

    a = -math.inf
    b = -math.inf
    result = 0

    for left, right in intervals:
        if left > b:
            result += 2
            a = right - 1
            b = right
        elif left > a:
            result += 1
            a = b
            b = right
    return result


def test_intersectionSizeTwo():
    assert intersectionSizeTwo(intervals=[[1, 3], [3, 7], [5, 7], [7, 8]]) == 5
    assert (
        intersectionSizeTwo(
            intervals=[
                [2, 10],
                [3, 7],
                [3, 15],
                [4, 11],
                [6, 12],
                [6, 16],
                [7, 8],
                [7, 11],
                [7, 15],
                [11, 12],
            ]
        )
        == 5
    )
    assert intersectionSizeTwo(intervals=[[1, 3], [1, 4], [2, 5], [3, 5]]) == 3
    assert intersectionSizeTwo(intervals=[[1, 3], [2, 6], [8, 10], [15, 18]]) == 6
    assert intersectionSizeTwo(intervals=[[1, 3], [3, 7], [8, 9]]) == 5
    assert intersectionSizeTwo(intervals=[[1, 2], [2, 3], [2, 4], [4, 5]]) == 5
