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
