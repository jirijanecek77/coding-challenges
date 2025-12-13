import operator
import time
from functools import reduce

filename = "data/aoc_06.txt"

test_data = [
    "123 328  51 64",
    " 45 64  387 23",
    "  6 98  215 314",
    "*   +   *   +  ",
]


def read_data(lines):
    data = list(line.strip("\n") for line in lines)
    return data[:-1], data[-1]


def calculate1(rows: list[list[str]], operators: str) -> int:
    result = 0
    n = len(rows)

    while operators:
        op = operators[0]
        numbers_len = 1
        while numbers_len < len(operators) and operators[numbers_len] == " ":
            numbers_len += 1
        operators = operators[numbers_len:]
        if operators:
            numbers_len -= 1

        str_nums = []
        for i in range(n):
            if operators:
                str_nums.append(rows[i][:numbers_len])
                rows[i] = rows[i][numbers_len + 1 :]
            else:
                str_nums.append(rows[i])

        nums = [int(s.strip()) for s in str_nums]

        if op == "+":
            result += sum(nums)
        else:
            result += reduce(operator.mul, nums)

    return result


def calculate2(rows: list[str], operators: str) -> int:
    result = 0
    n = len(rows)

    while operators:
        op = operators[0]
        numbers_len = 1
        while numbers_len < len(operators) and operators[numbers_len] == " ":
            numbers_len += 1
        operators = operators[numbers_len:]
        if operators:
            numbers_len -= 1

        str_nums = []
        for i in range(n):
            if operators:
                str_nums.append(rows[i][:numbers_len])
                rows[i] = rows[i][numbers_len + 1 :]
            else:
                str_nums.append(rows[i])

        nums = []
        for i in reversed(range(numbers_len)):
            num = 0
            for row in str_nums:
                if i < len(row) and row[i] != " ":
                    num *= 10
                    num += int(row[i])
            nums.append(num)

        if op == "+":
            result += sum(nums)
        else:
            result += reduce(operator.mul, nums)

    return result


if __name__ == "__main__":
    assert calculate2(*read_data(test_data)) == 3263827

    with open(filename) as file:
        start = time.perf_counter()

        print(calculate2(*read_data(file.readlines())))

        print(f"Elapsed time: {time.perf_counter() - start}")
