import time

from advent.utils import (
    Position,
    is_in_matrix,
    move,
    Direction,
)

filename = "data/aoc_08.txt"


test_data = [
    "30373",
    "25512",
    "65332",
    "33549",
    "35390",
]


def read_matrix(lines: list[str]) -> list[list[int]]:
    matrix = []
    for line in lines:
        line = line.strip()
        matrix.append([int(ch) for ch in line])
    return matrix


def solve1(matrix: list[list[int]]) -> int:
    res = set()
    n = len(matrix)

    for i in range(n):
        # left to right
        max_tree = -1
        for j in range(n - 1):
            if matrix[i][j] > max_tree:
                res.add((i, j))
                max_tree = matrix[i][j]
        # right to left
        max_tree = -1
        for j in range(n - 1, 0, -1):
            if matrix[i][j] > max_tree:
                res.add((i, j))
                max_tree = matrix[i][j]

    for j in range(n):
        # top to bottom
        max_tree = -1
        for i in range(n - 1):
            if matrix[i][j] > max_tree:
                res.add((i, j))
                max_tree = matrix[i][j]

        # bottom to top
        max_tree = -1
        for i in range(n - 1, 0, -1):
            if matrix[i][j] > max_tree:
                res.add((i, j))
                max_tree = matrix[i][j]
    for i in range(n):
        for j in range(n):
            if (i, j) in res:
                print("#", end="")
            else:
                print(".", end="")
        print()
    return len(res)


def solve2(matrix: list[list[int]]) -> int:
    n = len(matrix)

    def trace(pos, direction) -> int:
        target = matrix[pos.row][pos.col]
        res = 1
        pos = move(pos, direction)
        while is_in_matrix(pos, n) and matrix[pos.row][pos.col] < target:
            pos = move(pos, direction)
            if is_in_matrix(pos, n):
                res += 1
        return res

    return max(
        trace(Position(i, j), Direction.UP)
        * trace(Position(i, j), Direction.DOWN)
        * trace(Position(i, j), Direction.LEFT)
        * trace(Position(i, j), Direction.RIGHT)
        for i in range(1, n - 1)
        for j in range(1, n - 1)
    )


if __name__ == "__main__":
    assert solve2(read_matrix(test_data)) == 8

    with open(filename) as file:
        start = time.perf_counter()

        print(solve2(read_matrix(file.readlines())))

        print(f"Elapsed time: {time.perf_counter() - start}")
