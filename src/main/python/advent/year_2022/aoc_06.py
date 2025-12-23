import time

filename = "data/aoc_06.txt"


test_data = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"


def solve(message: str, uniques_len: int) -> int:
    seen = {}

    start = 0
    for i, ch in enumerate(message):
        if ch in seen and i - seen[ch] <= uniques_len:
            start = max(start, seen[ch] + 1)
        if i - start + 1 == uniques_len:
            break
        seen[ch] = i

    return start + uniques_len


if __name__ == "__main__":
    assert solve(test_data, 14) == 19

    with open(filename) as file:
        start = time.perf_counter()

        res = solve(file.read(), 14)
        print(res)

        print(f"Elapsed time: {time.perf_counter() - start}")
