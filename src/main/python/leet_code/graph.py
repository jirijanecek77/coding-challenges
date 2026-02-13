import heapq
import math
from bisect import bisect_left
from collections import defaultdict, deque
from functools import lru_cache
from itertools import starmap


# https://leetcode.com/problems/lexicographically-smallest-string-after-applying-operations/description/?envType=daily-question&envId=2025-10-19
def findLexSmallestString(s: str, a: int, b: int) -> str:
    queue = [s]
    visited = set()
    while queue:
        node = queue.pop()
        if node in visited:
            continue
        visited.add(node)

        # add
        queue.append(
            "".join(
                str((ord(x) - ord("0") + a) % 10) if i % 2 == 1 else x
                for i, x in enumerate(node)
            )
        )

        # rotate
        queue.append(node[-b:] + node[:-b])
    return min(visited)


def test_findLexSmallestString():
    assert findLexSmallestString("5525", a=9, b=2) == "2050"
    assert findLexSmallestString(s="0011", a=4, b=2) == "0011"


# https://leetcode.com/problems/swim-in-rising-water/description/
def swimInWater(grid: list[list[int]]) -> int:
    # dijkstra
    n = len(grid)
    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]

    # Start and end positions
    start = (0, 0)
    end = (n - 1, n - 1)

    queue = [(grid[0][0], start)]
    visited = set()

    while queue:
        max_high, current_pos = heapq.heappop(queue)
        if current_pos == end:
            return max_high

        if current_pos in visited:
            continue
        visited.add(current_pos)

        # Explore neighbors
        for dr, dc in directions:
            next_row, next_col = current_pos[0] + dr, current_pos[1] + dc

            # Check bounds and if the cell is not blocked/visited
            if (
                0 <= next_row < n
                and 0 <= next_col < n
                and (next_row, next_col) not in visited
            ):
                heapq.heappush(
                    queue,
                    (max(max_high, grid[next_row][next_col]), (next_row, next_col)),
                )

    # If the queue is exhausted, there's no path
    return -1


