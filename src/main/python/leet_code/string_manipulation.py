import re
from collections import Counter
from itertools import zip_longest


def isPalindrome(x: int) -> bool:
    if x < 0:
        return False

    temp = x
    rev = 0
    while temp > 0:
        num = temp % 10
        rev = rev * 10 + num
        temp = temp // 10
    return x == rev


def test_isPalindrome():
    assert isPalindrome(123321) == True
    assert isPalindrome(12321) == True
    assert isPalindrome(1241) == False
    assert isPalindrome(121) == True
    assert isPalindrome(-121) == False
    assert isPalindrome(0) == True


def addStrings(num1: str, num2: str) -> str:
    n = max(len(num1), len(num2))
    num1 = num1.zfill(n)[::-1]
    num2 = num2.zfill(n)[::-1]

    result = ""
    carry = 0
    for n1, n2 in zip(num1, num2):
        int1 = int(n1)
        int2 = int(n2)
        carry, res = divmod(int1 + int2 + carry, 10)
        result += str(res)

    if carry != 0:
        result += str(carry)
    return result[::-1]


def test_addStrings():
    assert addStrings(num1="123", num2="10") == "133"


def repeatedSubstringPattern(s: str) -> bool:
    if len(s) <= 1:
        return False
    k = ""
    for i in range(len(s) // 2):
        k = k + s[i]
        m = len(s) // len(k)
        if k * m == s:
            return True
    return False


def test_repeatedSubstringPattern():
    assert repeatedSubstringPattern(s="aba") == False
    assert repeatedSubstringPattern(s="abcabcabc") == True
    assert repeatedSubstringPattern(s="abab") == True
    assert repeatedSubstringPattern(s="abaaabaaabaa") == True
    assert repeatedSubstringPattern(s="blablab") == False


def numSub(s: str) -> int:
    return sum(
        map(
            lambda n: ((n + 1) * n // 2) % 1000000007,
            (len(e) for e in s.split("0")),
        )
    )


def test_numSub():
    assert numSub(s="1111") == 10
    assert numSub(s="0110") == 3
    assert numSub(s="0000") == 0
    assert numSub(s="01100111") == 9
    assert numSub(s="101") == 2


def commonChars(words: list[str]) -> list[str]:
    min_freq = Counter(words[0])
    for word in words:
        min_freq &= Counter(word)
    return list(min_freq.elements())


def test_commonChars():
    assert commonChars(words=["bellla", "label", "roller"]) == ["e", "l", "l"]


def mostCommonWord(paragraph: str, banned: list[str]) -> str:
    pattern = re.compile(r"[!?',;.]")
    words = pattern.sub("", paragraph).lower().split(" ")
    counter = Counter(words)
    for word in banned:
        counter.pop(word, None)
    return counter.most_common(1)[0][0]


def test_mostCommonWord():
    assert (
        mostCommonWord(
            paragraph="Bob hit a ball, the hit BALL flew far after it was hit.",
            banned=["hit"],
        )
        == "ball"
    )


# https://leetcode.com/problems/roman-to-integer/description/
def romanToInt(s: str) -> int:
    roman_to_int = {"I": 1, "V": 5, "X": 10, "L": 50, "C": 100, "D": 500, "M": 1000}

    n = len(s)
    result = 0
    for i in range(n):
        value = roman_to_int[s[i]]
        if i + 1 < n and value < roman_to_int[s[i + 1]]:
            result -= value
        else:
            result += value
    return result


def test_romanToInt():
    assert romanToInt(s="MCMIV") == 1904
    assert romanToInt(s="LVIII") == 58


def validateCoupons(
    code: list[str], businessLine: list[str], isActive: list[bool]
) -> list[str]:

    def is_valid_code(code: str) -> bool:
        if not code:
            return False

        for ch in code:
            i = ord(ch)
            if not (
                ord("a") <= i <= ord("z")
                or ord("A") <= i <= ord("Z")
                or ord("0") <= i <= ord("9")
                or i == ord("_")
            ):
                return False
        return True

    valid_lines = ["electronics", "grocery", "pharmacy", "restaurant"]

    return list(
        map(
            lambda x: x[0],
            sorted(
                (
                    (c, valid_lines.index(line))
                    for c, line, active in zip(code, businessLine, isActive)
                    if active and line in valid_lines and is_valid_code(c)
                ),
                key=lambda x: (x[1], x[0]),
            ),
        )
    )


def test_validateCoupons():
    assert validateCoupons(
        code=["GROCERY15", "ELECTRONICS_50", "DISCOUNT10"],
        businessLine=["grocery", "electronics", "invalid"],
        isActive=[False, True, True],
    ) == ["ELECTRONICS_50"]


def heightChecker(heights: list[int]) -> int:
    expected = sorted(heights)
    return [i for i, (e, h) in enumerate(zip(expected, heights)) if e != h]


def compareVersion(version1: str, version2: str) -> int:

    def compare(i1: int, i2: int) -> int:
        if i1 < i2:
            return -1
        if i1 > i2:
            return 1
        return 0

    def map_version(v: str) -> int:
        res = 0
        for ch in v:
            res *= 10
            res += int(ch)
        return res

    def map_versions(versions: tuple[str, str]) -> int:
        v1, v2 = versions
        return compare(map_version(v1), map_version(v2))

    return next(
        filter(
            None,
            map(
                map_versions,
                zip_longest(version1.split("."), version2.split("."), fillvalue="0"),
            ),
        ),
        0,
    )


def test_compareVersion():
    assert compareVersion(version1="1.0.1", version2="1") == 1
