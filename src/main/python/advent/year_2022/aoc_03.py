import time
from itertools import batched

from advent.utils import line_generator

filename = "data/aoc_03.txt"


test_data = [
    "vJrwpWtwJgWrhcsFMMfFFhFp",
    "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
    "PmmdzqPrVvPwwTWBwg",
    "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
    "ttgJtRGJQctTZtZT",
    "CrZsJsPPZsGzwwsLwLmpwMDw",
]


def calculate(sets: tuple[str, ...]) -> int:
    intersection = set(sets[0])
    for b in sets[1:]:
        intersection = intersection & set(b)
    return sum(
        map(
            lambda ch: (
                ord(ch) - ord("a") if ord(ch) >= ord("a") else ord(ch) - ord("A") + 26
            )
            + 1,
            intersection,
        )
    )


def solve1(lines: list[str]) -> int:
    return sum(
        calculate((line[: len(line) // 2], line[len(line) // 2 :])) for line in lines
    )


def solve2(lines: list[str]) -> int:
    return sum(calculate(batch) for batch in batched(lines, 3))


if __name__ == "__main__":
    assert solve2(test_data) == 70
    with open(filename) as file:
        start = time.perf_counter()

        res = solve2(line_generator(file.readlines()))
        print(res)

        print(f"Elapsed time: {time.perf_counter() - start}")
