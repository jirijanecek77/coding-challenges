import heapq
from collections import Counter

from utils import line_generator, Position, move_up, move_down, move_right, move_left

filename = "data/aoc_20.txt"

STONE = -1
MAX_STEPS = 20
MIN_SAVE = 100


def plot(data: list[list[int]]):
    for row in data:
        print("|".join(map(lambda i: "##" if i == STONE else str(i).rjust(2, ' '), row)))


def find_shortest_distances_for_path_part(playground: list[list[int]], start: Position) -> dict[Position, int]:
    distances = {}
    priority_queue = []
    heapq.heappush(priority_queue, (0, start))
    result = {}

    while priority_queue:
        current_distance, pos = heapq.heappop(priority_queue)
        if current_distance > MAX_STEPS:
            continue

        if playground[pos.row][pos.col] - playground[start.row][start.col] > 0:
            new_distance = playground[pos.row][pos.col] - playground[start.row][start.col] - current_distance
            result[pos] = min(result[pos], new_distance) if pos in result else new_distance

        for neighbor in [move_up(pos), move_down(pos), move_left(pos), move_right(pos)]:
            if 0 <= neighbor.row < len(playground) and 0 <= neighbor.col < len(playground[neighbor.row]):
                new_distance = current_distance + 1
                if neighbor in distances:
                    distances[neighbor] = min(new_distance, distances[neighbor])
                else:
                    distances[neighbor] = new_distance
                    heapq.heappush(priority_queue, (new_distance, neighbor))

    return result


def solve_01():
    with (open(filename) as file):
        playground = []
        generator = line_generator(file.readlines())
        start = None
        end = None
        for row_index, line in enumerate(generator):
            line_data = list(line)
            if "S" in line_data:
                start = Position(row_index, line_data.index("S"))
            if "E" in line_data:
                end = Position(row_index, line_data.index("E"))
            playground.append([STONE if col == "#" else 0 for col in line_data])

        # add distance from start to each path step
        pos = start
        i = 1
        while pos != end:
            playground[pos.row][pos.col] = i
            pos = next((p for p in [move_up(pos), move_down(pos), move_left(pos), move_right(pos)] if playground[p.row][p.col] == 0), None)
            i += 1
        playground[pos.row][pos.col] = i

        plot(playground)

        result = []
        for row_index in range(1, len(playground) - 1):
            for col_index in range(1, len(playground[row_index]) - 1):
                if playground[row_index][col_index] != STONE:
                    pos = Position(row_index, col_index)
                    # print(f"Checking {pos}")
                    distances = find_shortest_distances_for_path_part(playground, pos)
                    distances = {p: distance for p, distance in distances.items() if playground[p.row][p.col] != STONE}
                    # print(distances)
                    result.extend(filter(lambda d: d >= MIN_SAVE, distances.values()))

        print(sum(Counter(result).values()))


if __name__ == "__main__":
    solve_01()
