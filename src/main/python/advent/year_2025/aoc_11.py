import time
from functools import lru_cache

filename = "data/aoc_11.txt"

test_data = [
    "svr: aaa bbb",
    "aaa: fft",
    "fft: ccc",
    "bbb: tty",
    "tty: ccc",
    "ccc: ddd eee",
    "ddd: hub",
    "hub: fff",
    "eee: dac",
    "dac: fff",
    "fff: ggg hhh",
    "ggg: out",
    "hhh: out",
]


def read_data(lines) -> dict:
    def parse_line(line):
        parts = line.split(": ")
        return parts[0], parts[1].split()

    return dict(parse_line(line) for line in lines)


def calculate(vertices: dict) -> int:

    @lru_cache
    def dfs(node: str, seen_fft: bool, seen_dac: bool) -> int:
        if node == "out":
            return 1 if seen_fft and seen_dac else 0

        result = 0
        for neighbour in vertices[node]:
            result += dfs(
                neighbour, seen_fft or node == "fft", seen_dac or node == "dac"
            )
        return result

    return dfs("svr", False, False)


if __name__ == "__main__":
    assert calculate(read_data(test_data)) == 2

    with open(filename) as file:
        start = time.perf_counter()

        graph = read_data(file.readlines())
        print(calculate(graph))

        print(f"Elapsed time: {time.perf_counter() - start}")
