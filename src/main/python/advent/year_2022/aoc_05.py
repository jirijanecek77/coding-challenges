import time

filename = "data/aoc_05.txt"


test_data = [
    "    [D]\n",
    "[N] [C]\n",
    "[Z] [M] [P]\n",
    " 1   2   3\n",
    "\n",
    "move 1 from 2 to 1\n",
    "move 3 from 1 to 3\n",
    "move 2 from 2 to 1\n",
    "move 1 from 1 to 2",
]


def read_input(lines: list[str]) -> tuple[list[list[str]], list[tuple[int, int, int]]]:
    splitter = lines.index("\n")
    cranes_str_lines = lines[:splitter]

    indices = list(
        filter(
            None,
            (
                i if ch != " " and ch != "\n" else None
                for i, ch in enumerate(cranes_str_lines.pop())
            ),
        )
    )
    stacks = [[] for _ in indices]
    while cranes_str_lines:
        crane = cranes_str_lines.pop()
        for stack_id, str_index in enumerate(indices):
            if str_index < len(crane) and crane[str_index] != " ":
                stacks[stack_id].append(crane[str_index])

    moves = list(
        map(
            lambda parts: (int(parts[1]), int(parts[3]) - 1, int(parts[5]) - 1),
            map(lambda line: line.split(), lines[splitter + 1 :]),
        )
    )
    return stacks, moves


def solve(data: tuple[list[list[str]], list[tuple[int, int, int]]]) -> str:
    stacks, moves = data
    for count, from_idx, to_idx in moves:
        crates = stacks[from_idx][-count:]
        stacks[from_idx] = stacks[from_idx][:-count]
        stacks[to_idx].extend(crates)

    return "".join(stack[-1] if stack else "" for stack in stacks)


if __name__ == "__main__":
    assert solve(read_input(test_data)) == "MCD"
    with open(filename) as file:
        start = time.perf_counter()

        res = solve(read_input(file.readlines()))
        print(res)

        print(f"Elapsed time: {time.perf_counter() - start}")
