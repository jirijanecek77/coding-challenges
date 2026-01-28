# Definition for singly-linked list.
import math
from typing import Optional


class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


class DLListNode:
    def __init__(self, val=0, prev=None, next=None):
        self.val = val
        self.next = next
        self.prev = prev


# https://leetcode.com/problems/swapping-nodes-in-a-linked-list/
def swap_nodes(head: Optional[ListNode], k: int) -> Optional[ListNode]:
    i = 1
    left_node = None
    right_node = None
    node = head
    while node:
        if i == k:
            left_node = node
            right_node = head
        elif right_node:
            right_node = right_node.next
        i += 1
        node = node.next

    if left_node and right_node:
        left_node.val, right_node.val = right_node.val, left_node.val
    return head


# https://leetcode.com/problems/remove-nth-node-from-end-of-list/
def removeNthFromEnd(head: Optional[ListNode], n: int) -> Optional[ListNode]:
    # Input: head = [1,2,3,4,5], n = 2
    # Output: [1,2,3,5]
    if not head:
        return None

    node = head
    for _ in range(n):
        node = node.next

    target = head
    while node and node.next:
        node = node.next
        target = target.next

    if not node:
        head = head.next
    else:
        target.next = target.next.next

    return head


# https://leetcode.com/problems/delete-nodes-from-linked-list-present-in-array/?envType=daily-question&envId=2025-11-01
def modifiedList(nums: list[int], head: Optional[ListNode]) -> Optional[ListNode]:
    dummy_node = ListNode(0, head)
    last = dummy_node
    node = head
    to_delete = set(nums)
    while node:
        if node.val in to_delete:
            last.next = node.next
        else:
            last = node
        node = last.next

    return


def addTwoNumbers(l1: Optional[ListNode], l2: Optional[ListNode]) -> Optional[ListNode]:
    carry = 0
    head = last_node = None
    while l1 or l2:
        val = carry
        if l1:
            val += l1.val
            l1 = l1.next
        if l2:
            val += l2.val
            l2 = l2.next
        carry, val = divmod(val, 10)
        node = ListNode(val)
        if not head:
            head = last_node = node
        else:
            last_node.next = node
            last_node = last_node.next
    if carry:
        last_node.next = ListNode(carry)
    return head


# https://leetcode.com/problems/odd-even-linked-list/description/
def oddEvenList(head: Optional[ListNode]) -> Optional[ListNode]:
    if not head:
        return None

    evens_head = ListNode()
    evens = evens_head
    node = head
    while node:
        evens.next = node.next
        if node.next:
            node.next = node.next.next
        else:
            break

        node = node.next
        evens = evens.next

    node.next = evens_head.next
    return head


def print_list(node):
    res = []
    while node:
        res.append(node.val)
        node = node.next

    print(res, end="\n")


if __name__ == "__main__":
    l = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    node = oddEvenList(l)
    print_list(node)


def minimumPairRemoval(nums: list[int]) -> int:
    dll = None
    prev = None
    for num in nums:
        node = DLListNode(num, prev=prev)
        if not prev:
            dll = node
        else:
            prev.next = node
        prev = node

    def is_sorted(node):
        while node:
            if node.prev and node.prev.val > node.val:
                return False
            node = node.next
        return True

    res = 0
    while not is_sorted(dll):
        min_val = math.inf
        min_node = dll
        node = dll.next
        while node:
            if (node.val + node.prev.val) < min_val:
                min_node = node
                min_val = node.val + node.prev.val
            node = node.next

        p = min_node.prev.prev
        n = min_node.next
        new_node = DLListNode(min_node.val + min_node.prev.val, prev=p, next=n)
        if p:
            p.next = new_node
        else:
            dll = new_node
        if n:
            n.prev = new_node

        res += 1
    return res


def test_minimumPairRemoval():
    assert minimumPairRemoval(nums=[2, 2, -1, 3, -2, 2, 1, 1, 1, 0, -1]) == 9
    assert minimumPairRemoval(nums=[5, 2, 3, 1]) == 2
    assert minimumPairRemoval(nums=[2, 1, 3, 2]) == 2
