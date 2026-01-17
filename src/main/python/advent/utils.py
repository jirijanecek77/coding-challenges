import math
from collections import namedtuple
from enum import Enum

Vector = tuple[int, ...]


class Direction(str, Enum):
    UP = "^"
    DOWN = "v"
    LEFT = "<"
    RIGHT = ">"


Position = namedtuple("Position", ["row", "col"], defaults=(0, 0))
Point = namedtuple("Point", ["x", "y"], defaults=(0, 0))
Point3D = namedtuple("Point3D", ["x", "y", "z"], defaults=(0, 0, 0))

OrientedPosition = namedtuple(
    "OrientedPosition",
    ["position", "direction"],
    defaults=(Position(0, 0), Direction.RIGHT),
)


def vector_sum(a: Vector, b: Vector) -> Vector:
    return tuple(map(sum, zip(a, b)))


def line_generator(lines):
    for line in lines:
        yield line.strip()


def get_vector(start: Position, end: Position) -> Vector:
    return end.row - start.row, end.col - start.col


def move(position: Position, direction: Direction, steps: int = 1) -> Position:
    direction_to_move_map = {
        Direction.UP: move_up,
        Direction.DOWN: move_down,
        Direction.LEFT: move_left,
        Direction.RIGHT: move_right,
    }
    return direction_to_move_map[direction](position, steps)


def move_to_position(position: Position, vector: Vector) -> Position:
    return Position(position.row + vector[0], position.col + vector[1])


def move_up(position: Position, steps: int = 1) -> Position:
    return Position(position.row - steps, position.col)


def move_down(position: Position, steps: int = 1) -> Position:
    return Position(position.row + steps, position.col)


def move_left(position: Position, steps: int = 1) -> Position:
    return Position(position.row, position.col - steps)


def move_right(position: Position, steps: int = 1) -> Position:
    return Position(position.row, position.col + steps)


def turn_right(direction: Direction) -> Direction:
    match direction:
        case Direction.UP:
            return Direction.RIGHT
        case Direction.RIGHT:
            return Direction.DOWN
        case Direction.DOWN:
            return Direction.LEFT
        case Direction.LEFT:
            return Direction.UP


def is_in_matrix(position: Position, playground_size: int) -> bool:
    return 0 <= position.row < playground_size and 0 <= position.col < playground_size


def distance(p1: Position, p2: Position) -> float:
    return math.sqrt((p1.row - p2.row) ** 2 + (p1.col - p2.col) ** 2)
