from advent.utils import line_generator

filename = "data/aoc_05.txt"


def check(values: list[str], edges: dict[str, list[str]]):
    for i in range(len(values)):
        if values[i] in edges.keys():
            for j in range(len(values[:i])):
                if values[j] in edges[values[i]]:
                    values = values[:j] + [values[i]] + values[j:i] + values[i + 1 :]
                    break

    return values


def solve_01():
    with open(filename) as file:
        edges = {}
        generator = line_generator(file.readlines())
        for line in generator:
            if line == "":
                break
            parts = line.split("|")
            if parts[0] in edges:
                edges[parts[0]].append(parts[1])
            else:
                edges[parts[0]] = [parts[1]]

        result = 0
        for rules in generator:
            values = rules.split(",")
            if (new_val := check(values, edges)) != values:
                print(new_val)
                result += int(new_val[int(len(new_val) / 2)])

    print(result)


if __name__ == "__main__":
    solve_01()
