import operator
import time
from functools import reduce
from itertools import starmap, combinations

from advent.utils import Point

filename = "data/aoc_09.txt"

test_data = ["7,1", "11,1", "11,7", "9,7", "9,5", "2,5", "2,3", "7,3"]


def read_data(lines):
    return set(Point(*map(int, line.split(","))) for line in lines)


def square(points: tuple[Point, ...]) -> int:
    return reduce(operator.mul, starmap(lambda a, b: abs(a - b) + 1, zip(*points)))


def distance(points: tuple[Point, ...]) -> int:
    return sum(starmap(lambda a, b: (a - b) ** 2, zip(*points)))


def calculate1(points: set[Point]) -> int:
    return max(map(square, combinations(points, 2)))


def calculate2(points: set[Point]) -> int:
    def f(p: tuple[Point, ...]) -> bool:
        p1, p2 = p
        return Point(p1.x, p2.y) in points and Point(p2.x, p1.y) in points

    return max(map(square, filter(f, combinations(points, 2))))


if __name__ == "__main__":
    assert calculate2(read_data(test_data)) == 24

    with open(filename) as file:
        start = time.perf_counter()

        print(calculate2(read_data(file.readlines())))

        print(f"Elapsed time: {time.perf_counter() - start}")
