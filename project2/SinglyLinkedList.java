/*-----------------------------------------------------------------------------
 *  Author:        Leesper
 *  Written:       05/13/2014
 *  Compilation:   javac SinglyLinkedList.java
 *  Execution:     java SinglyLinkedList
 *  My solution to Interview Question 2.1, 2.2, 2.3 of "Cracking The Coding Interview"
 *---------------------------------------------------------------------------*/

import java.util.NoSuchElementException;
import java.security.InvalidParameterException;
import java.util.Comparator;

public class SinglyLinkedList<Item>
{
	private class Node
	{
		private Item item;
		private Node next;
	}
	
	// dummy, pre->next is the first node
	private Node 	pre;
	// list size
	private int		N;
	
	public SinglyLinkedList()
	{
		pre = new Node();
		pre.next = null;
	}

	public void addFirst(Item item)
	{
		Node oldfirst = pre.next;
		Node node = new Node();
		node.item = item;
		pre.next = node;
		node.next = oldfirst;
		++N;
	}
	
	public Item removeFirst()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}

		Node newfirst = pre.next.next;
		Item item = pre.next.item;
		pre.next = newfirst;
		--N;
		return item;
	}
	
	public int size()
	{
		return N;
	}
	
	public boolean isEmpty()
	{
		return 0 == N;
	}
	
	/*
	 * remove duplicates node in linked list
	 */
	public void removeDuplicates() 
	{ 
		mergeSort();
		Node curr = pre.next;
		while (curr != null && curr.next != null)
		{
			if (curr.item.equals(curr.next.item))
			{
				curr.next = curr.next.next;
				--N;
			}
			curr = curr.next;
		}
		
		curr = pre.next;
		while (curr != null)
		{
			StdOut.println(curr.item);
			curr = curr.next;
		}
	}
	
	private void mergeSort() { pre.next = mergeSortRecur(pre.next); }
	
	private Node mergeSortRecur(Node first)
	{
		if (first == null)		return null;
		if (first.next == null)	return first;
		
		Node middle = findMiddle(first);
		Node lPart = first;
		Node rPart = middle.next;
		middle.next = null;
		
		lPart = mergeSortRecur(lPart);
		rPart = mergeSortRecur(rPart);
		return merge(lPart, rPart);
	}
	
	private Node merge(Node left, Node right)
	{
		if (left == null)	return right;
		if (right == null)	return left;
		Node merged = null;
		if (left.item.toString().compareTo(right.item.toString()) < 0)
		{
			merged = left;
			merged.next = merge(left.next, right);
		}
		else
		{
			merged = right;
			merged.next = merge(left, right.next);
		}
		return merged;
	}
	
	private Node findMiddle(Node first)
	{
		// fast-slow pointer pattern
		Node slow = first;
		Node fast = first;
		Node before = null;
		while (fast != null && fast.next != null)
		{
			before = slow;
			slow = slow.next;
			fast = fast.next.next;
		}
		// so that we can locate the real middle
		return before;
	}
	
	private Node findLast(Node first)
	{
		Node curr = first;
		Node last = null;
		while (curr != null)	
		{
			last = curr;
			curr = curr.next;
		}
		return last;
	}
	
	/*
	 * for 2.2 of Cracking The Coding Interview
	 */
	public Item kth2last(int k)
	{
		if (k < 1 || k > size())
			throw new InvalidParameterException();
		Node curr = pre.next;
		Node ahead = pre.next;
		for (int s = 0; s < k - 1; ++s)
			ahead = ahead.next;
		
		while (ahead.next != null)
		{
			ahead = ahead.next;
			curr = curr.next;
		}
			
		return curr.item;
	}
	
	/*
	 * for 2.3 of Cracking The Coding Interview
	 */
	public void remove(Node node)
	{
		if (node == null)
			throw new InvalidParameterException();
		Node prev = null;
		while (node.next != null)
		{
			node.item = node.next.item;
			prev = node;
			node = node.next;
		}
		prev.next = node.next;
		--N;
	}
	
	public void testRemove()
	{
		Node curr = pre.next;
		while (curr.item.toString().compareTo("21") != 0)
			curr = curr.next;
		
		remove(curr);
		
		curr = pre.next;
		while (curr != null)
		{
			StdOut.println(curr.item);
			curr = curr.next;
		}
	}
	
	public static void main(String[] args)
	{
		
		SinglyLinkedList<Integer> sill = new SinglyLinkedList<Integer>();
		sill.addFirst(20);
		sill.addFirst(5);
		sill.addFirst(3);
		sill.addFirst(9);
		sill.addFirst(21);
		sill.addFirst(11);
		
		StdOut.printf("The 3rd item to the last: %d\n", sill.kth2last(3));
		
	}
}