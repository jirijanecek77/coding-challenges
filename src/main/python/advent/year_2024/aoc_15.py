import copy

from utils import (
    line_generator,
    Position,
    move_up,
    move_down,
    move_left,
    move_right,
    Direction,
)

filename = "data/aoc_15.txt"


def push_left_right(pos: Position, move_fn, playground: list[list[str]]) -> Position:
    new_pos = move_fn(pos)
    if playground[new_pos.row][new_pos.col] == ".":
        return new_pos

    if playground[new_pos.row][new_pos.col] == "#":
        return pos

    if new_pos != (x := push_left_right(new_pos, move_fn, playground)):
        playground[x.row][x.col] = playground[new_pos.row][new_pos.col]
        playground[new_pos.row][new_pos.col] = "."
        return new_pos
    return pos


def push_up_down(pos: Position, move_fn, playground: list[list[str]]) -> Position:
    new_pos = move_fn(pos)
    if playground[new_pos.row][new_pos.col] == ".":
        return new_pos

    if playground[new_pos.row][new_pos.col] == "#":
        return pos

    p1 = push_up_down(new_pos, move_fn, playground)
    if p1 == new_pos:
        return pos

    next_new_pos = (
        move_left(new_pos)
        if playground[new_pos.row][new_pos.col] == "]"
        else move_right(new_pos)
    )
    p2 = push_up_down(next_new_pos, move_fn, playground)
    if p2 == next_new_pos:
        return pos

    for p in [p1, p2]:
        playground[p.row][p.col] = playground[new_pos.row][p.col]
        playground[new_pos.row][p.col] = "."
    return new_pos


def perform_move(
    move: Direction, playground: list[list[str]], pos: Position
) -> Position:
    match move:
        case Direction.UP:
            return push_up_down(pos, move_up, playground)
        case Direction.DOWN:
            return push_up_down(pos, move_down, playground)
        case Direction.LEFT:
            return push_left_right(pos, move_left, playground)
        case Direction.RIGHT:
            return push_left_right(pos, move_right, playground)
        case _:
            raise ValueError


def plot(data: list[list[str]], pos: Position):
    with_pos = copy.deepcopy(data)
    with_pos[pos.row][pos.col] = "@"
    for row in with_pos:
        print("".join(row))


def solve_01():
    with open(filename) as file:
        playground = []
        generator = line_generator(file.readlines())
        pos = None
        for row_index, line in enumerate(generator):
            if line.strip() == "":
                break
            line = (
                line.strip()
                .replace(".", "..")
                .replace("#", "##")
                .replace("O", "[]")
                .replace("@", "@.")
            )
            line_data = list(line)
            if "@" in line_data:
                pos = Position(row_index, line_data.index("@"))
                line_data[pos.col] = "."
            playground.append(line_data)

        moves = []
        for line in generator:
            moves.extend(list(line.strip()))

        plot(playground, pos)
        for move in moves:
            pos = perform_move(move, playground, pos)
            print(move, pos)
            plot(playground, pos)
        # plot(playground, pos)

        result = sum(
            sum(
                col_index + 100 * row_index
                for col_index, val in enumerate(row)
                if val == "["
            )
            for row_index, row in enumerate(playground)
        )

        print(result)


if __name__ == "__main__":
    solve_01()
