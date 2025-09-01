from utils import line_generator, Position, move_up, move_down, move_left, move_right, is_in_playground

filename = "data/aoc_12.txt"


def get_edges(nums: list[int]) -> set[tuple[int, int]]:
    result = []
    for i in range(len(nums)):
        if i == 0:
            result.append((nums[i]))
        elif nums[i] - nums[i - 1] != 1:
            result.append(nums[i - 1])
            result.append(nums[i])

    result.append((nums[- 1]))
    return {(result[i - 1], e) for i, e in enumerate(result) if i % 2 == 1}


def test_get_edges():
    assert get_edges([0, 3, 4, 5, 6]) == {(0, 0), (3, 6)}
    assert get_edges([0, 1, 3, 5, 6, 7]) == {(0, 1), (3, 3), (5, 7)}
    assert get_edges([1, 3]) == {(1, 1), (3, 3)}


def draw(area: set[Position], min_row: int, max_row: int, min_col: int, max_col: int):
    print("", end='\n')
    print("".join(map(lambda x: str(x % 10), range(max_col + 1))), end='\n')

    for row in range(min_row, max_row + 1):
        for col in range(min_col, max_col + 1):
            if (col, row) in area:
                print("x", end="")
            else:
                print(".", end="")
        print("", end='\n')

    print("", end='\n')
    print("-----", end='\n')


def calc_common_edges(edge: tuple[int, int], last: set[tuple[int, int]]) -> int:
    last_starts = {e[0] for e in last}
    last_ends = {e[1] for e in last}
    result = 0
    if edge[0] not in last_starts:
        result += 1
    if edge[1] not in last_ends:
        result += 1
    return result


def calculate_sides(last_edges: set[tuple[int, int]], edges: set[tuple[int, int]]) -> int:
    return sum(calc_common_edges(e, last_edges) for e in edges - last_edges)


def calc_sides(area: set[Position]) -> int:
    total_sides = 0
    min_row = min([a[1] for a in area])
    max_row = max([a[1] for a in area])
    min_col = min([a[0] for a in area])
    max_col = max([a[0] for a in area])

    draw(area, min_row, max_row, min_col, max_col)
    last_edges = set()
    for row in range(min_row, max_row + 1):
        edges = get_edges(sorted([a[0] for a in area if a[1] == row]))
        total_sides += calculate_sides(last_edges, edges)
        last_edges = edges
    total_sides += calculate_sides(last_edges, set())

    last_edges = set()
    for col in range(min_col, max_col + 1):
        edges = get_edges(sorted([a[1] for a in area if a[0] == col]))
        total_sides += calculate_sides(last_edges, edges)
        last_edges = edges
    total_sides += calculate_sides(last_edges, set())

    return total_sides


def solve_01():
    with open(filename) as file:
        data = {}
        n = 0
        for row_index, line in enumerate(line_generator(file.readlines())):
            n = len(line)
            for col_index, val in enumerate(list(line)):
                data[(col_index, row_index)] = val

        result = 0
        while data:
            pos, val = list(data.items())[0]
            del data[pos]
            queue = [pos]
            area = set()
            perimeter_1 = 0

            while queue:
                p = queue.pop()
                area.add(p)
                for new_pos in [move_up(p), move_down(p), move_left(p), move_right(p)]:
                    if new_pos not in area and new_pos not in queue:
                        if is_in_playground(new_pos, n) and new_pos in data and data[new_pos] == val:
                            del data[new_pos]
                            queue.append(new_pos)
                        else:
                            perimeter_1 += 1

            perimeter_2 = calc_sides(area)
            print(f"area {val}: {area}")
            print(f"perimeter: {perimeter_2}")
            print(perimeter_2 * len(area))
            result += perimeter_2 * len(area)
        print('-----')
        print(result)


if __name__ == "__main__":
    solve_01()
