from itertools import permutations

from utils import (
    line_generator,
    get_vector,
    Position,
    move_to_position,
    is_in_playground,
)

filename = "data/aoc_08.txt"


def calculate_for_frequency(
    frequency: str, data: dict[Position, str], playground_size: int
) -> set[Position]:
    result = set()
    position_pairs = permutations(
        [position for position, freq in data.items() if freq == frequency], 2
    )
    for pos_pair in position_pairs:
        direction_vector = get_vector(pos_pair[0], pos_pair[1])
        new_position = pos_pair[1]
        while is_in_playground(
            (new_position := move_to_position(new_position, direction_vector)),
            playground_size,
        ):
            result.add(new_position)
    return result


def solve_01():
    with open(filename) as file:
        data = {}
        for row_index, line in enumerate(line_generator(file.readlines())):
            data.update(
                {
                    (row_index, col_index): val
                    for col_index, val in enumerate(list(line))
                    if val != "."
                }
            )
            playground_size = len(line)

        result = set(data.keys())
        for frequency in set(data.values()):
            result = result.union(
                calculate_for_frequency(frequency, data, playground_size)
            )

        print(len(result))


if __name__ == "__main__":
    solve_01()
