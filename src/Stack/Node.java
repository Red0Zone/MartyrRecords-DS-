package Stack;


import lists.Snode;

public class Node<T extends Comparable<T>> {
	Node next;
	T data;

	public Node(T data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public T getElement() {
		return data;
	}

	public void setElement(T data) {
		this.data = data;
	}

}
