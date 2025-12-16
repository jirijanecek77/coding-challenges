import time

from advent.utils import (
    Position,
    move_down,
    move_up,
    move_left,
    move_right,
    is_in_matrix,
)

filename = "data/aoc_21.txt"

test_data = [
    "...........",
    ".....###.#.",
    ".###.##..#.",
    "..#.#...#..",
    "....#.#....",
    ".##..S####.",
    ".##..#...#.",
    ".......##..",
    ".##.#.####.",
    ".##..##.##.",
    "...........",
]


def read_data(lines):
    stones = set()
    position = None
    n = len(lines)
    for row, line in enumerate(lines):
        for col, ch in enumerate(line):
            if ch == "#":
                stones.add(Position(row, col))
            elif ch == "S":
                position = Position(row, col)
    return stones, position, n


def calculate(stones: set[Position], position: Position, n: int, max_steps: int) -> int:
    def do_iteration(positions: set[Position]) -> set[Position]:
        res = set()
        for pos in positions:
            for move in (move_up, move_down, move_left, move_right):
                new_pos = move(pos)
                if is_in_matrix(new_pos, n) and new_pos not in stones:
                    res.add(new_pos)
        return res

    positions = {position}
    for _ in range(max_steps):
        positions = do_iteration(positions)
    return len(positions)


if __name__ == "__main__":
    assert calculate(*read_data(test_data), max_steps=6) == 16
    # assert calculate(test_data, with_multiplier=True) == 525152

    with open(filename) as file:
        start = time.perf_counter()

        print(calculate(*read_data(file.readlines()), max_steps=64))
        # print(calculate(file.readlines(), with_multiplier=True))

        print(f"Elapsed time: {time.perf_counter() - start}")
