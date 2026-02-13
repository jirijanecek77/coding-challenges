import math
from typing import Optional


class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

    def __str__(self):
        return str(self.val)


class DLListNode:
    def __init__(self, val=0, prev=None, next=None):
        self.val = val
        self.next = next
        self.prev = prev


def print_list(head: Optional[ListNode]) -> None:
    res = []
    while head:
        res.append(head.val)
        head = head.next

    print(res, end="\n")


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


# https://leetcode.com/problems/swap-nodes-in-pairs/description/
def swapPairs(head: Optional[ListNode]) -> Optional[ListNode]:
    prev_node = None
    curr_node = head

    while curr_node and curr_node.next:
        next_node = curr_node.next
        curr_node.next = next_node.next
        next_node.next = curr_node

        if not prev_node:
            head = next_node
        else:
            prev_node.next = next_node
        prev_node = curr_node
        curr_node = curr_node.next

    return head


def reverse_list_rec(head: Optional[ListNode]) -> Optional[ListNode]:
    # reverse list using recursion, go to the before last node and in postorder process the reverted list
    # ALWAYS return the last node in the recursion, and connect the current node to the end of the reverted list
    if not head or not head.next:
        return head

    new_head = reverse_list_rec(head.next)
    head.next.next = head
    head.next = None
    return new_head


def reverse_list(head: Optional[ListNode]) -> Optional[ListNode]:
    prev = None
    while head:
        next_node = head.next
        head.next = prev
        prev = head
        head = next_node
    return prev


# https://leetcode.com/problems/reorder-list/description/
def reorder_list(head: ListNode) -> ListNode:
    # Reverse list using recursion, go to the before last node and in postorder process the reverted list.
    # In this task only second half has to be reverted
    # always disconnect the last node, or use dummy if it could be inserted before the first one

    def reorder(node: ListNode, tail: Optional[ListNode]) -> Optional[ListNode]:
        if not tail or not tail.next:
            return node

        last = reorder(node, tail.next)
        res = last.next
        last.next = tail.next
        tail.next.next = res
        tail.next = None

        return res

    tmp = head
    half = head
    while tmp and tmp.next:
        tmp = tmp.next.next
        half = half.next

    reorder(head, half)
    return head


# https://leetcode.com/problems/linked-list-cycle-ii/description/
def detectCycle(head: Optional[ListNode]) -> Optional[ListNode]:
    slow = head
    fast = head

    # Move the slow pointer one step and the fast pointer two steps at a time through the linked list,
    # until they either meet or the fast pointer reaches the end of the list.
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next
        if slow == fast:
            # If the pointers meet, there is a cycle in the linked list.
            # Reset the slow pointer to the head of the linked list, and move both pointers one step at a time
            # until they meet again. The node where they meet is the starting point of the cycle.
            slow = head
            while slow != fast:
                slow = slow.next
                fast = fast.next
            return slow

    # If the fast pointer reaches the end of the list without meeting the slow pointer,
    # there is no cycle in the linked list. Return None.
    return None


# https://leetcode.com/problems/merge-two-sorted-lists/
def mergeTwoLists(
    list1: Optional[ListNode], list2: Optional[ListNode]
) -> Optional[ListNode]:
    head = None
    last_node = None
    while list1 or list2:
        if not list1:
            node = list2
            list2 = list2.next
        elif not list2:
            node = list1
            list1 = list1.next
        elif list1.val < list2.val:
            node = list1
            list1 = list1.next
        else:
            node = list2
            list2 = list2.next

        if not head:
            head = node
            last_node = head
        else:
            last_node.next = node
            last_node = last_node.next

    return head


# https://leetcode.com/problems/intersection-of-two-linked-lists/
def getIntersectionNode(headA: ListNode, headB: ListNode) -> Optional[ListNode]:
    seen = set()
    node = headA
    while node:
        seen.add(node)
        node = node.next

    node = headB
    while node:
        if node in seen:
            return node
        node = node.next

    return None


# https://leetcode.com/problems/remove-nth-node-from-end-of-list/
def removeNthFromEnd(head: Optional[ListNode], n: int) -> Optional[ListNode]:
    # Input: head = [1,2,3,4,5], n = 2
    # Output: [1,2,3,5]
    # go to the nth node from the beginning
    # start the second pointer from the beginning and move both until the first one reaches the end, then remove the node pointed by the second pointer
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


# https://leetcode.com/problems/add-two-numbers/description/
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


# https://leetcode.com/problems/minimum-pair-removal-to-sort-array-i/description/
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


if __name__ == "__main__":
    l1 = ListNode(1, ListNode(2, ListNode(5, ListNode(6, ListNode(6)))))
    node = reverse_list(l1)
    print_list(node)


#
# def test_minimumPairRemoval():
#     assert minimumPairRemoval(nums=[2, 2, -1, 3, -2, 2, 1, 1, 1, 0, -1]) == 9
#     assert minimumPairRemoval(nums=[5, 2, 3, 1]) == 2
#     assert minimumPairRemoval(nums=[2, 1, 3, 2]) == 2
