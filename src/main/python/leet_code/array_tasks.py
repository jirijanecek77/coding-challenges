import itertools
from collections import Counter, defaultdict
from functools import lru_cache
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


def relativeSortArray(arr1: list[int], arr2: list[int]) -> list[int]:
    key = {v: i for i, v in enumerate(arr2)}
    return sorted(arr1, key=lambda x: key.get(x, len(key) + x))


def test_relativeSortArray():
    assert relativeSortArray(
        arr1=[2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19], arr2=[2, 1, 4, 3, 9, 6]
    ) == [2, 2, 2, 1, 4, 3, 3, 9, 6, 7, 19]


def longestValidParentheses(s: str) -> int:
    stack = [-1]  # Base index
    max_length = 0

    for i, char in enumerate(s):
        if char == "(":
            stack.append(i)
        else:
            stack.pop()
            if not stack:
                stack.append(i)
            else:
                max_length = max(max_length, i - stack[-1])

    return max_length


def test_longestValidParentheses():
    assert longestValidParentheses(s=")()())") == 4
    assert longestValidParentheses(s="()(()") == 2
    assert longestValidParentheses(s="(()") == 2


def largestTimeFromDigits(arr: list[int]) -> str:
    permutations = list(
        filter(lambda x: x <= (2, 3, 5, 9), itertools.permutations(arr))
    )
    if not permutations:
        return ""
    result = max(permutations)
    return f"{result[0]}{result[1]}:{result[2]}{result[3]}"


def test_largestTimeFromDigits():
    assert largestTimeFromDigits([1, 2, 3, 4]) == "23:41"


# https://leetcode.com/problems/buddy-strings/description/
def buddyStrings(s: str, goal: str) -> bool:
    if len(s) != len(goal):
        return False

    if s == goal and len(set(s)) < len(s):
        return True

    diffs = [(a, b) for a, b in zip(s, goal) if a != b]
    if len(diffs) == 2:
        first, second = diffs
        return first == second[::-1]

    return False


def test_buddyStrings():
    assert buddyStrings("acccccb", "bccccca") == True
    assert buddyStrings("ab", "ba") == True
    assert buddyStrings("aa", "aa") == True
    assert buddyStrings("ab", "ab") == False


# https://leetcode.com/problems/sort-the-matrix-diagonally/description/
def diagonalSort(mat: list[list[int]]) -> list[list[int]]:
    n = len(mat)
    m = len(mat[0])

    def traverse(row: int, col: int, arr: list[int]):
        if row >= n or col >= m:
            arr.sort()
            return

        arr.append(mat[row][col])
        traverse(row + 1, col + 1, arr)
        mat[row][col] = arr.pop()

    for i in range(n):
        traverse(i, 0, [])

    for i in range(m):
        traverse(0, i, [])

    return mat


def test_diagonalSort():
    assert diagonalSort(mat=[[3, 3, 1, 1], [2, 2, 1, 2], [1, 1, 1, 2]]) == [
        [1, 1, 1, 1],
        [1, 2, 2, 2],
        [1, 2, 3, 3],
    ]


def generateSpiralMatrix(n: int) -> list[list[int]]:
    result = [[0] * n for _ in range(n)]
    while matrix:
        result += matrix.pop(0)
        matrix = (list(zip(*matrix)))[::-1]
    return result


def test_generateSpiralMatrix():
    assert generateSpiralMatrix(3) == [[1, 2, 3], [8, 9, 4], [7, 6, 5]]


def countCoveredBuildings(n: int, buildings: list[list[int]]) -> int:
    row_map = defaultdict(list)
    col_map = defaultdict(list)

    for building in buildings:
        row_map[building[0]].append(building[1])
        col_map[building[1]].append(building[0])

    for row in row_map:
        row_map[row].sort()
    for col in col_map:
        col_map[col].sort()

    res = 0
    for building in buildings:
        row = building[0]
        col = building[1]

        row_neighbs = row_map[row]
        col_neighbs = col_map[col]

        if (
            0 < row_neighbs.index(col) < len(row_neighbs) - 1
            and 0 < col_neighbs.index(row) < len(col_neighbs) - 1
        ):
            res += 1
    return res


def test_countCoveredBuildings():
    assert (
        countCoveredBuildings(n=5, buildings=[[1, 3], [3, 2], [3, 3], [3, 5], [5, 3]])
        == 1
    )


# https://leetcode.com/problems/count-special-triplets/?envType=daily-question&envId=2025-12-12
def specialTriplets(nums: list[int]) -> int:
    MOD = 10**9 + 7
    freqPrev = defaultdict(int)
    freqNext = defaultdict(int)

    for num in nums:
        freqNext[num] += 1

    res = 0
    for num in nums:
        freqNext[num] -= 1
        t = num * 2
        res += freqPrev[t] * freqNext[t]
        res %= MOD
        freqPrev[num] += 1

    return res


