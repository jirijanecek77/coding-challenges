from functools import cache

from advent.utils import line_generator

filename = "data/test.txt"

patterns = set()


@cache
def check_pattern(towel: str) -> int:
    if len(towel) == 0:
        return 0

    result = 0
    for length in range(1, len(towel)):
        if towel[:length] in patterns:
            if towel[length:] in patterns:
                result += 1
            result += check_pattern(towel[length:])
    return result


def solve_01():
    with open(filename) as file:
        generator = line_generator(file.readlines())
        for line in generator:
            if line.strip() == "":
                break
            for e in line.split(","):
                patterns.add(e.strip())

        result = sum(check_pattern(test_case.strip()) for test_case in generator)
        print(result)


if __name__ == "__main__":
    solve_01()
