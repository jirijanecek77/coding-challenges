import re

from utils import line_generator, Position, Vector

filename = "data/aoc_14.txt"
max_x = 101
max_y = 103


def parse_line(line: str) -> tuple[Position, Vector]:
    pattern = r"p\=(-?\d+),(-?\d+)\s*v\=(-?\d+),(-?\d+)"

    # Search using regex
    match = re.search(pattern, line)

    return (int(match.group(1)), int(match.group(2))), (
        int(match.group(3)),
        int(match.group(4)),
    )


def move_to_position(position: Position, vector: Vector) -> Position:
    return (position[0] + vector[0] + max_x) % max_x, (
        position[1] + vector[1] + max_y
    ) % max_y


def check(robots) -> bool:
    data = sorted([robot[0] for robot in robots])
    for row in range(max_y):
        cols = [col for col, r in data if r == row]
        count = 1
        for i in range(1, len(cols)):
            if cols[i] == cols[i - 1] + 1:
                count += 1  # Increment the count for consecutive numbers
                # Check if the count is 10
                if count >= 10:
                    return True
            # If the sequence breaks, reset the count
            elif cols[i] != cols[i - 1]:
                count = 1
    return False


def solve_01():
    with open(filename) as file:
        robots = [parse_line(line) for line in line_generator(file.readlines())]

        for i in range(10000):
            robots = [
                (move_to_position(robot[0], robot[1]), robot[1]) for robot in robots
            ]

            if check(robots):
                print(i + 1)
                for col in range(max_x):
                    for row in range(max_y):
                        print(
                            (
                                "X"
                                if any(robot[0] == (col, row) for robot in robots)
                                else "."
                            ),
                            end="",
                        )
                    print("\n", end="")

    q1 = len(
        list(
            filter(
                lambda robot: robot[0][0] < max_x // 2 and robot[0][1] < max_y // 2,
                robots,
            )
        )
    )
    q2 = len(
        list(
            filter(
                lambda robot: robot[0][0] > max_x // 2 and robot[0][1] < max_y // 2,
                robots,
            )
        )
    )
    q3 = len(
        list(
            filter(
                lambda robot: robot[0][0] < max_x // 2 and robot[0][1] > max_y // 2,
                robots,
            )
        )
    )
    q4 = len(
        list(
            filter(
                lambda robot: robot[0][0] > max_x // 2 and robot[0][1] > max_y // 2,
                robots,
            )
        )
    )

    print(q1 * q2 * q3 * q4)


if __name__ == "__main__":
    solve_01()