def test_swimInWater():
    assert (
        swimInWater(
            [
                [
                    375,
                    396,
                    190,
                    333,
                    304,
                    65,
                    99,
                    262,
                    214,
                    344,
                    167,
                    328,
                    124,
                    207,
                    253,
                    173,
                    55,
                    243,
                    132,
                    163,
                ],
                [
                    22,
                    12,
                    223,
                    298,
                    387,
                    238,
                    237,
                    213,
                    332,
                    379,
                    228,
                    128,
                    280,
                    225,
                    103,
                    114,
                    109,
                    64,
                    271,
                    172,
                ],
                [
                    359,
                    26,
                    80,
                    18,
                    370,
                    372,
                    206,
                    346,
                    342,
                    363,
                    184,
                    11,
                    393,
                    317,
                    291,
                    362,
                    194,
                    308,
                    274,
                    188,
                ],
                [
                    288,
                    158,
                    153,
                    260,
                    278,
                    296,
                    40,
                    231,
                    397,
                    334,
                    4,
                    7,
                    181,
                    219,
                    189,
                    101,
                    54,
                    112,
                    292,
                    25,
                ],
                [
                    391,
                    195,
                    165,
                    268,
                    248,
                    388,
                    143,
                    266,
                    87,
                    250,
                    204,
                    358,
                    187,
                    275,
                    32,
                    127,
                    66,
                    115,
                    146,
                    234,
                ],
                [
                    313,
                    218,
                    8,
                    19,
                    50,
                    164,
                    279,
                    23,
                    182,
                    73,
                    392,
                    74,
                    149,
                    323,
                    107,
                    283,
                    203,
                    302,
                    148,
                    28,
                ],
                [
                    326,
                    162,
                    301,
                    41,
                    131,
                    306,
                    96,
                    200,
                    160,
                    44,
                    137,
                    300,
                    398,
                    170,
                    94,
                    309,
                    38,
                    16,
                    83,
                    129,
                ],
                [
                    245,
                    339,
                    72,
                    310,
                    117,
                    140,
                    264,
                    366,
                    252,
                    314,
                    361,
                    282,
                    230,
                    353,
                    325,
                    374,
                    180,
                    351,
                    68,
                    77,
                ],
                [
                    205,
                    340,
                    367,
                    169,
                    209,
                    255,
                    221,
                    152,
                    226,
                    354,
                    381,
                    319,
                    285,
                    136,
                    138,
                    175,
                    389,
                    273,
                    35,
                    142,
                ],
                [
                    286,
                    249,
                    395,
                    81,
                    390,
                    37,
                    303,
                    338,
                    220,
                    71,
                    242,
                    399,
                    82,
                    176,
                    168,
                    263,
                    299,
                    51,
                    1,
                    125,
                ],
                [
                    198,
                    355,
                    133,
                    15,
                    210,
                    151,
                    376,
                    294,
                    3,
                    235,
                    336,
                    76,
                    113,
                    281,
                    122,
                    110,
                    257,
                    343,
                    246,
                    159,
                ],
                [
                    394,
                    92,
                    384,
                    316,
                    123,
                    211,
                    324,
                    329,
                    5,
                    284,
                    212,
                    239,
                    48,
                    224,
                    196,
                    9,
                    236,
                    119,
                    147,
                    233,
                ],
                [
                    20,
                    24,
                    331,
                    183,
                    90,
                    45,
                    106,
                    201,
                    70,
                    276,
                    186,
                    320,
                    13,
                    256,
                    202,
                    91,
                    352,
                    69,
                    178,
                    116,
                ],
                [
                    105,
                    10,
                    121,
                    86,
                    111,
                    385,
                    98,
                    139,
                    141,
                    269,
                    100,
                    95,
                    327,
                    130,
                    287,
                    58,
                    382,
                    126,
                    297,
                    191,
                ],
                [
                    272,
                    364,
                    222,
                    330,
                    53,
                    232,
                    118,
                    97,
                    63,
                    365,
                    322,
                    36,
                    185,
                    270,
                    47,
                    277,
                    241,
                    49,
                    373,
                    369,
                ],
                [
                    75,
                    0,
                    57,
                    193,
                    199,
                    357,
                    43,
                    60,
                    145,
                    360,
                    267,
                    134,
                    120,
                    29,
                    337,
                    349,
                    161,
                    62,
                    254,
                    350,
                ],
                [
                    348,
                    240,
                    78,
                    311,
                    371,
                    318,
                    17,
                    259,
                    335,
                    251,
                    31,
                    88,
                    341,
                    14,
                    39,
                    85,
                    217,
                    108,
                    293,
                    144,
                ],
                [
                    290,
                    46,
                    56,
                    104,
                    156,
                    356,
                    229,
                    377,
                    177,
                    347,
                    261,
                    52,
                    179,
                    345,
                    289,
                    135,
                    59,
                    34,
                    265,
                    295,
                ],
                [
                    386,
                    380,
                    27,
                    2,
                    6,
                    307,
                    192,
                    378,
                    33,
                    89,
                    84,
                    30,
                    102,
                    321,
                    197,
                    157,
                    61,
                    368,
                    67,
                    383,
                ],
                [
                    154,
                    312,
                    155,
                    215,
                    21,
                    315,
                    216,
                    150,
                    93,
                    79,
                    305,
                    174,
                    42,
                    227,
                    208,
                    244,
                    247,
                    258,
                    166,
                    171,
                ],
            ]
        )
        == 375
    )
    assert (
        swimInWater(
            [
                [0, 1, 2, 3, 4],
                [24, 23, 22, 21, 5],
                [12, 13, 14, 15, 16],
                [11, 17, 18, 19, 20],
                [10, 9, 8, 7, 6],
            ]
        )
        == 16
    )
    assert swimInWater([[0, 2], [1, 3]]) == 3


def minReorder(n: int, connections: list[list[int]]) -> int:
    graph = defaultdict(list)

    for u, v in connections:
        # Road from u to v
        graph[u].append((v, 1))
        # Reverse road from v to u
        graph[v].append((u, 0))

    def dfs(node, parent):
        change_count = 0
        for neighbor, direction in graph[node]:
            if neighbor != parent:
                # Count the edges that need to be changed
                change_count += direction
                change_count += dfs(neighbor, node)
        return change_count

    return dfs(0, -1)


def test_minReorder():
    assert minReorder(n=6, connections=[[0, 1], [1, 3], [2, 3], [4, 0], [4, 5]]) == 3


