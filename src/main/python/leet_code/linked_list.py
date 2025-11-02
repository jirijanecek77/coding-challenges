# Definition for singly-linked list.
from typing import Optional


class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next


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

    return dummy_node.next


if __name__ == "__main__":
    head = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    removeNthFromEnd(head, 2)

    node = head
    while node:
        print(node.val)
        node = node.next
