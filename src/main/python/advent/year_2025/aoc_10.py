import operator
import re
import time
from itertools import starmap

filename = "data/aoc_10.txt"

test_data = [
    "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}",
    "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}",
    "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}",
]


def read_data1(lines):
    pattern = r"[\(\[\{](.*?)[\)\]\}]"

    # Search using regex
    for line in lines:
        matches = re.findall(pattern, line)

        target = sum(2**i for i, ch in enumerate(matches[0]) if ch == "#")
        buttons = list(sum(2 ** int(i) for i in m.split(",")) for m in matches[1:-1])

        yield target, buttons


def min_button_press1(data) -> int:
    target = data[0]
    buttons = data[1]
    visited = set()
    queue = [0]
    i = 0
    while queue:
        n = len(queue)
        for _ in range(n):
            num = queue.pop(0)
            if num == target:
                return i

            if num in visited:
                continue

            visited.add(num)

            for button in buttons:
                queue.append(num ^ button)
        i += 1

    return -1


def calculate1(lines) -> int:
    return sum(map(min_button_press1, read_data1(lines)))


def read_data2(lines):
    pattern = r"[\(\[\{](.*?)[\)\]\}]"

    # Search using regex
    for line in lines:
        matches = re.findall(pattern, line)

        target = list(int(ch) for ch in matches[-1].split(","))
        # buttons = [tuple(int(i) for i in m.split(",")) for m in matches[1:-1]]
        buttons = []
        n = len(target)
        for m in matches[1:-1]:
            button = [0] * n
            for i in m.split(","):
                button[int(i)] = 1
            buttons.append(button)

        yield target, buttons


def min_button_press2(data) -> int:
    target = data[0]
    buttons = sorted(data[1], key=lambda e: sum(e), reverse=True)

    visited = set()
    queue = [(0,) * len(target)]
    i = 0
    while queue:
        n = len(queue)
        print(n)
        for _ in range(n):
            num = queue.pop(0)
            if num == target:
                return i

            if num in visited:
                continue

            visited.add(num)

            for button in buttons:
                new_button = tuple(starmap(operator.add, zip(num, button)))
                if all(map(lambda e: e[0] >= e[1], zip(target, new_button))):
                    queue.append(new_button)
        i += 1

    return -1


def calculate2(lines) -> int:
    return sum(map(min_button_press2, read_data2(lines)))


if __name__ == "__main__":
    assert calculate1(test_data) == 7
    assert calculate2(test_data) == 33

    with open(filename) as file:
        start = time.perf_counter()

        # print(calculate1(file.readlines()))
        print(calculate2(file.readlines()))

        print(f"Elapsed time: {time.perf_counter() - start}")
