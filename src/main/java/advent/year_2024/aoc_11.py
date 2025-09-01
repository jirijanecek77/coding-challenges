from functools import cache

filename = "data/aoc_11.txt"


@cache
def calculate(level: int, item: str) -> int:
    if level == 0:
        return 1

    if item == "0":
        result = calculate(level - 1, "1")
    elif len(item) % 2 == 0:
        result = calculate(level - 1, item[:len(item) // 2]) + calculate(level - 1, str(int(item[len(item) // 2:])))
    else:
        result = calculate(level - 1, str(int(item) * 2024))
    return result


def solve_01():
    with open(filename) as file:
        numbers = file.read().strip().split(" ")

        count = sum(calculate(75, num) for num in numbers)
        print(count)


if __name__ == "__main__":
    solve_01()
