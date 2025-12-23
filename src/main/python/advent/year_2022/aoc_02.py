import time

from advent.utils import line_generator

filename = "data/aoc_02.txt"


test_data = ["A Y", "B X", "C Z"]


def calculate1(line: str) -> int:
    points = {
        ("A", "X"): 4,
        ("B", "Y"): 5,
        ("C", "Z"): 6,
        ("A", "Y"): 8,
        ("B", "X"): 1,
        ("C", "X"): 7,
        ("A", "Z"): 3,
        ("B", "Z"): 9,
        ("C", "Y"): 2,
    }
    parts = tuple(line.split())
    return points[parts]


def calculate2(line: str) -> int:
    points = {
        ("A", "X"): 3 + 0,
        ("B", "Y"): 2 + 3,
        ("C", "Z"): 1 + 6,
        ("A", "Y"): 1 + 3,
        ("B", "X"): 1 + 0,
        ("C", "X"): 2 + 0,
        ("A", "Z"): 2 + 6,
        ("B", "Z"): 3 + 6,
        ("C", "Y"): 3 + 3,
    }
    parts = tuple(line.split())
    return points[parts]


def solve1(lines: list[str]) -> int:
    return sum(calculate1(line) for line in lines)


def solve2(lines: list[str]) -> int:
    return sum(calculate2(line) for line in lines)


if __name__ == "__main__":
    assert solve2(test_data) == 12
    with open(filename) as file:
        start = time.perf_counter()

        res = solve2(line_generator(file.readlines()))
        print(res)

        print(f"Elapsed time: {time.perf_counter() - start}")
