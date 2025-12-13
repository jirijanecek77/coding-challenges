filename = "data/aoc_01.txt"


def perform_instructions1(initial_state: int, lines: list[str]) -> int:
    res = 0
    state = initial_state
    for instruction in lines:
        sign = -1 if instruction[0] == "L" else 1
        diff = int(instruction[1:])
        multi, state = divmod(state + sign * diff, 100)
        if state == 0:
            res += 1

    return res


def perform_instructions2(dial: int, lines: list[str]) -> int:
    res = 0
    for instruction in lines:
        direction = instruction[0]
        inc = int(instruction[1:])

        res += inc // 100
        inc %= 100
        if direction == "L":
            if dial != 0 and dial - inc <= 0:
                res += 1
            dial = (dial - inc) % 100
        else:
            if inc + dial >= 100:
                res += 1
            dial = (dial + inc) % 100
    return res


def solve_01():
    assert (
        perform_instructions1(
            50,
            [
                "L68",
                "L30",
                "R48",
                "L5",
                "R60",
                "L55",
                "L1",
                "L99",
                "R14",
                "L82",
            ],
        )
        == 3
    )
    with open(filename) as file:
        res = perform_instructions1(50, file.readlines())
        print(res)


def solve_02():
    assert (
        perform_instructions2(
            50,
            [
                "L68",
                "L30",
                "R48",
                "L5",
                "R60",
                "L55",
                "L1",
                "L99",
                "R14",
                "L82",
            ],
        )
        == 6
    )

    with open(filename) as file:
        res = perform_instructions2(50, file.readlines())
        print(res)


if __name__ == "__main__":
    solve_02()
