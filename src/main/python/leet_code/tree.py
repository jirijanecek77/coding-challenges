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
