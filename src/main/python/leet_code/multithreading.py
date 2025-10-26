import threading
from functools import reduce
from typing import Optional


class Bank:
    def __init__(self, balance: list[int]):
        self.accounts = balance.copy()
        # Use a list of locks for fine-grained locking (one per account)
        self._locks = [threading.Lock() for _ in range(len(self.accounts))]

    def _is_valid_account(self, account: int) -> bool:
        return 0 <= account < len(self.accounts)

    def transfer(self, account1: int, account2: int, money: int) -> bool:
        idx1, idx2 = account1 - 1, account2 - 1
        if not self._is_valid_account(idx1):
            return False
        if not self._is_valid_account(idx2):
            return False

        if idx1 == idx2:
            return True  # Transfer to same account is a no-op
        # Lock in consistent order to prevent deadlocks
        first_lock, second_lock = (idx1, idx2) if idx1 < idx2 else (idx2, idx1)

        with self._locks[first_lock]:
            with self._locks[second_lock]:
                if self.accounts[idx1] < money:
                    return False
                self.accounts[idx1] -= money
                self.accounts[idx2] += money
                return True

    def deposit(self, account: int, money: int) -> bool:
        idx = account - 1
        if not self._is_valid_account(idx):
            return False

        with self._locks[idx]:
            self.accounts[idx] += money
            return True

    def withdraw(self, account: int, money: int) -> bool:
        idx = account - 1
        if not self._is_valid_account(idx):
            return False

        with self._locks[idx]:
            if self.accounts[idx] < money:
                return False
            self.accounts[idx] -= money
            return True


def execute_operations(
    operations: list[str], params: list
) -> tuple[Optional[Bank], list[Optional[bool]]]:
    def _exec_op(
        acc: tuple[Optional[Bank], list[Optional[bool]]], signature: tuple[str, list]
    ) -> tuple[Optional[Bank], list[Optional[bool]]]:
        op, params = signature
        bank, result = acc
        match op:
            case "Bank":
                if bank:
                    return None, result + [False]
                else:
                    return Bank(*params), result + [None]
            case "withdraw":
                return bank, result + ([bank.withdraw(*params) if bank else False])
            case "transfer":
                return bank, result + ([bank.transfer(*params) if bank else False])
            case "deposit":
                return bank, result + ([bank.deposit(*params) if bank else False])
            case _:
                return bank, result + [False]

    return reduce(_exec_op, zip(operations, params), (None, []))


def test_bank():
    result = execute_operations(
        ["Bank", "withdraw", "transfer", "deposit", "transfer", "withdraw"],
        [
            [[10, 100, 20, 50, 30]],
            [3, 10],
            [5, 1, 20],
            [5, 20],
            [3, 4, 15],
            [10, 50],
        ],
    )
    assert result[1] == [None, True, True, True, False, False]
