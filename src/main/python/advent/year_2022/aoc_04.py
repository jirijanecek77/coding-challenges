import time

from advent.utils import line_generator

filename = "data/aoc_04.txt"


test_data = [
    "2-4,6-8",
    "2-3,4-5",
    "5-7,7-9",
    "2-8,3-7",
    "6-6,4-6",
    "2-6,4-8",
]


def read_input(line: str) -> tuple[tuple[int, int], tuple[int, int]]:
    a, b = line.split(",")
    return tuple(map(int, a.split("-"))), tuple(map(int, b.split("-")))


def is_fully_in(intervals: tuple[tuple[int, int], tuple[int, int]]) -> bool:
    interval1, interval2 = intervals
    return (interval1[0] >= interval2[0] and interval1[1] <= interval2[1]) or (
        interval2[0] >= interval1[0] and interval2[1] <= interval1[1]
    )


def overlap(intervals: tuple[tuple[int, int], tuple[int, int]]) -> bool:
    interval1, interval2 = sorted(intervals)
    return interval2[0] <= interval1[1]


def solve1(lines: list[str]) -> int:
    return sum(is_fully_in(read_input(line)) for line in lines)


def solve2(lines: list[str]) -> int:
    return sum(overlap(read_input(line)) for line in lines)


if __name__ == "__main__":
    assert solve2(test_data) == 4
    with open(filename) as file:
        start = time.perf_counter()

        res = solve2(line_generator(file.readlines()))
        print(res)

        print(f"Elapsed time: {time.perf_counter() - start}")
