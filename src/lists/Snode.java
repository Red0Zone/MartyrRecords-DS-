package lists;

public class Snode<T extends Comparable<T>> {
	Snode next;
	T element;

	public Snode(T element) {
		this.element = element;
	}

	public Snode getNext() {
		return next;
	}

	public void setNext(Snode next) {
		this.next = next;
	}

	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}

//	public int compareTo(T node) {
//		if (((Snode) node).getElement() instanceof Martyrs)
//			return ((Martyrs) this.getElement()).getDateOfDeath().compareTo(((Martyrs) ((Snode)node).getElement()).getDateOfDeath());
//		return -1;
//	}

}
