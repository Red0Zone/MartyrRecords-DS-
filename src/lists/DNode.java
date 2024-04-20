package lists;
public class DNode<T extends Comparable<T>> {
	private T data;
	private DNode<T> next, previous;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public DNode<T> getNext() {
		return next;
	}

	public void setNext(DNode<T> next) {
		this.next = next;
	}

	public DNode<T> getPrevious() {
		return previous;
	}

	public void setPrevious(DNode<T> previous) {
		this.previous = previous;
	}

	public DNode(T data) {
		super();
		this.data = data;
		this.next = next;
		this.previous = previous;
	}

	public DNode() {
	}

	@Override
	public String toString() {
		return data+" " ;
	}

}