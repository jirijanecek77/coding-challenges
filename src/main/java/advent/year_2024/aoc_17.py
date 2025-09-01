import re

from utils import line_generator

filename = "data/test.txt"

patterns = set()


def parse_register(line: str) -> int:
    pattern = r"Register\s[ABC]:\s(\d+)"

    # Search using regex
    match = re.search(pattern, line)

    return int(match.group(1))


def combo(operand: int, a: int, b: int, c: int) -> int:
    if operand <= 3:
        return operand
    elif operand == 4:
        return a
    elif operand == 5:
        return b
    elif operand == 6:
        return c
    ValueError("Invalid operand")


def calculate(a: int, b: int, c: int, program: list[int], pointer: int = 0):
    while pointer < len(program):
        opcode = program[pointer]
        operand = program[pointer + 1]

        match opcode:
            case 0:
                a //= (1 << combo(operand, a, b, c))
                pointer += 2
            case 1:
                b ^= operand
                pointer += 2
            case 2:
                b = combo(operand, a, b, c) % 8
                pointer += 2
            case 3:
                pointer = operand if a != 0 else pointer + 2
            case 4:
                b ^= c
                pointer += 2
            case 5:
                yield combo(operand, a, b, c) % 8
                pointer += 2
            case 6:
                b = a // (1 << combo(operand, a, b, c))
                pointer += 2
            case 7:
                c = a // (1 << combo(operand, a, b, c))
                pointer += 2


def solve_01():
    with (open(filename) as file):
        generator = line_generator(file.readlines())
        register_a = parse_register(next(generator))
        register_b = parse_register(next(generator))
        register_c = parse_register(next(generator))
        next(generator)
        program = [int(e.strip()) for e in next(generator)[9:].split(",")]

        result = []
        for index, out in enumerate(calculate(register_a, register_b, register_c, program)):
            result.append(str(out))
        print(",".join(result))


if __name__ == "__main__":
    solve_01()
