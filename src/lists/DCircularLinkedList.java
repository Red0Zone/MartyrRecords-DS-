package lists;

public class DCircularLinkedList<T extends Comparable<T>> {
	private DNode<T> head;

	public DNode<T> getHead() {
		return head;
	}

	public DCircularLinkedList() {
		DNode<T> dummy = new DNode<>(null);
		head = dummy;
		head.setNext(head);
		head.setPrevious(head.getNext());
	}

	public void insertAtHead(T data) {
		DNode<T> newNode = new DNode<>(data);
		if (head.getNext() != head) {
			newNode.setNext(head.getNext());
			head.getNext().setPrevious(newNode); // head.next.prev=nono
		}
		head.setNext(newNode);
		newNode.setPrevious(head);
		newNode.setNext(head);
		if (newNode.getNext() == head)
			head.setPrevious(newNode);

	}

	public DNode find(T data) {
		DNode curr = head.getNext();

		if (curr == head)
			return null;
		while (curr != head && curr.getData().compareTo(data) < 0)
			curr = curr.getNext();

		if (curr != head && curr.getData().compareTo(data) == 0) {
			return curr;
		}

		return null;
	}

	public boolean isEmpty() {
		if (head.getNext() != head)
			return true;
		return false;
	}

	public void insertAtLast(T data) {
		DNode<T> newNode = new DNode<>(data);
		DNode<T> current = head.getNext();
		while (current.getNext() != head)
			current = current.getNext();
		current.setNext(newNode);
		newNode.setNext(head);
		head.setPrevious(newNode);
	}

	public DNode insertSorted(T data) {
		DNode<T> newNode = new DNode<>(data);
		DNode<T> current = head;

		while (current.getNext() != head && current.getNext().getData().compareTo(data) < 0)
			current = current.getNext();

		if (current.getNext() == head) {
			newNode.setPrevious(current);
			current.setNext(newNode);
			newNode.setNext(head);
			head.setPrevious(newNode);
		} else {
			newNode.setNext(current.getNext());
			newNode.setPrevious(current);
			current.getNext().setPrevious(newNode);
			current.setNext(newNode);
		}
		return newNode;

	}

//	public T delete(T data) {
//		DNode<T> current = head;
//
//		while (current.getNext() != null && current.getNext().getData().compareTo(data) <= 0)
//			current = current.getNext();
//		if (current != head && current.getNext() != null && current.getNext().getData().compareTo(data) < 0) {
//			T temp = current.getData();
//			if (current.getNext() == null)
//				current.getPrevious().setNext(null);
//			else {
//				current.getNext().setPrevious(current.getPrevious());
//				current.getPrevious().setNext(current.getNext());
//			}
//
//		}
//		return null;
//	}

	public boolean delete(T data) {
		DNode curr = head.getNext();

		if (head.getNext() == head)
			return false;
		else if (head.getNext().getNext() != head && head.getNext().getData().equals(data)) {
			head.getNext().getNext().setPrevious(head);

			head.setNext(head.getNext().getNext());
			return true;
		} else if (head.getNext().getNext() == head) {
			head.setNext(head);
			head.setPrevious(head);
			return true;
		} else {
			while (curr.getNext() != head && curr.getNext().getData().compareTo(data) < 0) {
				curr = curr.getNext();
			}
			if (curr.getNext() != head && curr.getNext().getData().compareTo(data) == 0) {
				if (curr.getNext().getNext() != head) {
					curr.getNext().getNext().setPrevious(curr);
					curr.setNext(curr.getNext().getNext());
					return true;
				}

				curr.getNext().setPrevious(null);
				curr.setNext(head);
				head.setPrevious(curr);
				return true;
			}

		}
		return false;
	}

	public void traverase() {
		System.out.print("Head -> ");
		DNode<T> current = head.getNext();
		while (current != head) {
			System.out.print(current + "->");
			current = current.getNext();
		}
		System.out.print("Head\n");
	}

	public void traveraseBackwards() {
		System.out.print("Head -> ");
		DNode<T> current = head.getPrevious();
		while (current != head) {
			System.out.print(current + "->");
			current = current.getPrevious();
		}
		System.out.print("Head\n");
	}

	public static void main(String[] args) {
		DCircularLinkedList<Integer> list = new DCircularLinkedList<Integer>();
		list.insertSorted(5);
		list.insertSorted(10);
		list.insertSorted(3);
		list.insertSorted(200);
		list.insertSorted(70);
		list.insertSorted(-9);
		list.insertSorted(8);
		list.insertSorted(20);
		list.traverase();
		list.traveraseBackwards();
//		list.traverase();
		list.delete(20);
		list.traverase();
		list.traveraseBackwards();
	}
}