filename = "data/aoc_04.txt"

EXPECTED_1 = ["XMAS", "SAMX"]
EXPECTED_2 = ["MAS", "SAM"]


def calculate1(data: list[list[str]]) -> int:
    counter = 0
    for col_index in range(len(data) - 3):
        for row_index in range(len(data) - 3):
            counter += 1 if "".join(data[col_index][row_index:row_index + 4]) in EXPECTED_1 else 0
            counter += 1 if "".join(
                [data[col_index][row_index], data[col_index + 1][row_index], data[col_index + 2][row_index],
                 data[col_index + 3][row_index]]) in EXPECTED_1 else 0
            counter += 1 if "".join([data[col_index][row_index], data[col_index + 1][row_index + 1], data[col_index + 2][row_index + 2],
                                     data[col_index + 3][row_index + 3]]) in EXPECTED_1 else 0
            counter += 1 if "".join([data[col_index][row_index + 3], data[col_index + 1][row_index + 2], data[col_index + 2][row_index + 1],
                                     data[col_index + 3][row_index]]) in EXPECTED_1 else 0

    return counter


def calculate2(data: list[list[str]]) -> int:
    counter = 0
    for col_index in range(len(data) - 2):
        for row_index in range(len(data) - 2):
            counter += 1 if "".join(
                [data[col_index][row_index], data[col_index + 1][row_index + 1], data[col_index + 2][row_index + 2]]) in EXPECTED_2 and "".join(
                [data[col_index][row_index + 2], data[col_index + 1][row_index + 1], data[col_index + 2][row_index]]) in EXPECTED_2 else 0

    return counter


def solve_01():
    with open(filename) as file:
        data = [list(line.replace("\n", "")) + list("....") for line in file.readlines()]
        data.extend([list("." * len(data[0])) for _ in range(4)])
        result = calculate1(data)
    print(result)


def solve_02():
    with open(filename) as file:
        data = [list(line.replace("\n", "")) + list("...") for line in file.readlines()]
        data.extend([list("." * len(data[0])) for _ in range(3)])
        result = calculate2(data)
    print(result)


if __name__ == "__main__":
    solve_02()
