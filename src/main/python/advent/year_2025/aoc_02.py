import time

filename = "data/aoc_02.txt"


def is_repeated_substring_pattern(s: str) -> bool:
    n = len(s)
    for i in range(1, (n // 2) + 1):
        if n % i == 0 and s[:i] * (n // i) == s:
            return True
    return False


def calculate_for_pair(start: int, end: int) -> int:
    return sum(
        i if is_repeated_substring_pattern(str(i)) else 0 for i in range(start, end + 1)
    )


def calculate_for_pairs(s: str):
    return sum(calculate_for_pair(*map(int, pair.split("-"))) for pair in s.split(","))


def solve():
    test_data = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
    assert calculate_for_pairs(test_data) == 4174379265

    with open(filename) as file:
        beg_ts = time.time()
        print(calculate_for_pairs(file.read()))
        end_ts = time.time()
        print(end_ts - beg_ts)


if __name__ == "__main__":
    solve()
