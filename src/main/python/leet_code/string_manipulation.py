import re
from collections import Counter


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
