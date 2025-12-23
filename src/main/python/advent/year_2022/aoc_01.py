import time

from advent.utils import line_generator

filename = "data/aoc_01.txt"


test_data = [
    "1000",
    "2000",
    "3000",
    "",
    "4000",
    "",
    "5000",
    "6000",
    "",
    "7000",
    "8000",
    "9000",
    "",
    "10000",
]


def solve(lines: list[str], count: int) -> int:
    acc = 0
    results = []
    for line in lines:
        if line:
            acc += int(line)
        else:
            results.append(acc)
            results = sorted(results)[-count:]
            acc = 0

    return sum(results)


if __name__ == "__main__":
    assert solve(test_data, 1) == 24000
    with open(filename) as file:
        start = time.perf_counter()

        res = solve(line_generator(file.readlines()), 3)
        print(res)

        print(f"Elapsed time: {time.perf_counter() - start}")
