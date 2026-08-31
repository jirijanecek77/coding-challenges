import math


# https://leetcode.com/problems/set-intersection-size-at-least-two/description/?envType=daily-question&envId=2025-11-20
def intersectionSizeTwo(intervals: list[list[int]]) -> int:
    intervals.sort(key=lambda x: (x[1], -x[0]))

    a = -math.inf
    b = -math.inf
    result = 0

    for left, right in intervals:
        if left > b:
            result += 2
            a = right - 1
            b = right
        elif left > a:
            result += 1
            a = b
            b = right
    return result


def test_intersectionSizeTwo():
    assert intersectionSizeTwo(intervals=[[1, 3], [3, 7], [5, 7], [7, 8]]) == 5
    assert (
        intersectionSizeTwo(
            intervals=[
                [2, 10],
                [3, 7],
                [3, 15],
                [4, 11],
                [6, 12],
                [6, 16],
                [7, 8],
                [7, 11],
                [7, 15],
                [11, 12],
            ]
        )
        == 5
    )
    assert intersectionSizeTwo(intervals=[[1, 3], [1, 4], [2, 5], [3, 5]]) == 3
    assert intersectionSizeTwo(intervals=[[1, 3], [2, 6], [8, 10], [15, 18]]) == 6
    assert intersectionSizeTwo(intervals=[[1, 3], [3, 7], [8, 9]]) == 5
    assert intersectionSizeTwo(intervals=[[1, 2], [2, 3], [2, 4], [4, 5]]) == 5


def merge_colored_intervals(intervals: list[tuple[int, int, bool]]) -> list[list[int]]:
    def merge(_intervals: list[list[int]]) -> list[list[int]]:
        _intervals.sort(key=lambda x: (x[0], -x[1]))
        merged = []
        for interval in _intervals:
            if not merged or merged[-1][1] < interval[0]:
                merged.append(interval)
            else:
                merged[-1][1] = max(merged[-1][1], interval[1])
        return merged

    white = merge([[start, end] for start, end, color in intervals if color])
    black = merge([[start, end] for start, end, color in intervals if not color])

    result = []
    i, j = 0, 0

    while i < len(white):
        w_start, w_end = white[i]

        if j == len(black):
            result.append([w_start, w_end])
            i += 1
            continue

        b_start, b_end = black[j]
        if b_end <= w_start:
            j += 1
        elif b_start >= w_end:
            result.append([w_start, w_end])
            i += 1
        else:
            if b_start <= w_start:
                w_start = b_end
            else:
                result.append([w_start, b_start])
                w_start = b_end

            if w_start >= w_end:
                i += 1
            else:
                white[i] = [w_start, w_end]
                if b_end <= w_end:
                    j += 1
    return result


def test_merge_colored_intervals():
    assert merge_colored_intervals(
        intervals=[(1, 3, True), (1, 6, True), (2, 4, False), (3, 5, True)]
    ) == [
        [1, 2],
        [4, 6],
    ]
    assert merge_colored_intervals(
        intervals=[(1, 3, True), (2, 4, True), (3, 5, True)]
    ) == [[1, 5]]


def white_intervals_sweep_line(
    intervals: list[tuple[int, int, bool]],
) -> list[list[int]]:
    events = []

    for start, end, color in intervals:
        events.append((start, 1, color))
        events.append((end, -1, color))

    # if times are equal, sort END (-1) before START (1).
    events.sort(key=lambda x: (x[0], x[1]))

    result = []
    active_whites = 0
    active_blacks = 0
    segment_start = None

    for time, event_type, color in events:
        was_white_visible = active_whites > 0 and active_blacks == 0

        if color:
            active_whites += event_type
        else:
            active_blacks += event_type

        is_white_visible = active_whites > 0 and active_blacks == 0
        if not was_white_visible and is_white_visible:
            segment_start = time
        elif was_white_visible and not is_white_visible:
            if segment_start is not None and time > segment_start:
                result.append([segment_start, time])
                segment_start = None

    return result


def test_white_intervals_sweep_line():
    assert white_intervals_sweep_line(
        intervals=[(1, 3, True), (1, 6, True), (2, 4, False), (3, 5, True)]
    ) == [
        [1, 2],
        [4, 6],
    ]
    assert merge_colored_intervals(
        intervals=[(1, 3, True), (2, 4, True), (3, 5, True)]
    ) == [[1, 5]]


def min_meeting_rooms(meetings: list[list[int]]) -> int:
    events = []

    for start, end in meetings:
        events.append([start, 1])
        events.append([end, -1])

    events.sort()
    rooms = 0
    result = 0
    for time, event_type in events:
        rooms += event_type
        result = max(result, rooms)
    return result


def test_min_meeting_rooms():
    assert min_meeting_rooms([[0, 6], [5, 10], [15, 20]]) == 2
    assert min_meeting_rooms([[7, 10], [10, 11]]) == 1
    assert min_meeting_rooms([[7, 10], [2, 4]]) == 1
    assert min_meeting_rooms([]) == 0
