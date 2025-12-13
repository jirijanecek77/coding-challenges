import time

from advent.utils import (
    Position,
    move_up,
    move_down,
    move_left,
    move_right,
    is_in_matrix,
)

filename = "data/aoc_04.txt"

test_data = [
    "..@@.@@@@.",
    "@@@.@.@.@@",
    "@@@@@.@.@@",
    "@.@@@@..@.",
    "@@.@@@@.@@",
    ".@@@@@@@.@",
    ".@.@.@.@@@",
    "@.@@@.@@@@",
    ".@@@@@@@@.",
    "@.@.@@@.@.",
]


def read_data(lines) -> tuple[set[Position], int]:
    return {
        Position(row_index, col_index)
        for row_index, line in enumerate(lines)
        for col_index, val in enumerate(line)
        if val == "@"
    }, len(lines)


def calculate(rolls_positions: set[Position], n: int) -> int:
    def has_max_3_neighbours(pos: Position) -> bool:
        return (
            sum(
                map(
                    lambda p: p in rolls_positions,
                    filter(
                        lambda p: is_in_matrix(p, n),
                        [
                            move_up(pos),
                            move_up(move_left(pos)),
                            move_up(move_right(pos)),
                            move_down(pos),
                            move_down(move_left(pos)),
                            move_down(move_right(pos)),
                            move_left(pos),
                            move_right(pos),
                        ],
                    ),
                )
            )
            <= 3
        )

    to_remove = rolls_positions
    result = 0
    while to_remove:
        to_remove = set(filter(lambda pos: has_max_3_neighbours(pos), rolls_positions))
        rolls_positions -= to_remove
        result += len(to_remove)
    return result


if __name__ == "__main__":
    assert calculate(*read_data(test_data)) == 43

    print("Mask solution:")
    with open(filename) as file:
        start = time.perf_counter()

        print(calculate(*read_data(file.readlines())))

        print(f"Elapsed time: {time.perf_counter() - start}")
