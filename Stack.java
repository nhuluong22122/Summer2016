package datastructure;

import java.util.LinkedList;

public class Stack<T>{//Generic T stands for Type
	LinkedList<T> list; // Create the linkedlist

	public Stack(){
		list = new LinkedList<T>();
	}
	//O(1)
	public void add(T n){ // push 
		list.addFirst(n);
	}
	//O(1)
	public T remove(){ // pop
		return list.removeFirst();
	}
	//O(1)
	public T peek(){
		return list.peekFirst();
	}
	//O(1)
	public boolean isEmpty(){
		return list.isEmpty();
	}
	//O(1)
	public int size(){
		return list.size();
	}
	//O(n)
	public void print(){
		for(int i = 0; i < list.size(); i++){
			 System.out.print(list.get(i) + " ");
		}
	}
}