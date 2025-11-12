from collections import Counter
from math import comb, gcd


def final_value_after_operations(operations: list[str]) -> int:
    return sum(map(lambda op: 1 if op in ["X++", "++X"] else -1, operations))


def test_final_value_after_operations():
    assert final_value_after_operations(["++X", "X++", "++X"]) == 3


def hasSameDigits(s: str) -> bool:
    if len(s) < 2:
        return False

    # nums = list(map(int, s))
    # while len(nums) > 2:
    #     nums = [(a + b) % 10 for a, b in pairwise(nums)]
    # return nums[0] == nums[1]
    s = [int(digits) for digits in s]
    n = len(s)
    # let's calculate all the relevant binomial coefficients:
    binomial_coeff = [comb(n - 2, i) for i in range(n - 1)]
    # calculating the final left and right digits
    left = sum([digit * coeff for digit, coeff in zip(s[:-1], binomial_coeff)]) % 10
    right = sum([digit * coeff for digit, coeff in zip(s[1:], binomial_coeff)]) % 10
    return left == right


def test_hasSameDigits():
    assert hasSameDigits("3902") == True
    assert hasSameDigits("34789") == False


def gcdOfStrings(str1: str, str2: str) -> str:
    n1 = len(str1)
    n2 = len(str2)

    n = gcd(n1, n2)
    prefix = str1[:n]
    for i in range(0, n1, n):
        if str1[i : i + n] != prefix:
            return ""

    for i in range(0, n2, n):
        if str2[i : i + n] != prefix:
            return ""

    return prefix


def test_gcdOfStrings():
    assert gcdOfStrings("ABABAB", "ABAB") == "AB"
    assert gcdOfStrings("AA", "A") == "A"
    assert gcdOfStrings("ABCD", "CD") == ""


def missingMultiple(nums: list[int], k: int) -> int:
    primitives = sorted({i for i in nums if i % k == 0})

    n = len(primitives) + 1
    for i, p in zip(range(1, n), primitives):
        if i * k != p:
            return i * k

    return n * k


def test_missingMultiple():
    assert (
        missingMultiple(
            [83, 96, 34, 56, 48, 30, 7, 14, 77, 66, 66, 66, 21, 17, 38, 7, 9], 7
        )
        == 28
    )


def reverseVowels(s: str) -> str:
    left = 0
    right = len(s) - 1
    vowels = {"a", "e", "i", "o", "u", "A", "E", "I", "O", "U"}
    s = list(s)
    while left < right:
        if s[left] not in vowels:
            left += 1
        elif s[right] not in vowels:
            right -= 1
        else:
            s[left], s[right] = s[right], s[left]
            left += 1
            right -= 1

    return "".join(s)


def test_reverseVowels():
    assert reverseVowels("hello") == "holle"
    assert reverseVowels("leetcode") == "leotcede"


# https://leetcode.com/problems/minimum-number-of-increments-on-subarrays-to-form-a-target-array/?envType=daily-question&envId=2025-10-30
def minNumberOperations(target: list[int]) -> int:
    last = 0
    res = 0
    for curr in target:
        if curr > last:
            res += curr - last
        last = curr
    return res


def test_minNumberOperations():
    assert minNumberOperations([2, 4, 1, 9, 10, 9, 1, 7, 1, 2, 9, 1]) == 27
    assert minNumberOperations([3, 1, 5, 4, 2]) == 7
    assert minNumberOperations([3, 1, 1, 2]) == 4


def numberOfBeams(bank: list[str]) -> int:
    beams = 0
    last = 0
    for row in bank:
        curr = row.count("1")
        if curr:
            beams += last * curr
            last = curr
    return beams


def test_numberOfBeams():
    assert numberOfBeams(["011001", "000000", "010100", "001000"]) == 8


def setZeroes(matrix: list[list[int]]) -> None:
    rows = set()
    cols = set()
    for i in range(len(matrix)):
        for j in range(len(matrix[0])):
            if matrix[i][j] == 0:
                rows.add(i)
                cols.add(j)
    for i in range(len(matrix)):
        for j in range(len(matrix[0])):
            if i in rows or j in cols:
                matrix[i][j] = 0


