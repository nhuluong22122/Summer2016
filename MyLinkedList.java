package datastructure;

public class MyLinkedList  {
	Node head; // the head of the train

	// O(n)
	
	public void add(Integer i) {
		if (head == null) {
			head = new Node();//set a new Node for head
			head.setValue(i); //Set value
		} else {
			Node current = head;
			while (current.getNext() != null) {
				current = current.getNext();
			}
			Node newNode = new Node();
			newNode.setValue(i);
			current.setNext(newNode);//Change the pointer
		}

	}

	// O(n)

	public Integer remove(int index) {
		if (index == 0) {//If remove the head
			Integer removed = head.getValue();//Get the first element
			head = head.getNext();//Set head to the next element
			return removed;//Return that removed value
		}
		//Else
		Node current = head;
		int count = 0;
		while (count < index - 1) { // go to the index 
			current = current.getNext(); // Proceed to that index
		}

		Integer removed = current.getNext().getValue();//Get the removed item
		current.setNext(current.getNext().getNext());//Set the current to the next 2 items
		return removed;
	}

	// O(n)

	public Integer get(int index) {
		Node current = head;
		int count = 0;
		while (count < index) {
			current = current.getNext();
			count++;
		}
		return current.getValue();
	}

	// O(n)

	public int size() {
		Node current = head;
		int count = 0;
		while (current != null) {
			count++;
			current = current.getNext();
		}
		return count;
	}

	// O(n)
	
	public boolean contains(Integer i) {
		Node current = head;
		while (current != null) {
			if (current.getValue() == i) {
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	// O(n)

	public void insert(Integer i, int index) {
		if (index == 0) {
			Node newNode = new Node();
			newNode.setValue(i);
			newNode.setNext(head);
			head = newNode;
			return;
		}
		Node current = head;
		int count = 0;
		while (count < index - 1) {
			current = current.getNext();
			count++;
		}

		Node newNode = new Node();
		newNode.setValue(i);

		newNode.setNext(current.getNext());
		current.setNext(newNode);
	}

	// O(n)
	public String toString() {
		String s = "LinkedList:";

		// Node current = head;
		// while (current != null) {
		// s = s + " " + current.value;
		// current = current.next;
		// }
		for (Node current = head; current != null; current = current.getNext()) {
			s = s + " " + current.getValue();
		}

		return s;
	}
}
