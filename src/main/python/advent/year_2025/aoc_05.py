import time

filename = "data/aoc_05.txt"

test_data = [
    "3-5",
    "10-14",
    "16-20",
    "12-18",
    "",
    "1",
    "5",
    "8",
    "11",
    "17",
    "32",
]


def read_data(lines):
    intervals = []
    ids = []
    read_intervals = True
    for line in lines:
        line = line.strip()
        if read_intervals:
            if line:
                start, end = line.split("-")
                intervals.append((int(start), int(end)))
            else:
                read_intervals = False
        else:
            ids.append(int(line))

    return sorted(intervals), set(ids)


def merge_intervals(intervals: list[tuple[int, int]]) -> list[tuple[int, int]]:
    merged = []
    for interval in intervals:
        if not merged or merged[-1][1] < interval[0] - 1:
            merged.append(interval)
        else:
            merged[-1] = (merged[-1][0], max(merged[-1][1], interval[1]))
    return merged


def calculate(intervals: list[tuple[int, int]], ids: set[int]) -> tuple[int, int]:
    intervals = merge_intervals(intervals)
    n = len(ids)
    total_intervals_len = 0
    for interval in intervals:
        start, end = interval
        total_intervals_len += end - start + 1
        ids -= set(filter(lambda _id: start <= _id <= end, ids))

    return n - len(ids), total_intervals_len


if __name__ == "__main__":
    assert calculate(*read_data(test_data)) == (3, 14)

    with open(filename) as file:
        start = time.perf_counter()

        print(calculate(*read_data(file.readlines())))

        print(f"Elapsed time: {time.perf_counter() - start}")
