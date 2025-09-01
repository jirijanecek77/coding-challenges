import os
from collections import Counter

filename = "data/aoc_01.txt"


def solve_01():
    with open(filename) as file:
        lines = file.readlines()

        list1, list2 = zip(*[line.strip().split("   ") for line in lines])

        print(sum(map(lambda x: abs(int(x[0]) - int(x[1])), zip(sorted(list1), sorted(list2)))))


def solve_02():
    with open(filename) as file:
        lines = file.readlines()

        list1, list2 = zip(*[line.strip().split("   ") for line in lines])

        counter1 = Counter(list1)
        counter2 = Counter(list2)

        print(sum(int(count) * int(key) * int(counter2[key]) for key, count in counter1.items()))


if __name__ == "__main__":
    solve_02()
