import heapq


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


# https://leetcode.com/problems/number-of-provinces/description/
def findCircleNum(is_connected: list[list[int]]) -> int:
    n = len(is_connected)
    disjoint_sets = UnionSet(n)

    for i in range(n):
        for j in range(i + 1, n):
            if is_connected[i][j] == 1:
                disjoint_sets.union(i, j)

    return sum(disjoint_sets.parent[i] == i for i in range(len(disjoint_sets.parent)))


def test_findCircleNum():
    assert (
        findCircleNum(
            is_connected=[
                [1, 1, 1, 0, 1, 1, 1, 0, 0, 0],
                [1, 1, 0, 0, 0, 0, 0, 1, 0, 0],
                [1, 0, 1, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 1, 1, 0, 0, 0, 1, 0],
                [1, 0, 0, 1, 1, 0, 0, 0, 0, 0],
                [1, 0, 0, 0, 0, 1, 0, 0, 0, 0],
                [1, 0, 0, 0, 0, 0, 1, 0, 1, 0],
                [0, 1, 0, 0, 0, 0, 0, 1, 0, 1],
                [0, 0, 0, 1, 0, 0, 1, 0, 1, 1],
                [0, 0, 0, 0, 0, 0, 0, 1, 1, 1],
            ]
        )
        == 1
    )


# https://leetcode.com/problems/power-grid-maintenance/description/
def processQueries(
    c: int, connections: list[list[int]], queries: list[list[int]]
) -> list[int]:
    disjoint_sets = UnionSet(c + 1)

    for conn in connections:
        disjoint_sets.union(conn[0], conn[1])

    heaps = {}
    for i in range(1, c + 1):
        group = disjoint_sets.find(i)
        if group not in heaps:
            heaps[group] = []
        heaps[group].append(i)
    for connected_stations in heaps.values():
        heapq.heapify(connected_stations)

    online_stations = [True] * (c + 1)

    output = []
    for command, station in queries:
        if command == 1:
            if online_stations[station]:
                output.append(station)
            else:
                group = disjoint_sets.find(station)
                connected_stations = heaps.get(group, [])
                while connected_stations and not online_stations[connected_stations[0]]:
                    heapq.heappop(connected_stations)
                output.append(connected_stations[0] if connected_stations else -1)
        if command == 2:
            online_stations[station] = False
    return output


def test_processQueries():
    assert processQueries(1, [], [[1, 1], [2, 1], [2, 1], [2, 1], [2, 1]]) == [1]
    assert processQueries(
        c=5,
        connections=[[1, 2], [2, 3], [3, 4], [4, 5]],
        queries=[[1, 3], [2, 1], [1, 1], [2, 2], [1, 2]],
    ) == [3, 2, 3]


def partitionLabels(s: str) -> list[int]:
    last_occurence = {c: i for i, c in enumerate(s)}
    start, end = 0, 0
    result = []
    for i, c in enumerate(s):
        end = max(end, last_occurence[c])
        if i == end:
            result.append(end - start + 1)
            start = end + 1
    return result


def test_partitionLabels():
    assert partitionLabels("ababcbacadefegdehijhklij") == [9, 7, 8]
