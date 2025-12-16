import time

from functools import cache

filename = "data/aoc_12.txt"

test_data = [
    "???.### 1,1,3",
    ".??..??...?##. 1,1,3",
    "?#?#?#?#?#?#?#? 1,3,1,6",
    "????.#...#... 4,1,1",
    "????.######..#####. 1,6,5",
    "?###???????? 3,2,1",
]


@cache
def num_valid_solutions(record: str, groups: tuple[int, ...]) -> int:
    if not record:
        # if there are no more spots to check;
        # our only chance at success is if there are no `groups` left
        return len(groups) == 0

    if not groups:
        # if there are no more groups the only possibility of success is that there are no `#` remaining
        # here, `?` are treated as `.`, so no recursion is necessary
        return "#" not in record

    char, rest_of_record = record[0], record[1:]

    if char == ".":
        # dots are ignores, so keep recursing
        return num_valid_solutions(rest_of_record, groups)

    if char == "#":
        group = groups[0]
        # we're at the start of a group! make sure there are enough here to fill the first group
        # to be valid, we have to be:
        if (
            # long enough to match
            len(record) >= group
            # made of only things that can be `#` (no `.`)
            and all(c != "." for c in record[:group])
            # either at the end of the record (allowed)
            # or the next character isn't also a `#` (would be too big)
            and (len(record) == group or record[group] != "#")
        ):
            return num_valid_solutions(record[group + 1 :], groups[1:])

        return 0

    if char == "?":
        return num_valid_solutions(f"#{rest_of_record}", groups) + num_valid_solutions(
            f".{rest_of_record}", groups
        )

    raise ValueError(f"unknown char: {char}")


def solve_line(line: str, with_multiplier=False) -> int:
    record, raw_shape = line.split()
    shape = tuple(map(int, raw_shape.split(",")))

    if with_multiplier:
        record = "?".join([record] * 5)
        shape *= 5

    return num_valid_solutions(record, shape)


def calculate(lines: list[str], with_multiplier=False) -> int:
    return sum(solve_line(line, with_multiplier=with_multiplier) for line in lines)


if __name__ == "__main__":
    assert calculate(test_data) == 21
    assert calculate(test_data, with_multiplier=True) == 525152

    with open(filename) as file:
        start = time.perf_counter()

        # print(calculate(file.readlines()))
        print(calculate(file.readlines(), with_multiplier=True))

        print(f"Elapsed time: {time.perf_counter() - start}")
