from collections import deque
from typing import Optional


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


# https://leetcode.com/problems/generate-parentheses/
def generate_parenthesis(n: int) -> list[str]:
    def dfs(left: int, right: int, s: str):
        result = []
        if right == 0:
            result.append(s)
        if left > 0:
            result.extend(dfs(left - 1, right, s + "("))
        if right > left:
            result.extend(dfs(left, right - 1, s + ")"))
        return result

    return dfs(n, n, "")


def dfs(root: Optional[TreeNode], target: int, seen: set[int]) -> bool:
    if not root:
        return False

    if target - root.val in seen:
        return True
    seen.add(root.val)
    return dfs(root.left, target, seen) or dfs(root.right, target, seen)


# https://leetcode.com/problems/two-sum-iv-input-is-a-bst/description/
def findTarget(root: Optional[TreeNode], k: int) -> bool:
    return dfs(root, k, set())


def test_find_target():
    assert (
        findTarget(
            root=TreeNode(
                val=5,
                left=TreeNode(3, TreeNode(2)),
                right=TreeNode(val=6, right=TreeNode(7)),
            ),
            k=9,
        )
        == True
    )


def largestValues(root: Optional[TreeNode]) -> list[int]:
    results = []

    def dfs(node: Optional[TreeNode], level: int):
        if not node:
            return

        if level > len(results) - 1:
            results.append(node.val)
        else:
            results[level] = max(results[level], node.val)

        dfs(node.right, level + 1)
        dfs(node.left, level + 1)

    dfs(root, 0)
    return results


def test_largest_values():
    assert largestValues(
        root=TreeNode(
            val=1,
            left=TreeNode(3, TreeNode(5), TreeNode(3)),
            right=TreeNode(val=2, right=TreeNode(9)),
        )
    ) == [1, 3, 9]


def permute(nums: list[int]) -> list[list[int]]:
    def recursive(
        arr: list[int], perm: list[int], res: list[list[int]]
    ) -> list[list[int]]:
        if not arr:
            res.append(perm.copy())
            return res

        for i in range(len(arr)):
            perm.append(arr[i])
            recursive(arr[:i] + arr[i + 1 :], perm, res)
            perm.pop()
        return res

    return recursive(nums, [], [])


def test_permute():
    assert permute([1, 2, 3]) == [
        [1, 2, 3],
        [1, 3, 2],
        [2, 1, 3],
        [2, 3, 1],
        [3, 1, 2],
        [3, 2, 1],
    ]


def calculate(s: str) -> int:
    stack = []
    num = 0
    prev_operator = "+"

    for i in range(len(s) + 1):
        ch = s[i] if i < len(s) else "\0"

        if ch.isdigit():
            num = num * 10 + int(ch)

        if not ch.isdigit() and ch != " " or i == len(s):
            if prev_operator == "+":
                stack.append(num)
            if prev_operator == "-":
                stack.append(-num)
            if prev_operator == "*":
                stack.append(stack.pop() * num)
            if prev_operator == "/":
                stack.append(int(stack.pop() / num))

            prev_operator = ch
            num = 0

    return sum(stack)


def test_calculate():
    assert calculate("1+2 *3") == 7


def find_cousins(root: Optional[TreeNode], x: int, y: int) -> bool:
    def dfs(
        node: Optional[TreeNode], parent: Optional[TreeNode], level: int, target: int
    ):
        if not node:
            return None

        if node.val == target:
            return level, parent

        return dfs(node.left, node, level + 1, target) or dfs(
            node.right, node, level + 1, target
        )

    level_x, parent_x = dfs(root, None, 0, x)
    level_y, parent_y = dfs(root, None, 0, y)

    return parent_x and parent_y and parent_x != parent_y and level_x == level_y


def find_cousins_bfs(root: Optional[TreeNode], x: int, y: int) -> bool:
    queue = deque([(root, None)])
    while queue:
        parent_x = parent_y = None
        n = len(queue)
        for _ in range(n):
            node, parent = queue.popleft()
            if not node:
                continue
            if node.val == x:
                parent_x = parent
            elif node.val == y:
                parent_y = parent
            if parent_x and parent_y:
                return parent_x != parent_y
            queue.append((node.left, node))
            queue.append((node.right, node))
    return False


def test_find_cousins():
    assert find_cousins_bfs(
        TreeNode(1, TreeNode(2, TreeNode(4)), TreeNode(3, TreeNode(5), TreeNode(6))),
        4,
        5,
    )
    assert not find_cousins_bfs(
        TreeNode(1, TreeNode(2, TreeNode(4)), TreeNode(3, TreeNode(5), TreeNode(6))),
        2,
        5,
    )
    assert not find_cousins_bfs(
        TreeNode(1, TreeNode(2), TreeNode(3, TreeNode(4), TreeNode(5))), 4, 5
    )


def maxOverlappingEvents(events: list[list[int]]) -> int:
    events.sort()
    n = len(events)

    def dfs(idx: int) -> int:
        if idx == n:
            return 0

        start_time, end_time, value = events[idx]

        parallel_events = []
        next_idx = idx + 1
        while next_idx < n and events[next_idx][0] <= end_time:
            parallel_events.append(next_idx)
            next_idx += 1

        if not parallel_events:
            return value + dfs(next_idx)
        return max(value + dfs(next_idx), max(dfs(i) for i in parallel_events))

    return dfs(0)


def test_maxOverlappingEvents():
    assert (
        maxOverlappingEvents(
            events=[[10, 83, 53], [63, 87, 45], [97, 100, 32], [51, 61, 16]]
        )
        == 93
    )
    assert maxOverlappingEvents(events=[[1, 3, 2], [4, 5, 2], [2, 4, 3]]) == 4