def test_specialTriplets():
    assert specialTriplets(nums=[8, 4, 2, 8, 4]) == 2
    assert specialTriplets(nums=[0, 1, 0, 0]) == 1
    assert specialTriplets(nums=[37, 9, 24, 12, 12, 24, 52, 35]) == 2


def getDescentPeriods(prices: list[int]) -> int:
    n = len(prices)
    res = 1
    cnt = 1
    for i in range(1, n):
        if prices[i - 1] - prices[i] == 1:
            cnt += 1
        else:
            cnt = 1
        res += cnt

    return res


def test_getDescentPeriods():
    assert getDescentPeriods(prices=[8, 6, 7, 7]) == 4
    assert getDescentPeriods(prices=[3, 2, 1, 4]) == 7


# https://leetcode.com/problems/count-square-submatrices-with-all-ones/description/
def countSquares(matrix: list[list[int]]) -> int:
    rows = len(matrix)
    cols = len(matrix[0])

    def solve_from_position(row: int, col: int) -> int:
        n = min(rows - row, cols - col)
        res = 0
        for square_size in range(n):
            if all(
                matrix[r][c]
                for r in range(row, row + square_size + 1)
                for c in range(col, col + square_size + 1)
            ):
                res += 1
            else:
                return res
        return res

    return sum(
        solve_from_position(row, col)
        for row in range(rows)
        for col in range(cols)
        if matrix[row][col]
    )


def countSquares_dfs(matrix: list[list[int]]) -> int:
    rows = len(matrix)
    cols = len(matrix[0])

    @lru_cache
    def dfs(row: int, col: int) -> int:
        if matrix[row][col] == 0:
            return 0
        if row == 0 or col == 0:
            return matrix[row][col]
        return 1 + min(dfs(row - 1, col), dfs(row, col - 1), dfs(row - 1, col - 1))

    return sum(dfs(row, col) for row in range(rows) for col in range(cols))


def test_countSquares():
    assert countSquares_dfs(matrix=[[0, 1, 1, 1], [1, 1, 1, 1], [0, 1, 1, 1]]) == 15


def threeSum(nums: list[int]) -> list[list[int]]:
    nums = sorted(nums)
    result = []
    n = len(nums)
    for i in range(n - 2):
        if i > 0 and nums[i] == nums[i - 1]:
            continue

        target = -nums[i]
        left, right = i + 1, n - 1
        while left < right:
            temp_sum = nums[left] + nums[right]
            if temp_sum == target:
                result.append([nums[i], nums[left], nums[right]])
                left += 1
                right -= 1

                # Skip duplicates for left
                while left < right and nums[left] == nums[left - 1]:
                    left += 1
                # Skip duplicates for right
                while left < right and nums[right] == nums[right + 1]:
                    right -= 1
            elif temp_sum < target:
                left += 1
            else:
                right -= 1

    return result


def test_threeSum():
    assert threeSum([-1, 0, 1, 2, -1, -4]) == [[-1, -1, 2], [-1, 0, 1]]


def findDisappearedNumbers(nums: list[int]) -> list[int]:
    n = len(nums)
    for i in range(n):
        index = abs(nums[i]) - 1
        nums[index] = -abs(nums[index])

    return [i + 1 for i, num in enumerate(nums) if num > 0]


def test_findDisappearedNumbers():
    assert findDisappearedNumbers([2, 3, 4, 5, 6, 7, 8, 1]) == []
    assert findDisappearedNumbers([4, 3, 2, 7, 8, 2, 3, 1]) == [5, 6]


# https://leetcode.com/problems/count-negative-numbers-in-a-sorted-matrix/?envType=daily-question&envId=2025-12-28
def countNegatives(grid: list[list[int]]) -> int:
    rows = len(grid)
    cols = len(grid[0])

    res = 0
    row = rows
    col = 0
    while row > 0:
        while col < cols and grid[row - 1][col] >= 0:
            col += 1
        res += cols - col
        row -= 1

    return res


def test_countNegatives():
    assert countNegatives([[3, 2], [1, 0]]) == 0
    assert (
        countNegatives(
            grid=[[4, 3, 2, -1], [3, 2, 1, -1], [1, 1, -1, -2], [-1, -1, -2, -3]]
        )
        == 8
    )


def minDominoRotations(tops: list[int], bottoms: list[int]) -> int:
    for i in range(1, 7):
        top = bottom = 0
        for t, b in zip(tops, bottoms):
            if t != i and b != i:
                break
            if t != i:
                top += 1
            if b != i:
                bottom += 1
        else:
            return min(top, bottom)

    return -1


def test_minDominoRotations():
    assert minDominoRotations(tops=[2, 1, 2, 4, 2, 2], bottoms=[5, 2, 6, 2, 3, 2]) == 2
