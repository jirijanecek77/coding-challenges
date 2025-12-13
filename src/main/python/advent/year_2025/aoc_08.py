import time
from itertools import combinations, starmap

filename = "data/aoc_08.txt"

test_data = [
    "162,817,812",
    "57,618,57",
    "906,360,560",
    "592,479,940",
    "352,342,300",
    "466,668,158",
    "542,29,236",
    "431,825,988",
    "739,650,466",
    "52,470,668",
    "216,146,977",
    "819,987,18",
    "117,168,530",
    "805,96,715",
    "346,949,466",
    "970,615,88",
    "941,993,340",
    "862,61,35",
    "984,92,344",
    "425,690,689",
]


def read_data(lines):
    return list(tuple(map(int, line.split(","))) for line in lines)


class UnionSet:
    def __init__(self, n: int):
        self.parent = list(range(n))
        self.rank = [1] * n

    def find(self, i: int) -> int:
        # If i itself is root or representative
        if self.parent[i] == i:
            return i

        # Else recursively find the representative
        # of the parent
        return self.find(self.parent[i])

    def union(self, i: int, j: int):
        root_i = self.find(i)
        root_j = self.find(j)

        if root_i == root_j:
            return

        if self.rank[root_i] < self.rank[root_j]:
            root_i, root_j = root_j, root_i

        self.parent[root_j] = root_i
        self.rank[root_i] += self.rank[root_j]


def euclidean_distance(p1: tuple[int, int, int], p2: tuple[int, int, int]) -> int:
    return sum(starmap(lambda a, b: (a - b) ** 2, zip(p1, p2)))


def sort_boxes(boxes: list[tuple[int, int, int]]) -> list[tuple[int, int]]:
    n = len(boxes)
    return sorted(
        combinations(range(n), 2),
        key=lambda indices: euclidean_distance(boxes[indices[0]], boxes[indices[1]]),
    )


def calculate(boxes: list[tuple[int, int, int]]) -> int:
    n = len(boxes)
    union_set = UnionSet(n)
    for i, j in sort_boxes(boxes):
        union_set.union(i, j)
        if max(union_set.rank) == n:
            return boxes[i][0] * boxes[j][0]
    raise Exception("No solution found")


if __name__ == "__main__":
    assert calculate(read_data(test_data)) == 25272

    with open(filename) as file:
        start = time.perf_counter()

        print(calculate(read_data(file.readlines())))

        print(f"Elapsed time: {time.perf_counter() - start}")
