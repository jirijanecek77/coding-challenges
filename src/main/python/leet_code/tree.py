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
