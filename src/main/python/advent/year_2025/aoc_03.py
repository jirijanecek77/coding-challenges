import time

filename = "data/aoc_03.txt"


def find_left_most_max_idx(line: str, start: int, end: int) -> int:
    return max(enumerate(line[start:end]), key=lambda x: x[1])[0] + start


def calculate_greedy(line: str, batteries: int) -> int:
    result = 0
    start = -1
    n = len(line)
    for i in range(batteries):
        batteries_left = n - batteries + i + 1
        start = find_left_most_max_idx(line, start + 1, batteries_left)
        result = result * 10 + int(line[start])
    return result


def calculate_monotonic_stack(line: str, batteries: int) -> int:
    stack = []
    n = len(line)
    for i in range(n):
        num = int(line[i])
        batteries_left = n - i
        while stack and num > stack[-1] and batteries_left > (batteries - len(stack)):
            stack.pop()
        if len(stack) < batteries:
            stack.append(num)

    result = i = 0
    while stack:
        result += 10**i * stack.pop()
        i += 1
    return result


test_data = [
    "811111111111119",
    "987654321111111",
    "234234234234278",
    "818181911112111",
]
if __name__ == "__main__":
    assert (
        sum(calculate_monotonic_stack(line, 12) for line in test_data) == 3121910778619
    )

    print("Greedy solution:")
    with open(filename) as file:
        start = time.perf_counter()
        print(sum((calculate_greedy(line[:-1], 12) for line in file.readlines())))
        print(f"Elapsed time: {time.perf_counter() - start}")

    print("\n")
    print("Monotonic stack solution:")
    with open(filename) as file:
        start = time.perf_counter()
        print(
            sum((calculate_monotonic_stack(line[:-1], 12) for line in file.readlines()))
        )
        print(f"Elapsed time: {time.perf_counter() - start}")
