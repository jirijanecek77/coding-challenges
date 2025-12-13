import re

from advent.utils import line_generator

filename = "data/aoc_13.txt"


def parse_button_line(line: str) -> tuple[int, int]:
    pattern = r"X\+(\d+),\s*Y\+(\d+)"

    # Search using regex
    match = re.search(pattern, line)

    return int(match.group(1)), int(match.group(2))


def parse_prize_line(line: str) -> tuple[int, int]:
    pattern = r"X\=(\d+),\s*Y\=(\d+)"

    # Search using regex
    match = re.search(pattern, line)

    return 10000000000000 + int(match.group(1)), 10000000000000 + int(match.group(2))


def calculate(a: tuple[int, int], b: tuple[int, int], prize: tuple[int, int]) -> int:
    x = (prize[1] * b[0] - prize[0] * b[1]), (a[1] * b[0] - a[0] * b[1])
    if x[0] % x[1] == 0:
        x = x[0] // x[1]
    else:
        return 0

    y = (prize[1] * a[0] - prize[0] * a[1]), (b[1] * a[0] - b[0] * a[1])
    if y[0] % y[1] == 0:
        y = y[0] // y[1]
    else:
        return 0
    print(x, y)
    return x * 3 + y


def solve_01():
    with open(filename) as file:
        a = (0, 0)
        b = (0, 0)
        result = 0
        for row_index, line in enumerate(line_generator(file.readlines())):
            if row_index % 4 == 0:
                a = parse_button_line(line)
            elif row_index % 4 == 1:
                b = parse_button_line(line)
            elif row_index % 4 == 2:
                prize = parse_prize_line(line)
                result += calculate(a, b, prize)
            else:
                continue

        print(result)


if __name__ == "__main__":
    solve_01()
