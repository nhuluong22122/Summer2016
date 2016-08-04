package datastructure;

import java.util.LinkedList;

public class Queue<T>{
	LinkedList<T> list;
	
	/**
	 * Constructor
	 */
	public Queue(){
		list = new LinkedList<T>();
	}
	//O(n)
	public void add(T n){
		list.addLast(n);
	}
	//O(1)
	public T remove(){
		return list.removeFirst();
	}
	//O(1)
	public T peek(){
		return list.peekFirst();
	}
	//Because size() of arraylist has O(1)
	public boolean isEmpty(){
		return list.isEmpty(); // or return list.size() == 0;
	}
	//O(1)
	public int size(){
		return list.size();
	}
	
	//O(n) because we need to loop through the for loop
	public void print(){
		for(int i = 0; i < list.size(); i++){
			 System.out.print(list.get(i) + " ");
		}
	}
}