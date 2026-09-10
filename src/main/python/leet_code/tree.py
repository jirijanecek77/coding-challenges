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
    assert maxProduct(TreeNode(1, TreeNode(2), TreeNode(3))) == 9


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
            TreeNode(
                5,
                TreeNode(3, TreeNode(5, TreeNode(1), TreeNode(8)), TreeNode(2)),
                TreeNode(6, TreeNode(5, TreeNode(8), TreeNode(8)), TreeNode(7)),
            ),
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
    assert level_order_traversal(TreeNode(1, TreeNode(2), TreeNode(3))) == [[1], [2, 3]]


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
            TreeNode(
                10,
                TreeNode(
                    5,
                    TreeNode(3, TreeNode(3), TreeNode(-2)),
                    TreeNode(2, None, TreeNode(1)),
                ),
                TreeNode(-3, None, TreeNode(11)),
            ),
            8,
        )
        == 3
    )


def subtreeWithAllDeepest(root: Optional[TreeNode]) -> Optional[TreeNode]:
    def dfs(node: Optional[TreeNode], depth: int, max_depth: int = 0, result=None):
        if not node:
            return depth

        left, result_l = dfs(node.left, depth + 1, max_depth, result)
        right, result_r = dfs(node.right, depth + 1, max_depth, result)

        new_depth = max(left, right)

        if new_depth > max_depth:
            max_depth = new_depth

            if left == right:
                result = node
            elif left > right:
                result = node.left
            else:
                result = node.right
        return max_depth, result

    _, result = dfs(root, 0)
    return result


def test_subtreeWithAllDeepest():
    expected = TreeNode(2, TreeNode(7), TreeNode(4))
    assert (
        subtreeWithAllDeepest(
            TreeNode(
                3,
                TreeNode(5, TreeNode(6), expected),
                TreeNode(1, TreeNode(0), TreeNode(8)),
            )
        )
        == expected
    )


def lowestCommonAncestorBST(root: TreeNode, p: TreeNode, q: TreeNode) -> TreeNode:
    if root.val > p.val and root.val > q.val:
        return lowestCommonAncestorBST(root.left, p, q)
    elif root.val < p.val and root.val < q.val:
        return lowestCommonAncestorBST(root.right, p, q)
    else:
        return root


def test_lowestCommonAncestorBST():
    p = TreeNode(2, TreeNode(0), TreeNode(4, TreeNode(3), TreeNode(5)))
    q = TreeNode(8, TreeNode(7), TreeNode(9))
    expected = TreeNode(6, p, q)
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
    p = TreeNode(2, TreeNode(0), TreeNode(4, TreeNode(20), TreeNode(5)))
    q = TreeNode(8, TreeNode(7), TreeNode(1))
    expected = TreeNode(6, p, q)
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
