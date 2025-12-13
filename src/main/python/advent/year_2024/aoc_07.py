from advent.utils import line_generator

filename = "data/test.txt"


def dp(numbers, target) -> bool:
    if target < 0:
        return False

    n = len(numbers)
    dp = [set() for _ in range(n + 1)]
    dp[0].add(0)  # Base case

    for i in range(1, n + 1):
        num = numbers[i - 1]
        for result in dp[i - 1]:
            if result + num <= target:
                dp[i].add(result + num)
            if result * num <= target:
                dp[i].add(result * num)

            concat = int(str(result) + str(num))
            if concat <= target:
                dp[i].add(concat)

    return target in dp[n]


def check(line: str) -> int:
    total, number_str = line.split(":")
    total = int(total.strip())
    operands = [int(n) for n in number_str.strip().split(" ")]

    return total if dp(operands, total) else 0


def solve_01():
    with open(filename) as file:
        result = sum([check(line) for line in line_generator(file.readlines())])

        print(result)


if __name__ == "__main__":
    solve_01()
