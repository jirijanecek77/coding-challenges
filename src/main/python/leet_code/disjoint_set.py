class DisjointUnionSets:
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

        if self.rank[root_i] > self.rank[root_j]:
            self.parent[root_j] = root_i
            self.rank[root_i] += self.rank[root_j]
        else:
            self.parent[root_i] = root_j
            self.rank[root_j] += self.rank[root_i]


# https://leetcode.com/problems/number-of-provinces/description/
def findCircleNum(is_connected: list[list[int]]) -> int:
    n = len(is_connected)
    disjoint_sets = DisjointUnionSets(n)

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
