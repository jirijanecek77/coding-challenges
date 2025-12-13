import time
from functools import lru_cache

filename = "data/aoc_07.txt"

test_data = [
    ".......S.......",
    "...............",
    ".......^.......",
    "...............",
    "......^.^......",
    "...............",
    ".....^.^.^.....",
    "...............",
    "....^.^...^....",
    "...............",
    "...^.^...^.^...",
    "...............",
    "..^...^.....^..",
    "...............",
    ".^.^.^.^.^...^.",
    "...............",
]


def read_data(lines):
    return list(line.strip("\n") for line in lines)


def calculate1(rows: list[str]) -> int:
    n = len(rows)
    result = 0
    visited = set()
    queue = [(rows[0].index("S"), 0)]
    while queue:
        col_idx, row_idx = queue.pop(0)
        if (col_idx, row_idx) in visited or row_idx == n:
            continue

        visited.add((col_idx, row_idx))
        if rows[row_idx][col_idx] == "^":
            result += 1
            queue.append((col_idx - 1, row_idx + 1))
            queue.append((col_idx + 1, row_idx + 1))
        else:
            queue.append((col_idx, row_idx + 1))

    return result


def calculate2(rows: list[str]) -> int:
    n = len(rows)

    @lru_cache
    def dfs(col_idx: int, row_idx: int) -> int:
        if row_idx == n:
            return 1

        if rows[row_idx][col_idx] == "^":
            return dfs(col_idx - 1, row_idx + 1) + dfs(col_idx + 1, row_idx + 1)
        else:
            return dfs(col_idx, row_idx + 1)

    return dfs(rows[0].index("S"), 1)


if __name__ == "__main__":
    assert calculate2(read_data(test_data)) == 40

    with open(filename) as file:
        start = time.perf_counter()

        print(calculate2(read_data(file.readlines())))

        print(f"Elapsed time: {time.perf_counter() - start}")
