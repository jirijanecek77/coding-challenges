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
