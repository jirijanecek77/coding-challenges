from collections import deque, defaultdict
from heapq import nlargest
from typing import Optional


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

    def __repr__(self):
        return f"Node({self.val})"

    def __eq__(self, value: object, /) -> bool:
        if not isinstance(value, TreeNode):
            return NotImplemented
        return (
            self.val == value.val
            and self.left == value.left
            and self.right == value.right
        )

    @classmethod
    def build(cls, level_order: list[int | None]) -> "TreeNode | None":
        if not level_order:
            return None

        values = iter(level_order)
        val = next(values, None)
        if val is None:
            return None

        root = cls(val)
        queue = deque([root])
        while queue:
            node = queue.popleft()
            left = next(values, None)
            if left is not None:
                node.left = cls(left)
                queue.append(node.left)
            right = next(values, None)
            if right is not None:
                node.right = cls(right)
                queue.append(node.right)
        return root


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
            root=TreeNode.build([5, 3, 6, 2, None, None, 7]),
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
    assert largestValues(root=TreeNode.build([1, 3, 2, 5, 3, None, 9])) == [1, 3, 9]


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
        TreeNode.build([1, 2, 3, 4, None, 5, 6]),
        4,
        5,
    )
    assert not find_cousins_bfs(
        TreeNode.build([1, 2, 3, 4, None, 5, 6]),
        2,
        5,
    )
    assert not find_cousins_bfs(TreeNode.build([1, 2, 3, None, None, 4, 5]), 4, 5)


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


def maxProduct(root: Optional[TreeNode]) -> int:
    sums = []

    def dfs(node: Optional[TreeNode]) -> int:
        if node is None:
            return 0
        s = node.val + dfs(node.left) + dfs(node.right)
        sums.append(s)
        return s

    total_sum = dfs(root)
    return max(
        map(lambda subtree_sum: (total_sum - subtree_sum) * subtree_sum, sums)
    ) % (10**9 + 7)


def test_maxProduct():
    assert maxProduct(TreeNode.build([1, 2, 3])) == 9


# https://leetcode.com/problems/k-th-largest-perfect-subtree-size-in-binary-tree/
def kthLargestPerfectSubtree(root: Optional[TreeNode], k: int) -> int:
    candidates = []

    def dfs(node) -> int:
        if not node:
            return 0

        left = dfs(node.left)
        right = dfs(node.right)

        if left == -1 or right == -1 or left != right:
            return -1

        candidates.append(1 + left + right)
        return 1 + left + right

    dfs(root)
    return nlargest(k, candidates)[-1] if len(candidates) >= k else -1


def test_kthLargestPerfectSubtree():
    assert (
        kthLargestPerfectSubtree(
            TreeNode.build([5, 3, 6, 5, 2, 5, 7, 1, 8, None, None, 8, 8]),
            2,
        )
        == 3
    )


def level_order_traversal(root: Optional[TreeNode]) -> list[list[int]]:
    if not root:
        return []

    queue = deque([root])

    res = []
    while queue:
        n = len(queue)

        level_nodes = []
        for _ in range(n):
            node = queue.popleft()
            level_nodes.append(node.val)
            if node.left:
                queue.append(node.left)
            if node.right:
                queue.append(node.right)

        res.append(level_nodes)
    return res


def test_level_order_traversal():
    assert level_order_traversal(TreeNode.build([1, 2, 3])) == [[1], [2, 3]]


def path_sum(root: Optional[TreeNode], target_sum: int) -> int:
    prefix_sums = defaultdict(int)
    prefix_sums[0] = 1

    def dfs(node, current_sum: int) -> int:
        if not node:
            return 0

        current_sum += node.val

        valid_paths_count = prefix_sums[current_sum - target_sum]

        prefix_sums[current_sum] += 1

        valid_paths_count += dfs(node.left, current_sum)
        valid_paths_count += dfs(node.right, current_sum)

        prefix_sums[current_sum] -= 1
        return valid_paths_count

    return dfs(root, 0)


def test_path_sum():
    assert (
        path_sum(
            TreeNode.build([10, 5, -3, 3, 2, None, 11, 3, -2, None, 1]),
            8,
        )
        == 3
    )


def subtreeWithAllDeepest(root: Optional[TreeNode]) -> Optional[TreeNode]:

    def dfs(node: Optional[TreeNode]) -> tuple[int, Optional[TreeNode]]:
        if not node:
            return 0, None

        left_depth, left_result = dfs(node.left)
        right_depth, right_result = dfs(node.right)

        if left_depth > right_depth:
            return left_depth + 1, left_result
        if right_depth > left_depth:
            return right_depth + 1, right_result
        return left_depth + 1, node

    _, result = dfs(root)
    return result


