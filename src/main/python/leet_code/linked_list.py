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


if __name__ == "__main__":
    head = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    swap_nodes(head, 2)

    node = head
    while node:
        print(node.val)
        node = node.next
