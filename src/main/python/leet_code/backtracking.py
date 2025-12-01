def letter_combinations_of_phone_number(digits: str) -> list[str]:
    res = []

    KEYBOARD = {
        "2": "abc",
        "3": "def",
        "4": "ghi",
        "5": "jkl",
        "6": "mno",
        "7": "pqrs",
        "8": "tuv",
        "9": "wxyz",
    }

    def dfs(start_index: int, path: list[str]) -> None:
        if start_index == len(digits):
            res.append("".join(path))
            return

        for letter in KEYBOARD[digits[start_index]]:
            path.append(letter)
            dfs(start_index + 1, path)
            path.pop()

    dfs(0, [])
    return res


def test_letter_combinations_of_phone_number():
    assert letter_combinations_of_phone_number("29") == [
        "aw",
        "ax",
        "ay",
        "az",
        "bw",
        "bx",
        "by",
        "bz",
        "cw",
        "cx",
        "cy",
        "cz",
    ]


def partition_palindromes(s: str) -> list[list[str]]:
    is_palindrome = lambda e: e == e[::-1]
    n = len(s)
    result = []

    def dfs(start_index: int, path: list[str]):
        if start_index == n:
            result.append(path)
            return
        for end_index in range(start_index + 1, n + 1):
            prefix = s[start_index:end_index]
            if is_palindrome(prefix):
                dfs(end_index, path + [prefix])

    dfs(0, [])
    return result


def test_partition_palindromes():
    assert partition_palindromes("aab") == [["a", "a", "b"], ["aa", "b"]]


def minSubarray(nums: list[int], p: int) -> int:
    target = sum(num for num in nums) % p
    if target == 0:
        return 0

    window_sum = 0
    length = len(nums) + 1
    left = 0
    for right in range(len(nums)):
        window_sum += nums[right]
        while window_sum >= target:
            length = min(length, right - left + 1)
            window_sum -= nums[left]
            left += 1
    if length >= len(nums):
        return -1
    return length


def test_minSubarray():
    assert minSubarray(nums=[6, 3, 5, 2], p=9) == 2
    assert minSubarray(nums=[3, 1, 4, 2], p=6) == 1