def countUnguarded(
    m: int, n: int, guards: list[list[int]], walls: list[list[int]]
) -> int:
    # Create grid
    grid = [[0] * n for _ in range(m)]

    # Mark guards and walls
    for r, c in guards:
        grid[r][c] = "G"
    for r, c in walls:
        grid[r][c] = "W"

    # Directions: left, right, up, down
    directions = [(0, -1), (0, 1), (-1, 0), (1, 0)]

    # For each guard, mark all cells it can see
    for gr, gc in guards:
        for dr, dc in directions:
            r, c = gr + dr, gc + dc

            # Keep going in this direction until hitting wall/guard/boundary
            while 0 <= r < m and 0 <= c < n:
                if grid[r][c] == "W" or grid[r][c] == "G":
                    break  # Hit wall or another guard

                grid[r][c] = "V"  # Mark as visible/guarded
                r += dr
                c += dc

    # Count unguarded cells
    unguarded = 0
    for i in range(m):
        for j in range(n):
            if grid[i][j] == 0:  # Not guarded, not wall, not guard
                unguarded += 1

    return unguarded


def test_countUnguarded():
    assert (
        countUnguarded(
            m=4, n=6, guards=[[0, 0], [1, 1], [2, 3]], walls=[[0, 1], [2, 2], [1, 4]]
        )
        == 7
    )


def sumOfUnique(nums: list[int]) -> int:
    c = Counter(nums)
    return sum(a for a, b in c.items() if b == 1)


def test_sumOfUnique():
    assert sumOfUnique([1, 2, 3, 2]) == 4


def kClosest(points: list[list[int]], k: int) -> list[list[int]]:
    return sorted(points, key=lambda p: p[0] ** 2 + p[1] ** 2)[:k]


def test_kClosest():
    assert kClosest([[1, 3], [-2, 2]], 1) == [[-2, 2]]


def canFormArray(arr: list[int], pieces: list[list[int]]) -> bool:
    def remove_item(l: list, item) -> list:
        l.remove(item)
        return l

    def search(acc: list[int], remains: list[int], available: list[list[int]]) -> bool:
        if not remains:
            if not available:
                return acc == arr
            else:
                return False

        return any(
            search(acc + piece, remains[len(piece) :], remove_item(available, piece))
            for piece in available
            if piece[0] == remains[0]
        )

    return search([], arr, list(pieces))


def test_canFormArray():
    assert canFormArray(arr=[91, 4, 64, 78], pieces=[[78], [4, 64], [91]]) == True
    assert canFormArray(arr=[49, 18, 16], pieces=[[16, 18, 49]]) == False


def numPairsDivisibleBy60(time: list[int]) -> int:
    result = 0
    counter = [0] * 60

    for t in time:
        r = t % 60
        result += counter[((60 - (r)) % 60)]
        counter[r] += 1
    return result


def test_numPairsDivisibleBy60():
    assert (
        numPairsDivisibleBy60(
            [418, 204, 77, 278, 239, 457, 284, 263, 372, 279, 476, 416, 360, 18]
        )
        == 1
    )
    assert numPairsDivisibleBy60([18, 18, 71, 471, 121, 362, 467, 107, 138, 254]) == 0
    assert numPairsDivisibleBy60([20, 40]) == 1
    assert numPairsDivisibleBy60([60, 60, 60]) == 3
    assert numPairsDivisibleBy60([30, 20, 150, 100, 40]) == 3


# https://leetcode.com/problems/minimum-number-of-operations-to-make-all-array-elements-equal-to-1/description/?envType=daily-question&envId=2025-11-12
def minOperations(nums: list[int]) -> int:
    n = len(nums)
    if q := nums.count(1):
        return n - q
    return next(
        (
            w + n - 2
            for w in range(n + 1)
            for i in range(n - w + 1)
            if gcd(*nums[i : i + w]) == 1
        ),
        -1,
    )


def test_minOperations():
    assert minOperations([6, 10, 15]) == 4


def isRectangleOverlap(rec1: list[int], rec2: list[int]) -> bool:
    x1 = (rec1[0], rec1[2])
    x2 = (rec2[0], rec2[2])
    x = sorted([x1, x2])

    y1 = (rec1[1], rec1[3])
    y2 = (rec2[1], rec2[3])
    y = sorted([y1, y2])

    return x[0][1] > x[1][0] and y[0][1] > y[1][0]


def test_isRectangleOverlap():
    assert isRectangleOverlap(rec1=[0, 0, 1, 1], rec2=[1, 0, 2, 1]) == False
    assert isRectangleOverlap([0, 0, 2, 2], [1, 1, 3, 3]) == True
