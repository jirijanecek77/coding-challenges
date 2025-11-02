def is_power_of_four(n: int) -> bool:
    return n > 0 and (n & (n - 1)) == 0 and n % 3 == 1


def test_is_power_of_four():
    assert is_power_of_four(16)
    assert not is_power_of_four(8)
    assert not is_power_of_four(15)
