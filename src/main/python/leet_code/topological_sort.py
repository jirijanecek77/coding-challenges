from collections import defaultdict, deque
from typing import Iterable


def canFinish(numCourses: int, prerequisites: list[list[int]]) -> bool:
    # https://leetcode.com/problems/course-schedule/
    graph = defaultdict(list)
    in_degree = defaultdict(int)

    for from_node, to_node in prerequisites:
        graph[from_node].append(to_node)
        in_degree[to_node] += 1

    queue = deque([course for course in range(numCourses) if in_degree[course] == 0])
    topo_order = []
    while queue:
        course = queue.popleft()
        topo_order.append(course)
        for next_course in graph[course]:
            in_degree[next_course] -= 1
            if in_degree[next_course] == 0:
                queue.append(next_course)

    return len(topo_order) == numCourses


def test_canFinish():
    assert (
        canFinish(
            20, [[0, 10], [3, 18], [5, 5], [6, 11], [11, 14], [13, 1], [15, 1], [17, 4]]
        )
        == False
    )
    assert canFinish(2, [[1, 0]]) == True
    assert canFinish(2, [[1, 0], [0, 1]]) == False


def longestIncreasingPath(matrix: list[list[int]]) -> int:
    # https://leetcode.com/problems/longest-increasing-path-in-a-matrix/description/

    rows = len(matrix)
    cols = len(matrix[0])
    in_degree = defaultdict(int)

    def get_neighbors(r: int, c: int) -> Iterable[tuple[int, int]]:
        dt = [(0, 1), (0, -1), (-1, 0), (1, 0)]
        return filter(
            lambda x: 0 <= x[0] < rows
            and 0 <= x[1] < cols
            and matrix[x[0]][x[1]] > matrix[r][c],
            map(lambda x: (x[0] + r, x[1] + c), dt),
        )

    for row in range(rows):
        for col in range(cols):
            in_degree[(row, col)] = 0
    for row in range(rows):
        for col in range(cols):
            for neigh in get_neighbors(row, col):
                in_degree[neigh] += 1

    queue = deque([key for key, val in in_degree.items() if val == 0])
    result = 0
    while queue:
        n = len(queue)
        result += 1
        for _ in range(n):
            cell = queue.popleft()
            for neigh in get_neighbors(cell[0], cell[1]):
                in_degree[neigh] -= 1
                if in_degree[neigh] == 0:
                    queue.append(neigh)

    return result


def test_longestIncreasingPath():
    assert longestIncreasingPath([[9, 9, 4], [6, 6, 8], [2, 1, 1]]) == 4


def countPaths(matrix: list[list[int]]) -> int:
    # https://leetcode.com/problems/number-of-increasing-paths-in-a-grid/

    MOD = 10**9 + 7
    rows = len(matrix)
    cols = len(matrix[0])
    in_degree = defaultdict(int)

    def get_neighbors(r: int, c: int) -> Iterable[tuple[int, int]]:
        dt = [(0, 1), (0, -1), (-1, 0), (1, 0)]
        return filter(
            lambda x: 0 <= x[0] < rows
            and 0 <= x[1] < cols
            and matrix[x[0]][x[1]] > matrix[r][c],
            map(lambda x: (x[0] + r, x[1] + c), dt),
        )

    for row in range(rows):
        for col in range(cols):
            in_degree[(row, col)] = 0
    for row in range(rows):
        for col in range(cols):
            for neigh in get_neighbors(row, col):
                in_degree[neigh] += 1

    dp = [[1] * cols for _ in range(rows)]
    queue = deque([key for key, val in in_degree.items() if val == 0])
    while queue:
        cell = queue.popleft()
        for neigh in get_neighbors(cell[0], cell[1]):
            in_degree[neigh] -= 1
            if in_degree[neigh] == 0:
                queue.append(neigh)
            row, col = neigh
            dp[row][col] += dp[cell[0]][cell[1]]
            dp[row][col] %= MOD

    result = 0
    for row in range(rows):
        for col in range(cols):
            result += dp[row][col]
            result %= MOD
    return result


def test_countPaths():
    assert countPaths([[1, 1], [3, 4]]) == 8


def task_scheduling(tasks: list[str], requirements: list[list[str]]) -> list[str]:
    # topological sorting

    graph = defaultdict(list)
    indegree = {task: 0 for task in tasks}

    for pre, post in requirements:
        graph[pre].append(post)
        indegree[post] += 1

    topo_order = []
    queue = deque([key for key, value in indegree.items() if value == 0])

    while queue:
        node = queue.popleft()
        topo_order.append(node)
        for neighbor in graph[node]:
            indegree[neighbor] -= 1
            if indegree[neighbor] == 0:
                queue.append(neighbor)

    return topo_order if len(topo_order) == len(tasks) else []


def test_task_scheduling():
    assert task_scheduling(
        tasks=["a", "b", "c", "d"], requirements=[["a", "b"], ["c", "b"], ["b", "d"]]
    ) == ["a", "c", "b", "d"]
