def fractionToDecimal(numerator: int, denominator: int) -> str:
    sign = "-" if numerator * denominator < 0 else ""
    numerator = abs(numerator)
    num = numerator // denominator
    rem = numerator % denominator
    res = [str(num)]
    seen = [rem]
    numerator = rem

    if rem == 0:
        return sign + "".join(res)

    res.append(".")

    while rem > 0:
        numerator *= 10
        if numerator < denominator:
            res.append("0")
            continue

        num = numerator // denominator
        res.append(str(num))
        rem = numerator % denominator
        if rem in seen:
            index = seen.index(rem) + 2

            return sign + "".join(res[:index]) + "(" + "".join(res[index:]) + ")"
        seen.append(rem)
        numerator = rem

    return sign + "".join(res)


def test_fractionToDecimal():
    assert fractionToDecimal(numerator=-50, denominator=8) == "-6.25"
    assert fractionToDecimal(numerator=22, denominator=7) == "3.(142857)"
    assert fractionToDecimal(numerator=1, denominator=6) == "0.1(6)"
    assert fractionToDecimal(numerator=4, denominator=333) == "0.(012)"
    assert fractionToDecimal(1, 2) == "0.5"
    assert fractionToDecimal(2, 1) == "2"
