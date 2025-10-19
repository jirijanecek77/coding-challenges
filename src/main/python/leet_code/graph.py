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
