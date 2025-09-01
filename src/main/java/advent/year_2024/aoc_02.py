from itertools import pairwise, count, islice

filename = "data/aoc_02.txt"


def check(diffs: list[int], valid: list[int]):
    return map(lambda x: 1 if x in valid else 0, diffs)


def is_valid(values: list[int]) -> bool:
    diffs = [j - i for i, j in pairwise(values)]
    return sum(check(diffs, [1, 2, 3])) == len(diffs) or sum(check(diffs, [-1, -2, -3])) == len(diffs)


def is_valid_02(values: list[int]) -> bool:
    if is_valid(values):
        return True

    for x in [values[:i] + values[i + 1:] for i in range(len(values))]:
        if is_valid(x):
            return True

    return False


def solve_01():
    with open(filename) as file:
        lines = file.readlines()

        results = sum([is_valid([int(val) for val in line.split(" ")]) for line in lines])

        print(results)


def solve_02():
    with open(filename) as file:
        lines = file.readlines()

        results = [is_valid_02([int(val) for val in line.split(" ")]) for line in lines]

        print(sum(map(lambda x: 1 if x else 0, results)))


if __name__ == "__main__":
    solve_02()
