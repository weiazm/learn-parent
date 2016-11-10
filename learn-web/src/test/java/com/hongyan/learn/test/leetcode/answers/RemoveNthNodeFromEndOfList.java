package com.hongyan.learn.test.leetcode.answers;

/*
 * Given a linked list, remove the nth node from the end of list and return its head.

 * For example,

 *    Given linked list: 1->2->3->4->5, and n = 2.

 *    After removing the second node from the end, the linked list becomes 1->2->3->5.
 * Note:
 * Given n will always be valid.
 * Try to do this in one pass.
 */
public class RemoveNthNodeFromEndOfList {

    class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
          next = null;
      }
  }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur1 = dummy, cur2 = dummy;
        for(int i = 0; cur2.next != null; i++, cur2 = cur2.next) {
            if(i >= n)
                cur1 = cur1.next;
        }
        cur1.next = cur1.next.next;
        return dummy.next;
    }
}