def orangesRotting(grid: list[list[int]]) -> int:
    if all(cell == 0 for row in grid for cell in row):
        return 0
    rotten = []
    for i, row in enumerate(grid):
        for j, cell in enumerate(row):
            if cell == 2:
                rotten.append((i, j))

    directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]
    minutes = -1
    queue = deque(rotten)
    while queue:
        minutes += 1
        n = len(queue)
        for _ in range(n):
            i, j = queue.popleft()
            for dr, dc in directions:
                ni, nj = i + dr, j + dc
                if 0 <= ni < len(grid) and 0 <= nj < len(grid[0]) and grid[ni][nj] == 1:
                    grid[ni][nj] = 2
                    queue.append((ni, nj))

    return minutes if not any(cell == 1 for row in grid for cell in row) else -1


def test_orangesRotting():
    assert orangesRotting([[0]]) == 0
    assert orangesRotting([[2, 1, 1], [1, 1, 0], [0, 1, 1]]) == 4


def findAllPeople(n: int, meetings: list[list[int]], firstPerson: int) -> list[int]:
    result = {0}

    current_time = 0
    queue = sorted(
        [(0, 0, firstPerson), *((m[2], m[0], m[1]) for m in meetings)], reverse=True
    )
    while queue:
        time, person_from, person_to = queue.pop()

        if time >= current_time:
            if person_from in result or person_to in result:
                result.add(person_from)
                result.add(person_to)

        current_time = time

    return list(result)


def test_findAllPeople():
    assert findAllPeople(
        n=12,
        meetings=[
            [10, 8, 6],
            [9, 5, 11],
            [0, 5, 18],
            [4, 5, 13],
            [11, 6, 17],
            [0, 11, 10],
            [10, 11, 7],
            [5, 8, 3],
            [7, 6, 16],
            [3, 6, 10],
            [3, 11, 1],
            [8, 3, 2],
            [5, 0, 7],
            [3, 8, 20],
            [11, 0, 20],
            [8, 3, 4],
            [1, 9, 4],
            [10, 7, 11],
            [8, 10, 18],
        ],
        firstPerson=9,
    ) == [0, 1, 4, 5, 6, 9, 11]

    assert findAllPeople(
        n=6, meetings=[[1, 2, 5], [2, 3, 8], [1, 5, 10]], firstPerson=1
    ) == [0, 1, 2, 3, 5]


def sliding_puzzle(board: list[list[int]]) -> int:
    directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
    n = len(board)
    m = len(board[0])
    queue = deque([tuple(tuple(line) for line in board)])
    visited = set()

    nums = list(range(1, n * m)) + [0]
    target = tuple(tuple(nums[i * m : (i + 1) * m]) for i in range(n))

    res = 0
    while queue:
        q_len = len(queue)

        for _ in range(q_len):
            cur_board = queue.popleft()
            visited.add(cur_board)
            (i, j) = next(
                (i, j) for i in range(n) for j in range(m) if cur_board[i][j] == 0
            )

            if cur_board == target:
                return res

            for di, dj in directions:
                ni, nj = i + di, j + dj
                if 0 <= ni < n and 0 <= nj < m:
                    new_board = list(list(line) for line in cur_board)
                    new_board[ni][nj], new_board[i][j] = (
                        new_board[i][j],
                        new_board[ni][nj],
                    )
                    new_state = tuple(tuple(line) for line in new_board)
                    if new_state not in visited:
                        queue.append(new_state)
        res += 1
    return -1


def test_sliding_puzzle():
    assert sliding_puzzle(board=[[4, 1, 3], [2, 0, 5]]) == 5


def task_scheduling(tasks: list[str], requirements: list[list[str]]) -> list[str]:
    # topological sorting

    graph = {node: [] for node in tasks}
    for pre, post in requirements:
        graph[pre].append(post)

    res = []
    queue = deque()

    indegree = {node: 0 for node in graph}
    for neighbors in graph.values():
        for neighbor in neighbors:
            indegree[neighbor] += 1
    for node, degree in indegree.items():
        if degree == 0:
            queue.append(node)

    while queue:
        node = queue.popleft()
        res.append(node)
        for neighbor in graph[node]:
            indegree[neighbor] -= 1
            if indegree[neighbor] == 0:
                queue.append(neighbor)

    return res if len(res) == len(tasks) else None


def test_task_scheduling():
    assert task_scheduling(
        tasks=["a", "b", "c", "d"], requirements=[["a", "b"], ["c", "b"], ["b", "d"]]
    ) == ["a", "c", "b", "d"]


