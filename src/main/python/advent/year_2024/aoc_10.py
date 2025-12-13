from itertools import chain

from advent.utils import (
    line_generator,
    Position,
    move_up,
    move_down,
    move_left,
    move_right,
)

filename = "data/aoc_10.txt"


def calculate_ranking(pos: Position, data: dict[Position, int]) -> list[Position]:
    val = data[pos]
    if val == 9:
        return [pos]

    return list(
        chain.from_iterable(
            calculate_ranking(new_pos, data)
            for new_pos in [
                move_up(pos),
                move_down(pos),
                move_left(pos),
                move_right(pos),
            ]
            if new_pos in data.keys() and data[new_pos] == val + 1
        )
    )


def solve_01():
    with open(filename) as file:
        data = {}
        for row_index, line in enumerate(line_generator(file.readlines())):
            for col_index, val in enumerate(list(line)):
                data[(col_index, row_index)] = int(val) if val != "." else -1

        starts = [pos for pos, val in data.items() if val == 0]
        result = 0
        for pos in starts:
            result += len(calculate_ranking(pos, data))

        print(result)


if __name__ == "__main__":
    solve_01()
