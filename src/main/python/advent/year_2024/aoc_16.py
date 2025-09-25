import heapq

from utils import line_generator, Position, Direction, move, OrientedPosition

filename = "data/aoc_16.txt"

STONE = -1


def plot(data: list[list[int]], paths: set[Position]):
    for row_index, row in enumerate(data):
        print(
            "".join(
                map(
                    lambda i: (
                        "#"
                        if i[1] == STONE
                        else ("O" if Position(row_index, i[0]) in paths else ".")
                    ),
                    enumerate(row),
                )
            )
        )


def backtrack_path(start: OrientedPosition, current: OrientedPosition, path, parents):
    print(current)
    if current == start:
        return path
    result = []
    for predecessor in parents[current]:
        result.extend(backtrack_path(start, predecessor, path + [current], parents))
    return result


def find_shortest_distance(playground: list[list[int]], start: OrientedPosition):
    distances = {start: 0}
    priority_queue = []
    heapq.heappush(priority_queue, (0, start))
    parents = {start: []}

    while priority_queue:
        current_distance, current_oriented_pos = heapq.heappop(priority_queue)

        for direction in Direction:
            neighbor = move(current_oriented_pos.position, Direction(direction))
            if (
                0 <= neighbor.row < len(playground)
                and 0 <= neighbor.col < len(playground[neighbor.row])
                and playground[neighbor.row][neighbor.col] != STONE
            ):
                if direction != current_oriented_pos.direction:
                    new_distance = current_distance + 1000
                    neighbor = OrientedPosition(
                        current_oriented_pos.position, direction
                    )
                else:
                    new_distance = current_distance + 1
                    neighbor = OrientedPosition(neighbor, direction)

                if neighbor in distances:
                    if new_distance == distances[neighbor]:
                        parents[neighbor].append(current_oriented_pos)
                    elif new_distance < distances[neighbor]:
                        parents[neighbor] = [current_oriented_pos]

                    distances[neighbor] = min(new_distance, distances[neighbor])
                else:
                    parents[neighbor] = [current_oriented_pos]
                    distances[neighbor] = new_distance
                    heapq.heappush(priority_queue, (new_distance, neighbor))

    return distances, parents


def solve_01():
    with open(filename) as file:
        playground = []
        generator = line_generator(file.readlines())
        start = None
        end = None
        for row_index, line in enumerate(generator):
            line_data = list(line)
            if "S" in line_data:
                start = OrientedPosition(
                    Position(row_index, line_data.index("S")), Direction.RIGHT
                )
            if "E" in line_data:
                end = Position(row_index, line_data.index("E"))
            playground.append([STONE if col == "#" else 0 for col in line_data])

        distances, parents = find_shortest_distance(playground, start)
        print(
            min(
                [
                    (distance, orient_pos.direction)
                    for orient_pos, distance in distances.items()
                    if orient_pos.position == end
                ]
            )
        )
        path = backtrack_path(
            start, OrientedPosition(end, Direction.RIGHT), [], parents
        )
        shortest_paths = set(map(lambda p: p.position, path))
        print(len(shortest_paths))

        plot(playground, shortest_paths)


if __name__ == "__main__":
    solve_01()
