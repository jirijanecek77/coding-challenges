import pandas as pd

from utils import line_generator

filename = "data/test.txt"

n = 0


def print_data(position, direction, y):
    if direction == 0:
        dir_chr = "^"
    elif direction == 1:
        dir_chr = ">"
    elif direction == 2:
        dir_chr = "v"
    else:
        dir_chr = ","

    for i in range(n):
        for j in range(n):
            if (i, j) == position:
                print(dir_chr)
            if j in y and i in y[j]:
                print("#")
            else:
                print(".")
        print()


def can_put_obstacle_up(j, i, x, y) -> bool:
    # print_data((i,j), 0, y)
    # possible = list(filter(lambda e: e > j, x[j - 1]))
    # if not possible:
    #     return False
    # if i in x[j]:
    #     return False
    # if j in y[i]:
    #     return False
    return True


def visit(position, x, y) -> int:
    obstructions = set()
    direction = 0
    while True:
        match direction:
            case 0:
                # up
                possible = list(filter(lambda e: e < position[0], y[position[1]]))
                if not possible:
                    return len(obstructions)
                new_pos = max(possible)

                for j in range(new_pos + 1, position[0]):
                    if can_put_obstacle_up(j, position[1], x, y):
                        obstructions.add((j, position[1]))

                direction = 1
                position = (new_pos + 1, position[1])

            case 1:
                # right
                possible = list(filter(lambda e: e > position[1], x[position[0]]))
                if not possible:
                    return len(obstructions)
                new_pos = min(possible)
                direction = 2
                position = (position[0], new_pos - 1)

            case 2:
                # down
                possible = list(filter(lambda e: e > position[0], y[position[1]]))
                if not possible:
                    return len(obstructions)
                new_pos = min(possible)
                direction = 3
                position = (new_pos - 1, position[1])
            case 3:
                # left
                possible = list(filter(lambda e: e < position[1], x[position[0]]))
                if not possible:
                    return len(obstructions)
                new_pos = max(possible)
                direction = 0
                position = (position[0], new_pos + 1)


def solve_01():
    x = {}
    y = {}
    with open(filename) as file:
        data = [list(line) for line in line_generator(file.readlines())]
        n = len(data)
        for i in range(n):
            for j in range(n):
                if "#" in data[i][j]:
                    if i in x:
                        x[i].append(j)
                    else:
                        x[i] = [j]
                    if j in y:
                        y[j].append(i)
                    else:
                        y[j] = [i]
                elif "^" in data[i][j]:
                    position = (i, j)

        print(visit(position, x, y))


if __name__ == "__main__":
    solve_01()
