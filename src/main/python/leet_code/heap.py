# https://leetcode.com/problems/meeting-rooms-iii/description/?envType=daily-question&envId=2025-12-27
from collections import defaultdict, Counter
from heapq import heapify, heappush, heappop


def mostBooked(n: int, meetings: list[list[int]]) -> int:
    ready = [i for i in range(n)]
    counter = defaultdict(int)
    heapify(ready)
    rooms = []

    for start, end in sorted(meetings):
        while rooms and rooms[0][0] <= start:
            time, room = heappop(rooms)
            heappush(ready, room)

        if ready:
            room = heappop(ready)
            heappush(rooms, [end, room])
        else:
            time, room = heappop(rooms)
            heappush(rooms, [time + end - start, room])

        counter[room] += 1
    return max(counter.items(), key=lambda x: x[1])[0]


def test_mostBooked():
    assert (
        mostBooked(n=4, meetings=[[18, 19], [3, 12], [17, 19], [2, 13], [7, 10]]) == 0
    )
    assert mostBooked(n=2, meetings=[[0, 10], [1, 5], [2, 7], [3, 4]]) == 0
    assert mostBooked(n=3, meetings=[[1, 20], [2, 10], [3, 5], [4, 9], [6, 8]]) == 1


def missingMultiple(nums: list[int], k: int) -> int:
    heapify(nums)
    n = k
    while nums:
        num = heappop(nums)
        if num == n:
            n += k
        elif num > n:
            return n

    return n


def test_missingMultiple():
    assert missingMultiple(nums=[1, 2, 3, 4], k=2) == 6
    assert missingMultiple(nums=[2, 3, 4, 7, 11], k=5) == 5
    assert missingMultiple(nums=[1, 2, 3, 4], k=1) == 5


class Element:
    def __init__(self, freq: int, word: str):
        self.freq = freq
        self.word = word

    def __lt__(self, other):
        if self.freq == other.freq:
            return self.word > other.word
        return self.freq < other.freq


def topKFrequent(words: list[str], k: int) -> list[str]:

    counter = Counter(words)
    heap = []

    for word, freq in counter.items():
        heappush(heap, Element(freq, word))

        while len(heap) > k:
            heappop(heap)

    res = []
    while heap:
        res.append(heappop(heap).word)
    return res[::-1]


def test_topKFrequent():
    assert topKFrequent(
        words=["i", "love", "leetcode", "i", "love", "coding"], k=2
    ) == ["i", "love"]

    assert topKFrequent(
        words=["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"],
        k=4,
    ) == ["the", "is", "sunny", "day"]
