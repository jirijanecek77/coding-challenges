from utils import line_generator

filename = "data/aoc_18.txt"

from collections import deque


def shortest_path(obstacles: list[tuple[int, int]], n: int) -> int:
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]

    # Start and end positions
    start = (0, 0)
    end = (n - 1, n - 1)

    # BFS queue
    queue = deque([(start, 0)])  # (current_cell, distance_from_start)
    visited = set()
    visited.add(start)

    while queue:
        (current_row, current_col), steps = queue.popleft()

        if (current_row, current_col) == end:
            return steps

        # Explore neighbors
        for dr, dc in directions:
            next_row, next_col = current_row + dr, current_col + dc

            # Check bounds and if the cell is not blocked/visited
            if 0 <= next_row < n and 0 <= next_col < n:
                if (next_row, next_col) not in obstacles and (
                    next_row,
                    next_col,
                ) not in visited:
                    queue.append(((next_row, next_col), steps + 1))
                    visited.add((next_row, next_col))

    # If the queue is exhausted, there's no path
    return 0


def solve_01():
    with open(filename) as file:
        obstacles = list()
        n = 70
        for index, line in enumerate(line_generator(file.readlines())):
            x, y = line.strip().split(",")
            obstacles.append((int(x), int(y)))

        result_index = find_by_bisection(n, obstacles)
        print(obstacles[result_index])


def find_by_bisection(n: int, obstacles: list[tuple[int, int]]) -> int:
    left = 0
    right = len(obstacles)
    while left < right - 1:
        mid = (left + right) // 2 + 1
        if shortest_path(obstacles[0:mid], n + 1) == 0:
            right = (left + right) // 2
        else:
            left = (left + right) // 2
    return right


if __name__ == "__main__":
    solve_01()
