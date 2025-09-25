filename = "data/aoc_03.txt"


def get_next_token(data: str, index: int) -> tuple[int, int]:
    state = 0
    num = 0
    digit = ""
    enabled = True
    for i in range(index, len(data)):
        char = data[i]
        match state:
            case 0:
                if char == "m":
                    state = 1
                elif char == "d":
                    state = 10
                else:
                    state = 0
            case 1:
                state = (2 if char == "u" else 0)
            case 2:
                state = (3 if char == "l" else 0)
            case 3:
                state = (4 if char == "(" else 0)
            case 4:
                if char.isdigit():
                    digit += char
                elif char == "," and digit != "":
                    num = int(digit)
                    digit = ""
                    state = 5
                else:
                    digit = ""
                    state = 0
            case 5:
                if char.isdigit():
                    digit += char
                elif char == ")" and digit != "" and enabled:
                    return num * int(digit), i + 1
                else:
                    digit = ""
                    state = 0
            case 10:
                state = (11 if char == "o" else 0)
            case 11:
                if char == "(":
                    state = 12
                elif char == "n":
                    state = 20
                else:
                    state = 0
            case 12:
                if char == ")":
                    enabled = True
                state = 0
            case 20:
                state = (21 if char == "'" else 0)
            case 21:
                state = (22 if char == "t" else 0)
            case 22:
                state = (23 if char == "(" else 0)
            case 23:
                if char == ")":
                    enabled = False
                state = 0

    return 0, -1


def solve_01():
    with open(filename) as file:
        data = file.read()
        index = 0
        result = 0

        while index >= 0:
            output, index = get_next_token(data, index)
            result += output

        print(result)


if __name__ == "__main__":
    solve_01()