# https://leetcode.com/problems/last-day-where-you-can-still-cross/description/?envType=daily-question&envId=2025-12-31
def latestDayToCross(row: int, col: int, cells: list[list[int]]) -> int:
    def check(k):
        def dfs(i, j, visited):
            if not (0 < i <= row and 0 < j <= col) or (i, j) in visited:
                return False
            if i == row:
                return True
            visited.add((i, j))
            return any(
                starmap(
                    dfs, ((i, j + 1, visited), (i + 1, j, visited), (i, j - 1, visited))
                )
            )

        return not any(dfs(1, j + 1, {*map(tuple, cells[: k + 1])}) for j in range(col))

    return bisect_left(range(len(cells)), x=True, key=check)


def test_latestDayToCross():
    assert (
        latestDayToCross(
            row=6,
            col=2,
            cells=[
                [4, 2],
                [6, 2],
                [2, 1],
                [4, 1],
                [6, 1],
                [3, 1],
                [2, 2],
                [3, 2],
                [1, 1],
                [5, 1],
                [5, 2],
                [1, 2],
            ],
        )
        == 3
    )
    assert latestDayToCross(row=2, col=2, cells=[[1, 1], [1, 2], [2, 1], [2, 2]]) == 1
    assert (
        latestDayToCross(
            row=3,
            col=3,
            cells=[
                [1, 2],
                [2, 1],
                [3, 3],
                [2, 2],
                [1, 1],
                [1, 3],
                [2, 3],
                [3, 2],
                [3, 1],
            ],
        )
        == 3
    )


# https://leetcode.com/problems/minimum-cost-path-with-edge-reversals/?envType=daily-question&envId=2026-01-27
def minCost(n: int, edges: list[list[int]]) -> int:
    graph = defaultdict(list)

    for u, v, w in edges:
        graph[u].append((v, w))
        graph[v].append((u, 2 * w))

    # Initialize distance array from 0 node
    distances = [math.inf] * n
    distances[0] = 0

    # Dijkstra - Uniform Cost Search
    heap = [(0, 0)]
    while heap:
        distance, node = heapq.heappop(heap)
        if node == n - 1:
            return distance

        for neighbor, weight in graph[node]:
            new_distance = distance + weight
            if new_distance < distances[neighbor]:
                distances[neighbor] = new_distance
                heapq.heappush(heap, (new_distance, neighbor))

    return -1


def test_minCost():
    assert minCost(n=4, edges=[[0, 1, 3], [3, 1, 1], [2, 3, 4], [0, 2, 2]]) == 5


def minimumCost(
    source: str, target: str, original: list[str], changed: list[str], cost: list[int]
) -> int:
    graph = defaultdict(list)
    for o, ch, c in zip(original, changed, cost):
        i = ord(o) - ord("a")
        j = ord(ch) - ord("a")
        if i in graph:
            curr = next(filter(lambda x: x[0] == j, graph[i]), None)
            if curr:
                new_cost = min(curr[1], c)
                graph[i].remove((j, c))
                graph[i].append((j, new_cost))
            else:
                graph[i].append((j, c))
        else:
            graph[i].append((j, c))

    @lru_cache(None)
    def dijkstra(s: int, t: int) -> int:
        distances = [math.inf] * 26
        distances[s] = 0

        heap = [(0, s)]
        while heap:
            distance, node = heapq.heappop(heap)

            if node == t:
                return distance

            for neighbor, weight in graph[node]:
                new_distance = distance + weight
                if new_distance < distances[neighbor]:
                    distances[neighbor] = new_distance
                    heapq.heappush(heap, (new_distance, neighbor))
        return -1

    res = 0
    for s, t in zip(source, target):
        if s != t:
            dist = dijkstra(ord(s) - ord("a"), ord(t) - ord("a"))
            if dist == -1:
                break
            else:
                res += dist
    else:
        return res
    return -1


def test_minimumCost():
    assert (
        minimumCost(
            source="abcd",
            target="acbe",
            original=["a", "b", "c", "c", "e", "d"],
            changed=["b", "c", "b", "e", "b", "e"],
            cost=[2, 5, 5, 1, 2, 20],
        )
        == 28
    )
    assert (
        minimumCost(
            source="abcd", target="abce", original=["a"], changed=["e"], cost=[10000]
        )
        == -1
    )