def test_subtreeWithAllDeepest():
    root = TreeNode.build([3, 5, 1, 6, 2, 0, 8, None, None, 7, 4])
    expected = root.left.right
    assert subtreeWithAllDeepest(root) == expected


def lowestCommonAncestorBST(root: TreeNode, p: TreeNode, q: TreeNode) -> TreeNode:
    if root.val > p.val and root.val > q.val:
        return lowestCommonAncestorBST(root.left, p, q)
    elif root.val < p.val and root.val < q.val:
        return lowestCommonAncestorBST(root.right, p, q)
    else:
        return root


def test_lowestCommonAncestorBST():
    expected = TreeNode.build([6, 2, 8, 0, 4, 7, 9, None, None, 3, 5])
    p = expected.left
    q = expected.right
    assert (
        lowestCommonAncestorBST(
            expected,
            p=p,
            q=q,
        )
        == expected
    )


def lowestCommonAncestor(
    root: TreeNode, p: TreeNode, q: TreeNode
) -> Optional[TreeNode]:
    if not root:
        return None

    if root == p or root == q:
        return root

    left = lowestCommonAncestor(root.left, p, q)
    right = lowestCommonAncestor(root.right, p, q)

    if left and right:
        return root

    return left or right


def test_lowestCommonAncestor():
    expected = TreeNode.build([6, 2, 8, 0, 4, 7, 1, None, None, 20, 5])
    p = expected.left
    q = expected.right
    assert (
        lowestCommonAncestor(
            expected,
            p=p,
            q=q,
        )
        == expected
    )


class PTreeNode:
    def __init__(self, val=0, left=None, right=None, parent=None):
        self.val = val
        self.left = left
        self.right = right
        self.parent = parent

    def __repr__(self):
        return f"Node({self.val})"


def lowestCommonAncestorIII(p: PTreeNode, q: PTreeNode) -> Optional[PTreeNode]:
    a = p
    b = q
    while a != b:
        a = a.parent if a else q
        b = b.parent if b else p
    return a


def test_lowestCommonAncestorIII():
    n3 = PTreeNode(3)
    n5 = PTreeNode(5)
    n1 = PTreeNode(1)
    n6 = PTreeNode(6)
    n2 = PTreeNode(2)
    n0 = PTreeNode(0)
    n8 = PTreeNode(8)
    n7 = PTreeNode(7)
    n4 = PTreeNode(4)

    n3.left = n5
    n3.right = n1
    n5.parent = n3
    n1.parent = n3
    n5.left = n6
    n5.right = n2
    n6.parent = n5
    n2.parent = n5
    n1.left = n0
    n1.right = n8
    n0.parent = n1
    n8.parent = n1
    n2.left = n7
    n2.right = n4
    n7.parent = n2
    n4.parent = n2
    assert lowestCommonAncestorIII(p=n5, q=n4) == n5


def numOfMinutes(n: int, headID: int, manager: list[int], informTime: list[int]) -> int:

    employees = defaultdict(list)
    times = defaultdict(int)

    for employee, (mng, time) in enumerate(zip(manager, informTime)):
        employees[mng].append(employee)
        times[mng] = time

    queue = deque([-1])

    result = 0
    while queue:
        n = len(queue)

        max_time = 0
        for _ in range(n):
            mng = queue.popleft()
            max_time = max(max_time, times[mng])

            queue.extend(employees[mng])

        result += max_time

    return result


def test_numOfMinutes():
    assert numOfMinutes(7, 6, [1, 2, 3, 4, 5, 6, -1], [0, 6, 5, 4, 3, 2, 1]) == 21
    assert (
        numOfMinutes(
            n=6, headID=2, manager=[2, 2, -1, 2, 2, 2], informTime=[0, 0, 1, 0, 0, 0]
        )
        == 1
    )


def sufficientSubset(root: TreeNode | None, limit: int) -> TreeNode | None:
    if not root:
        return None

    if not root.left and not root.right:
        return None if root.val < limit else root

    root.left = sufficientSubset(root.left, limit - root.val)
    root.right = sufficientSubset(root.right, limit - root.val)
    return root if root.left or root.right else None


def test_sufficientSubset():
    assert sufficientSubset(
        TreeNode.build([1, 2, 3, 4, -99, -99, 7, 8, 9, -99, -99, 12, 13, -99, 14]),
        1,
    ) == TreeNode.build([1, 2, 3, 4, None, None, 7, 8, 9, None, 14])
