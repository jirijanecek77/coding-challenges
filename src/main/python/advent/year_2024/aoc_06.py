import time

from advent.utils import (
    Position,
    Direction,
    move,
    turn_right,
    is_in_matrix,
    OrientedPosition,
)

filename = "data/aoc_06.txt"

test_data = [
    "....#.....",
    ".........#",
    "..........",
    "..#.......",
    ".......#..",
    "..........",
    ".#..^.....",
    "........#.",
    "#.........",
    "......#...",
]


def read_data(lines):
    obstacles = set()
    position = None
    n = len(lines)
    for row, line in enumerate(lines):
        for col, ch in enumerate(line):
            if ch == "#":
                obstacles.add(Position(row, col))
            elif ch == "^":
                position = Position(row, col)
    return obstacles, position, n


def check(position, direction, visited, obstacles) -> bool:
    match direction:
        case Direction.UP:
            candidates = sorted(
                map(
                    lambda v: v.position,
                    filter(
                        lambda v: v.position.col == position.col
                        and v.position.row < position.row
                        and v.direction == direction,
                        visited,
                    ),
                ),
                reverse=True,
            )
            return (
                not any(
                    filter(
                        lambda o: position.row > o.row > candidates[0].row
                        and o.col == position.col,
                        obstacles,
                    )
                )
                if candidates
                else False
            )
        case Direction.DOWN:
            candidates = sorted(
                map(
                    lambda v: v.position,
                    filter(
                        lambda v: v.position.col == position.col
                        and v.position.row > position.row
                        and v.direction == direction,
                        visited,
                    ),
                ),
            )
            return (
                not any(
                    filter(
                        lambda o: position.row < o.row < candidates[0].row
                        and o.col == position.col,
                        obstacles,
                    )
                )
                if candidates
                else False
            )
        case Direction.LEFT:
            candidates = sorted(
                map(
                    lambda v: v.position,
                    filter(
                        lambda v: v.position.col < position.col
                        and v.position.row == position.row
                        and v.direction == direction,
                        visited,
                    ),
                ),
                reverse=True,
            )
            return (
                not any(
                    filter(
                        lambda o: position.col > o.col > candidates[0].col
                        and o.row == position.row,
                        obstacles,
                    )
                )
                if candidates
                else False
            )
        case Direction.RIGHT:
            candidates = sorted(
                map(
                    lambda v: v.position,
                    filter(
                        lambda v: v.position.col > position.col
                        and v.position.row == position.row
                        and v.direction == direction,
                        visited,
                    ),
                )
            )
            return (
                not any(
                    filter(
                        lambda o: position.col < o.col < candidates[0].col
                        and o.row == position.row,
                        obstacles,
                    )
                )
                if candidates
                else False
            )
    return False


def calculate1(obstacles, position, n) -> int:
    direction = Direction.UP
    visited = set()
    while is_in_matrix(position, n):
        visited.add(position)

        new_position = move(position, direction)
        while new_position in obstacles:
            direction = turn_right(direction)
            new_position = move(position, direction)
        position = new_position

    return len(visited)


def calculate2(obstacles, position, n) -> int:
    direction = Direction.UP
    visited = set()
    new_obstacles = set()
    while is_in_matrix(position, n):
        new_position = move(position, direction)
        if (
            is_in_matrix(new_position, n)
            and new_position not in obstacles
            and check(position, turn_right(direction), visited, obstacles)
        ):
            new_obstacles.add(new_position)

        visited.add(OrientedPosition(position, direction))

        while new_position in obstacles:
            direction = turn_right(direction)
            new_position = move(position, direction)

        position = new_position

    return len(new_obstacles)


def solve_01():
    # assert calculate1(*read_data(test_data)) == 41
    assert calculate2(*read_data(test_data)) == 6

    with open(filename) as file:
        start = time.perf_counter()

        # print(calculate1(*read_data(file.readlines())))
        print(calculate2(*read_data(file.readlines())))

        print(f"Elapsed time: {time.perf_counter() - start}")


if __name__ == "__main__":
    solve_01()
