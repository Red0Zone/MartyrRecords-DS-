package lists;

import java.util.LinkedList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

public class singleLinkedList<T extends Comparable<T>> {

	private Snode<T> dummyHead;
	private Snode<T> head;
	private Snode<T> tail;

	// a constructor where the Dummy head object inistialaized and the head refer to
	// the new DummyNode object;
	public singleLinkedList() {
		dummyHead = new Snode<>(null);
		head = dummyHead;
	}

//	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // //

	public void insertSorted(T data) {
		Snode<T> curr = head.getNext();
		Snode<T> newNode = new Snode<>(data);
		if (head.getNext() == null) {
			head.setNext(newNode);
			tail = newNode;

		} else if (head.getNext().getElement().compareTo(newNode.getElement()) > 0) {
			newNode.setNext(head.getNext());
			head.setNext(newNode);

		} else {
			while (curr.getNext() != null && curr.getNext().getElement().compareTo(newNode.getElement()) < 1) {
				curr = curr.getNext();
			}
			newNode.setNext(curr.getNext());
			curr.setNext(newNode);

		}
		if (tail.getNext() != null) {
			tail = tail.getNext();
		}
	}
//	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // //

	public void insertAtHead(T element) {
		Snode<T> newNode = new Snode<>(element);
		if (head.getNext() != null)
			newNode.setNext(head.getNext());
		head.setNext(newNode);

		if (tail.getNext() != null)
			tail.setNext(tail.getNext());
	}

	public void insortAtTail(T element) {
		Snode<T> newNode = new Snode<>(element);
		tail.setNext(newNode);
		tail = tail.getNext();

	}

	public T search(T data) {
		Snode<T> curr = head.getNext();
		while (curr != null && curr.getElement().compareTo(data) < 1) {
			if (curr.getElement().compareTo(data) == 0)
				return curr.getElement();
			curr = curr.getNext();
		}
		return null;
	}
//	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // //

//	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // //

	public boolean Delete(T data) {

		Snode<T> curr = head.getNext();
		if (head.getNext() != null) {
			if (head.getNext().getElement().compareTo(data) == 0) {
				head.setNext(curr.getNext());
				return true;
			}

			while (curr.getNext() != null && curr.getNext() != tail
					&& curr.getNext().getElement().compareTo(data) < 1) {
				if (curr.getNext().getElement().compareTo(data) == 0) {
					curr.setNext(curr.getNext().getNext());
					return true;
				}
				curr = curr.getNext();

			}
			if (curr.getNext() == tail && curr.getNext().getElement().compareTo(data) == 0) {
				tail = curr;
				curr.setNext(curr.getNext().getNext());
				return true;

			}
		}
		return false;
	}

//	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // // //	// // //

	public void traverase() {
		System.out.print("Head->");
		Snode<T> curr = head.getNext();
		while (curr != null) {
			System.out.println(curr.getElement() + "->");
			curr = curr.getNext();
		}
		System.out.print("null\n");
	}

	public Snode<T> getFirst() {
		return head.next;
	}

	public Snode<T> getLast() {
		return tail;
	}

	public boolean isEmpty() {
		if (getFirst() != null)
			return false;
		return true;
	}

}
