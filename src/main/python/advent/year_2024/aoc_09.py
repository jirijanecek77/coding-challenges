from itertools import chain

filename = "data/aoc_09.txt"


def get_next(data, index) -> tuple[int, int]:
    id_ = index // 2
    length = int(data[index])
    return id_ if index % 2 == 0 else -1, length


def solve_01():
    with open(filename) as file:
        data = list(file.read())
        buffer = [get_next(data, i) for i in range(len(data))]
        result = []

        while buffer:
            id_, length = buffer.pop(-1)
            if id_ == -1:
                result.insert(0, (id_, length))
                continue

            target_index, target_len = next(((index, buf[1]) for index, buf in enumerate(buffer) if buf[0] == -1 and buf[1] >= length), (None, 0))

            if target_index is not None:
                result.insert(0, (-1, length))
                buffer[target_index] = id_, length
                if target_len > length:
                    buffer.insert(target_index + 1, (-1, target_len - length))
            else:
                result.insert(0, (id_, length))

    print(sum(index * val for index, val in enumerate(chain.from_iterable([[id_] * it for id_, it in result])) if val != -1))


if __name__ == "__main__":
    solve_01()
