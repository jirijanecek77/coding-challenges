import math
import time

filename = "data/aoc_07.txt"


test_data = [
    "$ cd /",
    "$ ls",
    "dir a",
    "14848514 b.txt",
    "8504156 c.dat",
    "dir d",
    "$ cd a",
    "$ ls",
    "dir e",
    "29116 f",
    "2557 g",
    "62596 h.lst",
    "$ cd e",
    "$ ls",
    "584 i",
    "$ cd ..",
    "$ cd ..",
    "$ cd d",
    "$ ls",
    "4060174 j",
    "8033020 d.log",
    "5626152 d.ext",
    "7214296 k",
]


class Node:
    def __init__(self, name, size=0):
        self.name = name
        self.children = []
        self.size = size


def dfs(node: Node) -> int:
    if not node.children:
        return 0

    return (0 if node.size > 100000 else node.size) + sum(
        dfs(child) for child in node.children
    )


def bfs(root: Node, target) -> int:
    queue = [root]
    res = math.inf
    while queue:
        node = queue.pop(0)
        if node.children and node.size - target > 0:
            res = min(res, node.size)
            for child in node.children:
                queue.append(child)
    return res


def solve(commands: list[str]) -> int:
    root = Node("/")
    stack = []
    node = root

    for command in commands[1:]:
        command = command.strip()
        if command == "$ ls":
            stack.append(node)
        elif command == "$ cd ..":
            d = stack.pop()
            node = stack[-1]
            node.size += d.size
        elif command.startswith("$ cd"):
            name = command[5:]
            node = next(filter(lambda n: n.name == name, node.children))
        elif command.startswith("dir"):
            name = command[4:]
            node.children.append(Node(name))
        else:
            size, name = command.split()
            size = int(size)
            node.children.append(Node(name, size=size))
            node.size += size

    size = 0
    while stack:
        node = stack.pop()
        node.size += size
        size = node.size

    solution_1 = dfs(root)
    print(f"Solution 1: {solution_1}")

    target = 30000000 - 70000000 + root.size
    return bfs(root, target)


if __name__ == "__main__":
    assert solve(test_data) == 24933642

    with open(filename) as file:
        start = time.perf_counter()

        print(solve(file.readlines()))

        print(f"Elapsed time: {time.perf_counter() - start}")
